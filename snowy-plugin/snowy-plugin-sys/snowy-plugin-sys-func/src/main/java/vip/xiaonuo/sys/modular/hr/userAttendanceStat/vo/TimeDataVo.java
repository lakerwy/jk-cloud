package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TimeDataVo {

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 1)
    private Date beginTime;

    /** 结束 */
    @ApiModelProperty(value = "结束时间", position = 2)
    private Date endTime;

    public TimeDataVo(){};

    public TimeDataVo(Date beginTime,Date endTime){
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
