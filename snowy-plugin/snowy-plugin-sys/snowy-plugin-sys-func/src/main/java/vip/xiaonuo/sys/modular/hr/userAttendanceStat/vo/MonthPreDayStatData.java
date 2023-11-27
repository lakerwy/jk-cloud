package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class MonthPreDayStatData {
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

    @ApiModelProperty(value = "班次打卡详情:map1:key-dutyName,value-打卡名称，map2:key-dutyAtdTime,value-打卡时间；map3:key-dutyAtdDate,value-打卡结果", position = 7)
    private List<Map<String,String>> dutyMapList;
    /*@ApiModelProperty(value = "上班打卡时间", position = 8)
    private String onDutyTime;

    @ApiModelProperty(value = "上班打卡结果", position = 9)
    private String onDutyStatus;

    @ApiModelProperty(value = "休息打卡时间", position = 10)
    private String restTime;

    @ApiModelProperty(value = "休息打卡结果", position = 11)
    private String restStatus;

    @ApiModelProperty(value = "下班打卡时间", position = 12)
    private String offDutyTime;

    @ApiModelProperty(value = "下班打卡结果", position = 13)
    private String offDutyStatus;*/

    @ApiModelProperty(value = "关联的审批单", position = 14)
    private String processorIdentifier;

    @ApiModelProperty(value = "出勤天数", position = 15)
    private Double attendanceDays;

    @ApiModelProperty(value = "休息天数", position = 16)
    private Double restDays;

    @ApiModelProperty(value = "工作时长", position = 17)
    private Integer workMinute;

    @ApiModelProperty(value = "迟到次数", position = 18)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长", position = 19)
    private Integer lateMinute;

    @ApiModelProperty(value = "严重迟到次数", position = 20)
    private Integer seriousLateNum;

    @ApiModelProperty(value = "严重迟到时长", position = 21)
    private Integer seriousLateMinute;

    @ApiModelProperty(value = "早退次数", position = 22)
    private Integer leaveEarlyNum;

    @ApiModelProperty(value = "早退时长", position = 23)
    private Integer leaveEarlyMinute;

    @ApiModelProperty(value = "缺卡次数", position = 24)
    private Integer lackAttendance;

    @ApiModelProperty(value = "旷工天数", position = 25)
    private Integer lackWork;

    @ApiModelProperty(value = "出差时长", position = 26)
    private Double businessTripDays;

    @ApiModelProperty(value = "外出时长", position = 27)
    private Double outWorkHours;

    /** 旷工数据，出差数据，外出数据*/

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 28)
    private String personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 29)
    private String sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 30)
    private String marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 31)
    private String maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 32)
    private String bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 33)
    private String annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 34)
    private String compensatoryLeave;

    @ApiModelProperty(value = "加班总时长", position = 35)
    private Double workOtHours;
}
