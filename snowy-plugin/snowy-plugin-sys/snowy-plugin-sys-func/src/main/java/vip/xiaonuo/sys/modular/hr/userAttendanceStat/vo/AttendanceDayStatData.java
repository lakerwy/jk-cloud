package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AttendanceDayStatData {

    @ApiModelProperty(value = "打卡时间)", position = 1)
    private Date checkTime;

    /** 状态 */
    @ApiModelProperty(value = "状态，1 正常，2 无效，3 迟到，4 早退，5 补卡，6 请假", position = 2)
    private Integer status;

    public AttendanceDayStatData(){}

    public AttendanceDayStatData(Date checkTime,Integer status){
        this.checkTime = checkTime;
        this.status = status;
    }
}
