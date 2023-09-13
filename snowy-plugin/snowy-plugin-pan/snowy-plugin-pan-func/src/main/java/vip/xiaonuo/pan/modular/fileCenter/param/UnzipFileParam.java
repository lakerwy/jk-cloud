package vip.xiaonuo.pan.modular.fileCenter.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/12 8:45
 */
@Setter
@Getter
public class UnzipFileParam {
    @ApiModelProperty(value = "文件id", required = true)
    private String userFileId;

    @ApiModelProperty(value = "解压模式 0-解压到当前文件夹， 1-自动创建该文件名目录，并解压到目录里， 2-手动选择解压目录", required = true)
    private int unzipMode;

    @ApiModelProperty(value = "解压目的文件目录，仅当 unzipMode 为 2 时必传")
    private String filePath;
}
