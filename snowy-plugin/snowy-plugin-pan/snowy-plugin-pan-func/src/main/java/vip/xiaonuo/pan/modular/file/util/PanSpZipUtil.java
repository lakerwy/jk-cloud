package vip.xiaonuo.pan.modular.file.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.file.vo.RepeatFileVo;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.param.UnzipFileParam;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.mapper.UserFileMapper;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class PanSpZipUtil {
    @Resource
    UserFileMapper userFileMapper;
    @Resource
    FileFactory fileFactory;
    @Resource
    UserFileService userFileService;
    @Resource
    PanFileService fileService;

    public void iteraSaveFile(UserFile userFile, PanFile panFile, int unzipMode, List<String> fileEntryNameList, String filePath, String parentId){
        //需要批量保存的数据
        List<UserFile> userFileList = new ArrayList<>();
        List<PanFile> panFileList = new ArrayList<>();
        for (String entryName : fileEntryNameList) {
            saveUnzipFileSp(userFile, panFile, unzipMode, entryName, filePath,parentId,userFileList,panFileList);
        }
        if(CollectionUtil.isNotEmpty(panFileList)){fileService.saveBatch(panFileList);}
        if(CollectionUtil.isNotEmpty(userFileList)){userFileService.saveBatch(userFileList);}

    }


    public void saveUnzipFileSp(UserFile userFile, PanFile panFile, int unzipMode, String entryName, String filePath, String parentId,List<UserFile> userFileList,List<PanFile> panFileList) {
        String unzipUrl = FileCenterUtil.getTempFile(panFile.getFileUrl()).getAbsolutePath().replace("." + userFile.getExtendName(), "");
        String totalFileUrl = unzipUrl + FileUtil.FILE_SEPARATOR +entryName;
        File currentFile = new File(totalFileUrl);

        String fileId = null;
        Long fileSize = 0L;
        if (!currentFile.isDirectory()) {
            FileInputStream fis = null;
            String md5Str = UUID.randomUUID().toString();
            try {
                fis = new FileInputStream(currentFile);
                md5Str = DigestUtils.md5Hex(fis);
            } catch (IOException e) {
                log.error(e+"");
            } finally { IOUtils.closeQuietly(fis); }

            FileInputStream fileInputStream = null;
            try {
                List<PanFile> list = fileService.list(new LambdaQueryWrapper<PanFile>().eq(PanFile::getIdentifier, md5Str));
                if (list != null && !list.isEmpty()) { //文件已存在
                    fileId = list.get(0).getId();
                    fileSize = list.get(0).getFileSize();
                } else { //文件不存在
                    fileInputStream = new FileInputStream(currentFile);
                    CopyFile createFile = new CopyFile();
                    createFile.setExtendName(FilenameUtils.getExtension(totalFileUrl));
                    String saveFileUrl = fileFactory.getCopier().copy(fileInputStream, createFile);
                    PanFile tempFileBean = new PanFile(saveFileUrl, currentFile.length(), FileFactory.storageType, md5Str, userFile.getUserId());
                    panFileList.add(tempFileBean);
                    //fileService.save(tempFileBean);  //批量保存
                    fileId = tempFileBean.getId();
                    fileSize = tempFileBean.getFileSize();
                }
            } catch (IOException e) {
                log.error("解压失败！"+e.getMessage());
            } finally {
                IOUtils.closeQuietly(fileInputStream);
                boolean delete = currentFile.delete();
            }
        }
        CommonFile commonFile = null;
        if (unzipMode == 0) {
            commonFile = new CommonFile(userFile.getFilePath(), entryName, currentFile.isDirectory());
        } else if (unzipMode == 1) {
            commonFile = new CommonFile(userFile.getFilePath() + FileUtil.FILE_SEPARATOR + userFile.getFileName(), entryName, currentFile.isDirectory());
        } else if (unzipMode == 2) {
            commonFile = new CommonFile(filePath, entryName, currentFile.isDirectory());
        }
        assert commonFile != null;
        commonFile.setParentId(parentId);
        UserFile saveUserFile = new UserFile(commonFile, userFile.getUserId(), fileId);
        String fileName = getRepeatFileName(saveUserFile, saveUserFile.getFilePath());

        if (saveUserFile.getIsDir() == 1 && !fileName.equals(saveUserFile.getFileName())) {
            //如果是目录，而且重复，什么也不做
        } else {
            saveUserFile.setFileName(fileName);
            saveUserFile.setParentId(commonFile.getParentId());
            //解压后的foldType 和原压缩文件一致
            saveUserFile.setFoldType(userFile.getFoldType());
            saveUserFile.setFileSize(fileSize);
            userFileList.add(saveUserFile);
            //userFileService.save(saveUserFile);//批量保存
        }
    }

    public String getRepeatFileName(UserFile userFile, String savefilePath) {
        String fileName = userFile.getFileName();
        String extendName = userFile.getExtendName();

        String userId = userFile.getUserId();
        int isDir = userFile.getIsDir();
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserFile::getFilePath, savefilePath)
                .eq(UserFile::getDeleteFlag, "NOT_DELETE")
                .eq(UserFile::getUserId, userId)
                .eq(UserFile::getFileName, fileName)
                .eq(UserFile::getIsDir, isDir);
        if (userFile.isFile()) {
            lambdaQueryWrapper.eq(UserFile::getExtendName, extendName);
        }
        List<UserFile> list = userFileMapper.mySelectList(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return fileName;
        }

        int i = 0;
        while (!CollectionUtils.isEmpty(list)) {
            i++;
            LambdaQueryWrapper<UserFile> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(UserFile::getFilePath, savefilePath)
                    .eq(UserFile::getDeleteFlag, "NOT_DELETE")
                    .eq(UserFile::getUserId, userId)
                    .eq(UserFile::getFileName, fileName + "(" + i + ")")
                    .eq(UserFile::getIsDir, isDir);
            if (userFile.isFile()) {
                lambdaQueryWrapper1.eq(UserFile::getExtendName, extendName);
            }
            list = userFileMapper.mySelectList(lambdaQueryWrapper1);
        }
        return fileName + "(" + i + ")";

    }


    /**
     * 解压文件夹 - 保存所有文件【包括子文件夹】
     * @param userFile 压缩文件Zip的Pan_User_File
     * @param unzipUrl  压缩文件Zip解压后的文件夹地址
     * @param unzipFileParam 解压参数
     *
     */
    public void iteraSaveFileNew(UserFile userFile,String unzipUrl, UnzipFileParam unzipFileParam){
        //根据 解压类型的不同 获取最顶层父级userFile
        UserFile parentUserFile = new UserFile();
        if(0 == unzipFileParam.getUnzipMode()){
            // 解压到 当前文件 的顶层父级
            parentUserFile = userFile;
        } else if (1 == unzipFileParam.getUnzipMode()) {
            // a.zip 解压 到 A 文件夹下
            UserFile userFile1 = new UserFile();
            userFile1.setFileName(userFile.getFileName());
            userFile1.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
            userFile1.setParentId(userFile.getParentId());
            userFile1.setIsDir(1);
            userFile1.setFoldType(userFile.getFoldType());
            userFile1.setOrgId(userFile.getOrgId());
            userFileService.save(userFile1);
            parentUserFile = userFile1;
        } else if(2 == unzipFileParam.getUnzipMode()){
            // 解压 到 指定文件夹
            parentUserFile = userFileService.getParentIdByPath(unzipFileParam.getFilePath());
        }
        //文件路径
        unzipUrl = unzipUrl + FileUtil.FILE_SEPARATOR + userFile.getFileName();
        //需要批量保存的数据
        List<UserFile> userFileList = new ArrayList<>();
        List<PanFile> panFileList = new ArrayList<>();
        saveUnzipFileSpNew(parentUserFile,unzipUrl,userFileList,panFileList);
        if(CollectionUtil.isNotEmpty(panFileList)){fileService.saveBatch(panFileList);}
        if(CollectionUtil.isNotEmpty(userFileList)){userFileService.saveBatch(userFileList);}
    }


    /**
     * 递归处理 解压文件|文件夹
     * @param parentUserFile 父级实体
     * @param unzipUrl 解压文件夹地址
     * @param userFileList 待添加的结果集
     * @param panFileList 待添加的结果集
     */
    public void saveUnzipFileSpNew(UserFile parentUserFile,String unzipUrl,List<UserFile> userFileList,List<PanFile> panFileList) {
        // 根据解压后的文件地址，获取解压文件[顶层]
        File[] tmpFiles = FileUtil.ls(unzipUrl);
        // 循环处理当前层文件|文件夹
        for (File tmpFile:tmpFiles) {
            // panFile相关参数
            String fileId = null;
            Long fileSize = 0L;

            // 文件|文件夹构造
            CommonFile commonFile = new CommonFile(parentUserFile.getFilePath(), tmpFile.getName(), tmpFile.isDirectory());
            assert commonFile != null;
            commonFile.setParentId(parentUserFile.getId());

            //文件夹处理
            if(tmpFile.isDirectory()){
                UserFile saveUserFile = new UserFile(commonFile, parentUserFile.getUserId(), fileId);
                saveUserFile.setId(IdWorker.getIdStr());
                RepeatFileVo repeatFileVo = getRepeatFileNameNew(saveUserFile, saveUserFile.getFilePath());
                //若不存在，需要保存
                if (ObjectUtil.isEmpty(repeatFileVo.getOldUserFile()) && repeatFileVo.getNewFileName().equals(saveUserFile.getFileName())) {
                    saveUserFile.setFileName(repeatFileVo.getNewFileName());
                    saveUserFile.setParentId(commonFile.getParentId());
                    //解压后的foldType 和原压缩文件一致
                    saveUserFile.setFoldType(parentUserFile.getFoldType());
                    saveUserFile.setFileSize(fileSize);
                    userFileList.add(saveUserFile);
                }
                //父级
                UserFile tmpParentUserFile = ObjectUtil.isNotEmpty(repeatFileVo.getOldUserFile())?repeatFileVo.getOldUserFile():saveUserFile;
                //递归
                saveUnzipFileSpNew(tmpParentUserFile,tmpFile.getPath(),userFileList,panFileList);
            }else{
                //文件处理(panFile)
                FileInputStream fis = null;
                String md5Str = UUID.randomUUID().toString();
                try {
                    fis = new FileInputStream(tmpFile);
                    md5Str = DigestUtils.md5Hex(fis);
                } catch (IOException e) {
                    log.error(e+"");
                } finally { IOUtils.closeQuietly(fis); }

                FileInputStream fileInputStream = null;
                try {
                    List<PanFile> list = fileService.list(new LambdaQueryWrapper<PanFile>().eq(PanFile::getIdentifier, md5Str));
                    if (list != null && !list.isEmpty()) { //文件已存在
                        fileId = list.get(0).getId();
                        fileSize = list.get(0).getFileSize();
                    } else { //文件不存在
                        fileInputStream = new FileInputStream(tmpFile);
                        CopyFile createFile = new CopyFile();
                        createFile.setExtendName(FilenameUtils.getExtension(tmpFile.getName()));
                        String saveFileUrl = fileFactory.getCopier().copy(fileInputStream, createFile);
                        PanFile tempFileBean = new PanFile(saveFileUrl, tmpFile.length(), FileFactory.storageType, md5Str, parentUserFile.getUserId());
                        panFileList.add(tempFileBean);
                        fileId = tempFileBean.getId();
                        fileSize = tempFileBean.getFileSize();
                    }
                } catch (IOException e) {
                    log.error("解压失败！"+e.getMessage());
                } finally {
                    IOUtils.closeQuietly(fileInputStream);
                    boolean delete = tmpFile.delete();
                }
                //构造保存文件
                UserFile saveUserFile = new UserFile(commonFile, parentUserFile.getUserId(), fileId);
                saveUserFile.setId(IdWorker.getIdStr());
                RepeatFileVo repeatFileVo = getRepeatFileNameNew(saveUserFile, saveUserFile.getFilePath());
                saveUserFile.setFileName(repeatFileVo.getNewFileName());
                saveUserFile.setParentId(commonFile.getParentId());
                //解压后的foldType 和原压缩文件一致
                saveUserFile.setFoldType(parentUserFile.getFoldType());
                saveUserFile.setFileSize(fileSize);
                userFileList.add(saveUserFile);
            }
        }
    }


    /**
     * 判断文件|文件夹是否已存在，若存在，返回已存在的文件信息
     * @param userFile
     * @param savefilePath
     * @return
     */
    public RepeatFileVo getRepeatFileNameNew(UserFile userFile, String savefilePath) {
        RepeatFileVo repeatFileVo = new RepeatFileVo();
        String fileName = userFile.getFileName();
        String extendName = userFile.getExtendName();

        String userId = userFile.getUserId();
        int isDir = userFile.getIsDir();
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserFile::getFilePath, savefilePath)
                .eq(UserFile::getDeleteFlag, "NOT_DELETE")
                .eq(UserFile::getUserId, userId)
                .eq(UserFile::getFileName, fileName)
                .eq(UserFile::getIsDir, isDir);
        if (userFile.isFile()) {
            lambdaQueryWrapper.eq(UserFile::getExtendName, extendName);
        }
        List<UserFile> list = userFileMapper.mySelectList(lambdaQueryWrapper);
        //不存在
        if (CollectionUtils.isEmpty(list)) {
            repeatFileVo.setNewFileName(fileName);
            return repeatFileVo;
        }

        //已存在(文件夹|文件已存在)
        repeatFileVo.setOldUserFile(list.get(0));
        int i = 0;
        while (!CollectionUtils.isEmpty(list)) {
            i++;
            LambdaQueryWrapper<UserFile> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(UserFile::getFilePath, savefilePath)
                    .eq(UserFile::getDeleteFlag, "NOT_DELETE")
                    .eq(UserFile::getUserId, userId)
                    .eq(UserFile::getFileName, fileName + "(" + i + ")")
                    .eq(UserFile::getIsDir, isDir);
            if (userFile.isFile()) {
                lambdaQueryWrapper1.eq(UserFile::getExtendName, extendName);
            }
            list = userFileMapper.mySelectList(lambdaQueryWrapper1);
        }
        repeatFileVo.setNewFileName(fileName + "(" + i + ")");
        return repeatFileVo;
    }

}
