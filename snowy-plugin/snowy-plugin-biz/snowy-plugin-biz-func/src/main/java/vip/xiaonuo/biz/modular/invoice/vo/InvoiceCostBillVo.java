package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;

/**
 * 成本发票申请 - 成本发票 - 列表
 */

@Getter
@Setter
public class InvoiceCostBillVo extends InvoiceCostBill {
    /** 成本发票申请 - SN流水号 */
    @ApiModelProperty(value = "成本发票申请 - SN流水号", position = 17)
    private String sn;

    /** 成本发票申请 - 模型Id */
    @ApiModelProperty(value = "成本发票申请 - 模型Id", position = 18)
    private String modelId;

    /** 成本发票申请 - 流程实例Id */
    @ApiModelProperty(value = "成本发票申请 - 流程实例Id", position = 19)
    private String processInstanceId;

    /** 成本发票申请 - 流程状态 */
    @ApiModelProperty(value = "成本发票申请 - 流程状态", position = 20)
    private String status;
}
