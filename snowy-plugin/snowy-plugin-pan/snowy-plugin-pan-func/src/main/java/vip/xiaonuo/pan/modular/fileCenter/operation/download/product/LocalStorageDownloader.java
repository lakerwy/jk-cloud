package vip.xiaonuo.pan.modular.fileCenter.operation.download.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.*;

@Slf4j
@Component
public class LocalStorageDownloader extends Downloader {

    @Override
    public InputStream getInputStream(DownloadFile downloadFile) {
        //设置文件路径
        File file = new File(UFOPUtils.getStaticPath() + downloadFile.getFileUrl());

        InputStream inputStream = null;
        try {
            if (downloadFile.getRange() != null) {
                RandomAccessFile randowAccessFile = new RandomAccessFile(file, "r");
                randowAccessFile.seek(downloadFile.getRange().getStart());
                byte[] bytes = new byte[downloadFile.getRange().getLength()];
                randowAccessFile.read(bytes);
                inputStream = new ByteArrayInputStream(bytes);
            } else {
                inputStream = new FileInputStream(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;

    }
}
