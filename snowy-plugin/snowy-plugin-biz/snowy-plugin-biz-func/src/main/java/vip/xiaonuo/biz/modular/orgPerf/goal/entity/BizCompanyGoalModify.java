package vip.xiaonuo.biz.modular.orgPerf.goal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司绩效考核-目标调整实体
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
@TableName("BIZ_COMPANY_GOAL_MODIFY")
public class BizCompanyGoalModify {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", position = 2)
    private String goalId;

    /** 流程表单数据JSON */
    @ApiModelProperty(value = "流程表单数据JSON", position = 3)
    private String dataJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 4)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 6)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 7)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 8)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 9)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 12)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 13)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 14)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 15)
    private String processInstanceId;
}
