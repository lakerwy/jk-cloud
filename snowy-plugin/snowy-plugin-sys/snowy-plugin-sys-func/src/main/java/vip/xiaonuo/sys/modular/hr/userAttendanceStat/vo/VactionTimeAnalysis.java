package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class VactionTimeAnalysis {

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 1)
    private Date firstBeginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 2)
    private Date firstEndTime;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 3)
    private Date secondBeginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 4)
    private Date secondEndTime;

    @ApiModelProperty(value = "时间列表-下标",position = 5)
    private List<String> dayList;

    @ApiModelProperty(value = "考勤数据",position = 6)
    private List<HrUserAttendanceStat> hrUserAttendanceStatList;

    @ApiModelProperty(value = "时间类别：1 日，2 周，3 月，默认为1")
    private Integer timeType;

    @ApiModelProperty(value = "时间区间列表",position = 7)
    private List<TimeDataVo> timeDataVoList;

}
