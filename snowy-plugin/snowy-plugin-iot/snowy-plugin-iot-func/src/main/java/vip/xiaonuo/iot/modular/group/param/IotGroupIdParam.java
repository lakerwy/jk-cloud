package vip.xiaonuo.iot.modular.group.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 设备分组Id参数
 *
 * @author mayike
 * @date  2024/08/06 08:55
 **/
@Getter
@Setter
public class IotGroupIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
