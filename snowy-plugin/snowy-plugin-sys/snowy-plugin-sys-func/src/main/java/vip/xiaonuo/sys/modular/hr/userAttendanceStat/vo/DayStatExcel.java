package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DayStatExcel {
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

    @ApiModelProperty(value = "日期，年月日-星期几", position = 6)
    private String date;

    @ApiModelProperty(value = "班次详情", position = 7)
    private String attendanceTypeDetails;

    @ApiModelProperty(value = "上班1打卡时间", position = 8)
    private String onDutyTime1;

    @ApiModelProperty(value = "上班1打卡结果", position = 9)
    private String onDutyStatus1;

    @ApiModelProperty(value = "上班2打卡时间", position = 10)
    private String onDutyTime2;

    @ApiModelProperty(value = "上班2打卡结果", position = 11)
    private String onDutyStatus2;

    @ApiModelProperty(value = "休息打卡时间", position = 12)
    private String restTime;

    @ApiModelProperty(value = "休息打卡结果", position = 13)
    private String restStatus;

    @ApiModelProperty(value = "下班1打卡时间", position = 14)
    private String offDutyTime1;

    @ApiModelProperty(value = "下班1打卡结果", position = 15)
    private String offDutyStatus1;

    @ApiModelProperty(value = "下班2打卡时间", position = 16)
    private String offDutyTime2;

    @ApiModelProperty(value = "下班2打卡结果", position = 17)
    private String offDutyStatus2;

    @ApiModelProperty(value = "关联的审批单", position = 18)
    private String processorIdentifier;

    @ApiModelProperty(value = "出勤天数", position = 19)
    private Double attendanceDays;

    @ApiModelProperty(value = "休息天数", position = 20)
    private Double restDays;

    @ApiModelProperty(value = "工作时长", position = 21)
    private Integer workMinute;

    @ApiModelProperty(value = "迟到次数", position = 22)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长", position = 23)
    private Integer lateMinute;

    @ApiModelProperty(value = "严重迟到次数", position = 24)
    private Integer seriousLateNum;

    @ApiModelProperty(value = "严重迟到时长", position = 25)
    private Integer seriousLateMinute;

    @ApiModelProperty(value = "旷工迟到天数", position = 13)
    private Integer lackLateNum;

    @ApiModelProperty(value = "早退次数", position = 26)
    private Integer leaveEarlyNum;

    @ApiModelProperty(value = "早退时长", position = 27)
    private Integer leaveEarlyMinute;

    @ApiModelProperty(value = "上班缺卡次数", position = 28)
    private Integer lackOnDutyNum;

    @ApiModelProperty(value = "下班缺卡次数", position = 29)
    private Integer lackOffDutyNum;

    @ApiModelProperty(value = "旷工天数", position = 30)
    private Integer lackWork;

    @ApiModelProperty(value = "出差时长", position = 31)
    private Double businessTripHours;

    @ApiModelProperty(value = "外出时长", position = 32)
    private Double outWorkHours;

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 33)
    private String personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 34)
    private String sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 35)
    private String marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 36)
    private String maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 37)
    private String bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 38)
    private String annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 39)
    private String compensatoryLeave;

    @ApiModelProperty(value = "加班总时长", position = 40)
    private Double workOtHours;

    @ApiModelProperty(value = "工作日(转调休)", position = 41)
    private Double compensatoryWork;

    @ApiModelProperty(value = "休息日(转调休)", position = 42)
    private Double compensatoryRest;

    @ApiModelProperty(value = "节假日(转调休)", position = 43)
    private Double compensatoryFestival;
}
