package vip.xiaonuo.biz.modular.invoice.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 项目回款-实体-导入
 */

@Getter
@Setter
public class InvoicePrjRefundVo{

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 申请人Id */
    @ApiModelProperty(value = "申请人Id", position = 3)
    private String applyId;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 4)
    private String applyName;

    /** 收款日期 */
    @ApiModelProperty(value = "收款日期", position = 5)
    private String skDate;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 6)
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 7)
    private String htProjectName;

    /** 销售合同编号 */
    @ApiModelProperty(value = "销售合同编号", position = 8)
    private String htContractNo;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 9)
    private BigDecimal htContractAmount;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 10)
    private String htCounterparty;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 8)
    private String unitName;

    /** 金额 */
    @ApiModelProperty(value = "金额", position = 9)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 错误信息  */
    @ApiModelProperty(value = "错误信息", position = 100)
    private String errorMsg;

}
