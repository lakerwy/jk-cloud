package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleCost;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleAddParam;

import java.util.List;

/**
 * 发票系统 - 销售发票新增
 */

@Getter
@Setter
public class InvoiceSaleAdd extends InvoiceSaleAddParam {

    /** 【销售发票】开票信息-集合*/
    @ApiModelProperty(value = "【销售发票】开票信息-集合", position = 100)
    private List<InvoiceSaleKp> kpList;

    /** 【销售发票】成本票应收-集合 */
    @ApiModelProperty(value = "【销售发票】成本票应收-集合", position = 100)
    private List<InvoiceSaleCost> costList;

    /** 【销售发票】开票确认-集合 */
    @ApiModelProperty(value = "【销售发票】开票确认-集合", position = 100)
    private List<InvoiceSaleBill> billList;

}
