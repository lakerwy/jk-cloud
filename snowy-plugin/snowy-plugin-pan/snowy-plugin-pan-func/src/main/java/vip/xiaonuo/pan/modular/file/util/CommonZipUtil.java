package vip.xiaonuo.pan.modular.file.util;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.config.service.DevConfigService;
import vip.xiaonuo.dev.modular.server.consts.InitConfConstant;
import vip.xiaonuo.dev.modular.server.util.SftpFileUploader;
import vip.xiaonuo.pan.modular.file.vo.PanSourceFileVo;

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

    @Resource
    DevConfigService devConfigService;

    public Long zip(List<PanSourceFileVo> childrenFileList, String inputFile){
        try {
            log.error("PanSourceFile:压缩文件数量："+childrenFileList.size());
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ZipOutputStream zops = new ZipOutputStream(Files.newOutputStream(Paths.get(inputFile)));
            List<ZipDownloadBean> list = new ArrayList<>();
            for (PanSourceFileVo panSourceFileVo : childrenFileList) {
                File file = new File(panSourceFileVo.getFilePath());
                if(!file.exists()){
                    log.error("文件不存在！panId:"+panSourceFileVo.getFileId()+",fileName:"+panSourceFileVo.getFileName());
                    continue;
                }
                ZipDownloadBean bean = new ZipDownloadBean(null, panSourceFileVo.getFilePath(), panSourceFileVo.getFileName());
                bean.setFileLength(file.length());
                list.add(bean);
            }
            log.error("ZipDownloadBean:压缩文件数量："+list.size());
            CountDownLatch countDownLatch = new CountDownLatch(list.size());
            Map<String, Long> sizeMap = new HashMap<>();
            sizeMap.put("fileSize", 0L);
            Lock lock = new ReentrantLock();
            for (ZipDownloadBean bean : list) {
                executorService.execute(() -> {
                    try (InputStream ips = Files.newInputStream(Paths.get(bean.getAbsolutePath()))) {
                        lock.lock();
                        try {
                            // 使用文件名（即相对路径）创建ZipEntry
                            ZipEntry zipEntry = new ZipEntry(bean.getPath());
                            zops.putNextEntry(zipEntry);

                            byte[] bytes = new byte[bean.getFileLength() == null ? (1024 * 10) : bean.getFileLength().intValue()];
                            Long fileSize = bean.getFileLength();
                            sizeMap.put("fileSize", sizeMap.get("fileSize") + fileSize);
                            int len;
                            while ((len = ips.read(bytes)) > 0) {
                                zops.write(bytes, 0, len);
                            }
                            zops.closeEntry();
                        } finally {
                            lock.unlock();
                        }
                        zops.flush();
                    } catch (Exception e) {// ...
                        log.error("读取文件异常，文件路径：" + bean.getAbsolutePath());
                        throw new CommonException(Thread.currentThread().getName() + "读取文件异常，文件路径：" + bean.getAbsolutePath());
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            zops.close(); // 关闭流
            executorService.shutdownNow();
            return sizeMap.get("fileSize") != null ? sizeMap.get("fileSize") : 0l;
        }catch (Exception e){
            throw new CommonException("压缩失败！e:"+e.getMessage());
        }
    }

    /**
     * 递归获取文件及其路径信息
     *
     * @param file 文件对象
     * @param fileList 文件信息集合
     */
    private void getFileList(File file, List<ZipDownloadBean> fileList) {
        if (file == null) { return; }

        // 如果是文件夹，递归获取子文件及其路径信息
        if (file.isDirectory()) {
            for (File tempFile : Objects.requireNonNull(file.listFiles())) {
                getFileList(tempFile, fileList);
            }
        } else if (file.isFile() && file.length() != 0) {// 如果是文件，则只保留文件名作为相对路径，并添加到ZipDownloadBean中
            String fileName = file.getName();
            log.error("ZipDownloadBean03:压缩文件名称："+fileName);
            ZipDownloadBean bean = new ZipDownloadBean(null, file.getAbsolutePath(), fileName);
            bean.setFileLength(file.length());
            fileList.add(bean);
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
        log.error("压缩命令日志打压zip："+script);
        sftpFileUploader.execZipRemote(script,InitConfConstant.EPC_SOURCE_BULID_PATH+InitConfConstant.BUILD_SH);
    }

}
