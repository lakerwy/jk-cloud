package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MonthStatDataStatus {
    @ApiModelProperty(value = "时间", position = 1)
    private String time;

    @ApiModelProperty(value = "状态", position = 2)
    private String status;

    @ApiModelProperty(value = "周时间", position = 3)
    private String weekTime;

}
