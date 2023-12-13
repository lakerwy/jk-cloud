package vip.xiaonuo.pan.modular.fileCenter.component;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import vip.xiaonuo.pan.modular.file.entity.PanFile;
import vip.xiaonuo.pan.modular.file.service.IFileTransferService;
import vip.xiaonuo.pan.modular.file.service.PanFileService;
import vip.xiaonuo.pan.modular.fileCenter.factory.FileFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;
import vip.xiaonuo.pan.modular.recoveryfile.service.PanRecoveryFileService;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;
import vip.xiaonuo.pan.modular.userFile.mapper.UserFileMapper;
import vip.xiaonuo.pan.modular.userFile.service.UserFileService;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

/**
 * 功能描述：异步任务业务类（@Async也可添加在方法上）
 */
@Slf4j
@Component
@Async("asyncTaskExecutor")
public class AsyncTaskComp {

    @Resource
    UserFileMapper userFileMapper;   //某些操作不能过滤 DELETE_FLAG

    @Resource
    PanRecoveryFileService recoveryFileService;
    @Resource
    IFileTransferService fileTransferService;
    @Resource
    FileFactory fileFactory;
    @Resource
    UserFileService userFileService;
    @Resource
    PanFileService fileService;
    @Resource
    FileDealComp fileDealComp;

//    //todo
//    @Value("${ufop.storage-type}")
//    private Integer storageType;

    public Long getFilePointCount(String fileId) {
        LambdaQueryWrapper<UserFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserFile::getFileId, fileId);
        return userFileService.count(lambdaQueryWrapper);
    }

    public Future<String> deleteUserFile(String userFileId) {
        UserFile userFile = userFileMapper.mySelectById(userFileId);
        if (userFile.getIsDir() == 1) {
            LambdaQueryWrapper<UserFile> userFileLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userFileLambdaQueryWrapper.eq(UserFile::getDeleteBatchNum, userFile.getDeleteBatchNum());
            List<UserFile> list = userFileMapper.mySelectList(userFileLambdaQueryWrapper);
            recoveryFileService.deleteUserFileByDeleteBatchNum(userFile.getDeleteBatchNum());
            for (UserFile userFileItem : list) {

                Long filePointCount = getFilePointCount(userFileItem.getFileId());

                if (filePointCount != null && filePointCount == 0 && userFileItem.getIsDir() == 0) {
                    PanFile panFile = fileService.getById(userFileItem.getFileId());
                    try {
                        fileTransferService.deleteFile(panFile);
                        fileService.removeById(panFile.getId());
                    } catch (Exception e) {
                        log.error("删除本地文件失败：" + JSON.toJSONString(panFile));
                    }
                }
            }
        } else {

            recoveryFileService.deleteUserFileByDeleteBatchNum(userFile.getDeleteBatchNum());
            Long filePointCount = getFilePointCount(userFile.getFileId());

            if (filePointCount != null && filePointCount == 0 && userFile.getIsDir() == 0) {
                PanFile panFile = fileService.getById(userFile.getFileId());
                try {
                    fileTransferService.deleteFile(panFile);
                    fileService.removeById(panFile.getId());
                } catch (Exception e) {
                    log.error("删除本地文件失败：" + JSON.toJSONString(panFile));
                }
            }
        }

        return new AsyncResult<>("deleteUserFile");
    }

//    public Future<String> checkESUserFileId(String userFileId) {
//        UserFile userFile = userFileMapper.selectById(userFileId);
//        if (userFile == null) {
//            fileDealComp.deleteESByUserFileId(userFileId);
//        }
//        return new AsyncResult<>("checkUserFileId");
//    }


    public Future<String> saveUnzipFile(UserFile userFile, PanFile panFile, int unzipMode, String entryName, String filePath,String parentId) {
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
            } finally {
                IOUtils.closeQuietly(fis);
            }

            FileInputStream fileInputStream = null;
            try {
//                Map<String, Object> param = new HashMap<>();
//                param.put("identifier", md5Str);
//                List<PanFile> list = fileMapper.selectByMap(param);
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
;                   fileService.save(tempFileBean);
                    fileId = tempFileBean.getId();
                    fileSize = tempFileBean.getFileSize();
                }
            } catch (IOException e) {
//                e.printStackTrace();
                log.error(e+"");
            } finally {
                IOUtils.closeQuietly(fileInputStream);
                System.gc();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    log.error(e+"");
                }
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
        String fileName = fileDealComp.getRepeatFileName(saveUserFile, saveUserFile.getFilePath());

        if (saveUserFile.getIsDir() == 1 && !fileName.equals(saveUserFile.getFileName())) {
            //如果是目录，而且重复，什么也不做
        } else {
            saveUserFile.setFileName(fileName);
            saveUserFile.setParentId(commonFile.getParentId());
            //解压后的foldType 和原压缩文件一致
            saveUserFile.setFoldType(userFile.getFoldType());
            saveUserFile.setFileSize(fileSize);
            userFileService.save(saveUserFile);
        }
       // fileDealComp.restoreParentFilePath(commonFile, userFile.getUserId());

        return new AsyncResult<>("saveUnzipFile");
    }


}