package vip.xiaonuo.pan.modular.fileCenter.util;

import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

public class FileCommonUtil {


    public static UserFile getFileDir(String userId, String filePath, String fileName) {
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        userFile.setFileId(null);
        userFile.setFileName(fileName);
        userFile.setFilePath(CommonFile.formatPath(filePath));
        userFile.setExtendName(null);
        userFile.setIsDir(1);
        //userFile.setDeleteBatchNum(null);
        return userFile;
    }

    public static UserFile getUserFile(String userId, String fileId, String filePath, String fileName, String extendName) {
        UserFile userFile = new UserFile();
        userFile.setUserId(userId);
        userFile.setFileId(fileId);
        userFile.setFileName(fileName);
        userFile.setFilePath(CommonFile.formatPath(filePath));
        userFile.setExtendName(extendName);
        userFile.setIsDir(0);
        return userFile;
    }

    public static UserFile searchQiwenFileParam(UserFile userFile) {
        UserFile param = new UserFile();
        param.setFilePath(CommonFile.formatPath(userFile.getFilePath()));
        param.setFileName(userFile.getFileName());
        param.setExtendName(userFile.getExtendName());
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
