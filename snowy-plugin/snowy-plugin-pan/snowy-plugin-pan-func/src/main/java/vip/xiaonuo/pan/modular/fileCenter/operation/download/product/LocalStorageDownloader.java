package vip.xiaonuo.pan.modular.fileCenter.operation.download.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;

import java.io.*;
import java.nio.file.Files;

@Slf4j
@Component
public class LocalStorageDownloader extends Downloader {

    @Override
    public InputStream getInputStream(DownloadFile downloadFile) {
        //设置文件路径
//        File file = new File(FileCenterUtil.getStaticPath() + downloadFile.getFileUrl());
        File file = new File(downloadFile.getFileUrl());
        InputStream inputStream = null;
        try {
            if (downloadFile.getRange() != null) {
                RandomAccessFile randowAccessFile = new RandomAccessFile(file, "r");
                randowAccessFile.seek(downloadFile.getRange().getStart());
                byte[] bytes = new byte[downloadFile.getRange().getLength()];
                randowAccessFile.read(bytes);
                inputStream = new ByteArrayInputStream(bytes);
            } else {
                inputStream = Files.newInputStream(file.toPath());
            }
        } catch (IOException e) {
            log.error(""+e);
        }
        return inputStream;

    }
}
