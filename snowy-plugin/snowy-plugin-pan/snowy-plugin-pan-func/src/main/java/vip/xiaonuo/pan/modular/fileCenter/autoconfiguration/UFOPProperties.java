package vip.xiaonuo.pan.modular.fileCenter.autoconfiguration;

import lombok.Data;
import vip.xiaonuo.dev.modular.file.util.DevFileLocalUtil;
import vip.xiaonuo.pan.modular.fileCenter.domain.ThumbImage;

@Data
public class UFOPProperties {

    private String storageType = "0";
    private String localStoragePath = DevFileLocalUtil.getUploadFileFolder();
    private ThumbImage thumbImage = new ThumbImage();
}
