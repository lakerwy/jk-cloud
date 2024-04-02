package vip.xiaonuo.biz.modular.invoice.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;

import java.math.BigDecimal;

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


    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 开票单位 */
    @ApiModelProperty(value = "开票单位", position = 6)
    private String billUnit;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 10)
    private BigDecimal htContractAmount;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 11)
    private String htCounterparty;

    /** 发票类型 */
    @ApiModelProperty(value = "发票类型", position = 14)
    private String fpType;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 8)
    private String htProjectName;

    /** 销售合同编号 */
    @ApiModelProperty(value = "销售合同编号", position = 9)
    private String htContractNo;

    /** 购买方 */
    @ApiModelProperty(value = "购买方", position = 12)
    private String purchaser;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 12)
    private String htCompanyOrDepartment;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 4)
    private String applyName;

    /** 收取方式 */
    @ApiModelProperty(value = "收取方式", position = 35)
    private String gatherType;

    /** 税差税率 */
    @ApiModelProperty(value = "税差税率", position = 36)
    private Float taxDiffRate;

    /** 本次应收税差 */
    @ApiModelProperty(value = "本次应收税差", position = 32)
    private BigDecimal ysTaxDiff;

    /** 其他说明事项 */
    @ApiModelProperty(value = "其他说明事项", position = 37)
    private String otherMemo;
}
