package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleCost;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;
import vip.xiaonuo.biz.modular.invoice.param.*;

import java.util.List;

/**
 * 发票系统 - 销售发票修改【流程中修改需要提供-雪花算法Id，修改找到对应记录，避免每次edit全部删除，又全部新增】
 */

@Getter
@Setter
public class InvoiceSaleEdit extends InvoiceSaleEditParam {

    /** 【销售发票】开票信息-集合 */
    @ApiModelProperty(value = "【销售发票】开票信息-集合", position = 100)
    private List<InvoiceSaleKp> kpList;

    /** 【销售发票】成本票应收-集合 */
    @ApiModelProperty(value = "【销售发票】成本票应收-集合", position = 100)
    private List<InvoiceSaleCost> costList;

    /** 【销售发票】开票确认-集合 */
    @ApiModelProperty(value = "【销售发票】开票确认-集合", position = 100)
    private List<InvoiceSaleBill> billList;

}
