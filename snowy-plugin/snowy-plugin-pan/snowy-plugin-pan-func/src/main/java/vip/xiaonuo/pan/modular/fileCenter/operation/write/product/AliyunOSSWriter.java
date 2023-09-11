package vip.xiaonuo.pan.modular.fileCenter.operation.write.product;

import com.aliyun.oss.OSS;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.Writer;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.domain.WriteFile;
import vip.xiaonuo.pan.modular.fileCenter.util.FileCenterUtil;

import java.io.InputStream;

public class AliyunOSSWriter extends Writer {


    public AliyunOSSWriter(){

    }

    @Override
    public void write(InputStream inputStream, WriteFile writeFile) {
        OSS ossClient = DevFileAliyunUtil.getClient();

        ossClient.putObject(DevFileAliyunUtil.getDefaultBucketName(), FileCenterUtil.getAliyunObjectNameByFileUrl(writeFile.getFileUrl()), inputStream);
        ossClient.shutdown();
    }



}
