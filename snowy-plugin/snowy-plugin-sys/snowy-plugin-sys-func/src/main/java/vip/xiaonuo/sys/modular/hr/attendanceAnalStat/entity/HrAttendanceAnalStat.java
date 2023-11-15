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
package vip.xiaonuo.sys.modular.hr.attendanceAnalStat.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤-员工考勤统计分析数据汇总表实体
 *
 * @author ljy
 * @date  2023/11/04 08:35
 **/
@Getter
@Setter
@TableName("HR_ATTENDANCE_ANAL_STAT")
public class HrAttendanceAnalStat {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 人员ID */
    @ApiModelProperty(value = "人员ID", position = 2)
    private String userId;

    /** 人员名称 */
    @ApiModelProperty(value = "人员名称", position = 3)
    private String userName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 4)
    private String postion;

    /** 是否打卡 */
    @ApiModelProperty(value = "是否打卡", position = 5)
    private Integer attendanceFlag;

    /** 是否请假 */
    @ApiModelProperty(value = "是否请假", position = 6)
    private Integer holidayFlag;

    /** 请假多久：最小单位-半天 */
    @ApiModelProperty(value = "请假多久：最小单位-半天", position = 7)
    private Double holidayDays;

    /** 假期类型：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7 */
    @ApiModelProperty(value = "假期类型：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7", position = 8)
    private Integer holidayType;

    /** 是否加班 */
    @ApiModelProperty(value = "是否加班", position = 9)
    private Integer workotFlag;

    /** 加班时长，最小单位：0.5小时 */
    @ApiModelProperty(value = "加班时长，最小单位：0.5小时", position = 10)
    private Double workotHours;

    /** 是否迟到 */
    @ApiModelProperty(value = "是否迟到", position = 11)
    private Integer lateFlag;

    /** 迟到分钟数，最小单位：1分钟 */
    @ApiModelProperty(value = "迟到分钟数，最小单位：1分钟", position = 12)
    private Integer lateMinutes;

    /** 是否早退 */
    @ApiModelProperty(value = "是否早退", position = 13)
    private Integer leaveEarlyFlag;

    /** 早退时间：最小单位：1分钟 */
    @ApiModelProperty(value = "早退时间：最小单位：1分钟", position = 14)
    private Integer leaveEarlyMinutes;

    /** 是否出差 */
    @ApiModelProperty(value = "是否出差", position = 15)
    private Integer businessTripFlag;

    /** 出差时间：最小单位：0.5天 */
    @ApiModelProperty(value = "出差时间：最小单位：0.5天", position = 16)
    private Double businessTripDays;

    /** 是否外出 */
    @ApiModelProperty(value = "是否外出", position = 17)
    private Integer gooutFlag;

    /** 外出时长，最小单位：0.5小时 */
    @ApiModelProperty(value = "外出时长，最小单位：0.5小时", position = 18)
    private Double gooutHours;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 19)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 考勤时间 */
    @ApiModelProperty(value = "考勤时间", position = 25)
    private Date checkTime;

    /** 是否补卡 */
    @ApiModelProperty(value = "是否补卡:空或者0非补卡，1 为补卡", position = 26)
    private Integer resubmitFlag;

    /** 工时-单位：分钟 */
    @ApiModelProperty(value = "工时-单位：分钟", position = 27)
    private Double workHours;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 28)
    private String orgId;
}
