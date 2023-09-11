package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/11 9:13
 */
@Getter
@Setter
public class DownloadFileParam {

    @ApiModelProperty(value = "文件主键id---userFile 的主键ID")
    private String id;

    @ApiModelProperty(value = "批次号")
    private String shareBatchNum;

    @ApiModelProperty(value = "提取码")
    private String extractionCode;
}
