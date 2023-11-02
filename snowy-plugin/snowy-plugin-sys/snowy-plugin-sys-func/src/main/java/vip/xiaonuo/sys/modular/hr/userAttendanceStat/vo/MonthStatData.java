package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MonthStatData {

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

    @ApiModelProperty(value = "迟到次数", position = 7)
    private Integer lateNum;

    @ApiModelProperty(value = "迟到时长", position = 8)
    private Integer lateMinute;

    @ApiModelProperty(value = "上班缺卡次数", position = 9)
    private Integer lackOnDutyNum;

    @ApiModelProperty(value = "下班缺卡次数", position = 10)
    private Integer lackOffDutyNum;

    @ApiModelProperty(value = "旷工天数", position = 11)
    private Integer lackWork;

    @ApiModelProperty(value = "工作时长", position = 12)
    private Integer workMinute;

    /** 事假 */
    @ApiModelProperty(value = "事假", position = 13)
    private String personalLeave;

    /** 病假 */
    @ApiModelProperty(value = "病假", position = 14)
    private String sickLeave;

    /** 婚假 */
    @ApiModelProperty(value = "婚假", position = 15)
    private String marriageLeave;

    /** 产假 */
    @ApiModelProperty(value = "产假", position = 16)
    private String maternityLeave;

    /** 丧假 */
    @ApiModelProperty(value = "丧假", position = 17)
    private String bereavementLeave;

    /** 年假 */
    @ApiModelProperty(value = "年假", position = 18)
    private String annualLeave;

    /** 调休 */
    @ApiModelProperty(value = "调休", position = 19)
    private String compensatoryLeave;

    @ApiModelProperty(value = "出差时长", position = 20)
    private Double businessTripHours;

    @ApiModelProperty(value = "外出时长", position = 21)
    private Double outWorkHours;

    @ApiModelProperty(value = "加班总时长", position = 22)
    private Double workOtHours;

    @ApiModelProperty(value = "考勤结果", position = 23)
    private List<MonthStatDataStatus> monthStatDataStatusList;

}
