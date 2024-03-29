package vip.xiaonuo.sys.modular.hr.jkAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Setter
@Getter
public class AttendConfigCache {

    @ApiModelProperty(value = "打卡类型，OnDuty-上班，REST-休息，OffDuty-下班", position = 1)
    private String dutyType;
    @ApiModelProperty(value = "打卡开始时间", position = 2)
    private Time beginTime;
    @ApiModelProperty(value = "打卡结束时间", position = 3)
    private Time endTime;
    @ApiModelProperty(value = "打卡点时间，如8:30等", position = 4)
    private Time compareTime;
    @ApiModelProperty(value = "工时", position = 5)
    private String workHours;
    @ApiModelProperty(value = "顺序", position = 6)
    private Integer orderNumber;
    @ApiModelProperty(value = "工作开始时间", position = 7)
    private Time workBeginTime;
    @ApiModelProperty(value = "工作结束时间", position = 8)
    private Time workEndTime;
    @ApiModelProperty(value = "考勤班次类别组ID", position = 9)
    private String attendanceTypeId;
    @ApiModelProperty(value = "考勤班次类别组名称", position = 10)
    private String attendanceTypeName;
}
