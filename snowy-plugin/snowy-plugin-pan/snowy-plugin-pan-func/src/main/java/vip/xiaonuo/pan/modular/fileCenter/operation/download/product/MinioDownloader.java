package vip.xiaonuo.pan.modular.fileCenter.operation.download.product;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MinioDownloader extends Downloader {

    @Override
    public InputStream getInputStream(DownloadFile downloadFile) {
        InputStream inputStream = null;
        try {

            MinioClient minioClient = DevFileMinIoUtil.getClient();
            if (downloadFile.getRange() != null) {
                inputStream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(DevFileMinIoUtil.getDefaultBucketName())
                        .object(downloadFile.getFileUrl())
                        .offset((long) downloadFile.getRange().getStart())
                        .length((long) downloadFile.getRange().getLength())
                        .build());
            } else {
                inputStream = minioClient.getObject(GetObjectArgs.builder()
                        .bucket(DevFileMinIoUtil.getDefaultBucketName())
                        .object(downloadFile.getFileUrl())
                        .build());
            }


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e.getMessage());
        }


        return inputStream;
    }

}
