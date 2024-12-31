package vip.xiaonuo.biz.modular.bidFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投标费用 - excel - 实体
 */
@Getter
@Setter
public class BidFeeExcelData {

    /** 申请单位（总部：1 分公司：2 其他：3） */
    @ApiModelProperty(value = "申请单位（总部：1 分公司：2 其他：3）", position = 2)
    private String applyUnit;

    /** 合作方负责人 */
    @ApiModelProperty(value = "合作方负责人", position = 3)
    private String leader;

    /** 资金支付类型 */
    @ApiModelProperty(value = "资金支付类型", position = 4)
    private String paymentType;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 6)
    private String deptId;

    /** 代收代付说明 */
    @ApiModelProperty(value = "代收代付说明", position = 7)
    private String paymentDesc;

    /** 保函费用说明 */
    @ApiModelProperty(value = "保函费用说明", position = 8)
    private String guaranteeFeeDesc;

    /** 预计开票时间 */
    @ApiModelProperty(value = "预计开票时间", position = 9)
    private String estimatedInvoiceDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attachments;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 11)
    private String applyDate;

    /** 领款单位名称 */
    @ApiModelProperty(value = "领款单位名称", position = 12)
    private String payeeName;

    /** 开户行 */
    @ApiModelProperty(value = "开户行", position = 13)
    private String bankName;

    /** 收款账号 */
    @ApiModelProperty(value = "收款账号", position = 14)
    private String payeeAccount;

    /** 立项ID */
    @ApiModelProperty(value = "立项ID", position = 15)
    private String projectId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 16)
    private String projectName;

    /** 立项单据流水号 */
    @ApiModelProperty(value = "立项单据流水号", position = 17)
    private String projectSn;

    /** 投标专员 */
    @ApiModelProperty(value = "投标专员", position = 18)
    private String bidManger;

    /** 投标专员ID */
    @ApiModelProperty(value = "投标专员ID", position = 19)
    private String bidMangerId;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 20)
    private String feeType;

    /** *支付金额（元） */
    @ApiModelProperty(value = "*支付金额（元）", position = 21)
    private BigDecimal payAmount;

    /** 支付金额（大写） */
    @ApiModelProperty(value = "支付金额（大写）", position = 22)
    private String payAmountUp;

    /** 转账备注 */
    @ApiModelProperty(value = "转账备注", position = 23)
    private String paymentRemark;

    /** 部门/分公司负责人 */
    @ApiModelProperty(value = "部门/分公司负责人", position = 24)
    private String manager;

    /** 部门/分公司负责人ID */
    @ApiModelProperty(value = "部门/分公司负责人ID", position = 25)
    private String managerId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 33)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 34)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 35)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 36)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 37)
    private String extJson;

    /** 核准人 */
    @ApiModelProperty(value = "核准人", position = 60)
    private String approvedBy;

    /** 会计 */
    @ApiModelProperty(value = "会计", position = 61)
    private String accounting;

    /** 出纳 */
    @ApiModelProperty(value = "出纳", position = 61)
    private String cashier;

    /** 招投标部 */
    @ApiModelProperty(value = "招投标部", position = 62)
    private String bidDept;

    /** 发起人 */
    @ApiModelProperty(value = "发起人", position = 63)
    private String initiator;

    /** 审批意见 - 集合 */
    @ApiModelProperty(value = "审批意见 - 集合", position = 80)
    private List<FlwProcessComment> commentList;

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
