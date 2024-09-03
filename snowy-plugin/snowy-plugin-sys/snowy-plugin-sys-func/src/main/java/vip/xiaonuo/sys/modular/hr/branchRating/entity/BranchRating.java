package vip.xiaonuo.sys.modular.hr.branchRating.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 分公司评级实体
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
@TableName("BRANCH_RATING")
public class BranchRating {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 区域 */
    @ApiModelProperty(value = "区域", position = 3)
    private String area;

    /** 分公司名称/经营范围 */
    @ApiModelProperty(value = "分公司名称/经营范围", position = 4)
    private String name;

    /** 区域经理 */
    @ApiModelProperty(value = "区域经理", position = 5)
    private String areaManager;

    /** 年份(如：2024) */
    @ApiModelProperty(value = "年份(如：2024)", position = 6)
    private String year;

    /** 季度(上半年、下半年) */
    @ApiModelProperty(value = "季度(上半年、下半年)", position = 7)
    private String quarter;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 8)
    private String totalScore;

    /** 级别(A、B、C、D、-) */
    @ApiModelProperty(value = "级别(A、B、C、D、-)", position = 9)
    private String level;

    /** 公示状态(已公示、未公示) */
    @ApiModelProperty(value = "公示状态(已公示、未公示)", position = 10)
    private String status;

    /** 排序序号 */
    @ApiModelProperty(value = "排序序号", position = 11)
    private Integer indexSort;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
