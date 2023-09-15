package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/11 9:13
 */
@Getter
@Setter
public class DownloadFileParam {

    @ApiModelProperty(value = "文件主键id集合---userFile 的主键ID")
    @NotEmpty
    private List<String> ids;

    @ApiModelProperty(value = "批次号")
    private String shareBatchNum;

    @ApiModelProperty(value = "提取码")
    private String extractionCode;

}
