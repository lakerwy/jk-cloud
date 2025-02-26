package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BizFormTravelTotal {

    /** 申请报销金额总计 */
    @ApiModelProperty(value = "申请报销金额总计", position = 1)
    private BigDecimal applyAmountTotal;

    /** 核定总额合计 */
    @ApiModelProperty(value = "核定总额合计", position = 2)
    private BigDecimal verifyAmountTotal;

    /** 出纳支付金额合计 */
    @ApiModelProperty(value = "出纳支付金额合计", position = 3)
    private BigDecimal cnPayAmountTotal;

    /** 核定冲借支金额合计 */
    @ApiModelProperty(value = "核定冲借支金额合计", position = 4)
    private BigDecimal verifyOffsetAmountTotal;

    /** 拨付金额合计 */
    @ApiModelProperty(value = "拨付金额合计", position = 5)
    private BigDecimal verifyPayAmountTotal;

}
