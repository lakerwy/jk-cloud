package vip.xiaonuo.sys.modular.hr.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 物资领用申请（附表） - 物资清单+退回清单 Id参数
 *
 * @author yy
 * @date  2025/05/23 14:35
 **/
@Getter
@Setter
public class GoodsItemIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
