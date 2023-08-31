package vip.xiaonuo.flw.modular.process.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/31 11:22
 */
@Getter
@Setter
public class FlwProcessModelIdParam {

    /** 流程模型id */
    @ApiModelProperty(value = "流程模型id", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
