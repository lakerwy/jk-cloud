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


    /** 成本发票申请 -申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "成本发票申请 -申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 成本发票申请 -申请人姓名 */
    @ApiModelProperty(value = "成本发票申请 -申请人姓名", position = 4)
    private String applyName;

    /** 成本发票申请 -开票申请编号(销售发票流程SN) */
    @ApiModelProperty(value = "成本发票申请 -开票申请编号(销售发票流程SN)", position = 5)
    private String kpSn;

    /** 成本发票申请 -项目名称 */
    @ApiModelProperty(value = "成本发票申请 -项目名称", position = 7)
    private String htProjectName;

    /** 成本发票申请 -销售合同编号 */
    @ApiModelProperty(value = "成本发票申请 -销售合同编号", position = 8)
    private String htContractNo;



    /** 销售发票-购买方 */
    @ApiModelProperty(value = "销售发票-购买方", position = 12)
    private String purchaser;

    /** 销售发票-责任部门 */
    @ApiModelProperty(value = "销售发票-责任部门", position = 12)
    private String htCompanyOrDepartment;

    /** 销售发票-责任人 */
    @ApiModelProperty(value = "销售发票-责任人", position = 13)
    private String htOwner;

}
