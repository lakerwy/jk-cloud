package vip.xiaonuo.biz.modular.invoice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售发票 - 发票号 维度
 */
@Getter
@Setter
public class InvoiceSaleInfo extends InvoiceSaleBill {
    /** 销售发票申请 - 主键ID */
    @ApiModelProperty(value = "销售发票申请 - 主键ID", position = 61)
    private String sId;

    /** 销售发票申请 - 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "销售发票申请 - 申请人单位：总部|分公司|零星", position = 62)
    private String sApplyUnit;

    /** 销售发票申请 - 申请人Id */
    @ApiModelProperty(value = "销售发票申请 - 申请人Id", position = 63)
    private String sApplyId;

    /** 销售发票申请 - 申请人姓名 */
    @ApiModelProperty(value = "销售发票申请 - 申请人姓名", position = 64)
    private String sApplyName;

    /** 销售发票申请 - 开票单位Id */
    @ApiModelProperty(value = "销售发票申请 - 开票单位Id", position = 65)
    private String sBillId;

    /** 销售发票申请 - 开票单位 */
    @ApiModelProperty(value = "销售发票申请 - 开票单位", position = 66)
    private String sBillUnit;

    /** 销售发票申请 - 合同Id */
    @ApiModelProperty(value = "销售发票申请 - 合同Id", position = 67)
    private String sHtId;

    /** 销售发票申请 - 合同名称 */
    @ApiModelProperty(value = "销售发票申请 - 合同名称", position = 68)
    private String sHtProjectName;

    /** 销售发票申请 - 销售合同编号 */
    @ApiModelProperty(value = "销售发票申请 - 销售合同编号", position = 69)
    private String sHtContractNo;

    /** 销售发票申请 - 合同金额 */
    @ApiModelProperty(value = "销售发票申请 - 合同金额", position = 70)
    private BigDecimal sHtContractAmount;

    /** 销售发票申请 - 合同甲方 */
    @ApiModelProperty(value = "销售发票申请 - 合同甲方", position = 71)
    private String sHtCounterparty;

    /** 销售发票申请 - 责任部门 */
    @ApiModelProperty(value = "销售发票申请 - 责任部门", position = 72)
    private String sHtCompanyOrDepartment;

    /** 销售发票申请 - 责任人 */
    @ApiModelProperty(value = "销售发票申请 - 责任人", position = 73)
    private String sHtOwner;

    /** 销售发票申请 - 项目类型 */
    @ApiModelProperty(value = "销售发票申请 - 项目类型", position = 74)
    private String sHtProjectType;

    /** 销售发票申请 - 累计开票金额 */
    @ApiModelProperty(value = "销售发票申请 - 累计开票金额", position = 75)
    private BigDecimal sLjKpAmount;

    /** 销售发票申请 - 累计回款金额 */
    @ApiModelProperty(value = "销售发票申请 - 累计回款金额", position = 76)
    private BigDecimal sLjHkAmount;

    /** 销售发票申请 - 结算资料上传 */
    @ApiModelProperty(value = "销售发票申请 - 结算资料上传", position = 77)
    private String sJsAttach;

    /** 销售发票申请 - 购买方 */
    @ApiModelProperty(value = "销售发票申请 - 购买方", position = 78)
    private String sPurchaser;

    /** 销售发票申请 - 纳税人类型 */
    @ApiModelProperty(value = "销售发票申请 - 纳税人类型", position = 79)
    private String sTaxpayerType;

    /** 销售发票申请 - 发票类型 */
    @ApiModelProperty(value = "销售发票申请 - 发票类型", position = 80)
    private String sFpType;

    /** 销售发票申请 - 纳税人识别号 */
    @ApiModelProperty(value = "销售发票申请 - 纳税人识别号", position = 81)
    private String sTaxpayerNo;

    /** 销售发票申请 - 地址电话 */
    @ApiModelProperty(value = "销售发票申请 - 地址电话", position = 82)
    private String sAddressInfo;

    /** 销售发票申请 - 开户银行、账号 */
    @ApiModelProperty(value = "销售发票申请 - 开户银行、账号", position = 83)
    private String sBankInfo;

    /** 销售发票申请 - 发票备注 */
    @ApiModelProperty(value = "销售发票申请 - 发票备注", position = 84)
    private String sFpMemo;

    /** 销售发票申请 - 增值税 */
    @ApiModelProperty(value = "销售发票申请 - 增值税", position = 85)
    private BigDecimal sAddTax;

    /** 销售发票申请 - 所得税 */
    @ApiModelProperty(value = "销售发票申请 - 所得税", position = 86)
    private BigDecimal sIncomeTax;

    /** 销售发票申请 - 个人所得税 */
    @ApiModelProperty(value = "销售发票申请 - 个人所得税", position = 87)
    private BigDecimal sPerIncomeTax;

    /** 销售发票申请 - 城建税 */
    @ApiModelProperty(value = "销售发票申请 - 城建税", position = 88)
    private BigDecimal sBuildTax;

    /** 销售发票申请 - 教育附加 */
    @ApiModelProperty(value = "销售发票申请 - 教育附加", position = 89)
    private BigDecimal sEduTax;

    /** 销售发票申请 - 地方教育附加 */
    @ApiModelProperty(value = "销售发票申请 - 地方教育附加", position = 90)
    private BigDecimal sPlaceEduTax;

    /** 销售发票申请 - 印花税 */
    @ApiModelProperty(value = "销售发票申请 - 印花税", position = 91)
    private BigDecimal sStampTax;

    /** 销售发票申请 - 其他税种 */
    @ApiModelProperty(value = "销售发票申请 - 其他税种", position = 92)
    private BigDecimal sOtherTax;

    /** 销售发票申请 - 完税凭证日期 */
    @ApiModelProperty(value = "销售发票申请 - 完税凭证日期", position = 93)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sProofDate;

    /** 销售发票申请 - 本次预缴税款合计 */
    @ApiModelProperty(value = "销售发票申请 - 本次预缴税款合计", position = 94)
    private BigDecimal sPreTaxTotal;

    /** 销售发票申请 - 累计预缴税款金额 */
    @ApiModelProperty(value = "销售发票申请 - 累计预缴税款金额", position = 95)
    private BigDecimal sLjPreTax;

    /** 销售发票申请 - 完税凭证附件上传 */
    @ApiModelProperty(value = "销售发票申请 - 完税凭证附件上传", position = 96)
    private String sPfAttach;

    /** 销售发票申请 - 开票备注 */
    @ApiModelProperty(value = "销售发票申请 - 开票备注", position = 97)
    private String sKpMemo;

    /** 销售发票申请 - 本次应收税差 */
    @ApiModelProperty(value = "销售发票申请 - 本次应收税差", position = 98)
    private BigDecimal sYsTaxDiff;

    /** 销售发票申请 - 本次预收税差 */
    @ApiModelProperty(value = "销售发票申请 - 本次预收税差", position = 99)
    private BigDecimal sPerTaxDiff;

    /** 销售发票申请 - 累计预收税差 */
    @ApiModelProperty(value = "销售发票申请 - 累计预收税差", position = 100)
    private BigDecimal sLjPerTaxDiff;

    /** 销售发票申请 - 收取方式 */
    @ApiModelProperty(value = "销售发票申请 - 收取方式", position = 101)
    private String sGatherType;

    /** 销售发票申请 - 税差税率 */
    @ApiModelProperty(value = "销售发票申请 - 税差税率", position = 102)
    private String sTaxDiffRate;

    /** 销售发票申请 - 其他说明事项 */
    @ApiModelProperty(value = "销售发票申请 - 其他说明事项", position = 103)
    private String sOtherMemo;

    /** 销售发票申请 - SN流水号 */
    @ApiModelProperty(value = "销售发票申请 - SN流水号", position = 104)
    private String sSn;

    /** 销售发票申请 - 模型Id */
    @ApiModelProperty(value = "销售发票申请 - 模型Id", position = 105)
    private String sModelId;

    /** 销售发票申请 - 流程实例Id */
    @ApiModelProperty(value = "销售发票申请 - 流程实例Id", position = 106)
    private String sProcessInstanceId;

    /** 销售发票申请 - 流程状态 */
    @ApiModelProperty(value = "销售发票申请 - 流程状态", position = 107)
    private String sStatus;

    /** 销售发票申请 - 备注 */
    @ApiModelProperty(value = "销售发票申请 - 备注", position = 108)
    private String sMemo;
}
