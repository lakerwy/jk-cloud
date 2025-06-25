package vip.xiaonuo.biz.modular.orgPerf.goal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 绩效考核-目标调整实体
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
@TableName("BIZ_GOAL_MODIFY")
public class BizGoalModify {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "绩效目标类型(公司|班子|子公司|生产部门|职能部门)", position = 3)
    private String perfGoalType;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "绩效评估类别(业务类|职能类)", position = 5)
    private String perfAssType;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "考核维度（季度考核|年度考核）", position = 7)
    private String assPeriod;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", position = 9)
    private String goalId;

    /** 流程表单数据JSON */
    @ApiModelProperty(value = "流程表单数据JSON", position = 11)
    private String dataJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 21)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 25)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 27)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 29)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 31)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 33)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 35)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 37)
    private String processInstanceId;
}
