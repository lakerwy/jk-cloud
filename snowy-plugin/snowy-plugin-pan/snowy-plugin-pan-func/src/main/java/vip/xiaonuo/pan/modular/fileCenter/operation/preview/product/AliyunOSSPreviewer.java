package vip.xiaonuo.pan.modular.fileCenter.operation.preview.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.domain.ThumbImage;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.Previewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.domain.PreviewFile;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.InputStream;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class AliyunOSSPreviewer extends Previewer {

    public AliyunOSSPreviewer(ThumbImage thumbImage) {
        setThumbImage(thumbImage);
    }


    @Override
    protected InputStream getInputStream(PreviewFile previewFile) {
        OSS ossClient = DevFileAliyunUtil.getClient();
        OSSObject ossObject = ossClient.getObject(DevFileAliyunUtil.getDefaultBucketName(),
                UFOPUtils.getAliyunObjectNameByFileUrl(previewFile.getFileUrl()));
        InputStream inputStream = ossObject.getObjectContent();
        previewFile.setOssClient(ossClient);
        return inputStream;
    }

}
