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
 * 项目产值台账 - 项目管理实体
 *
 * @author yy
 * @date  2025/06/03 09:39
 **/
@Getter
@Setter
@TableName("BIZ_POR_PROJECT")
public class PorProject {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 立项Id */
    @ApiModelProperty(value = "立项Id", position = 2)
    private String projectId;

    /** 计划开工日期 */
    @ApiModelProperty(value = "计划开工日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planStartDate;

    /** 计划完工日期 */
    @ApiModelProperty(value = "计划完工日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planEndDate;

    /** 计划工期总日历天数 */
    @ApiModelProperty(value = "计划工期总日历天数", position = 5)
    private Integer planDates;

    /** 项目状态 */
    @ApiModelProperty(value = "项目状态", position = 6)
    private String projectStatus;

    /** 开工日期 */
    @ApiModelProperty(value = "开工日期", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realStartDate;

    /** 实际完工日期 */
    @ApiModelProperty(value = "实际完工日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date realEndDate;

    /** 实际工期总日历天数 */
    @ApiModelProperty(value = "实际工期总日历天数", position = 9)
    private Integer realDates;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String projectMemo;

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

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 15)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 16)
    private String attach;

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


    /** 责任部门Id */
    @ApiModelProperty(value = "责任部门Id", position = 21)
    private String dutyDeptId;

    /** 责任部门 */
    @ApiModelProperty(value = "责任部门", position = 22)
    private String dutyDeptName;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 23)
    private String dutyId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 24)
    private String dutyName;
}
