package vip.xiaonuo.biz.modular.branch.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司日常费用拨付 - 付款信息实体
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
@TableName("BIZ_BRANCH_PAYMENT_INFO")
public class BranchPaymentInfo {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 合同主键ID */
    @ApiModelProperty(value = "关联分公司日常费用拨付ID", position = 3)
    private String allotmentApplyId;

    /** 合同主键ID */
    @ApiModelProperty(value = "合同主键ID", position = 4)
    private String contractId;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 5)
    private String contractNo;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 6)
    private String counterparty;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 7)
    private Date sealDate;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 8)
    private String paymentType;

    /** 款项具体内容 */
    @ApiModelProperty(value = "款项具体内容", position = 9)
    private String paymentDetail;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 10)
    private String unitName;

    /** 付款金额 */
    @ApiModelProperty(value = "付款金额", position = 11)
    private BigDecimal paymentAmount;

    /** 审定付款金额 */
    @ApiModelProperty(value = "审定付款金额", position = 12)
    private BigDecimal approvedAmount;

    /** 原始附件 */
    @ApiModelProperty(value = "原始附件", position = 13)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 25)
    private String dataScopeId;
}
