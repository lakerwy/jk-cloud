package vip.xiaonuo.dev.modular.config.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 更新系统配置项值
 */
@Getter
@Setter
public class DevConfigSetParam {
    /** 配置键 */
    @ApiModelProperty(value = "配置键", required = true, position = 1)
    @NotBlank(message = "configKey不能为空")
    private String configKey;

    /** 配置值 */
    @ApiModelProperty(value = "配置值",  position = 2)
    private String configValue;
}
