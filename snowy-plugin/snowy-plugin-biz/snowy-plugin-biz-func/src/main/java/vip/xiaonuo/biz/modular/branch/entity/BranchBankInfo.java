package vip.xiaonuo.biz.modular.branch.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司银行账户信息实体
 *
 * @author pzh
 * @date  2024/08/05 11:46
 **/
@Getter
@Setter
@TableName("BIZ_BRANCH_BANK_INFO")
public class BranchBankInfo {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 开户行名称 */
    @ApiModelProperty(value = "开户行名称", position = 4)
    private String bankName;

    /** 银行账户 */
    @ApiModelProperty(value = "银行账户", position = 5)
    private String bankAccount;

    /** 账户余额（元） */
    @ApiModelProperty(value = "账户余额（元）", position = 6)
    private BigDecimal accountBalance;

    /** 更新时间 */
    @ApiModelProperty(value = "账户余额最后更新时间", position = 7)
    private Date balanceUpdateTime;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 8)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
