package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 成本发票 - 成本确认表同一销售发票流水号下成本发票价税合计金额小计 - 统计值
 */


@Getter
@Setter
public class InvoiceCostPrvBillTotal {

    /** 销售发票申请-流水号 = 成本发票申请-开票申请编号(销售发票流程SN) */
    @ApiModelProperty(value = "销售发票申请-流水号 = 成本发票申请-开票申请编号(销售发票流程SN)", position = 1)
    private String KP_SN;

    /** 供应商名称 */
    @ApiModelProperty(value = "供应商名称", position = 2)
    private String prvName;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 3)
    private Float taxRate;

    /** 价税合计 - 小计 */
    @ApiModelProperty(value = "价税合计 - 小计 ", position = 4)
    private BigDecimal amountTotal;

    /** 税额 - 小计 */
    @ApiModelProperty(value = "税额 - 小计 ", position = 5)
    private BigDecimal taxAmountTotal;
}
