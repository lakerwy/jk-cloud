package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinDataResult {

    /** 主键ID（合同id） */
    @ApiModelProperty(value = "主键ID（合同id）", position = 1)
    private String id;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 2)
    private String contractNo;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 3)
    private String projectName;

    /** 项目类型(来源立项) */
    @ApiModelProperty(value = "项目类型(来源立项)", position = 4)
    private String bizProjectType;

    /** 甲方 */
    @ApiModelProperty(value = "甲方", position = 5)
    private String counterparty;

    /** 乙方 */
    @ApiModelProperty(value = "乙方", position = 6)
    private String secondparty;

    /** 经营部门(来源立项) */
    @ApiModelProperty(value = "经营部门(来源立项)", position = 7)
    private String manageDept;

    /** 责任单位 */
    @ApiModelProperty(value = "责任单位", position = 8)
    private String companyOrDepartment;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 9)
    private String owner;

    /** 合同总金额 */
    @ApiModelProperty(value = "合同总金额", position = 10)
    private BigDecimal sumAmount;

    /** 合同金额 - 建科所占金额 */
    @ApiModelProperty(value = "合同金额 - 建科所占金额 ", position = 11)
    private BigDecimal contractAmount;




    /** 开票金额 （该合同在“销售发票”中所有开票金额的小计） */
    @ApiModelProperty(value = "开票金额 （该合同在“销售发票”中所有开票金额的小计）", position = 31)
    private BigDecimal invoiceTotal;

    /** 项目回款 （该合同在“项目回款”中所有收款金额的小计） */
    @ApiModelProperty(value = "项目回款 （该合同在“项目回款”中所有收款金额的小计）", position = 32)
    private BigDecimal ppcTotal;

    /** 拨付金额 （该合同在“拨付明细”中的所有“拨付金额”+“生产院”中的所有“拨付合计”+“施工”中的所有“拨付金额”） */
    @ApiModelProperty(value = "拨付金额 （该合同在“拨付明细”中的所有“拨付金额”+“生产院”中的所有“拨付合计”+“施工”中的所有“拨付金额”）", position = 33)
    private BigDecimal dbdTotal;

    /** 扣管理费 （该合同在“拨付明细”中的所有“扣管理费”+“生产院”中所有的“（集团管理费+扣管理费）”+“施工”中的所有“扣管理费） */
    @ApiModelProperty(value = "扣管理费 （该合同在“拨付明细”中的所有“扣管理费”+“生产院”中所有的“（集团管理费+扣管理费）”+“施工”中的所有“扣管理费）", position = 34)
    private BigDecimal kManageTotal;

    /** 扣税差 （该合同在“拨付明细”中的所有“扣税差”+“生产院”中所有4项“扣税差”+“施工”中所有的“扣税差”） */
    @ApiModelProperty(value = "扣税差 （该合同在“拨付明细”中的所有“扣税差”+“生产院”中所有4项“扣税差”+“施工”中所有的“扣税差”）", position = 35)
    private BigDecimal kTaxTotal;

    /** 扣派驻人员工資 （该合同在“施工”中所有的“扣派驻人员工资”） */
    @ApiModelProperty(value = "扣派驻人员工資 （该合同在“施工”中所有的“扣派驻人员工资”）", position = 36)
    private BigDecimal kGzTotal;

    /** 扣签字费 （该合同在“拨付明细”中所有的“扣签字费”+在"生产院"所有的“扣签字费”+在“施工”中所有的“扣签字费”） */
    @ApiModelProperty(value = "扣签字费 （该合同在“拨付明细”中所有的“扣签字费”+在”生产院“所有的“扣签字费”+在“施工”中所有的“扣签字费”）", position = 37)
    private BigDecimal kSignTotal;

    /** 扣签字费税差（该合同在“拨付明细”中所有的“扣签字费税差”） */
    @ApiModelProperty(value = "扣签字费税差（该合同在“拨付明细”中所有的“扣签字费税差”）", position = 38)
    private BigDecimal kSignTaxTotal;

    /** 扣职工薪酬（该合同在“施工”中所有的“扣职工薪酬”） */
    @ApiModelProperty(value = "扣职工薪酬（该合同在“施工”中所有的“扣职工薪酬”）", position = 39)
    private BigDecimal kSalaryTotal;

    /** 其他扣款事项（该合同在“拨付明细中所有的”“其他扣款事项”+在“生产院”中所有的“其他扣款事项”+“施工”中所有的“其他扣款事项”） */
    @ApiModelProperty(value = "其他扣款事项（该合同在“拨付明细中所有的”“其他扣款事项”+在“生产院”中所有的“其他扣款事项”+“施工”中所有的“其他扣款事项”）", position = 40)
    private BigDecimal kOtherContentTotal;

    /** 其他扣款金额（该合同在“拨付明细”中所有的“其他扣款金额”+在“生产院”中所有的“其他扣款”+在“施工”中所有的“其他扣款金额”） */
    @ApiModelProperty(value = "其他扣款金额（该合同在“拨付明细”中所有的“其他扣款金额”+在“生产院”中所有的“其他扣款”+在“施工”中所有的“其他扣款金额”）", position = 41)
    private BigDecimal kOtherTotal;

    /** 项目资金余额（该合同在本表的“项目回款”-“拨付金额”-“扣管理费”-“扣税差”-“扣派驻人员工资”-“扣签字费”-“扣签字费税差”-“扣职工薪酬”-“其他扣款金额”） */
    @ApiModelProperty(value = "项目资金余额（该合同在本表的“项目回款”-“拨付金额”-“扣管理费”-“扣税差”-“扣派驻人员工资”-“扣签字费”-“扣签字费税差”-“扣职工薪酬”-“其他扣款金额”）", position = 42)
    private BigDecimal balanceTotal;

    /** 应收账款（该合同在本表的“开票金额”-“项目回款”） */
    @ApiModelProperty(value = "应收账款（该合同在本表的“开票金额”-“项目回款”）", position = 43)
    private BigDecimal ysTotal;

    /** 转入管理费（该合同在“转入管理费”中所有转款金额的小计） */
    @ApiModelProperty(value = "转入管理费（该合同在“转入管理费”中所有转款金额的小计）", position = 44)
    private BigDecimal inManageTotal;

    /** 转入签字费（该合同在“转入签字费”中所有转款金额的小计） */
    @ApiModelProperty(value = "转入签字费（该合同在“转入签字费”中所有转款金额的小计）", position = 45)
    private BigDecimal inSignTotal;

}
