package vip.xiaonuo.util.modular.log.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 操作日志Id参数
 *
 * @author pzh
 * @date  2025/02/13 16:48
 **/
@Getter
@Setter
public class OperationLogIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
