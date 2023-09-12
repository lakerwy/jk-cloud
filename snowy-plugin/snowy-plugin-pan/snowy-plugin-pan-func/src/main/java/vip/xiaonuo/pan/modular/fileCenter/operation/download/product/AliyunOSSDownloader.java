package vip.xiaonuo.pan.modular.fileCenter.operation.download.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.domain.DownloadFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

import java.io.InputStream;

@Slf4j
@Component
public class AliyunOSSDownloader extends Downloader {

    @Override
    public InputStream getInputStream(DownloadFile downloadFile) {

        OSS ossClient = DevFileAliyunUtil.getClient();
        OSSObject ossObject;
        if (downloadFile.getRange() != null) {
            GetObjectRequest getObjectRequest = new GetObjectRequest(DevFileAliyunUtil.getDefaultBucketName(),
                    FileCenterUtil.getAliyunObjectNameByFileUrl(downloadFile.getFileUrl()));
            getObjectRequest.setRange(downloadFile.getRange().getStart(),
                    downloadFile.getRange().getStart() + downloadFile.getRange().getLength() - 1);
            ossObject = ossClient.getObject(getObjectRequest);
        } else {
            ossObject = ossClient.getObject(DevFileAliyunUtil.getDefaultBucketName(),
                    FileCenterUtil.getAliyunObjectNameByFileUrl(downloadFile.getFileUrl()));
        }

        InputStream inputStream = ossObject.getObjectContent();

        downloadFile.setOssClient(ossClient);
        return inputStream;
    }


}
