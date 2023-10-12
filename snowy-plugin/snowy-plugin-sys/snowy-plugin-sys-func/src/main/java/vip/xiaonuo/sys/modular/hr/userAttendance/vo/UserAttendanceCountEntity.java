package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserAttendanceCountEntity implements Serializable {

    private static final long serialVersionUID = 525837701569619304L;

    @ApiModelProperty(value = "用户名称", position = 1)
    private String userName;
    @ApiModelProperty(value = "打卡状态 - 是否异常", position = 2)
    private String dayAttendanceStatus;
    @ApiModelProperty(value = "时间", position = 3)
    private String dayDate;
    @ApiModelProperty(value = "考勤打卡时间", position = 4)
    private Date checkTime;

    public UserAttendanceCountEntity(){}
    public UserAttendanceCountEntity(String userName,String dayAttendanceStatus,String dayDate,Date checkTime){
        this.userName = userName;
        this.dayAttendanceStatus = dayAttendanceStatus;
        this.dayDate = dayDate;
        this.checkTime = checkTime;
    }
}
