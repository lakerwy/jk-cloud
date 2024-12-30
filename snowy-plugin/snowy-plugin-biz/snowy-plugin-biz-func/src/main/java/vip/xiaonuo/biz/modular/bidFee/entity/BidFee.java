package vip.xiaonuo.biz.modular.bidFee.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 投标费用审批实体
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
@Getter
@Setter
@TableName("BIZ_BID_FEE")
public class BidFee {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请单位 */
    @ApiModelProperty(value = "申请单位", position = 2)
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
    private Date estimatedInvoiceDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attachments;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 11)
    private Date applyDate;

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

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 26)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 27)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 28)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 29)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 30)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 31)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 32)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

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
}
