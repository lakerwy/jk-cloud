package vip.xiaonuo.sys.modular.hr.userAttendanceStat.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class AttendanceAnalysisParam {
    /** 当前页 */
    @ApiModelProperty(value = "部门ID")
    private String orgId;

    @ApiModelProperty(value = "时间:yyyy-MM-dd",required = true)
    @NotNull(message = "时间不能为空")
    private String time;

    @ApiModelProperty(value = "时间类别：1 日，2 周，3 月",required = true)
    @NotNull(message = "时间类别不能为空")
    private Integer timeType;

    @ApiModelProperty(value = "数据类别[默认为1,2]：1 应到人数，2 打卡人数，3 出勤率，4 平均工时，5 请假人数，6 加班人数，7 迟到人数")
    private List<String> type;
}
