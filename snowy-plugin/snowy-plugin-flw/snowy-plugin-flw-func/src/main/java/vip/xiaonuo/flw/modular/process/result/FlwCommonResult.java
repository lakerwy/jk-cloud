package vip.xiaonuo.flw.modular.process.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程公共结果
 */
@Data
public class FlwCommonResult {

    @ApiModelProperty(value = "业务主键id", position = 1)
    private String id;

    @ApiModelProperty(value = "业务sn值", position = 2)
    private String sn;
}
