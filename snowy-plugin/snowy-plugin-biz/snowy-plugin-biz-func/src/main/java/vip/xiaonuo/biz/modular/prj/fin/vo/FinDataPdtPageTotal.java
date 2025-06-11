package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class FinDataPdtPageTotal {

    /** 拨付外部产值 */
    @ApiModelProperty(value = "拨付外部产值", position = 5)
    private BigDecimal bfOutput;

    /** 拨付经营费 */
    @ApiModelProperty(value = "拨付经营费", position = 6)
    private BigDecimal bfExpense;

    /** 拨付分包款 */
    @ApiModelProperty(value = "拨付分包款", position = 7)
    private BigDecimal bfSub;

    /** 拨付劳务费 */
    @ApiModelProperty(value = "拨付劳务费", position = 8)
    private BigDecimal bfLabor;

    /** 拨付其他费用 */
    @ApiModelProperty(value = "拨付其他费用", position = 9)
    private BigDecimal bfOther;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 10)
    private BigDecimal kkTax;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;
}
