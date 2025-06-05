package vip.xiaonuo.biz.modular.prj.por.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目产值台账 - 项目管理 - 项目计划管理实体
 *
 * @author yy
 * @date  2025/06/03 09:42
 **/
@Getter
@Setter
@TableName("BIZ_POR_TASK")
public class PorTask {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 2)
    private String porProjectId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 3)
    private String taskName;

    /** 责任人id */
    @ApiModelProperty(value = "责任人id", position = 4)
    private String taskLeaderId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 5)
    private String taskLeaderName;

    /** 计划开始日期 */
    @ApiModelProperty(value = "计划开始日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskPlanStartDate;

    /** 计划结束日期 */
    @ApiModelProperty(value = "计划结束日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskPlanEndDate;

    /** 实际开始日期 */
    @ApiModelProperty(value = "实际开始日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskRealStartDate;

    /** 实际结束日期 */
    @ApiModelProperty(value = "实际结束日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date taskRealEndDate;

    /** 实际工期天数 */
    @ApiModelProperty(value = "实际工期天数", position = 10)
    private Integer taskRealDates;

    /** 任务完成进度百分比 */
    @ApiModelProperty(value = "任务完成进度百分比", position = 11)
    private Double taskFinishRate;

    /** 任务描述 */
    @ApiModelProperty(value = "任务描述", position = 12)
    private String taskDesc;

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

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 17)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 18)
    private String attach;

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
