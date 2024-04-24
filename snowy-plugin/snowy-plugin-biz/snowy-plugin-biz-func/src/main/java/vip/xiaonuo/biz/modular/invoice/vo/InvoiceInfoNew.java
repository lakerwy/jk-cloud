package vip.xiaonuo.biz.modular.invoice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售发票-合计统计 - 后台构造 - 相同部分合并
 */

@Getter
@Setter
public class InvoiceInfoNew {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 4)
    private String applyName;

    /** 开票单位 */
    @ApiModelProperty(value = "开票单位", position = 6)
    private String billUnit;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 8)
    private String htProjectName;

    /** 销售合同编号 */
    @ApiModelProperty(value = "销售合同编号", position = 9)
    private String htContractNo;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 10)
    private BigDecimal htContractAmount;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 11)
    private String htCounterparty;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 12)
    private String htCompanyOrDepartment;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 13)
    private String htOwner;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 14)
    private String htProjectType;

    /** 累计开票金额 */
    @ApiModelProperty(value = "累计开票金额", position = 9)
    private BigDecimal ljKpAmount;

    /** 累计回款金额 */
    @ApiModelProperty(value = "累计回款金额", position = 10)
    private BigDecimal ljHkAmount;

    /** 结算资料上传 */
    @ApiModelProperty(value = "结算资料上传", position = 11)
    private String jsAttach;

    /** 购买方 */
    @ApiModelProperty(value = "购买方", position = 12)
    private String purchaser;

    /** 纳税人类型 */
    @ApiModelProperty(value = "纳税人类型", position = 13)
    private String taxpayerType;

    /** 发票类型 */
    @ApiModelProperty(value = "发票类型", position = 14)
    private String fpType;

    /** 纳税人识别号 */
    @ApiModelProperty(value = "纳税人识别号", position = 15)
    private String taxpayerNo;

    /** 地址电话 */
    @ApiModelProperty(value = "地址电话", position = 16)
    private String addressInfo;

    /** 开户银行、账号 */
    @ApiModelProperty(value = "开户银行、账号", position = 17)
    private String bankInfo;

    /** 发票备注 */
    @ApiModelProperty(value = "发票备注", position = 18)
    private String fpMemo;

    /** 增值税 */
    @ApiModelProperty(value = "增值税", position = 19)
    private BigDecimal addTax;

    /** 所得税 */
    @ApiModelProperty(value = "所得税", position = 20)
    private BigDecimal incomeTax;

    /** 个人所得税 */
    @ApiModelProperty(value = "个人所得税", position = 21)
    private BigDecimal perIncomeTax;

    /** 城建税 */
    @ApiModelProperty(value = "城建税", position = 22)
    private BigDecimal buildTax;

    /** 教育附加 */
    @ApiModelProperty(value = "教育附加", position = 23)
    private BigDecimal eduTax;

    /** 地方教育附加 */
    @ApiModelProperty(value = "地方教育附加", position = 24)
    private BigDecimal placeEduTax;

    /** 印花税 */
    @ApiModelProperty(value = "印花税", position = 25)
    private BigDecimal stampTax;

    /** 其他税种 */
    @ApiModelProperty(value = "其他税种", position = 26)
    private BigDecimal otherTax;

    /** 完税凭证日期 */
    @ApiModelProperty(value = "完税凭证日期", position = 27)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date proofDate;

    /** 本次预缴税款合计 */
    @ApiModelProperty(value = "本次预缴税款合计", position = 28)
    private BigDecimal preTaxTotal;

    /** 累计预缴税款金额 */
    @ApiModelProperty(value = "累计预缴税款金额", position = 29)
    private BigDecimal ljPreTax;

    /** 开票备注 */
    @ApiModelProperty(value = "开票备注", position = 31)
    private String kpMemo;

    /** 本次应收税差 */
    @ApiModelProperty(value = "本次应收税差", position = 32)
    private BigDecimal ysTaxDiff;

    /** 本次实际收取税差 */
    @ApiModelProperty(value = "本次实际收取税差", position = 33)
    private BigDecimal perTaxDiff;

    /** 累计实际收取税差 */
    @ApiModelProperty(value = "累计实际收取税差", position = 34)
    private BigDecimal ljPerTaxDiff;

    /** 收取方式 */
    @ApiModelProperty(value = "收取方式", position = 35)
    private String gatherType;

    /** 税差税率 */
    @ApiModelProperty(value = "税差税率", position = 36)
    private String taxDiffRate;

    /** 其他说明事项 */
    @ApiModelProperty(value = "其他说明事项", position = 37)
    private String otherMemo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 45)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 46)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 47)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 48)
    private String status;


    /** 以上均来源于 BIZ_INVOICE_SALE 基本信息 **/


    /** 销售发票申请 - 成本票应收 - 含税金额 - 小计 */
    @ApiModelProperty(value = "销售发票申请 - 成本票应收 - 含税金额 - 小计", position = 81)
    private BigDecimal saleCostTotal;

    /** 销售发票申请 - 开票确认 - 价税合计 - 小计 */
    @ApiModelProperty(value = "销售发票申请 - 开票确认 - 价税合计 - 小计", position = 82)
    private BigDecimal saleBillTotal;

    /** 销售发票申请 - 开票确认 - 税额 - 小计 */
    @ApiModelProperty(value = "销售发票申请 - 开票确认 - 税额 - 小计", position = 83)
    private BigDecimal saleBillTaxTotal;

    /** 成本发票申请 - 成本票确认 - 含税金额 - 小计 */
    @ApiModelProperty(value = "成本发票申请 - 成本票确认 - 含税金额 - 小计", position = 84)
    private BigDecimal costBillTotal;

//    /** (按 供应商+税率 统计)成本发票申请 - 成本票确认 - 含税金额 - 小计 */
//    @ApiModelProperty(value = "(按 供应商+税率 统计)成本发票申请 - 成本票确认 - 含税金额 - 小计", position = 85)
//    private List<InvoiceCostPrvBillTotal> prvCostList;

    /** (按 供应商+税率 统计)成本发票申请 - 供应商名称 */
    @ApiModelProperty(value = "(按 供应商+税率 统计)成本发票申请 - 供应商名称", position = 100)
    private String prvName;

    /** (按 供应商+税率 统计)成本发票申请 - 税率 */
    @ApiModelProperty(value = "(按 供应商+税率 统计)成本发票申请 - 税率", position = 101)
    private Float taxRate;

    /** (按 供应商+税率 统计)成本发票申请 - 价税合计 - 小计 */
    @ApiModelProperty(value = "(按 供应商+税率 统计)成本发票申请 - 价税合计 - 小计 ", position = 102)
    private BigDecimal amountTotal;

    /** (按 供应商+税率 统计)成本发票申请 - 税额 - 小计 */
    @ApiModelProperty(value = "(按 供应商+税率 统计)成本发票申请 - 税额 - 小计 ", position = 103)
    private BigDecimal taxAmountTotal;
}
