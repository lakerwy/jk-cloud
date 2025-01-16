package vip.xiaonuo.biz.modular.prjFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程款拨付 - 款项拨付添加参数
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
@Getter
@Setter
public class PrjFeeAllotmentItemAddParam {

    /** 关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID） */
    @ApiModelProperty(value = "关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID）", position = 2)
    private String prjFeeAllotmentId;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 3)
    private String contractNo;

    /** 乙方（分包单位名称、收款单位） */
    @ApiModelProperty(value = "乙方（分包单位名称、收款单位）", position = 4)
    private String counterparty;

    /** 业务内容 */
    @ApiModelProperty(value = "业务内容", position = 5)
    private String businessType;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 6)
    private BigDecimal contractAmount;

    /** 发票金额（累计） */
    @ApiModelProperty(value = "发票金额（累计）", position = 7)
    private BigDecimal invoiceAmountAcc;

    /** 已付金额 */
    @ApiModelProperty(value = "已付金额", position = 8)
    private BigDecimal payAmount;

    /** 已付金额中垫付款金额 */
    @ApiModelProperty(value = "已付金额中垫付款金额", position = 9)
    private BigDecimal advancedAmount;

    /** 已开票未付金额 */
    @ApiModelProperty(value = "已开票未付金额", position = 10)
    private BigDecimal invoicedNotPayAmount;

    /** 本次计划拨付金额 */
    @ApiModelProperty(value = "本次计划拨付金额", position = 11)
    private BigDecimal planDisburseAmount;

    /** 出纳支付金额 */
    @ApiModelProperty(value = "出纳支付金额", position = 12)
    private BigDecimal cashierPaymentAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 13)
    private String attachments;

}
