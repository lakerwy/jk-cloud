package vip.xiaonuo.flw.modular.process.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 公共查询参数
 *
 * @author mayike
 * @date 2023/7/27 10:36
 */
@Setter
@Getter
public class FlwCommonParam {

    /** 模型id */
    @ApiModelProperty(value = "模型id", required = true, position = 1)
    @NotBlank(message = "modelId不能为空")
    private String modelId;
}
