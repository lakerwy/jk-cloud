package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class FinDataPdtPageTotal {

    /** 外部产值及付款 - 本次应付外部产值 */
    @ApiModelProperty(value = "外部产值及付款 - 本次应付外部产值", position = 5)
    private BigDecimal bfOutput;

    /** 经营费及付款 - 本次应付经营费 */
    @ApiModelProperty(value = "经营费及付款 - 本次应付经营费", position = 6)
    private BigDecimal bfExpense;

    /** 分包及付款 - 本次应付分包款 */
    @ApiModelProperty(value = "分包及付款 - 本次应付分包款", position = 7)
    private BigDecimal bfSub;

    /** 劳务费 - 本次应付劳务费 */
    @ApiModelProperty(value = "劳务费 - 本次应付劳务费", position = 8)
    private BigDecimal bfLabor;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;

    /** 拨付合计 */
    @ApiModelProperty(value = "拨付合计", position = 24)
    private BigDecimal bfTotal;

    /** 集团管理费 */
    @ApiModelProperty(value = "集团管理费", position = 25)
    private BigDecimal jtManage;

    /** 外部产值及付款 - 扣管理费 */
    @ApiModelProperty(value = "外部产值及付款 - 扣管理费", position = 26)
    private BigDecimal outputKManage;

    /** 外部产值及付款 - 扣税差 */
    @ApiModelProperty(value = "外部产值及付款 - 扣税差", position = 27)
    private BigDecimal outputKTax;

    /** 外部产值及付款 - 扣签字费 */
    @ApiModelProperty(value = "外部产值及付款 - 扣签字费", position = 28)
    private BigDecimal outputKSign;

    /** 外部产值及付款 - 实付金额 */
    @ApiModelProperty(value = "外部产值及付款 - 实付金额", position = 29)
    private BigDecimal outputRealPay;

    /** 外部产值及付款 - 未付金额 */
    @ApiModelProperty(value = "外部产值及付款 - 未付金额", position = 30)
    private BigDecimal outputNoPay;

    /** 经营费及付款 - 扣税差 */
    @ApiModelProperty(value = "经营费及付款 - 扣税差", position = 31)
    private BigDecimal expenseKTax;

    /** 经营费及付款 - 实付金额 */
    @ApiModelProperty(value = "经营费及付款 - 实付金额", position = 32)
    private BigDecimal expenseRealPay;

    /** 经营费及付款 - 未付金额 */
    @ApiModelProperty(value = "经营费及付款 - 未付金额", position = 33)
    private BigDecimal expenseNoPay;

    /** 分包及付款 - 扣税差 */
    @ApiModelProperty(value = "分包及付款 - 扣税差", position = 34)
    private BigDecimal subKTax;

    /** 分包及付款 - 实付金额 */
    @ApiModelProperty(value = "分包及付款 - 实付金额", position = 35)
    private BigDecimal subRealPay;

    /** 分包及付款 - 未付金额 */
    @ApiModelProperty(value = "分包及付款 - 未付金额", position = 36)
    private BigDecimal subNoPay;

    /** 劳务费 - 扣税差 */
    @ApiModelProperty(value = "劳务费 - 扣税差", position = 37)
    private BigDecimal laborKTax;

    /** 劳务费 - 实付金额 */
    @ApiModelProperty(value = "劳务费 - 实付金额", position = 38)
    private BigDecimal laborRealPay;

    /** 劳务费 - 未付金额 */
    @ApiModelProperty(value = "劳务费 - 未付金额", position = 39)
    private BigDecimal laborNoPay;
}
