package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCost;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;

import java.util.List;

/**
 * 发票系统：成本发票Vo(携带信息)
 */

@Getter
@Setter
public class InvoiceCostVo extends InvoiceCost {
    /** 【成本发票】发票信息-集合 */
    @ApiModelProperty(value = "【成本发票】发票信息-集合", position = 100)
    private List<InvoiceCostBill> billList;
}
