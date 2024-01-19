package vip.xiaonuo.pan.modular.file.util;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.config.entity.DevConfig;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.server.param.UnixCommandParam;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
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
                        String relativePath = bean.getPath().substring(bean.getPath().lastIndexOf("CodeFormer\\") + 11);
                        String fileName = relativePath.contains("\\") ? relativePath.substring(relativePath.lastIndexOf("\\") + 1) : relativePath;
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
    private static void getFileList(File file, List<ZipDownloadBean> fileList){
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
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException{
        List<String> sourceFile = new ArrayList<>();
        sourceFile.add("C:\\Users\\Administrator\\Desktop\\测试文件");
        sourceFile.add("C:\\Users\\Administrator\\Desktop\\任务管理SQL.txt");
        String uuid = UUID.randomUUID().toString();
        String inputFile = "C:\\Users\\Administrator\\Desktop\\测试文件\\中文名称.zip";
        //unzip(sourceFile,inputFile);
    }

    public void zipData(){
        UnixCommandParam unixCommandParam = new UnixCommandParam();
        String ip = devConfigService.getValueByKey(SYSTEM_SOURCE_IP);
        ip = StringUtils.isNotEmpty(ip) ? ip : "10.0.33.82";
        String userName = devConfigService.getValueByKey(SOURCE_SERVER_NAME);
        userName = StringUtils.isNotEmpty(userName) ? userName : "root";
        String password = devConfigService.getValueByKey(SOURCE_SERVER_PS);
        password = StringUtils.isNotEmpty(password) ? password : "";
        unixCommandParam.setSshPort(22);
        unixCommandParam.setSshHost(ip);
        unixCommandParam.setSshUsername(userName);
        unixCommandParam.setSshPassword(password);
        zipUnixCommandExec(unixCommandParam);
    }
    public void zipUnixCommandExec(UnixCommandParam unixCommandParam) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(unixCommandParam.getSshUsername(), unixCommandParam.getSshHost(), unixCommandParam.getSshPort());
            session.setPassword(unixCommandParam.getSshPassword());

            session.setConfig("StrictHostKeyChecking", "no"); //允许连接不信任的主机
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(unixCommandParam.getCommand());
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    log.info(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    if(channel.getExitStatus() != 0){
                        log.error("压缩失败，请检查连接信息！exit-status: " + channel.getExitStatus());
                    }
                    log.info("压缩失败，请检查连接信息-exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            log.error("执行远程mysql建表命令时发生错误：" + e.getMessage());
            e.printStackTrace();
            throw new CommonException("执行远程mysql建表命令时发生错误：" + e.getMessage());
        }
    }
}
