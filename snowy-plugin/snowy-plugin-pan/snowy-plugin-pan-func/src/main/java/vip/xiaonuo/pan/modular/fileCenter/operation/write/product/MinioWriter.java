package vip.xiaonuo.pan.modular.fileCenter.operation.write.product;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.Writer;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.domain.WriteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioWriter extends Writer {


    public MinioWriter(){

    }

    @Override
    public void write(InputStream inputStream, WriteFile writeFile) {


        try {
            MinioClient minioClient = DevFileMinIoUtil.getClient();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).build());
            if(!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).build());
            }

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).object(FileCenterUtil.getAliyunObjectNameByFileUrl(writeFile.getFileUrl())).stream(
                                    inputStream, inputStream.available(), -1)
//                            .contentType("video/mp4")
                            .build());

        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

}
