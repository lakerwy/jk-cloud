package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 销售发票 - 开票确认 - 统计值
 */


@Getter
@Setter
public class InvoiceSaleBillTotal {

    /** 外键：BIZ_INVOICE_SALE.ID */
    @ApiModelProperty(value = "外键：BIZ_INVOICE_SALE.ID", position = 1)
    private String invoiceSaleId;

    /** 价税合计 - 小计 */
    @ApiModelProperty(value = "价税合计 - 小计 ", position = 2)
    private BigDecimal amountTotal;

    /** 税额 - 小计 */
    @ApiModelProperty(value = "税额 - 小计 ", position = 3)
    private BigDecimal taxAmountTotal;
}
