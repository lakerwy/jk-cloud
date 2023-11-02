package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/11 9:15
 */
@Setter
@Getter
public class PreviewParam {

    private String userFileId;
    @ApiModelProperty("批次号")
    private String shareBatchNum;
    @ApiModelProperty("提取码")
    private String extractionCode;
    private String isMin;
    private Integer platform;
    private String url;
    private String token;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享  注：（不传或不在指定范围） 不做权限校验")
    private String rightType;
}
