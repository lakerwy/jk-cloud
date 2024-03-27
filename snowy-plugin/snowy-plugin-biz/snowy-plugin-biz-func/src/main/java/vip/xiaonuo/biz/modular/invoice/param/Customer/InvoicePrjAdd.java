package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundAddParam;

import java.util.List;

/**
 * 项目回款 新增 实例
 */

@Getter
@Setter
public class InvoicePrjAdd extends InvoicePrjRefundAddParam {

    /** 【销售合同】-集合 */
    @ApiModelProperty(value = "【销售合同】-集合", position = 100)
    private List<InvoicePrjContract> contractList;
}
