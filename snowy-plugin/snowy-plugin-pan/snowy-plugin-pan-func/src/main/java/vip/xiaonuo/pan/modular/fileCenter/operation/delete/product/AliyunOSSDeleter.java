package vip.xiaonuo.pan.modular.fileCenter.operation.delete.product;

import com.aliyun.oss.OSS;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.Deleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.domain.DeleteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

@Component
public class AliyunOSSDeleter extends Deleter {

    @Override
    public void delete(DeleteFile deleteFile) {
        OSS ossClient = DevFileAliyunUtil.getClient();
        try {
            ossClient.deleteObject(DevFileAliyunUtil.getDefaultBucketName(), FileCenterUtil.getAliyunObjectNameByFileUrl(deleteFile.getFileUrl()));
        } finally {
            ossClient.shutdown();
        }
        deleteCacheFile(deleteFile);
    }
}
