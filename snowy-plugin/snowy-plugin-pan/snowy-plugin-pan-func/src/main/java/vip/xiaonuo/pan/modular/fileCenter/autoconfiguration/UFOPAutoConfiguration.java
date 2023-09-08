package vip.xiaonuo.pan.modular.fileCenter.autoconfiguration;


import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.modular.file.util.DevFileLocalUtil;
import vip.xiaonuo.pan.modular.fileCenter.factory.UFOPFactory;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.product.AliyunOSSUploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.product.MinioUploader;
import vip.xiaonuo.pan.modular.fileCenter.util.RedisUtil;
import vip.xiaonuo.pan.modular.fileCenter.util.UFOPUtils;
import vip.xiaonuo.pan.modular.fileCenter.util.concurrent.locks.RedisLock;


@Component
public class UFOPAutoConfiguration {

    @Bean
    public UFOPFactory ufopFactory() {
        UFOPUtils.LOCAL_STORAGE_PATH = DevFileLocalUtil.getUploadFileFolder();
        String bucketName = "pan";
        if (StringUtils.isNotEmpty(bucketName)) {
            UFOPUtils.ROOT_PATH = "pan";
        } else {
            UFOPUtils.ROOT_PATH = "upload";
        }
        return new UFOPFactory();
    }

    @Bean
    public AliyunOSSUploader aliyunOSSUploader() {
        return new AliyunOSSUploader();
    }
    @Bean
    public MinioUploader minioUploader() {
        return new MinioUploader();
    }
    @Bean
    public RedisLock redisLock() {
        return new RedisLock();
    }
    @Bean
    public RedisUtil redisUtil() {
        return new RedisUtil();
    }

}
