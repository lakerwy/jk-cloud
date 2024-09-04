package vip.xiaonuo.sys.modular.hr.branchRating.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 分公司评级-公示年份和季度实体
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Getter
@Setter
@TableName("BRANCH_RATING_PUBLIC")
public class BranchRatingPublic {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 当前公示年份(如：2024) */
    @ApiModelProperty(value = "当前公示年份(如：2024)", position = 3)
    private String year;

    /** 当前公示季度(上半年、下半年) */
    @ApiModelProperty(value = "当前公示季度(上半年、下半年)", position = 4)
    private String quarter;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 6)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
