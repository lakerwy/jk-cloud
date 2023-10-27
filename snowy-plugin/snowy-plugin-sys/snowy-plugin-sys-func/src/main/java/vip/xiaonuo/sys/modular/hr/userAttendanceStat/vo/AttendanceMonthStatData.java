package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AttendanceMonthStatData {
    @ApiModelProperty(value = "日期-天)", position = 1)
    private String day;

    @ApiModelProperty(value = "考勤结果)", position = 2)
    private String status;

    @ApiModelProperty(value = "考勤状态code)", position = 2)
    private String statusCode;

    public AttendanceMonthStatData(){}

    public AttendanceMonthStatData(String day,String status){
        this.day = day;
        this.status = status;
    }

    public AttendanceMonthStatData(String day,String status,String statusCode){
        this.day = day;
        this.status = status;
        this.statusCode = statusCode;
    }
}
