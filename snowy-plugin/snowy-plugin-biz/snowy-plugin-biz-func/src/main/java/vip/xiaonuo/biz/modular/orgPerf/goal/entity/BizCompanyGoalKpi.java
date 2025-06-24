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
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 3)
    private String goalId;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 9)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 11)
    private String assRule;

    /** 评分标准 */
    @ApiModelProperty(value = "评分标准", position = 13)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 15)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 17)
    private BigDecimal goalValue;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 19)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 27)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 29)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 31)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 33)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 35)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
