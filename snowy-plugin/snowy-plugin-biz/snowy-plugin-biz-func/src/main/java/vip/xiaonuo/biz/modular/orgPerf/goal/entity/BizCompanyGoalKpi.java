package vip.xiaonuo.biz.modular.orgPerf.goal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司绩效考核-考核指标实体
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Getter
@Setter
@TableName("BIZ_COMPANY_GOAL_KPI")
public class BizCompanyGoalKpi {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联-目标登记主键ID */
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 2)
    private String goalId;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 3)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 4)
    private String assRule;

    /** 评分标准 */
    @ApiModelProperty(value = "评分标准", position = 5)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 6)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 7)
    private BigDecimal goalValue;

    /** 完成值 */
    @ApiModelProperty(value = "完成值", position = 8)
    private BigDecimal completedValue;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

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
}
