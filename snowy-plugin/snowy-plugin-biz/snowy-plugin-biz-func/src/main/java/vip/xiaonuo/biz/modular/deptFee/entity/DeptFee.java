package vip.xiaonuo.biz.modular.deptFee.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 部门费用管理实体
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
@Getter
@Setter
@TableName("BIZ_DEPT_FEE")
public class DeptFee {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String deptName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 3)
    private String deptId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private String year;

    /** 季度（例如：1） */
    @ApiModelProperty(value = "季度（例如：1）", position = 5)
    private String season;

    /** 部门费用 */
    @ApiModelProperty(value = "部门费用", position = 6)
    private BigDecimal deptFee;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 13)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕日志 */
    @ApiModelProperty(value = "留痕记录", position = 16)
    private String note;

}
