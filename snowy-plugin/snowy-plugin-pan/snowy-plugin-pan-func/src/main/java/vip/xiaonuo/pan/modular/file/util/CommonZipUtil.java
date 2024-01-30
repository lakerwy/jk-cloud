package vip.xiaonuo.pan.modular.file.util;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.util.SftpFileUploader;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class CommonZipUtil {
    private String SYSTEM_SOURCE_IP = "SYSTEM_SOURCE_IP";
    private String SOURCE_SERVER_NAME = "SOURCE_SERVER_NAME";
    private String SOURCE_SERVER_PS = "SOURCE_SERVER_PS";

    @Resource
    DevConfigService devConfigService;
    public Long zip(List<String> sourceFile,String inputFile){
        try{
            ExecutorService executorService = Executors.newFixedThreadPool(10);//初始化线程池
            ZipOutputStream zops = new ZipOutputStream(Files.newOutputStream(Paths.get(inputFile))); //初始化ZIP输出流
            List<ZipDownloadBean> list = new ArrayList<>();
            sourceFile.forEach(sourcePath -> {
                File file = new File(sourcePath);
                getFileList(file, list);//递归读取源文件夹下所有文件，并记录其路径信息
            });
            CountDownLatch countDownLatch = new CountDownLatch(list.size()); //初始化计数器，线程等待

            Map<String,Long> sizeMap = new HashMap<>();
            sizeMap.put("fileSize",0l);
            Lock lock = new ReentrantLock();// 锁初始化
            for (ZipDownloadBean bean : list) {
                executorService.execute(() -> {
                    try(InputStream ips = Files.newInputStream(Paths.get(bean.getPath()))) {
                        String relativePath = bean.getPath();
                        String fileName = relativePath.contains("\\") ? relativePath.substring(relativePath.lastIndexOf("\\") + 1) : relativePath;

                        //String fileName = inputFile.contains("\\") ? inputFile.substring(inputFile.lastIndexOf("\\") + 1) : inputFile;
                        //String relativePath = bean.getPath();
                        //String fileName = relativePath.contains("\\") ? relativePath.substring(relativePath.lastIndexOf("\\") + 1) : relativePath;
                        ZipEntry zipEntry = new ZipEntry(fileName);//初始化ZipEntry

                        lock.lock();
                        zops.putNextEntry(zipEntry);
                        byte[] bytes = new byte[bean.getFileLength() == null ? (1024*10) : bean.getFileLength().intValue()];
                        Long fileSize = bean.getFileLength();
                        sizeMap.put("fileSize",sizeMap.get("fileSize")+fileSize);
                        int len;
                        // 通过二进制流写文件
                        while ((len = ips.read(bytes)) > 0) {
                            zops.write(bytes, 0, len);
                        }
                        zops.closeEntry();// 关闭ZipEntry
                        lock.unlock();
                        zops.flush();
                    } catch (Exception e) {
                        throw new CommonException(Thread.currentThread().getName() + "读取文件异常，文件路径：" + bean.getPath());
                    }
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await();
            zops.close(); // 关闭流
            //long end = System.currentTimeMillis();
            //System.out.println("耗时：" + (end - start));
            executorService.shutdownNow();
            return sizeMap.get("fileSize") != null ? sizeMap.get("fileSize") : 0l;
        }catch (Exception e){
            throw new CommonException("解压失败！e:"+e.getMessage());
        }
    }

    /**
     * 递归获取文件及其路径信息
     *
     * @param file 文件对象
     * @param fileList 文件信息集合
     */
    private void getFileList(File file, List<ZipDownloadBean> fileList){
        if (file == null) { return; }
        // 如果是文件夹，就继续递归
        if (file.isDirectory()) {
            for (File tempFile : Objects.requireNonNull(file.listFiles())) {
                getFileList(tempFile, fileList);
            }
        }
        // 如果是文件，就解析一下，放到文件集合中
        if (file.isFile()) {
            if(file.length() != 0){
                ZipDownloadBean bean = new ZipDownloadBean(null, file.getAbsolutePath(),file.getPath());
                bean.setFileLength(file.length());
                fileList.add(bean);
            }
        }
    }


    public void zipUnixCommandExec(String zipPath,List<String> files){
        String systemSourceIp = devConfigService.getValueByKey(InitConfConstant.SYSTEM_SOURCE_IP);//源服务器IP
        if (StrUtil.isEmpty(systemSourceIp)) {
            systemSourceIp = "10.0.33.82";
        }
        String sourceServerName = devConfigService.getValueByKey(InitConfConstant.SOURCE_SERVER_NAME);//源服务器账号
        if (StrUtil.isEmpty(sourceServerName)) {
            sourceServerName = "root";
        }
        String sourceServerPs = devConfigService.getValueByKey(InitConfConstant.SOURCE_SERVER_PS);//源服务器密码
        if (StrUtil.isEmpty(sourceServerPs)) {
            sourceServerPs = "Jianke66";
        }
        //String zipName = zipPath+File.separator+"zip_"+ new Date().getTime();
        StringBuffer sourceFile =  new StringBuffer();
        files.forEach(path -> sourceFile.append(path).append(" "));
        String script = String.format("zip  %s  %s",zipPath,sourceFile);
        SftpFileUploader sftpFileUploader = new SftpFileUploader(systemSourceIp,sourceServerName,sourceServerPs,script);
        log.info("压缩命令日志打压zip："+script);
        sftpFileUploader.execZipRemote(script,InitConfConstant.EPC_SOURCE_BULID_PATH+InitConfConstant.BUILD_SH);
    }

}
