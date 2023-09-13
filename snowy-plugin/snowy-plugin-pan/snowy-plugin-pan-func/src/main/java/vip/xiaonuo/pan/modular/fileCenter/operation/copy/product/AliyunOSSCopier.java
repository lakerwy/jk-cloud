package vip.xiaonuo.pan.modular.fileCenter.operation.copy.product;

import com.aliyun.oss.OSS;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.Copier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.domain.CopyFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

import java.io.InputStream;
import java.util.UUID;

@Component
public class AliyunOSSCopier extends Copier {

    @Override
    public String copy(InputStream inputStream, CopyFile copyFile) {
        String uuid = UUID.randomUUID().toString();
        String fileUrl = FileCenterUtil.getUploadFileUrl(uuid, copyFile.getExtendName());
        OSS ossClient = DevFileAliyunUtil.getClient();
        try {
            ossClient.putObject(DevFileAliyunUtil.getDefaultBucketName(), fileUrl, inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
            ossClient.shutdown();
        }
        return fileUrl;
    }

}
