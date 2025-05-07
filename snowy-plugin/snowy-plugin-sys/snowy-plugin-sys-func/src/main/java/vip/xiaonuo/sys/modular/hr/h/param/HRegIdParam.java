package vip.xiaonuo.sys.modular.hr.h.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 招待事项登记Id参数
 *
 * @author yy
 * @date  2025/05/06 16:11
 **/
@Getter
@Setter
public class HRegIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
