package vip.xiaonuo.biz.modular.orgPerf.goal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 组织绩效考核-目标登记实体
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
@Setter
@TableName("BIZ_ORG_GOAL")
public class BizOrgGoal {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 考核部门（子公司|生产部门） */
    @ApiModelProperty(value = "考核部门（子公司|生产部门）", position = 2)
    private String assOrg;

    /** 考核时段（季度考核|年度考核） */
    @ApiModelProperty(value = "考核时段（季度考核|年度考核）", position = 3)
    private String assPeriod;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 5)
    private String orgId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 6)
    private String owner;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 7)
    private String ownerId;

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 8)
    private String assType;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）", position = 9)
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）", position = 10)
    private Integer assSeason;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 18)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
