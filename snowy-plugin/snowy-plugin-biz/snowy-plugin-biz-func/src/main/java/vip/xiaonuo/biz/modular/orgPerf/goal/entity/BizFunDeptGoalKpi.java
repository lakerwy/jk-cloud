package vip.xiaonuo.biz.modular.orgPerf.goal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 职能部门绩效考核-考核指标实体
 *
 * @author pzh
 * @date  2025/06/26 16:36
 **/
@Getter
@Setter
@TableName("BIZ_FUN_DEPT_GOAL_KPI")
public class BizFunDeptGoalKpi {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联-目标登记主键ID */
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 2)
    private String goalId;

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 3)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 4)
    private String assRule;

    /** 计算公式 */
    @ApiModelProperty(value = "计算公式", position = 5)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 6)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 7)
    private BigDecimal goalValue;

    /** 计划完成时间 */
    @ApiModelProperty(value = "计划完成时间", position = 8)
    private Date planCompTime;

    /** 数据来源 */
    @ApiModelProperty(value = "数据来源", position = 9)
    private String fromDept;

    /** 数据来源-部门ID */
    @ApiModelProperty(value = "数据来源-部门ID", position = 10)
    private String fromDeptId;

    /** 实际完成情况 */
    @ApiModelProperty(value = "实际完成情况", position = 11)
    private String actualCompDesc;

    /** 成果附件 */
    @ApiModelProperty(value = "成果附件", position = 12)
    private String attach;

    /** 完成进度 */
    @ApiModelProperty(value = "完成进度", position = 13)
    private BigDecimal progress;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 19)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
