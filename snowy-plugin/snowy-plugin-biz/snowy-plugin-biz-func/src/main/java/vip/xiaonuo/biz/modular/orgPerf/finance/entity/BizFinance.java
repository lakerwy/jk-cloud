package vip.xiaonuo.biz.modular.orgPerf.finance.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务情况实体
 *
 * @author pzh
 * @date  2025/06/17 11:47
 **/
@Getter
@Setter
@TableName("BIZ_FINANCE")
public class BizFinance {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 3)
    private String orgId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private Integer year;

    /** 月份（例如：1） */
    @ApiModelProperty(value = "月份（例如：1）", position = 5)
    private Integer month;

    /** 累计回款额 */
    @ApiModelProperty(value = "累计回款额", position = 6)
    private BigDecimal recAmountAcc;

    /** 累计净利润 */
    @ApiModelProperty(value = "累计净利润", position = 7)
    private BigDecimal profitAmountAcc;

    /** 预算执行率（成本） */
    @ApiModelProperty(value = "预算执行率（成本）", position = 8)
    private BigDecimal budgetExecRate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 15)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 16)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕记录 */
    @ApiModelProperty(value = "留痕记录", position = 18)
    private String note;
}
