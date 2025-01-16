package vip.xiaonuo.biz.modular.prjFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 投标费用 - excel - 实体
 */
@Getter
@Setter
public class PrjFeeAllotmentExcelData {

    /** 业务表主键id */
    @ApiModelProperty(value = "业务表主键id", position = 1)
    private String id;

    /** 合同主键ID */
    @ApiModelProperty(value = "合同主键ID", position = 2)
    private String hetongId;

    /** 拨付次数 */
    @ApiModelProperty(value = "拨付次数", position = 3)
    private Integer curDisburseNum;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 3)
    private String projectName;

    /** 申请人单位 */
    @ApiModelProperty(value = "申请人单位", position = 4)
    private String htMainType;

    /** 项目合同编号 */
    @ApiModelProperty(value = "项目合同编号", position = 5)
    private String contractNo;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 6)
    private String companyOrDepartment;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 7)
    private String owner;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 8)
    private String feeType;

    /** 是否出图（是|否） */
    @ApiModelProperty(value = "是否出图（是|否）", position = 9)
    private String isMap;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 10)
    private String counterparty;

    /** 合同总金额 */
    @ApiModelProperty(value = "合同总金额", position = 11)
    private BigDecimal sumAmount;

    /** 建科所占额 */
    @ApiModelProperty(value = "建科所占额", position = 12)
    private BigDecimal contractAmount;

    /** 经营模式 */
    @ApiModelProperty(value = "经营模式", position = 13)
    private String managementModel;

    /** 结算金额（元） */
    @ApiModelProperty(value = "结算金额（元）", position = 14)
    private BigDecimal settlementAmount;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 15)
    private String feeDate;

    /** 银行收款金额 */
    @ApiModelProperty(value = "银行收款金额", position = 16)
    private BigDecimal bankReceiptAmount;

    /** 扣保理费金额 */
    @ApiModelProperty(value = "扣保理费金额", position = 17)
    private BigDecimal factoringFeeAmount;

    /** 收款总额 */
    @ApiModelProperty(value = "收款总额", position = 18)
    private BigDecimal totalReceiptAmount;

    /** 累计收款（含保理费）金额 */
    @ApiModelProperty(value = "累计收款（含保理费）金额", position = 19)
    private BigDecimal accReceiptAmount;

    /** 累计收款（含保理费）比例 */
    @ApiModelProperty(value = "累计收款（含保理费）比例", position = 20)
    private BigDecimal accReceiptRate;

    /** 累计开票 */
    @ApiModelProperty(value = "累计开票", position = 21)
    private BigDecimal invoiceAmount;

    /** 管理费比例（%） */
    @ApiModelProperty(value = "管理费比例（%）", position = 22)
    private BigDecimal manageFeeRate;

    /** 本次管理费（2%） */
    @ApiModelProperty(value = "本次管理费（2%）", position = 23)
    private BigDecimal curManageFee;

    /** 累计管理费（含本次） */
    @ApiModelProperty(value = "累计管理费（含本次）", position = 24)
    private BigDecimal accManageFee;

    /** 本次税差扣款 */
    @ApiModelProperty(value = "本次税差扣款", position = 25)
    private BigDecimal curTaxDiffDed;

    /** 累计税差扣款（含本次） */
    @ApiModelProperty(value = "累计税差扣款（含本次）", position = 26)
    private BigDecimal accTaxDiffDed;

    /** 本次其他扣款 */
    @ApiModelProperty(value = "本次其他扣款", position = 27)
    private BigDecimal curOtherDed;

    /** 累计其他扣款（含本次） */
    @ApiModelProperty(value = "累计其他扣款（含本次）", position = 28)
    private BigDecimal accOtherDed;

    /** 本次可拨付金额 */
    @ApiModelProperty(value = "本次可拨付金额", position = 29)
    private BigDecimal curDisburseableAmount;

    /** 本次拨付金额 */
    @ApiModelProperty(value = "本次拨付金额", position = 30)
    private BigDecimal curDisburseAmount;

    /** 累计拨付金额（含本次） */
    @ApiModelProperty(value = "累计拨付金额（含本次）", position = 31)
    private BigDecimal accDisburseAmount;

    /** 本次挂账金额 */
    @ApiModelProperty(value = "本次挂账金额", position = 32)
    private BigDecimal curSuspendedAmount;

    /** 前期银行收款累计 */
    @ApiModelProperty(value = "前期银行收款累计", position = 33)
    private BigDecimal bankReceiptAmountAcc;

    /** 前期保理费累计 */
    @ApiModelProperty(value = "前期保理费累计", position = 34)
    private BigDecimal factoringFeeAmountAcc;

    /** 前期回款总额累计 */
    @ApiModelProperty(value = "前期回款总额累计", position = 35)
    private BigDecimal totalReceiptAcc;

    /** 前期拨付款 */
    @ApiModelProperty(value = "前期拨付款", position = 36)
    private BigDecimal disburseAmountAcc;

    /** 前期税差扣款 */
    @ApiModelProperty(value = "前期税差扣款", position = 37)
    private BigDecimal taxDiffDedAcc;

    /** 前期管理费（2%） */
    @ApiModelProperty(value = "前期管理费（2%）", position = 38)
    private BigDecimal manageFeeAcc;

    /** 前期其他扣款 */
    @ApiModelProperty(value = "前期其他扣款", position = 39)
    private BigDecimal otherDedAcc;

    /** 前期挂账金额 */
    @ApiModelProperty(value = "前期挂账金额", position = 40)
    private BigDecimal suspendedAmountAcc;

    /** 发票金额合计 */
    @ApiModelProperty(value = "发票金额合计", position = 41)
    private BigDecimal totalInvoiceAmount;

    /** 已付金额合计 */
    @ApiModelProperty(value = "已付金额合计", position = 42)
    private BigDecimal totalPaymentAmount;

    /** 已付金额中垫付款金额合计 */
    @ApiModelProperty(value = "已付金额中垫付款金额合计", position = 43)
    private BigDecimal totalAdvancedAmount;

    /** 已开票未付金额合计 */
    @ApiModelProperty(value = "已开票未付金额合计", position = 44)
    private BigDecimal totalInvoicedNotPayAmount;

    /** 本次计划拨付金额合计 */
    @ApiModelProperty(value = "本次计划拨付金额合计", position = 45)
    private BigDecimal totalPlanDisburseAmount;

    /** 出纳支付金额合计 */
    @ApiModelProperty(value = "出纳支付金额合计", position = 46)
    private BigDecimal totalCashierPaymentAmount;

    /** SN流水号（拨付单编号） */
    @ApiModelProperty(value = "SN流水号（拨付单编号）", position = 54)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 55)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 56)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 57)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 58)
    private String extJson;

    /** 审批意见 - 集合 */
    @ApiModelProperty(value = "审批意见 - 集合", position = 80)
    private List<FlwProcessComment> commentList;

    /** 款项拨付 - 集合 */
    @ApiModelProperty(value = "款项拨付 - 集合", position = 90)
    private List<PrjFeeAllotmentItem> feeItemList;


    @Getter
    @Setter
    public static class PrjFeeAllotmentItem {

        /** 序号 */
        @ApiModelProperty(value = "序号", position = 1)
        private Integer sortNum;

        /** 关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID） */
        @ApiModelProperty(value = "关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID）", position = 2)
        private String prjFeeAllotmentId;

        /** 分包合同编号 */
        @ApiModelProperty(value = "分包合同编号", position = 3)
        private String contractNo;

        /** 乙方（分包单位名称、收款单位） */
        @ApiModelProperty(value = "乙方（分包单位名称、收款单位）", position = 4)
        private String counterparty;

        /** 业务内容 */
        @ApiModelProperty(value = "业务内容", position = 5)
        private String businessType;

        /** 合同金额 */
        @ApiModelProperty(value = "合同金额", position = 6)
        private BigDecimal contractAmount;

        /** 发票金额（累计） */
        @ApiModelProperty(value = "发票金额（累计）", position = 7)
        private BigDecimal invoiceAmountAcc;

        /** 已付金额 */
        @ApiModelProperty(value = "已付金额", position = 8)
        private BigDecimal payAmount;

        /** 已付金额中垫付款金额 */
        @ApiModelProperty(value = "已付金额中垫付款金额", position = 9)
        private BigDecimal advancedAmount;

        /** 已开票未付金额 */
        @ApiModelProperty(value = "已开票未付金额", position = 10)
        private BigDecimal invoicedNotPayAmount;

        /** 本次计划拨付金额 */
        @ApiModelProperty(value = "本次计划拨付金额", position = 11)
        private BigDecimal planDisburseAmount;

        /** 出纳支付金额 */
        @ApiModelProperty(value = "出纳支付金额", position = 12)
        private BigDecimal cashierPaymentAmount;

        /** 附件 */
        @ApiModelProperty(value = "附件", position = 13)
        private String attachments;
    }

    /**
     * 审批记录类
     *
     * @author pzh
     * @date  2024/12/30 10:00
     **/
    @Getter
    @Setter
    public static class FlwProcessComment {

        /** 任务id */
        @ApiModelProperty(value = "任务id", position = 1)
        private String taskId;

        /** 任务名称 */
        @ApiModelProperty(value = "任务名称", position = 2)
        private String taskName;

        /** 活动节点id */
        @ApiModelProperty(value = "活动节点id", position = 3)
        private String activityId;

        /** 用户id */
        @ApiModelProperty(value = "用户id", position = 4)
        private String userId;

        /** 用户名称 */
        @ApiModelProperty(value = "用户名称", position = 5)
        private String userName;

        /** 审批操作类型 */
        @ApiModelProperty(value = "审批操作类型", position = 6)
        private String operateType;

        /** 审批操作文字 */
        @ApiModelProperty(value = "审批操作文字", position = 7)
        private String operateText;

        /** 审批意见 */
        @ApiModelProperty(value = "审批意见", position = 8)
        private String comment;

        /** 审批时间 */
        @ApiModelProperty(value = "审批时间", position = 9)
        private String approveTime;

//        /** 审批附件 */
//        @ApiModelProperty(value = "审批附件", position = 10)
//        private List<FlwTaskAttachmentResult> attachmentList;

        /** 审批耗时，最小单位：分钟 */
        @ApiModelProperty(value = "审批耗时，最小单位：分钟", position = 11)
        private String approveExpendHours;

        /** 评论-主键id */
        @ApiModelProperty(value = "评论-主键id", position = 12)
        private String commentId;
    }
}
