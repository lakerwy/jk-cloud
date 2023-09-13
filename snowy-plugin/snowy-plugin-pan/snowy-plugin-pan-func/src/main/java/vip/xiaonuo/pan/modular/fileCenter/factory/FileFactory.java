package vip.xiaonuo.pan.modular.fileCenter.factory;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.api.DevConfigApi;
import vip.xiaonuo.pan.modular.fileCenter.enums.StorageTypeEnum;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.Copier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.product.AliyunOSSCopier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.product.LocalStorageCopier;
import vip.xiaonuo.pan.modular.fileCenter.operation.copy.product.MinioCopier;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.Deleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.product.AliyunOSSDeleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.product.LocalStorageDeleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.delete.product.MinioDeleter;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.Downloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.product.AliyunOSSDownloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.product.LocalStorageDownloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.download.product.MinioDownloader;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.Previewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.product.AliyunOSSPreviewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.product.LocalStoragePreviewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.preview.product.MinioPreviewer;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.Reader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.product.AliyunOSSReader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.product.LocalStorageReader;
import vip.xiaonuo.pan.modular.fileCenter.operation.read.product.MinioReader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.Uploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.product.AliyunOSSUploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.product.LocalStorageUploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.upload.product.MinioUploader;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.Writer;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.product.AliyunOSSWriter;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.product.LocalStorageWriter;
import vip.xiaonuo.pan.modular.fileCenter.operation.write.product.MinioWriter;

import javax.annotation.Resource;

@Component
@ConditionalOnWebApplication
public class FileFactory {

    public static Integer storageType = 0;

    public static Integer width = 50;
    public static Integer height = 50;
    @Resource
    private AliyunOSSUploader aliyunOSSUploader;
    @Resource
    private MinioUploader minioUploader;


    private static final String PAN_STORAGE_TYPE = "PAN_STORAGE_TYPE";

    private static final String PAN_THUMB_IMAGE_HEIGHT= "PAN_THUMB_IMAGE_HEIGHT";
    private static final String PAN_THUMB_IMAGE_WIDTH= "PAN_THUMB_IMAGE_WIDTH";

    public  static void  initClient() {
        DevConfigApi devConfigApi = SpringUtil.getBean(DevConfigApi.class);
        String valueByKey = devConfigApi.getValueByKey(PAN_STORAGE_TYPE);
        if(StrUtil.isNotEmpty(valueByKey)){
            storageType = Integer.parseInt(valueByKey);
        }
        String valueByKey1 = devConfigApi.getValueByKey(PAN_THUMB_IMAGE_WIDTH);
        if(StrUtil.isNotEmpty(valueByKey1)){
            width = Integer.parseInt(valueByKey1);
        }
        String valueByKey2 = devConfigApi.getValueByKey(PAN_THUMB_IMAGE_HEIGHT);
        if(StrUtil.isNotEmpty(valueByKey2)){
            height = Integer.parseInt(valueByKey2);
        }
    }

    public Uploader getUploader() {
        initClient();
        int type = storageType;
        Uploader uploader = null;
        if (StorageTypeEnum.LOCAL.getCode() == type) {
            uploader = new LocalStorageUploader();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == type) {
            uploader = aliyunOSSUploader;
        } else if (StorageTypeEnum.MINIO.getCode() == type) {
            uploader = minioUploader;
        }
        return uploader;
    }


    public Downloader getDownloader(int storageType) {
        Downloader downloader = null;
        if (StorageTypeEnum.LOCAL.getCode() == storageType) {
            downloader = new LocalStorageDownloader();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            downloader = new AliyunOSSDownloader();
        } else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            downloader = new MinioDownloader();
        }
        return downloader;
    }


    public Deleter getDeleter(int storageType) {
        Deleter deleter = null;
        if (StorageTypeEnum.LOCAL.getCode() == storageType) {
            deleter = new LocalStorageDeleter();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            deleter = new AliyunOSSDeleter();
        }else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            deleter = new MinioDeleter();
        }
        return deleter;
    }

    public Reader getReader(int storageType) {
        Reader reader = null;
        if (StorageTypeEnum.LOCAL.getCode() == storageType) {
            reader = new LocalStorageReader();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            reader = new AliyunOSSReader();
        }else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            reader = new MinioReader();
        }
        return reader;
    }

    public Writer getWriter(int storageType) {
        Writer writer = null;
        if (StorageTypeEnum.LOCAL.getCode() == storageType) {
            writer = new LocalStorageWriter();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            writer = new AliyunOSSWriter();
        } else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            writer = new MinioWriter();
        }
        return writer;
    }

    public Previewer getPreviewer(int storageType) {
        Previewer previewer = null;
        if (StorageTypeEnum.LOCAL.getCode() == storageType) {
            previewer = new LocalStoragePreviewer();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            previewer = new AliyunOSSPreviewer();
        } else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            previewer = new MinioPreviewer();
        }
        return previewer;
    }

    public Copier getCopier() {
        initClient();
        int type = storageType;
        Copier copier = null;
        if (StorageTypeEnum.LOCAL.getCode() == type) {
            copier = new LocalStorageCopier();
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == type) {
            copier = new AliyunOSSCopier();
        } else if (StorageTypeEnum.MINIO.getCode() == type) {
            copier = new MinioCopier();
        }
        return copier;
    }
}
