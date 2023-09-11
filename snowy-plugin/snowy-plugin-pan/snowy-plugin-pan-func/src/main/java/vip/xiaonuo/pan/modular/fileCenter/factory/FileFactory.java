package vip.xiaonuo.pan.modular.fileCenter.factory;
import vip.xiaonuo.pan.modular.fileCenter.config.FileCenterProperties;
import vip.xiaonuo.pan.modular.fileCenter.enums.StorageTypeEnum;
import vip.xiaonuo.pan.modular.fileCenter.entity.ThumbImage;
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

public class FileFactory {
    private String storageType;
    private ThumbImage thumbImage;
    @Resource
    private AliyunOSSUploader aliyunOSSUploader;
    @Resource
    private MinioUploader minioUploader;

    public FileFactory() {
    }

    //todo
    public FileFactory(FileCenterProperties fileCenterProperties) {
        this.storageType = fileCenterProperties.getStorageType();
        this.thumbImage = fileCenterProperties.getThumbImage();
    }

    public Uploader getUploader() {

        int type = Integer.parseInt(storageType);
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
            previewer = new LocalStoragePreviewer(thumbImage);
        } else if (StorageTypeEnum.ALIYUN_OSS.getCode() == storageType) {
            previewer = new AliyunOSSPreviewer(thumbImage);
        } else if (StorageTypeEnum.MINIO.getCode() == storageType) {
            previewer = new MinioPreviewer(thumbImage);
        }
        return previewer;
    }

    public Copier getCopier() {
        int type = Integer.parseInt(storageType);
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
