package vip.xiaonuo.biz.modular.branch.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司日常费用拨付 - 申请流程实体
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
@TableName("BIZ_BRANCH_ALLOTMENT_APPLY")
public class BranchAllotmentApply {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司银行账户信息ID", position = 3)
    private String branchAccountId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 4)
    private String branchName;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 5)
    private String operator;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 6)
    private Date paymentDate;

    /** 付款银行名称 */
    @ApiModelProperty(value = "付款银行名称", position = 7)
    private String bankName;

    /** 银行账户 */
    @ApiModelProperty(value = "银行账户", position = 8)
    private String bankAccount;

    /** 银行账户余额 */
    @ApiModelProperty(value = "银行账户余额", position = 9)
    private BigDecimal accountBalance;

    /** 出纳登记账户余额 */
    @ApiModelProperty(value = "出纳登记账户余额", position = 10)
    private BigDecimal realBalance;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 17)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 18)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 19)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 20)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 21)
    private String extJson;
}
