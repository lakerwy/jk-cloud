package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttendanceStatNumData {

    @ApiModelProperty(value = "打卡人数", position = 1)
    private Long attendanceNum;
    @ApiModelProperty(value = "应到人数", position = 2)
    private Long userNum;
    @ApiModelProperty(value = "未打卡人数", position = 3)
    private Long noAttendanceNum;
    @ApiModelProperty(value = "迟到人数", position = 4)
    private Long lateUserNum;
    @ApiModelProperty(value = "外勤人数", position = 5)
    private Long egressNum;


}
