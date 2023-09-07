package vip.xiaonuo.pan.modular.file.entity;

import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 14:34
 */
@Data
public class UploadFileInfo {

    private String bucketName;
    private String key;
    private String uploadId;
}
