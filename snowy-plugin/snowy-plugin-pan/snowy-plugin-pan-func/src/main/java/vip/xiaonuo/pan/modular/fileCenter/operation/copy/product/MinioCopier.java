package vip.xiaonuo.pan.modular.fileCenter.operation.copy.product;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.Copier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class MinioCopier extends Copier {

    @Override
    public String copy(InputStream inputStream, CopyFile copyFile) {
        String uuid = UUID.randomUUID().toString();
        String fileUrl = UFOPUtils.getUploadFileUrl(uuid, copyFile.getExtendName());

        MinioClient minioClient;
        try {
            minioClient = DevFileMinIoUtil.getClient();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).build());
            if(!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).object(fileUrl).stream(
                                    inputStream, inputStream.available(), 1024 * 1024 * 5)
//                            .contentType("video/mp4")
                            .build());

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return fileUrl;
    }


}
