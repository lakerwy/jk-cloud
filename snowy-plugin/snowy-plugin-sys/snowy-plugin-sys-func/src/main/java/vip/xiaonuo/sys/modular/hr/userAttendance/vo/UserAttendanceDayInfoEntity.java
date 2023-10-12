package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAttendanceDayInfoEntity implements Serializable {

    private static final long serialVersionUID = 3509600620084792630L;
    @ApiModelProperty(value = "考勤打卡时间", position = 1)
    private String checkTime;
    @ApiModelProperty(value = "打卡类型", position = 2)
    private String checkType;
    @ApiModelProperty(value = "打卡是否有效", position = 3)
    private String abnormalFlag;
}
