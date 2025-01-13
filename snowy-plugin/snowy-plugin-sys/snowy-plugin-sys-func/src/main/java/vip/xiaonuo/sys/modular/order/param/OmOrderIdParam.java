package vip.xiaonuo.sys.modular.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 运维工单Id参数
 *
 * @author yong.ye
 * @date  2025/01/13 16:13
 **/
@Getter
@Setter
public class OmOrderIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
