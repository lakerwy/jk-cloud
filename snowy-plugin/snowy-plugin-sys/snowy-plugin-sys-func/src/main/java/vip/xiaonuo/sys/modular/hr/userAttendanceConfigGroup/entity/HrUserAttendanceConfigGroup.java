/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤班次组配置表实体
 *
 * @author ljy
 * @date  2023/09/21 15:55
 **/
@Getter
@Setter
@TableName("HR_USER_ATTENDANCE_CONFIG_GROUP")
public class HrUserAttendanceConfigGroup {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 班次名称 */
    @ApiModelProperty(value = "班次名称", position = 2)
    private String attendanceTypeName;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 3)
    private String leadId;

    /** 负责人名称 */
    @ApiModelProperty(value = "负责人名称", position = 4)
    private String leadName;

    /** 班次应用人数 */
    @ApiModelProperty(value = "班次应用人数", position = 5)
    private Integer attendanceUserNum;

    /** 额外信息 */
    @ApiModelProperty(value = "额外信息", position = 6)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 是否默认考勤配置 */
    @ApiModelProperty(value = "是否默认考勤配置", position = 12)
    private String isDefaultConfig;//IS_DEFAULT_CONFIG

    /** 有效开始时间 */
    @ApiModelProperty(value = "有效开始时间", position = 13)
    private String validBeginTime;

    /** 有效结束时间 */
    @ApiModelProperty(value = "有效结束时间", position = 14)
    private String validEndTime;

    /** 严重迟到分钟数 */
    @ApiModelProperty(value = "严重迟到分钟数", position = 15)
    private Integer seriousLateMinute;

    /** 严重早退分钟数 */
    @ApiModelProperty(value = "严重早退分钟数", position = 15)
    private Integer seriousLeaveMinute;

    /**考勤时间*/
    @ApiModelProperty(value = "考勤时间", position = 16)
    @TableField(exist = false)
    private String atdTime;
}
