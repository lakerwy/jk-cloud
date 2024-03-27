package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundEditParam;

import java.util.List;

/**
 * 项目回款 编辑 实例
 */

@Getter
@Setter
public class InvoicePrjEdit extends InvoicePrjRefundEditParam {

    /** 【销售合同】-集合 */
    @ApiModelProperty(value = "【销售合同】-集合", position = 100)
    private List<InvoicePrjContract> contractList;
}
