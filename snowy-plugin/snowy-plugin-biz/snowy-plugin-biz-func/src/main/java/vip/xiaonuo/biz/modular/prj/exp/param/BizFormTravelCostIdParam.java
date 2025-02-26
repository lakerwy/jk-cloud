package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 费用报销（二期）- 差旅费用报销单 - 成本明细Id参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Getter
@Setter
public class BizFormTravelCostIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
