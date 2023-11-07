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
package vip.xiaonuo.biz.modular.prj.allotment.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目拨付款申请单实体
 *
 * @author yong.ye
 * @date  2023/09/28 11:30
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_ALLOTMENT")
public class BizPrjAllotment {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 合作方/分公司编码 */
    @ApiModelProperty(value = "合作方/分公司编码", position = 2)
    private String hzfCode;

    /** 合作方/分公司名称 */
    @ApiModelProperty(value = "合作方/分公司名称", position = 3)
    private String hzfName;

    /** 申请日期 */
    @ApiModelProperty(value = "申请日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

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
    private BigDecimal xmContractAmount;

    /** 项目基本情况-竣工结算金额 */
    @ApiModelProperty(value = "项目基本情况-竣工结算金额", position = 9)
    private BigDecimal xmCompleteAmount;

    /** 项目基本情况-形象进度 */
    @ApiModelProperty(value = "项目基本情况-形象进度", position = 10)
    private String xmProgress;

    /** 项目基本情况-进度结算金额 */
    @ApiModelProperty(value = "项目基本情况-进度结算金额", position = 11)
    private BigDecimal xmProgressAmount;

    /** 项目基本情况-进度应收款 */
    @ApiModelProperty(value = "项目基本情况-进度应收款", position = 12)
    private BigDecimal xmReceiveAmount;

    /** 收款开票信息-本次收款-日期 */
    @ApiModelProperty(value = "收款开票信息-本次收款-日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date skDate;

    /** 收款开票信息-本次收款-金额 */
    @ApiModelProperty(value = "收款开票信息-本次收款-金额", position = 14)
    private BigDecimal skAmount;

    /** 收款开票信息-本次收款-备注 */
    @ApiModelProperty(value = "收款开票信息-本次收款-备注", position = 15)
    private String skMemo;

    /** 收款开票信息-累计收款金额 */
    @ApiModelProperty(value = "收款开票信息-累计收款金额", position = 16)
    private BigDecimal skLjAmount;

    /** 收款开票信息-累计开票金额 */
    @ApiModelProperty(value = "收款开票信息-累计开票金额", position = 17)
    private BigDecimal skLjkpAmount;

    /** 收款开票信息-管理费费率 */
    @ApiModelProperty(value = "收款开票信息-管理费费率", position = 18)
    private String skRate;

    /** 收款开票信息-本次管理费金额 */
    @ApiModelProperty(value = "收款开票信息-本次管理费金额", position = 19)
    private BigDecimal skManageAmount;

    /** 项目付款扣款信息-小计 */
    @ApiModelProperty(value = "项目付款扣款信息-小计", position = 20)
    private BigDecimal fkTotalAmount;

    /** 项目付款扣款信息-前期累计拨付金额 */
    @ApiModelProperty(value = "项目付款扣款信息-前期累计拨付金额", position = 21)
    private BigDecimal fkLjPreAmount;

    /** 项目付款扣款信息-累计收取管理费 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取管理费", position = 21)
    private BigDecimal fkLjManageAmount;

    /** 项目付款扣款信息-累计收取税差 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取税差", position = 22)
    private BigDecimal fkLjTaxAmount;

    /** 项目付款扣款信息-累计收取签字费 */
    @ApiModelProperty(value = "项目付款扣款信息-累计收取签字费", position = 23)
    private BigDecimal fkLjSignAmount;

    /** 项目付款扣款信息-累计工程罚款 */
    @ApiModelProperty(value = "项目付款扣款信息-累计工程罚款", position = 24)
    private BigDecimal fkLjFineAmount;

    /** 项目付款扣款信息-累计扣取保证金 */
    @ApiModelProperty(value = "项目付款扣款信息-累计扣取保证金", position = 25)
    private BigDecimal fkLjGuaranteeAmount;

    /** 项目付款扣款信息-其他扣罚款累计金额 */
    @ApiModelProperty(value = "项目付款扣款信息-其他扣罚款累计金额", position = 26)
    private BigDecimal fkLjOtherAmount;

    /** 本次可结算金额 */
    @ApiModelProperty(value = "本次可结算金额", position = 27)
    private BigDecimal thisAllowAmount;

    /** 本次拨付款性质：手动选择（预付款/进度款/尾款） */
    @ApiModelProperty(value = "本次拨付款性质：手动选择（预付款/进度款/尾款）", position = 28)
    private String thisNature;

    /** 关联结算单编号 */
    @ApiModelProperty(value = "关联结算单编号", position = 29)
    private String settleNo;

    /** 申请付款信息-计划付款单编号 */
    @ApiModelProperty(value = "申请付款信息-计划付款单编号", position = 30)
    private String planNo;

    /** 本次申请付款金额合计 */
    @ApiModelProperty(value = "本次申请付款金额合计", position = 45)
    private BigDecimal thisApplyAmount;

    /** 部门审批-工程总承包事业部-本次工程罚款金额 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-本次工程罚款金额", position = 46)
    private BigDecimal gczcFineAmount;

    /** 部门审批-工程总承包事业部-本次保证金扣款 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-本次保证金扣款", position = 47)
    private BigDecimal gczcGuaranteeAmount;

    /** 部门审批-工程总承包事业部-含质保金 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-含质保金", position = 48)
    private BigDecimal gczcQualityAmount;

    /** 部门审批-工程总承包事业部-意见 */
    @ApiModelProperty(value = "部门审批-工程总承包事业部-意见", position = 49)
    private String gczcIdea;

    /** 部门审批-资产财务中心-工资预留 */
    @ApiModelProperty(value = "部门审批-资产财务中心-工资预留", position = 50)
    private BigDecimal cwWageAmount;

    /** 部门审批-资产财务中心-合同未归档扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-合同未归档扣款", position = 51)
    private BigDecimal cwContractFineAmount;

    /** 部门审批-资产财务中心-资金占用费扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-资金占用费扣款", position = 52)
    private BigDecimal cwFundFineAmount;

    /** 部门审批-资产财务中心-五险一金 */
    @ApiModelProperty(value = "部门审批-资产财务中心-五险一金", position = 53)
    private BigDecimal cwFiveAmount;

    /** 部门审批-资产财务中心-保底额清算扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-保底额清算扣款", position = 54)
    private BigDecimal cwQsFineAmount;

    /** 部门审批-资产财务中心-本次收取管理费审定金额 */
    @ApiModelProperty(value = "部门审批-资产财务中心-本次收取管理费审定金额", position = 55)
    private BigDecimal cwManageAmount;

    /** 部门审批-资产财务中心-残保预留 */
    @ApiModelProperty(value = "部门审批-资产财务中心-残保预留", position = 56)
    private BigDecimal cwDisaFineAmount;

    /** 部门审批-资产财务中心-其他扣款 */
    @ApiModelProperty(value = "部门审批-资产财务中心-其他扣款", position = 57)
    private BigDecimal cwOtherFineAmount;

    /** 部门审批-资产财务中心-审定本次拨付金额 */
    @ApiModelProperty(value = "部门审批-资产财务中心-审定本次拨付金额", position = 58)
    private BigDecimal cwAuditAmount;

    /** 部门审批-资产财务中心-意见 */
    @ApiModelProperty(value = "部门审批-资产财务中心-意见", position = 59)
    private String cwIdea;

    /** 部门审批-资产财务中心-小计 */
    @ApiModelProperty(value = "部门审批-资产财务中心-小计", position = 60)
    private BigDecimal cwTotalAmount;

    /** 出纳支付信息-本次付款单位编号 */
    @ApiModelProperty(value = "出纳支付信息-本次付款单位编号", position = 61)
    private String cnUnitCode;

    /** 出纳支付信息-付款单位名称 */
    @ApiModelProperty(value = "出纳支付信息-付款单位名称", position = 62)
    private String cnUnitName;

    /** 出纳支付信息-本次付款合同编号 */
    @ApiModelProperty(value = "出纳支付信息-本次付款合同编号", position = 61)
    private String cnContractNo;

    /** 出纳支付信息-合同名称 */
    @ApiModelProperty(value = "出纳支付信息-合同名称", position = 62)
    private String cnContractName;

    /** 出纳支付信息-本次实际付款金额 */
    @ApiModelProperty(value = "出纳支付信息-本次实际付款金额", position = 63)
    private BigDecimal cnRealAmount;

    /** 出纳支付信息-本次实际付款金额合计 */
    @ApiModelProperty(value = "出纳支付信息-本次实际付款金额合计", position = 64)
    private BigDecimal cnRealTotalAmount;

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

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 70)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 71)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 72)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 73)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 74)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

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
