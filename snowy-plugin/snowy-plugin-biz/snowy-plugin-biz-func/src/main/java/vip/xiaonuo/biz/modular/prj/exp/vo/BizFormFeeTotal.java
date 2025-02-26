package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BizFormFeeTotal {

    /** 申请金额合计 */
    @ApiModelProperty(value = "申请金额合计", position = 1)
    private BigDecimal applyAmountTotal;

    /** 财务核定金额合计 */
    @ApiModelProperty(value = "财务核定金额合计", position = 2)
    private BigDecimal verifyAmountTotal;

    /** 核定冲借支金额合计 */
    @ApiModelProperty(value = "核定冲借支金额合计", position = 3)
    private BigDecimal verifyOffsetAmountTotal;

    /** 核定付款金额合计 */
    @ApiModelProperty(value = "核定付款金额合计", position = 4)
    private BigDecimal verifyPayAmountTotal;

    /** 出纳支付金额合计 */
    @ApiModelProperty(value = "出纳支付金额合计", position = 5)
    private BigDecimal cnPayAmountTotal;

}
