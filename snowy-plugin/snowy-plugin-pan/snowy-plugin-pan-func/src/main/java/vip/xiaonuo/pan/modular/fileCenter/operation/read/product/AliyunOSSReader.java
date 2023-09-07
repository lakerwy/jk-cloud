package vip.xiaonuo.pan.modular.fileCenter.operation.read.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.io.FilenameUtils;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.modular.file.util.DevFileAliyunUtil;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.Reader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.domain.ReadFile;
import vip.xiaonuo.pan.modular.fileCenter.util.ReadFileUtils;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;

import java.io.IOException;
import java.io.InputStream;

public class AliyunOSSReader extends Reader {

    @Override
    public String read(ReadFile readFile) {
        String fileUrl = readFile.getFileUrl();
        String fileType = FilenameUtils.getExtension(fileUrl);
        OSS ossClient = DevFileAliyunUtil.getClient();
        OSSObject ossObject = ossClient.getObject(DevFileAliyunUtil.getDefaultBucketName(),
                UFOPUtils.getAliyunObjectNameByFileUrl(fileUrl));
        InputStream inputStream = ossObject.getObjectContent();
        try {
            return ReadFileUtils.getContentByInputStream(fileType, inputStream);
        } catch (IOException e) {
            throw new CommonException("读取文件失败", e);
        } finally {
            ossClient.shutdown();
        }
    }

    public InputStream getInputStream(String fileUrl) {
        OSS ossClient = DevFileAliyunUtil.getClient();
        OSSObject ossObject = ossClient.getObject(DevFileAliyunUtil.getDefaultBucketName(),
                UFOPUtils.getAliyunObjectNameByFileUrl(fileUrl));
        return ossObject.getObjectContent();
    }

}
