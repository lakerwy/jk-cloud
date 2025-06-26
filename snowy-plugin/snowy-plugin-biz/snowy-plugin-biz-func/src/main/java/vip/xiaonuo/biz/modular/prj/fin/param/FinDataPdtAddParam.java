package vip.xiaonuo.biz.modular.prj.fin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 生产院添加参数
 *
 * @author yy
 * @date  2025/06/25 14:37
 **/
@Getter
@Setter
public class FinDataPdtAddParam {

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 2)
    private String contractId;

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 支付日期 */
    @ApiModelProperty(value = "支付日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bfDate;

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

//    /** 其他费用 */
//    @ApiModelProperty(value = "其他费用", position = 9)
//    private BigDecimal bfOther;
//
//    /** 扣税差 */
//    @ApiModelProperty(value = "扣税差", position = 10)
//    private BigDecimal kkTax;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;

    /** 其他扣款事项 */
    @ApiModelProperty(value = "其他扣款事项", position = 12)
    private String kkOtherContent;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 13)
    private String content;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 19)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 20)
    private String attach;

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
