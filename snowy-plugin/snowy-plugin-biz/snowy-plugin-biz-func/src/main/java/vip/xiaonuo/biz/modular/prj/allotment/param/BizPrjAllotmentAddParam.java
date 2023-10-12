/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.prj.allotment.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目计划付款单添加参数
 *
 * @author yong.ye
 * @date  2023/09/28 11:30
 **/
@Getter
@Setter
public class BizPrjAllotmentAddParam {

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

    /** 申请日期 */
//    @ApiModelProperty(value = "申请日期", position = 4)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date applyDate;

    /** 拨付款申请单编号 */
    @ApiModelProperty(value = "拨付款申请单编号", position = 5)
    private String allotmentNo;

    /** 项目基本情况-合同编号 */
    @ApiModelProperty(value = "项目基本情况-合同编号", position = 6)
    private String xmContractNo;

    /** 项目基本情况-合同名称 */
    @ApiModelProperty(value = "项目基本情况-合同名称", position = 7)
    private String xmContractName;

    /** 项目基本情况-合同金额 */
    @ApiModelProperty(value = "项目基本情况-合同金额", position = 8)
    private Double xmContractAmount;

    /** 项目基本情况-竣工结算金额 */
    @ApiModelProperty(value = "项目基本情况-竣工结算金额", position = 9)
    private Double xmCompleteAmount;

    /** 项目基本情况-形象进度 */
    @ApiModelProperty(value = "项目基本情况-形象进度", position = 10)
    private String xmProgress;

    /** 项目基本情况-进度结算金额 */
    @ApiModelProperty(value = "项目基本情况-进度结算金额", position = 11)
    private Double xmProgressAmount;

    /** 项目基本情况-进度应收款 */
    @ApiModelProperty(value = "项目基本情况-进度应收款", position = 12)
    private Double xmReceiveAmount;

    /** 收款开票信息-本次收款-日期 */
    @ApiModelProperty(value = "收款开票信息-本次收款-日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date skDate;

    /** 收款开票信息-本次收款-金额 */
    @ApiModelProperty(value = "收款开票信息-本次收款-金额", position = 14)
    private Double skAmount;

    /** 收款开票信息-本次收款-备注 */
    @ApiModelProperty(value = "收款开票信息-本次收款-备注", position = 15)
    private String skMemo;

    /** 收款开票信息-累计收款金额 */
    @ApiModelProperty(value = "收款开票信息-累计收款金额", position = 16)
    private Double skLjAmount;

    /** 收款开票信息-累计开票金额 */
    @ApiModelProperty(value = "收款开票信息-累计开票金额", position = 17)
    private Double skLjkpAmount;

    /** 收款开票信息-管理费费率 */
    @ApiModelProperty(value = "收款开票信息-管理费费率", position = 18)
    private String skRate;

    /** 收款开票信息-本次管理费金额 */
    @ApiModelProperty(value = "收款开票信息-本次管理费金额", position = 19)
    private Double skManageAmount;

    /** 项目付款扣款信息-小计 */
    @ApiModelProperty(value = "项目付款扣款信息-小计", position = 20)
    private Double fkTotalAmount;

    /** 项目付款扣款信息-前期累计拨付金额 */
    @ApiModelProperty(value = "项目付款扣款信息-前期累计拨付金额", position = 21)
    private Double fkLjPreAmount;

    /** 项目付款扣款信息-累计收取管理费 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取管理费", position = 21)
    private Double fkLjManageAmount;

    /** 项目付款扣款信息-累计收取税差 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取税差", position = 22)
    private Double fkLjTaxAmount;

    /** 项目付款扣款信息-累计收取签字费 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取签字费", position = 23)
    private Double fkLjSignAmount;

    /** 项目付款扣款信息-累计工程罚款 */
    @ApiModelProperty(value = "项目付款扣款信息-累计工程罚款", position = 24)
    private Double fkLjFineAmount;

    /** 项目付款扣款信息-累计扣取保证金 */
    @ApiModelProperty(value = "项目付款扣款信息-累计扣取保证金", position = 25)
    private Double fkLjGuaranteeAmount;

    /** 项目付款扣款信息-其他扣罚款累计金额 */
    @ApiModelProperty(value = "项目付款扣款信息-其他扣罚款累计金额", position = 26)
    private Double fkLjOtherAmount;

    /** 本次可结算金额 */
    @ApiModelProperty(value = "本次可结算金额", position = 27)
    private Double thisAllowAmount;

    /** 本次拨付款性质：手动选择（预付款/进度款/尾款） */
    @ApiModelProperty(value = "本次拨付款性质：手动选择（预付款/进度款/尾款）", position = 28)
    private String thisNature;

    /** 关联结算单编号 */
    @ApiModelProperty(value = "关联结算单编号", position = 29)
    private String settleNo;

    /** 申请付款信息-计划付款单编号 */
    @ApiModelProperty(value = "申请付款信息-计划付款单编号", position = 30)
    private String planNo;

//    /** 申请付款信息-本次付款合同编号 */
//    @ApiModelProperty(value = "申请付款信息-本次付款合同编号", position = 31)
//    private String planContractNo;
//
//    /** 申请付款信息-合同名称 */
//    @ApiModelProperty(value = "申请付款信息-合同名称", position = 32)
//    private String planContractName;
//
//    /** 申请付款信息-款项性质：预付款/进度款/尾款 */
//    @ApiModelProperty(value = "申请付款信息-款项性质：预付款/进度款/尾款", position = 33)
//    private String planNature;
//
//    /** 申请付款信息-收款单位编码 */
//    @ApiModelProperty(value = "申请付款信息-收款单位编码", position = 34)
//    private String planUnitCode;
//
//    /** 申请付款信息-收款单位名称 */
//    @ApiModelProperty(value = "申请付款信息-收款单位名称", position = 35)
//    private String planUnitName;
//
//    /** 申请付款信息-银行账号 */
//    @ApiModelProperty(value = "申请付款信息-银行账号", position = 36)
//    private String planBankNo;
//
//    /** 申请付款信息-开户行 */
//    @ApiModelProperty(value = "申请付款信息-开户行", position = 37)
//    private String planBankName;
//
//    /** 申请付款信息-款项内容 */
//    @ApiModelProperty(value = "申请付款信息-款项内容", position = 38)
//    private String planContent;
//
//    /** 申请付款信息-本次拨付金额 */
//    @ApiModelProperty(value = "申请付款信息-本次拨付金额", position = 39)
//    private Double planAmount;
//
//    /** 申请付款信息-合同金额 */
//    @ApiModelProperty(value = "申请付款信息-合同金额", position = 40)
//    private Double planContractAmount;
//
//    /** 申请付款信息-发票金额 */
//    @ApiModelProperty(value = "申请付款信息-发票金额", position = 41)
//    private Double planFpAmount;
//
//    /** 申请付款信息-已支付金额 */
//    @ApiModelProperty(value = "申请付款信息-已支付金额", position = 42)
//    private Double planYzfAmount;
//
//    /** 申请付款信息-已开票未付金额 */
//    @ApiModelProperty(value = "申请付款信息-已开票未付金额", position = 43)
//    private Double planWzfAmount;
//
//    /** 申请付款信息-附件上传 */
//    @ApiModelProperty(value = "申请付款信息-附件上传", position = 44)
//    private String planAttach;

    /** 本次申请付款金额合计 */
    @ApiModelProperty(value = "本次申请付款金额合计", position = 45)
    private Double thisApplyAmount;

    /** 部门审批-工程总承包事业部-本次工程罚款金额 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-本次工程罚款金额", position = 46)
    private Double gczcFineAmount;

    /** 部门审批-工程总承包事业部-本次保证金扣款 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-本次保证金扣款", position = 47)
    private Double gczcGuaranteeAmount;

    /** 部门审批-工程总承包事业部-含质保金 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-含质保金", position = 48)
    private Double gczcQualityAmount;

    /** 部门审批-工程总承包事业部-意见 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-意见", position = 49)
    private String gczcIdea;

    /** 部门审批-资产财务中心-工资预留 */
    @ApiModelProperty(value = "部门审批-资产财务中心-工资预留", position = 50)
    private Double cwWageAmount;

    /** 部门审批-资产财务中心-合同未归档扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-合同未归档扣款", position = 51)
    private Double cwContractFineAmount;

    /** 部门审批-资产财务中心-资金占用费扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-资金占用费扣款", position = 52)
    private Double cwFundFineAmount;

    /** 部门审批-资产财务中心-五险一金 */
    @ApiModelProperty(value = "部门审批-资产财务中心-五险一金", position = 53)
    private Double cwFiveAmount;

    /** 部门审批-资产财务中心-保底额清算扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-保底额清算扣款", position = 54)
    private Double cwQsFineAmount;

    /** 部门审批-资产财务中心-本次收取管理费审定金额 */
    @ApiModelProperty(value = "部门审批-资产财务中心-本次收取管理费审定金额", position = 55)
    private Double cwManageAmount;

    /** 部门审批-资产财务中心-残保预留 */
    @ApiModelProperty(value = "部门审批-资产财务中心-残保预留", position = 56)
    private Double cwDisaFineAmount;

    /** 部门审批-资产财务中心-其他扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-其他扣款", position = 57)
    private Double cwOtherFineAmount;

    /** 部门审批-资产财务中心-审定本次拨付金额 */
    @ApiModelProperty(value = "部门审批-资产财务中心-审定本次拨付金额", position = 58)
    private Double cwAuditAmount;

    /** 部门审批-资产财务中心-意见 */
    @ApiModelProperty(value = "部门审批-资产财务中心-意见", position = 59)
    private String cwIdea;

    /** 部门审批-资产财务中心-小计 */
    @ApiModelProperty(value = "部门审批-资产财务中心-小计", position = 60)
    private Double cwTotalAmount;

    /** 出纳支付信息-本次付款合同编号 */
    @ApiModelProperty(value = "出纳支付信息-本次付款合同编号", position = 61)
    private String cnContractNo;

    /** 出纳支付信息-合同名称 */
    @ApiModelProperty(value = "出纳支付信息-合同名称", position = 62)
    private String cnContractName;

    /** 出纳支付信息-本次实际付款金额 */
    @ApiModelProperty(value = "出纳支付信息-本次实际付款金额", position = 63)
    private Double cnRealAmount;

    /** 出纳支付信息-本次实际付款金额合计 */
    @ApiModelProperty(value = "出纳支付信息-本次实际付款金额合计", position = 64)
    private Double cnRealTotalAmount;

    /** 出纳支付信息-实际付款日期 */
    @ApiModelProperty(value = "出纳支付信息-实际付款日期", position = 65)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cnRealDate;

    /** 出纳支付信息-意见 */
    @ApiModelProperty(value = "出纳支付信息-意见", position = 66)
    private String cnIdea;

    /** SN */
    @ApiModelProperty(value = "SN", position = 67)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 68)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 69)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 75)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 76)
    private String extJson;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 77)
    private String dataScopeId;

}
