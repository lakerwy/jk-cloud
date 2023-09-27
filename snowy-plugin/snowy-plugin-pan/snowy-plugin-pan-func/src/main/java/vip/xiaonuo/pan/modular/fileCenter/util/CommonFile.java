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
    private String dirPath;
    private String parentPath;

    public CommonFile(String pathname, boolean isDirectory) {
        this.path = formatPath(pathname);
        this.isDirectory = isDirectory;
    }

    public CommonFile(String path, boolean isDirectory, String parentId) {
        this.path = path;
        this.isDirectory = isDirectory;
        this.parentId = parentId;
    }

    public CommonFile(String path, boolean isDirectory, String parentId,String dirPath) {
        this.path = path;
        this.isDirectory = isDirectory;
        this.parentId = parentId;
        this.dirPath = dirPath;
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

    /**
     * /a/b/c
     * @return
     */
    public String getChild() {
        if (separator.equals(dirPath)) {
            return null;
        }
        if (!dirPath.contains(separator)) {
            return null;
        }
        int firstSlashIndex = dirPath.indexOf(separator);
        if(firstSlashIndex!=-1){
            return dirPath.substring(0,firstSlashIndex+1);  //  /   /a  /a/b
        }

        return "";
    }

    public String getChildPath() {
        if (separator.equals(dirPath)) {
            return null;
        }
        if (!dirPath.contains(separator)) {
            return null;
        }
        int firstSlashIndex = dirPath.indexOf(separator);
        int secondSlashIndex = dirPath.indexOf(separator, firstSlashIndex + 1);

        if (firstSlashIndex != -1 && secondSlashIndex != -1) {
            //this.dirPath = dirPath.substring(secondSlashIndex);
            return dirPath.substring(secondSlashIndex);  //  /a
        }else {
            if(secondSlashIndex == -1){
                // /c
                return "";
            }
        }

        return "";
    }

    public String getChildName() {
        if (separator.equals(dirPath)) {
            return null;
        }
        if (!dirPath.contains(separator)) {
            return null;
        }
        int firstSlashIndex = dirPath.indexOf(separator);
        int secondSlashIndex = dirPath.indexOf(separator, firstSlashIndex + 1);

        if (firstSlashIndex != -1 && secondSlashIndex != -1) {
            //this.dirPath = dirPath.substring(secondSlashIndex);
            return dirPath.substring(firstSlashIndex+1,secondSlashIndex);  //  /a
        }else {
            if(secondSlashIndex == -1){
                // /c
                return dirPath.substring(firstSlashIndex+1);
            }
        }
         return "";
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
