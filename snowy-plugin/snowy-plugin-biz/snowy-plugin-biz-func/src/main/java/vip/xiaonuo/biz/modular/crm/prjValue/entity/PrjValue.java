package vip.xiaonuo.biz.modular.crm.prjValue.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产院及子公司产值实体
 *
 * @author pzh
 * @date  2025/06/05 10:04
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_VALUE")
public class PrjValue {

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

    /** 经营产值 */
    @ApiModelProperty(value = "经营产值", position = 6)
    private BigDecimal operateAmount;

    /** 生产产值 */
    @ApiModelProperty(value = "生产产值", position = 7)
    private BigDecimal productAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
