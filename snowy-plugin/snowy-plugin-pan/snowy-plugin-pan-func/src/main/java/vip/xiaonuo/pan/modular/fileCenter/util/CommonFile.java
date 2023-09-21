package vip.xiaonuo.pan.modular.fileCenter.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 16:52
 * 文件夹和文件的构建处理类
 */
@Getter
@Setter
public class CommonFile {

    private final String path;
    public static final String separator = "/";
    private boolean isDirectory;
    private String parentId;

    public CommonFile(String pathname, boolean isDirectory) {
        this.path = formatPath(pathname);
        this.isDirectory = isDirectory;
    }

    public CommonFile(String path, boolean isDirectory, String parentId) {
        this.path = path;
        this.isDirectory = isDirectory;
        this.parentId = parentId;
    }

    public CommonFile(String parent, String child, boolean isDirectory) {
        if (parent != null) {
            String parentPath = separator.equals(formatPath(parent)) ? "" : formatPath(parent);
            String childPath = formatPath(child);
            if (childPath.startsWith(separator)) {
                childPath = childPath.replaceFirst(separator, "");
            }
            this.path = parentPath + separator + childPath;
        } else {
            this.path = formatPath(child);
        }
        this.isDirectory = isDirectory;
    }
    public static String formatPath(String path) {
        path = FileCenterUtil.pathSplitFormat(path);
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

    public String getParent() {
        if (separator.equals(this.path)) {
            return null;
        }
        if (!this.path.contains("/")) {
            return null;
        }
        int index = path.lastIndexOf(separator);
        if (index == 0) {
            return separator;
        }
        return path.substring(0, index);
    }

    public CommonFile getParentFile() {
        String parentPath = this.getParent();
        return new CommonFile(parentPath, true);
    }

    public String getName() {
        int index = path.lastIndexOf(separator);
        if (!path.contains(separator)) {
            return path;
        }
        return path.substring(index + 1);
    }

    public String getExtendName() {
        return FilenameUtils.getExtension(getName());
    }

    public String getNameNotExtend() {
        return FilenameUtils.removeExtension(getName());
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public boolean isFile() {
        return !isDirectory;
    }
}
