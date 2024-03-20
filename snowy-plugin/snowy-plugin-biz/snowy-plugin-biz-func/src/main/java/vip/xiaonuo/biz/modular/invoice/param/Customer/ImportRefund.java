package vip.xiaonuo.biz.modular.invoice.param.Customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;

import java.util.List;

/**
 * 导入 项目回款信息
 */

@Getter
@Setter
public class ImportRefund {

    /** 项目回款信息-集合 */
    @ApiModelProperty(value = "项目回款信息-集合", position = 100)
    private List<ImportRefundParam> importData;

}
