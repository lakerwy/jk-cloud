package vip.xiaonuo.pan.modular.file.entity;

import lombok.Data;
import vip.xiaonuo.dev.modular.file.enums.DevFileEngineTypeEnum;
import vip.xiaonuo.pan.modular.file.enums.DevFileEnum;

import java.awt.image.BufferedImage;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/7 14:34
 */
@Data
public class UploadFileResult {
    private String fileName;
    private String extendName;
    private long fileSize;
    private String fileUrl;
    private String identifier;
    private DevFileEngineTypeEnum engineTypeEnum;
    private DevFileEnum devFileEnum;
    private BufferedImage bufferedImage;
}
