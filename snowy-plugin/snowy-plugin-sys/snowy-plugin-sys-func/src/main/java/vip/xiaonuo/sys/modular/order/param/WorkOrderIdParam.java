package vip.xiaonuo.sys.modular.order.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 事件处理单Id参数
 *
 * @author yong.ye
 * @date  2025/01/13 15:34
 **/
@Getter
@Setter
public class WorkOrderIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
