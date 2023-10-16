package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class AttendanceTimeSlot implements Serializable {
    private static final long serialVersionUID = -8207742516854399884L;

    @ApiModelProperty(value = "打卡类型，OnDuty-上班，REST-休息，OffDuty-下班", position = 1)
    private String dutyType;
    @ApiModelProperty(value = "打卡开始时间", position = 2)
    private Date beginTime;
    @ApiModelProperty(value = "打卡结束时间", position = 3)
    private Date endTime;
    @ApiModelProperty(value = "打卡点时间，如8:30等", position = 4)
    private Date compareTime;
    @ApiModelProperty(value = "工时", position = 5)
    private String workHours;
    @ApiModelProperty(value = "顺序", position = 6)
    private Integer orderNumber;
    @ApiModelProperty(value = "工作开始时间", position = 7)
    private Date workBeginTime;
    @ApiModelProperty(value = "工作结束时间", position = 8)
    private Date workEndTime;
    @ApiModelProperty(value = "考勤班次类别组ID", position = 9)
    private String attendanceTypeId;
}
