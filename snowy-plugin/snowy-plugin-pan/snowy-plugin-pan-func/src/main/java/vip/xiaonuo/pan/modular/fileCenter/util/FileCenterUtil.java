package vip.xiaonuo.pan.modular.fileCenter.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.file.util.DevFileLocalUtil;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 16:56
 * 文件操作帮助类
 */
public class FileCenterUtil {
//    public static String LOCAL_STORAGE_PATH ;
    public static String ROOT_PATH ;
    public static final String[] IMG_FILE = {"bmp", "jpg", "png", "tif", "gif", "jpeg"};
    public static final String[] DOC_FILE = {"doc", "docx", "ppt", "pptx", "xls", "xlsx", "txt", "hlp", "wps", "rtf", "html", "pdf"};
    public static final String[] VIDEO_FILE = {"avi", "mp4", "mpg", "mov", "swf"};
    public static final String[] MUSIC_FILE = {"wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac"};
    public static final String[] TXT_FILE = {"txt", "html", "java", "xml", "js", "css", "json", "sql"};
    public static final int IMAGE_TYPE = 1;
    public static final int DOC_TYPE = 2;
    public static final int VIDEO_TYPE = 3;
    public static final int MUSIC_TYPE = 4;
    public static final int OTHER_TYPE = 5;
    public static final int SHARE_FILE = 6;
    public static final int RECYCLE_FILE = 7;

    private static void  init(){
        JSONObject client = DevFileLocalUtil.getOOSClient();
        ROOT_PATH = client.getStr("localFileUploadFolder");
    }

    public static String getRootPath(){
        //StrUtil.isNotEmpty(ROOT_PATH) 不能判断 null ?
        if(StrUtil.isEmpty(ROOT_PATH)){
            init();
            return ROOT_PATH;
        }
        return ROOT_PATH;
    }


    /**
     * 判断是否为图片文件
     *
     * @param extendName 文件扩展名
     * @return 是否为图片文件
     */
    public static boolean isImageFile(String extendName) {
        for (String extend : IMG_FILE) {
            if (extendName.equalsIgnoreCase(extend)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为视频文件
     * @param extendName 扩展名
     * @return 是否为视频文件
     */
    public static boolean isVideoFile(String extendName) {
        for (String extend : VIDEO_FILE) {
            if (extendName.equalsIgnoreCase(extend)) {
                return true;
            }
        }
        return false;
    }

    public static String pathSplitFormat(String filePath) {
        return filePath.replace("///", "/")
                .replace("//", "/")
                .replace("\\\\\\", "/")
                .replace("\\\\", "/");
    }

    public static File getLocalSaveFile(String fileUrl) {
        String uploadFileFolder = DevFileLocalUtil.getUploadFileFolder()+ FileUtil.FILE_SEPARATOR+fileUrl;
        return new File(uploadFileFolder);
    }

    public static File getCacheFile(String fileUrl) {
        String cachePath = DevFileLocalUtil.getUploadFileFolder() + FileUtil.FILE_SEPARATOR + "cache" + FileUtil.FILE_SEPARATOR + fileUrl;

        return new File(cachePath);
    }

    public static File getTempFile(String fileUrl) {
        String tempPath = DevFileLocalUtil.getUploadFileFolder() + FileUtil.FILE_SEPARATOR+ "temp" + FileUtil.FILE_SEPARATOR + fileUrl;
        File tempFile = new File(tempPath);
        File parentFile = tempFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }

        return tempFile;
    }

    public static File getProcessFile(String fileUrl) {
        String processPath = DevFileLocalUtil.getUploadFileFolder() + FileUtil.FILE_SEPARATOR+ "temp" + FileUtil.FILE_SEPARATOR+ "process" + File.separator + fileUrl;
        File processFile = new File(processPath);
        File parentFile = processFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return processFile;
    }

    /**
     * 路径解码
     * @param url url
     * @return 结果
     */
    public static String urlDecode(String url){
        String decodeUrl;
        try {
            decodeUrl = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new CommonException("不支持的编码格式", e);
        }
        return  decodeUrl;
    }

    public static String getStaticPath() {
        String localStoragePath = getRootPath();

        if (StringUtils.isNotEmpty(localStoragePath)) {
            return new File(localStoragePath).getPath() + FileUtil.FILE_SEPARATOR;
        }else {
            String projectRootAbsolutePath = getProjectRootPath();

            int index = projectRootAbsolutePath.indexOf("file:");
            if (index != -1) {
                projectRootAbsolutePath = projectRootAbsolutePath.substring(0, index);
            }

            return new File(projectRootAbsolutePath + "static").getPath() + FileUtil.FILE_SEPARATOR;
        }


    }
    /**
     * 获取项目所在的根目录路径 resources路径
     * @return 结果
     */
    public static String getProjectRootPath() {
        String absolutePath;
        try {
            String url = ResourceUtils.getURL("classpath:").getPath();
            absolutePath = urlDecode(new File(url).getAbsolutePath()) + File.separator;
        } catch (FileNotFoundException e) {
            throw new CommonException(e.getMessage());
        }

        return absolutePath;
    }


    public static String formatPath(String path) {
        path = FileCenterUtil.pathSplitFormat(path);
        if ("/".equals(path)) {
            return path;
        }
        if (path.endsWith("/")) {
            int length = path.length();
            return path.substring(0, length - 1);
        }
        return path;
    }

    public static String getAliyunObjectNameByFileUrl(String fileUrl) {
        if (fileUrl.startsWith("/") || fileUrl.startsWith("\\")) {
            fileUrl = fileUrl.substring(1);
        }
        return fileUrl;
    }

    /**
     * 获取上传文件路径
     * 返回路径格式 “upload/yyyyMMdd/”
     * @param identifier 文件名（一般传入md5或uuid,防止文件名重复）
     * @param extendName 文件扩展名
     * @return 返回上传文件路径
     */
    public static String getUploadFileUrl(String identifier, String extendName) {
//        init();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String path = getStaticPath() + formater.format(new Date()) + FileUtil.FILE_SEPARATOR;

        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        path = path + identifier + "." + extendName;

        return path;
    }
}
