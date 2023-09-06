package vip.xiaonuo.pan.modular.file.service.impl;

import com.alibaba.excel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.pan.modular.file.param.MultipartFileParam;
import vip.xiaonuo.pan.modular.file.service.StorageService;
import vip.xiaonuo.pan.modular.file.utils.Constants;
import vip.xiaonuo.pan.modular.file.utils.FileMD5Util;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/4 17:40
 */
@Component
@Slf4j
public class StorageServiceImpl implements StorageService {

    //这个必须与前端设定的值一致
    //@Value("${breakpoint.upload.chunkSize}")
    private final long CHUNK_SIZE = 20971520;

    //@Value("${breakpoint.upload.dir}")
    private final String finalDirPath = "D:/data0/uploads/";

    // 保存文件的根目录
    private Path rootPaht;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private  StringRedisTemplate stringRedisTemplate;

    public StorageServiceImpl() {
        this.rootPaht = Paths.get(finalDirPath);
    }

    @Override
    public void deleteAll() {
        log.info("开发初始化清理数据，start");
        FileSystemUtils.deleteRecursively(rootPaht.toFile());
        commonCacheOperator.delete(Constants.FILE_UPLOAD_STATUS);
        commonCacheOperator.delete(Constants.FILE_MD5_KEY);
        log.info("开发初始化清理数据，end");
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootPaht);
        } catch (FileAlreadyExistsException e) {
            log.error("文件夹已经存在了，不用再创建。");
        } catch (IOException e) {
            log.error("初始化root文件夹失败。", e);
        }
    }

    @Override
    public void uploadFileRandomAccessFile(MultipartFileParam param) throws IOException {
        String fileName = param.getName();
        String tempDirPath = finalDirPath + param.getMd5();
        String tempFileName = fileName + "_tmp";
        File tmpDir = new File(tempDirPath);
        File tmpFile = new File(tempDirPath, tempFileName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        RandomAccessFile accessTmpFile = new RandomAccessFile(tmpFile, "rw");
        long offset = CHUNK_SIZE * param.getChunk();
        //定位到该分片的偏移量
        accessTmpFile.seek(offset);
        //写入该分片数据
        accessTmpFile.write(param.getFile().getBytes());
        // 释放
        accessTmpFile.close();

        boolean isOk = checkAndSetUploadProgress(param, tempDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
            System.out.println("upload complete !!" + flag + " name=" + fileName);
        }
    }

    @Override
    public void uploadFileByMappedByteBuffer(MultipartFileParam param) throws IOException {
        String fileName = param.getName();
        String uploadDirPath = finalDirPath + param.getMd5();
        String tempFileName = fileName + "_tmp";
        File tmpDir = new File(uploadDirPath);
        File tmpFile = new File(uploadDirPath, tempFileName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        RandomAccessFile tempRaf = new RandomAccessFile(tmpFile, "rw");
        FileChannel fileChannel = tempRaf.getChannel();

        //写入该分片数据
        long offset = CHUNK_SIZE * param.getChunk();
        byte[] fileData = param.getFile().getBytes();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, fileData.length);
        mappedByteBuffer.put(fileData);
        // 释放
        FileMD5Util.freedMappedByteBuffer(mappedByteBuffer);
        fileChannel.close();

        boolean isOk = checkAndSetUploadProgress(param, uploadDirPath);
        if (isOk) {
            boolean flag = renameFile(tmpFile, fileName);
            System.out.println("upload complete !!" + flag + " name=" + fileName);
        }
    }

    /**
     * 检查并修改文件上传进度
     *
     * @param param
     * @param uploadDirPath
     * @return
     * @throws IOException
     */
    private boolean checkAndSetUploadProgress(MultipartFileParam param, String uploadDirPath) throws IOException {
        String fileName = param.getName();
        File confFile = new File(uploadDirPath, fileName + ".conf");
        RandomAccessFile accessConfFile = new RandomAccessFile(confFile, "rw");
        //把该分段标记为 true 表示完成
        System.out.println("set part " + param.getChunk() + " complete");
        accessConfFile.setLength(param.getChunks());
        accessConfFile.seek(param.getChunk());
        accessConfFile.write(Byte.MAX_VALUE);

        //completeList 检查是否全部完成,如果数组里是否全部都是(全部分片都成功上传)
        byte[] completeList = FileUtils.readFileToByteArray(confFile);
        byte isComplete = Byte.MAX_VALUE;
        for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
            //与运算, 如果有部分没有完成则 isComplete 不是 Byte.MAX_VALUE
            isComplete = (byte) (isComplete & completeList[i]);
            System.out.println("check part " + i + " complete?:" + completeList[i]);
        }

        accessConfFile.close();
        if (isComplete == Byte.MAX_VALUE) {
            commonCacheOperator.putHash(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "true");
            //stringRedisTemplate.opsForHash().put(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "true");
            commonCacheOperator.put(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName);
            //stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName);
            return true;
        } else {
            if (!commonCacheOperator.hasHashKey(Constants.FILE_UPLOAD_STATUS, param.getMd5())) {
                commonCacheOperator.putHash(Constants.FILE_UPLOAD_STATUS, param.getMd5(), "false");
            }
            if (commonCacheOperator.hasKey(Constants.FILE_MD5_KEY + param.getMd5())) {
                //stringRedisTemplate.opsForValue().set(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName + ".conf");
                commonCacheOperator.put(Constants.FILE_MD5_KEY + param.getMd5(), uploadDirPath + "/" + fileName + ".conf");
            }
            return false;
        }
    }

    /**
     * 文件重命名
     *
     * @param toBeRenamed   将要修改名字的文件
     * @param toFileNewName 新的名字
     * @return
     */
    public boolean renameFile(File toBeRenamed, String toFileNewName) {
        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            log.info("File does not exist: " + toBeRenamed.getName());
            return false;
        }
        String p = toBeRenamed.getParent();
        File newFile = new File(p + File.separatorChar + toFileNewName);
        //修改文件名
        return toBeRenamed.renameTo(newFile);
    }

    @Override
    public List<String> getMissChunk(String pathValue) {
        List<String> missChunkList = new LinkedList<>();
        try {
            File confFile = new File(pathValue);
            byte[] completeList = new byte[0];
            completeList = FileUtils.readFileToByteArray(confFile);
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i + "");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return missChunkList;
    }

}
