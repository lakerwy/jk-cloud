package vip.xiaonuo.pan.modular.fileCenter.operation.upload.domain;

import lombok.Data;
import vip.xiaonuo.pan.modular.fileCenter.enums.StorageTypeEnum;
import vip.xiaonuo.pan.modular.fileCenter.enums.UploadFileStatusEnum;

import java.awt.image.BufferedImage;

@Data
public class UploadFileResult {
    private String fileName;
    private String extendName;
    private long fileSize;
    private String fileUrl;
    private String identifier;
    private StorageTypeEnum storageType;
    private UploadFileStatusEnum status;
    private BufferedImage bufferedImage;

}
