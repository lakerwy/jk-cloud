package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;

/**
 * 销售发票申请 - 确认发票 - 列表
 */

@Getter
@Setter
public class InvoiceSaleBillVo extends InvoiceSaleBill {
    /** 销售发票申请 - SN流水号 */
    @ApiModelProperty(value = "销售发票申请 - SN流水号", position = 45)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "销售发票申请 - 模型Id", position = 46)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "销售发票申请 - 流程实例Id", position = 47)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "销售发票申请 - 流程状态", position = 48)
    private String status;
}
