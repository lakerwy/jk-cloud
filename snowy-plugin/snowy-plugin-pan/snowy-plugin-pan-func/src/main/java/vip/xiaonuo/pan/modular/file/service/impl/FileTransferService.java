package vip.xiaonuo.pan.modular.file.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.param.PreviewParam;
import vip.xiaonuo.pan.modular.file.param.UploadFileParam;
import vip.xiaonuo.pan.modular.file.service.IFileTransferService;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.vo.UploadFileVo;
import vip.xiaonuo.pan.modular.fileCenter.component.FileDealComp;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanImage;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanPictureFile;
import vip.xiaonuo.pan.modular.fileCenter.enums.StorageTypeEnum;
import vip.xiaonuo.pan.modular.fileCenter.enums.UploadFileStatusEnum;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.Deleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.domain.DeleteFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.Previewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.domain.PreviewFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.Uploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.domain.UploadFile;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.domain.UploadFileResult;
import vip.xiaonuo.pan.modular.fileCenter.service.PanImageService;
import vip.xiaonuo.pan.modular.fileCenter.service.PanPictureFileService;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.MimeUtils;
import vip.xiaonuo.pan.modular.uploadTask.entity.PanUploadTask;
import vip.xiaonuo.pan.modular.uploadTask.entity.PanUploadTaskDetail;
import vip.xiaonuo.pan.modular.uploadTask.service.PanUploadTaskDetailService;
import vip.xiaonuo.pan.modular.uploadTask.service.PanUploadTaskService;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.enums.UserFileEnum;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static vip.xiaonuo.pan.modular.fileCenter.util.CommonFile.separator;
import static vip.xiaonuo.pan.modular.userFile.service.impl.UserFileServiceImpl.IS_DIR;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/11 9:18
 */
@Slf4j
@Service
public class FileTransferService implements IFileTransferService {

    /**顶级父目录id*/
    private final static  String ROOT_FOLD_ID = "0";

    private final static  String ROOT_FOLD_PATH = "/";

    @Resource
    PanFileService panFileService;

    @Resource
    UserFileService userFileService;

    @Resource
    FileDealComp fileDealComp;

    @Resource
    PanUploadTaskDetailService panUploadTaskDetailService;

    @Resource
    PanUploadTaskService panUploadTaskService;

    @Resource
    FileFactory fileFactory;

    @Resource
    PanImageService panImageService;

    @Resource
    PanPictureFileService panPictureFileService;


    public static Executor exec = Executors.newFixedThreadPool(20);


    @Override
    public UploadFileVo uploadFileSpeed(UploadFileParam uploadFileParam) {
        UploadFileVo uploadFileVo = new UploadFileVo();

        LambdaQueryWrapper<PanFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PanFile::getIdentifier,uploadFileParam.getIdentifier());
        List<PanFile> list = panFileService.list(lambdaQueryWrapper);
        String filePath = uploadFileParam.getFilePath();
        if(StrUtil.isNotEmpty(filePath)){
            String parentId = userFileService.getParentIdByPath(uploadFileParam.getFilePath());
            if(StrUtil.isNotEmpty(parentId)){
                uploadFileParam.setParentId(parentId);
            }
        }
        String relativePath = uploadFileParam.getRelativePath();
        CommonFile commonFile = null;
        if (relativePath.contains("/")) {
            commonFile = new CommonFile(filePath, relativePath, false);
           // uploadFileParam.setParentId(buildFold(commonFile.getPath().substring(0,commonFile.getPath().lastIndexOf(ROOT_FOLD_PATH)),ROOT_FOLD_PATH,ROOT_FOLD_ID));
        } else {
            commonFile = new CommonFile(filePath, uploadFileParam.getFilename(), false);
        }

        String uid = StpLoginUserUtil.getLoginUser().getId();
        if (list != null && !list.isEmpty()) {
            PanFile file = list.get(0);
            UserFile userFile = new UserFile(commonFile, uid, file.getId());
//            commonFile.setParentId(uploadFileParam.getParentId());
            userFile.setParentId(uploadFileParam.getParentId());
//            //根据filePath
//            String parentId = userFileService.getParentIdByPath(uploadFileParam.getFilePath());
//            if(StrUtil.isNotEmpty(parentId)){
//
//            }
            //userFile.setParentId(uploadFileParam.getParentId());
            userFile.setFileSize(file.getFileSize());
            userFileService.save(userFile);

            if (relativePath.contains("/")) {
                CommonFile finalCommonFile = commonFile;
                exec.execute(()->{
                    fileDealComp.restoreParentFilePath(finalCommonFile, uid);
                });

            }

            uploadFileVo.setSkipUpload(true);
        } else {
            uploadFileVo.setSkipUpload(false);

            List<Integer> uploaded = panUploadTaskDetailService.selectUploadedChunkNumList(uploadFileParam.getIdentifier());
            if (uploaded != null && !uploaded.isEmpty()) {
                uploadFileVo.setUploaded(uploaded);
            } else {

                LambdaQueryWrapper<PanUploadTask> taskLambdaQueryWrapper= new LambdaQueryWrapper<>();
                taskLambdaQueryWrapper.eq(PanUploadTask::getIdentifier,uploadFileParam.getIdentifier());
                List<PanUploadTask> rslist = panUploadTaskService.list(taskLambdaQueryWrapper);

                if (rslist == null || rslist.isEmpty()) {
                    PanUploadTask uploadTask = new PanUploadTask();
                    uploadTask.setIdentifier(uploadFileParam.getIdentifier());
                    uploadTask.setUploadTime(DateUtil.date());
                    uploadTask.setUploadStatus(UploadFileStatusEnum.UNCOMPLATE.getCode());
                    uploadTask.setFileName(commonFile.getNameNotExtend());
                    uploadTask.setFilePath(commonFile.getParent());
                    uploadTask.setExtendName(commonFile.getExtendName());
                    uploadTask.setUserId(StpLoginUserUtil.getLoginUser().getId());
                    panUploadTaskService.save(uploadTask);
                }
            }

        }
        return uploadFileVo;
    }

    @Override
    @Transactional
    public void uploadFile(HttpServletRequest request, UploadFileParam uploadFileParam) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setChunkNumber(uploadFileParam.getChunkNumber());
        uploadFile.setChunkSize(uploadFileParam.getChunkSize());
        uploadFile.setTotalChunks(uploadFileParam.getTotalChunks());
        uploadFile.setIdentifier(uploadFileParam.getIdentifier());
        uploadFile.setTotalSize(uploadFileParam.getTotalSize());
        uploadFile.setCurrentChunkSize(uploadFileParam.getCurrentChunkSize());

        Uploader uploader = fileFactory.getUploader();
        if (uploader == null) {
            log.error("上传失败，请检查storageType是否配置正确");
            throw new CommonException("上传失败");
        }
        List<UploadFileResult> uploadFileResultList;
        try {
            uploadFileResultList = uploader.upload(request, uploadFile);
        } catch (Exception e) {
            log.error("上传失败，请检查连接配置是否正确");
            throw new CommonException("上传失败", e);
        }
        for (UploadFileResult uploadFileResult : uploadFileResultList) {
            String relativePath = uploadFileParam.getRelativePath();
            //  1231/33333/新建 DOC 文档.doc     /马义可的个人文件夹/2222222/231.doc              commonFile.getPath()
            CommonFile commonFile = null;
            if (relativePath.contains("/")) {
                //说明是文件夹上传
                commonFile = new CommonFile(uploadFileParam.getFilePath(), relativePath, false);
                //uploadFileParam.setParentId(buildFold(commonFile.getPath().substring(0,commonFile.getPath().lastIndexOf(ROOT_FOLD_PATH)),ROOT_FOLD_PATH,ROOT_FOLD_ID));
            } else {
                commonFile = new CommonFile(uploadFileParam.getFilePath(), uploadFileParam.getFilename(), false);
            }
            if (UploadFileStatusEnum.SUCCESS.equals(uploadFileResult.getStatus())) {
                PanFile panFile = new PanFile();
                panFile.setFileUrl(uploadFileResult.getFileUrl());
                panFile.setFileSize(uploadFileResult.getFileSize());
                panFile.setFileStatus(1);
                panFile.setStorageType(uploadFileResult.getStorageType().getCode());
                panFile.setIdentifier(uploadFileResult.getIdentifier());

                try {
                    panFileService.save(panFile);
                } catch (Exception e) {
                    log.warn("identifier Duplicate: {}", panFile.getIdentifier());
                    panFile = panFileService.getOne(new QueryWrapper<PanFile>().lambda().eq(PanFile::getIdentifier, panFile.getIdentifier()));
                }

                UserFile userFile = new UserFile(commonFile, StpLoginUserUtil.getLoginUser().getId(), panFile.getId());
                //根据filePath
                String parentId = userFileService.getParentIdByPath(uploadFileParam.getFilePath());
                commonFile.setParentId(parentId);
                if(StrUtil.isNotEmpty(parentId)){
                    userFile.setParentId(parentId);
                }
                userFile.setFileSize(uploadFileParam.getTotalSize());
                try {
                    userFileService.save(userFile);
                    //fileDealComp.uploadESByUserFileId(userFile.getUserFileId());
                } catch (Exception e) {
                    UserFile userFile1 = userFileService.getOne(new QueryWrapper<UserFile>().lambda()
                            .eq(UserFile::getUserId, userFile.getUserId())
                            .eq(UserFile::getFilePath, userFile.getFilePath())
                            .eq(UserFile::getFileName, userFile.getFileName())
                            .eq(UserFile::getExtendName, userFile.getExtendName())
                            .eq(UserFile::getIsDir, userFile.getIsDir()));
                    PanFile file1 = panFileService.getById(userFile1.getFileId());
                    if (!StringUtils.equals(panFile.getIdentifier(), file1.getIdentifier())) {
                        log.warn("文件冲突重命名处理: {}", JSON.toJSONString(userFile1));
                        String fileName = fileDealComp.getRepeatFileName(userFile, userFile.getFilePath());
                        userFile.setFileName(fileName);
                        userFileService.save(userFile);
                        //fileDealComp.uploadESByUserFileId(userFile.getUserFileId());
                    }
                }


                if (relativePath.contains("/")) {
                    CommonFile finalCommonFile = commonFile;
                    exec.execute(() -> {
                        fileDealComp.restoreParentFilePath(finalCommonFile, StpLoginUserUtil.getLoginUser().getId());
                        //需要更新下 userFile 的parentId
                    });

                }

                LambdaQueryWrapper<PanUploadTaskDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(PanUploadTaskDetail::getIdentifier, uploadFileParam.getIdentifier());
                panUploadTaskDetailService.remove(lambdaQueryWrapper);

                LambdaUpdateWrapper<PanUploadTask> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.set(PanUploadTask::getUploadStatus, UploadFileStatusEnum.SUCCESS.getCode())
                        .eq(PanUploadTask::getIdentifier, uploadFileParam.getIdentifier());
                panUploadTaskService.update(lambdaUpdateWrapper);


                try {
                    if (FileCenterUtil.isImageFile(uploadFileResult.getExtendName())) {
                        BufferedImage src = uploadFileResult.getBufferedImage();
                        PanImage image = new PanImage();
                        image.setImageWidth(src.getWidth());
                        image.setImageHeight(src.getHeight());
                        image.setFileId(panFile.getId());
                        panImageService.save(image);
                    }
                } catch (Exception e) {
                    log.error("生成图片缩略图失败！", e);
                }

                fileDealComp.parseMusicFile(uploadFileResult.getExtendName(), uploadFileResult.getStorageType().getCode(), uploadFileResult.getFileUrl(), panFile.getId());

            } else if (UploadFileStatusEnum.UNCOMPLATE.equals(uploadFileResult.getStatus())) {
                PanUploadTaskDetail uploadTaskDetail = new PanUploadTaskDetail();
                uploadTaskDetail.setFilePath(commonFile.getParent());
                uploadTaskDetail.setFileName(commonFile.getNameNotExtend());
                uploadTaskDetail.setChunkNumber(uploadFileParam.getChunkNumber());
                uploadTaskDetail.setChunkSize(uploadFileParam.getChunkSize());
                uploadTaskDetail.setRelativePath(uploadFileParam.getRelativePath());
                uploadTaskDetail.setTotalChunks(uploadFileParam.getTotalChunks());
                uploadTaskDetail.setTotalSize(uploadFileParam.getTotalSize());
                uploadTaskDetail.setIdentifier(uploadFileParam.getIdentifier());
                panUploadTaskDetailService.save(uploadTaskDetail);

            } else if (UploadFileStatusEnum.FAIL.equals(uploadFileResult.getStatus())) {
                LambdaQueryWrapper<PanUploadTaskDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(PanUploadTaskDetail::getIdentifier, uploadFileParam.getIdentifier());
                panUploadTaskDetailService.remove(lambdaQueryWrapper);

                LambdaUpdateWrapper<PanUploadTask> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.set(PanUploadTask::getUploadStatus, UploadFileStatusEnum.FAIL.getCode())
                        .eq(PanUploadTask::getIdentifier, uploadFileParam.getIdentifier());
                panUploadTaskService.update(lambdaUpdateWrapper);
            }
        }
    }

//    public static void main(String[] args) {
//        //  /马义可的个人文件夹/2222222/231.doc
//        String path = "/马义可的个人文件夹/2222222/3333/6666/231.doc";
//        int lastIndex = path.lastIndexOf("/");
//        String parentPath = path.substring(0, lastIndex);
//        check(parentPath,"/",0);
//        //parentPath = "/马义可的个人文件夹";
////        int lastIndex2 = parentPath.lastIndexOf("/");
////        String parentPath2 = parentPath.substring(0, lastIndex2);
////        String dirName = parentPath.substring(lastIndex2+1);
////        System.out.println(parentPath2);
////        System.out.println(dirName);
//    }

    /***
     * 循环查询创建文件夹
     * @param parentPath
     * @param path
     * @param parentId
     * @return
     */
    private  String buildFold(String parentPath,String path,String parentId){
//        System.out.println(path);
        int index = parentPath.indexOf(ROOT_FOLD_PATH);
//        String parentPath2 = parentPath.substring(0, index+1);
        parentPath = parentPath.substring(index+1);
        String dirName;
        if(!parentPath.contains(ROOT_FOLD_PATH)){
            dirName = parentPath;
            UserFile userFile = userFileService.getOne(new LambdaQueryWrapper<UserFile>().eq(UserFile::getFileName, dirName).eq(UserFile::getFilePath, path));
            if(ObjectUtil.isNotNull(userFile)){
                return userFile.getParentId();
            }else {
                userFile = new UserFile();
                userFile.setParentId(parentId);
                userFile.setIsDir(IS_DIR);
                userFile.setFilePath(path);
                userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
                userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
                userFile.setFileName(dirName);
                userFileService.save(userFile);
                return userFile.getId();
            }
        }
        int index1 = parentPath.indexOf(ROOT_FOLD_PATH);
        dirName = parentPath.substring(0,index1);
        UserFile userFile = userFileService.getOne(new LambdaQueryWrapper<UserFile>().eq(UserFile::getFileName, dirName).eq(UserFile::getFilePath, path));
        if(ObjectUtil.isNull(userFile)){
            userFile = new UserFile();
            userFile.setParentId(parentId);
            userFile.setIsDir(IS_DIR);
            userFile.setFilePath(path);
            userFile.setOrgId(StpLoginUserUtil.getLoginUser().getOrgId());
            userFile.setUserId(StpLoginUserUtil.getLoginUser().getId());
            userFile.setFileName(dirName);
            userFileService.save(userFile);
            parentId = userFile.getId();
        }
        path += path.equals("/")?dirName:"/"+dirName;
        return buildFold(parentPath,path,parentId);
    }

    @Override
    public void downloadFile(HttpServletResponse httpServletResponse, List<String> fileIds) {
        UserFile userFile = userFileService.getById(fileIds.get(0));

        if (userFile.isFile()) {

            PanFile fileBean = panFileService.getById(userFile.getFileId());
            Downloader downloader = fileFactory.getDownloader(fileBean.getStorageType());
            if (downloader == null) {
                log.error("下载失败，文件存储类型不支持下载，storageType:{}", fileBean.getStorageType());
                throw new CommonException("下载失败");
            }
            DownloadFile downloadFile = new DownloadFile();

            downloadFile.setFileUrl(fileBean.getFileUrl());
            httpServletResponse.setContentLengthLong(fileBean.getFileSize());
            downloader.download(httpServletResponse, downloadFile);
        } else {

            CommonFile commonFile = new CommonFile(userFile.getFilePath(), userFile.getFileName(), true);
            List<UserFile> userFileList = userFileService.selectUserFileByLikeRightFilePath(commonFile.getPath(),StpLoginUserUtil.getLoginUser().getId());
            List<String> userFileIds = userFileList.stream().map(UserFile::getId).collect(Collectors.toList());

            downloadUserFileList(httpServletResponse, userFile.getFilePath(), userFile.getFileName(), userFileIds);
        }
    }

    @Override
    public void downloadUserFileList(HttpServletResponse httpServletResponse, String filePath, String fileName, List<String> userFileIds) {
        String staticPath = FileCenterUtil.getStaticPath();
        String tempPath = staticPath + "temp" + File.separator;
        File tempDirFile = new File(tempPath);
        if (!tempDirFile.exists()) {
            tempDirFile.mkdirs();
        }

        FileOutputStream f = null;
        try {
            f = new FileOutputStream(tempPath + fileName + ".zip");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out = new BufferedOutputStream(zos);

        try {
            for (String userFileId : userFileIds) {
                UserFile userFile1 = userFileService.getById(userFileId);
                if (userFile1.isFile()) {
                    PanFile fileBean = panFileService.getById(userFile1.getFileId());
                    Downloader downloader = fileFactory.getDownloader(fileBean.getStorageType());
                    if (downloader == null) {
                        log.error("下载失败，文件存储类型不支持下载，storageType:{}", fileBean.getStorageType());
                        throw new CommonException("下载失败");
                    }
                    DownloadFile downloadFile = new DownloadFile();
                    downloadFile.setFileUrl(fileBean.getFileUrl());
                    InputStream inputStream = downloader.getInputStream(downloadFile);
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    try {
                        CommonFile commonFile = new CommonFile(StrUtil.removePrefix(userFile1.getFilePath(), filePath), userFile1.getFileName() + "." + userFile1.getExtendName(), false);
                        zos.putNextEntry(new ZipEntry(commonFile.getPath()));

                        byte[] buffer = new byte[1024];
                        int i = bis.read(buffer);
                        while (i != -1) {
                            out.write(buffer, 0, i);
                            i = bis.read(buffer);
                        }
                    } catch (IOException e) {
                        log.error("" + e);
                        e.printStackTrace();
                    } finally {
                        IOUtils.closeQuietly(bis);
                        try {
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    CommonFile commonFile = new CommonFile(StrUtil.removePrefix(userFile1.getFilePath(), filePath), userFile1.getFileName(), true);
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(commonFile.getPath() + separator));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }

        } catch (Exception e) {
            log.error("压缩过程中出现异常:"+ e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String zipPath = "";
        try {
            Downloader downloader = fileFactory.getDownloader(StorageTypeEnum.LOCAL.getCode());
            DownloadFile downloadFile = new DownloadFile();
            downloadFile.setFileUrl(FileCenterUtil.getStaticPath()+"temp" + File.separator + fileName + ".zip");
            File tempFile = new File(FileCenterUtil.getStaticPath() + downloadFile.getFileUrl());
            httpServletResponse.setContentLengthLong(tempFile.length());
            downloader.download(httpServletResponse, downloadFile);
            zipPath = FileCenterUtil.getStaticPath() + "temp" + File.separator + fileName + ".zip";
        } catch (Exception e) {
            //org.apache.catalina.connector.ClientAbortException: java.io.IOException: Connection reset by peer
            if (e.getMessage().contains("ClientAbortException")) {
                //该异常忽略不做处理
            } else {
                log.error("下传zip文件出现异常：{}", e.getMessage());
            }

        } finally {
            File file = new File(zipPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void previewFile(HttpServletResponse httpServletResponse, PreviewParam previewParam) {
        UserFile userFile = userFileService.getById(previewParam.getUserFileId());
        PanFile fileBean = panFileService.getById(userFile.getFileId());
        Previewer previewer = fileFactory.getPreviewer(fileBean.getStorageType());
        if (previewer == null) {
            log.error("预览失败，文件存储类型不支持预览，storageType:{}", fileBean.getStorageType());
            throw new CommonException("预览失败");
        }
        PreviewFile previewFile = new PreviewFile();
        previewFile.setFileUrl(fileBean.getFileUrl());
        try {
            if ("true".equals(previewParam.getIsMin())) {
                previewer.imageThumbnailPreview(httpServletResponse, previewFile);
            } else {
                previewer.imageOriginalPreview(httpServletResponse, previewFile);
            }
        } catch (Exception e){
            //org.apache.catalina.connector.ClientAbortException: java.io.IOException: 你的主机中的软件中止了一个已建立的连接。
            if (e.getMessage().contains("ClientAbortException")) {
                //该异常忽略不做处理
            } else {
                log.error("预览文件出现异常：{}", e.getMessage());
            }

        }
    }

    @Override
    public void previewPictureFile(HttpServletResponse httpServletResponse, PreviewParam previewParam) {
        byte[] bytesUrl = Base64.getDecoder().decode(previewParam.getUrl());
        PanPictureFile pictureFile = panPictureFileService.getOne(new LambdaQueryWrapper<PanPictureFile>().eq(PanPictureFile::getFileUrl,new String(bytesUrl)));
        Previewer previewer = fileFactory.getPreviewer(pictureFile.getStorageType());
        if (ObjectUtil.isNull(pictureFile)) {
            log.error("预览失败，文件存储类型不支持预览，storageType:{}", pictureFile.getStorageType());
            throw new CommonException("预览失败");
        }
        PreviewFile previewFile = new PreviewFile();
        previewFile.setFileUrl(pictureFile.getFileUrl());
//        previewFile.setFileSize(pictureFile.getFileSize());
        try {

            String mime= MimeUtils.getMime(pictureFile.getExtendName());
            httpServletResponse.setHeader("Content-Type", mime);

            String fileName = pictureFile.getFileName() + "." + pictureFile.getExtendName();
            try {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            httpServletResponse.addHeader("Content-Disposition", "fileName=" + fileName);// 设置文件名

            previewer.imageOriginalPreview(httpServletResponse, previewFile);
        } catch (Exception e){
            //org.apache.catalina.connector.ClientAbortException: java.io.IOException: 你的主机中的软件中止了一个已建立的连接。
            if (e.getMessage().contains("ClientAbortException")) {
                //该异常忽略不做处理
            } else {
                log.error("预览文件出现异常：{}", e.getMessage());
            }

        }
    }

    @Override
    public void deleteFile(PanFile panFile) {
        Deleter deleter = null;
        deleter = fileFactory.getDeleter(panFile.getStorageType());
        DeleteFile deleteFile = new DeleteFile();
        deleteFile.setFileUrl(panFile.getFileUrl());
        deleter.delete(deleteFile);
    }

    @Override
    public Long selectStorageSizeByUserId() {
        return userFileService.getTotalStorageSize();
    }

}
