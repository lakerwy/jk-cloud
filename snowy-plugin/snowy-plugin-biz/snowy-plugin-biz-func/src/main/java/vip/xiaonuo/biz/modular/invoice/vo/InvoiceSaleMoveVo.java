package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleMove;

/**
 * 销售发票 - 异动（信息）
 */

@Getter
@Setter
public class InvoiceSaleMoveVo extends InvoiceSaleMove {

    /** 销售发票-申请单： 合同名称 */
    @ApiModelProperty(value = "销售发票-申请单：合同名称", position = 81)
    private String htProjectName;

    /** 销售发票-申请单：销售合同编号 */
    @ApiModelProperty(value = "销售发票-申请单：销售合同编号", position = 82)
    private String htContractNo;

    /** 销售发票-申请单-确认发票：发票号码 */
    @ApiModelProperty(value = "销售发票-申请单-确认发票：发票号码", position = 83)
    private String fpNo;


}
