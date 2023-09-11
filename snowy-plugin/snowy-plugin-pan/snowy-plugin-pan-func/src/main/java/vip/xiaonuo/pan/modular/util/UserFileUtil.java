package vip.xiaonuo.pan.modular.util;

import cn.hutool.core.util.IdUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.CommonFile;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

/**
 * @author yong.ye
 * @date 2023/9/8 15:34
 */
public class UserFileUtil {

    public static UserFile getQiwenDir(String userId, String filePath, String fileName) {
        UserFile userFile = new UserFile();
//        userFile.setUserFileId(IdUtil.getSnowflakeNextIdStr());
        userFile.setUserId(userId);
        userFile.setFileId(null);
        userFile.setFileName(fileName);
        userFile.setFilePath(CommonFile.formatPath(filePath));
        userFile.setExtendName(null);
        userFile.setIsDir(1);
//        userFile.setUploadTime(DateUtil.getCurrentTime());
//        userFile.setCreateUserId(SessionUtil.getUserId());
//        userFile.setCreateTime(DateUtil.getCurrentTime());
//        userFile.setDeleteFlag(0);
//        userFile.setDeleteBatchNum(null);
        return userFile;
    }

    public static UserFile getQiwenFile(String userId, String fileId, String filePath, String fileName, String extendName) {
        UserFile userFile = new UserFile();
//        userFile.setUserFileId(IdUtil.getSnowflakeNextIdStr());
        userFile.setUserId(userId);
        userFile.setFileId(fileId);
        userFile.setFileName(fileName);
        userFile.setFilePath(CommonFile.formatPath(filePath));
        userFile.setExtendName(extendName);
        userFile.setIsDir(0);
//        userFile.setUploadTime(DateUtil.getCurrentTime());
//        userFile.setCreateTime(DateUtil.getCurrentTime());
//        userFile.setCreateUserId(SessionUtil.getUserId());
//        userFile.setDeleteFlag(0);
//        userFile.setDeleteBatchNum(null);
        return userFile;
    }

    public static UserFile searchQiwenFileParam(UserFile userFile) {
        UserFile param = new UserFile();
        param.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
        param.setFileName(userFile.getFileName());
        param.setExtendName(userFile.getExtendName());
        param.setDeleteFlag("NOT_DELETE");
        param.setUserId(userFile.getUserId());
        param.setIsDir(0);
        return param;
    }

    public static String formatLikePath(String filePath) {
        String newFilePath = filePath.replace("'", "\\'");
        newFilePath = newFilePath.replace("%", "\\%");
        newFilePath = newFilePath.replace("_", "\\_");
        return newFilePath;
    }

}
