package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjRefund;

import java.util.List;

/**
 * 项目回款-实体-导入
 */

@Getter
@Setter
public class InvoicePrjRefundVo extends InvoicePrjRefund {

    /** 【销售合同】-集合 */
    @ApiModelProperty(value = "【销售合同】-集合", position = 100)
    private List<InvoicePrjContract> contractList;

}
