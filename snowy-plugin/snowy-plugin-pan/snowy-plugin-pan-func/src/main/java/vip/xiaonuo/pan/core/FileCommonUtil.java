package vip.xiaonuo.pan.core;

import static cn.hutool.core.io.FileUtil.FILE_SEPARATOR;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 14:21
 * 文件工具类
 */
public class FileCommonUtil {

    public static final String separator = "/";
    public static String pathSplitFormat(String filePath) {
        return filePath.replace("///", "/").replace("//", "/").replace("\\\\\\", "/").replace("\\\\", "/");
    }

    public static String formatPath(String path) {
        path = pathSplitFormat(path);
        if ("/".equals(path)) {
            return path;
        }
        if (!path.startsWith(separator)) {
            path = separator + path;
        }
        if (path.endsWith("/")) {
            int length = path.length();
            return path.substring(0, length - 1);
        }

        return path;
    }
}
