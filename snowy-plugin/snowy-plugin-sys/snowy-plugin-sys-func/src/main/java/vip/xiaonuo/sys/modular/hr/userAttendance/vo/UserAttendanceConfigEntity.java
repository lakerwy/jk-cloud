package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAttendanceConfigEntity implements Serializable {
    private static final long serialVersionUID = 673991343992811511L;

    @ApiModelProperty(value = "顺序", position = 1)
    private Integer orderNumber;
    @ApiModelProperty(value = "打卡类型", position = 2)
    private String dutyType;
    @ApiModelProperty(value = "考勤组名称", position = 3)
    private String attendanceTypeName;
    @ApiModelProperty(value = "开始时间", position = 4)
    private String BeginMinTime;
    @ApiModelProperty(value = "考勤打卡时间", position = 5)
    private String checkTime;
    @ApiModelProperty(value = "结束时间", position = 6)
    private String endMinTime;
    @ApiModelProperty(value = "工时-工作时间", position = 7)
    private String workHours;
}
