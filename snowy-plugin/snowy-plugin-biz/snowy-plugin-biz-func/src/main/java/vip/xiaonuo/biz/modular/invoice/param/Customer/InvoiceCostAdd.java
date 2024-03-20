package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostAddParam;

import java.util.List;

/**
 * 发票系统 - 成本发票新增
 */

@Getter
@Setter
public class InvoiceCostAdd extends InvoiceCostAddParam {

    /** 【成本发票】发票信息-集合 */
    @ApiModelProperty(value = "【成本发票】发票信息-集合", position = 100)
    private List<InvoiceCostBill> billList;

}
