package vip.xiaonuo.oa.modular.attendance.holiday.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 假期类型配置表实体
 *
 * @author mayike
 * @date  2024/07/05 09:19
 **/
@Getter
@Setter
@TableName("OA_ATTN_HOLIDAY_RULE")
public class HolidayRule {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 假期类型 */
    @ApiModelProperty(value = "假期类型", position = 3)
    private String holidayName;

    /** 请假单位(hour/day) */
    @ApiModelProperty(value = "请假单位(hour/day)", position = 4)
    private String leaveUnit;

    /** 计算方式（按照工作日计算 0 /自然日计算 1） */
    @ApiModelProperty(value = "计算方式（按照工作日计算 0 /自然日计算 1）", position = 5)
    private Integer dayType;

    /** 是否限额 */
    @ApiModelProperty(value = "是否限额", position = 6)
    private Boolean expireFlag;

    /** 余额设置方式 （系统自动设置 0 手动发放） */
    @ApiModelProperty(value = "余额设置方式 （系统自动设置 0 手动发放）", position = 7)
    private Integer setWay;

    /** 有效期类型 （0 长期有效 1 手动设置） */
    @ApiModelProperty(value = "有效期类型 （0 长期有效 1 手动设置）", position = 8)
    private Integer expireLimit;

    /** 过期时间 */
    @ApiModelProperty(value = "过期时间", position = 9)
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern= DatePattern.NORM_DATE_PATTERN)
//    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private String expireDate;

    /** 最大可结转天数 */
    @ApiModelProperty(value = "最大可结转天数", position = 10)
    private Double maxCarryOver;

    /** 结转比例 */
    @ApiModelProperty(value = "结转比例", position = 11)
    private Double carryOverRatio;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String remark;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人ID */
    @ApiModelProperty(value = "创建人ID", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
