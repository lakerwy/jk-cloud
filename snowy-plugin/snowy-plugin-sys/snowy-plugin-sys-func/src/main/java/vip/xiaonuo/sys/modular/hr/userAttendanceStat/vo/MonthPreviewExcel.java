package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthPreviewExcel {
    @ApiModelProperty(value = "用户名称", position = 1)
    private String userName;

    @ApiModelProperty(value = "考勤组名称", position = 2)
    private String attendanceTypeName;

    @ApiModelProperty(value = "部门名称", position = 3)
    private String orgName;

    @ApiModelProperty(value = "员工编号", position = 4)
    private String empNo;

    @ApiModelProperty(value = "职位", position = 5)
    private String position;

    @ApiModelProperty(value = "出勤天数", position = 6)
    private Double attendanceDays;

    @ApiModelProperty(value = "休息天数", position = 7)
    private Double restDays;

    @ApiModelProperty(value = "工作时长", position = 8)
    private Integer workMinute;

    @ApiModelProperty(value = "迟到次数", position = 9)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长", position = 10)
    private Integer lateMinute;

    /** 严重迟到次数 */
    @ApiModelProperty(value = "严重迟到次数", position = 11)
    private Integer seriousLateNum;

    /** 严重迟到分钟数 */
    @ApiModelProperty(value = "严重迟到分钟数", position = 12)
    private Integer seriousLateMinutes;

    @ApiModelProperty(value = "旷工迟到次数", position = 13)
    private Integer lackLateNum;

    @ApiModelProperty(value = "旷工迟到时长", position = 14)
    private Integer lackLateTime;

    @ApiModelProperty(value = "早退次数", position = 15)
    private Integer leaveEarly;

    @ApiModelProperty(value = "早退时长", position = 16)
    private Integer leaveEarlyMinute;

    @ApiModelProperty(value = "上班缺卡次数", position = 17)
    private Integer lackOnDutyNum;

    @ApiModelProperty(value = "下班缺卡次数", position = 18)
    private Integer lackOffDutyNum;

    @ApiModelProperty(value = "旷工天数", position = 19)
    private Integer lackWork;

    @ApiModelProperty(value = "出差时长", position = 20)
    private Double businessTrip;

    @ApiModelProperty(value = "外出时长", position = 21)
    private Double outWorkHours;

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 22)
    private Double personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 23)
    private Double sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 24)
    private Double marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 25)
    private Double maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 26)
    private Double bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 27)
    private Double annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 28)
    private Double compensatoryLeave;

    @ApiModelProperty(value = "加班总时长", position = 29)
    private Double workOtHours;

    @ApiModelProperty(value = "工作日(转调休)", position = 30)
    private Double compensatoryWork;

    @ApiModelProperty(value = "休息日(转调休)", position = 31)
    private Double compensatoryRest;

    @ApiModelProperty(value = "节假日(转调休)", position = 32)
    private Double compensatoryFestival;
}
