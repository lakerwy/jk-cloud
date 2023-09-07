package vip.xiaonuo.pan.modular.fileCenter.operation.delete.product;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.file.util.DevFileMinIoUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.Deleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.domain.DeleteFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Slf4j
public class MinioDeleter extends Deleter {
    @Override
    public void delete(DeleteFile deleteFile) {

        try {
            MinioClient minioClient = DevFileMinIoUtil.getClient();
            // 从mybucket中删除myobject。
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(DevFileMinIoUtil.getDefaultBucketName()).object(deleteFile.getFileUrl()).build());
            log.info("successfully removed mybucket/myobject");
        } catch (MinioException e) {
            log.error("Error: " + e);
            throw new CommonException("Minio删除文件失败", e);
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new CommonException("Minio删除文件失败", e);
        }
        deleteCacheFile(deleteFile);

    }
}
