package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ExcelStatParam {

    @ApiModelProperty(value = "考勤组ID", position = 5)
    private String attendanceTypeId;

    @ApiModelProperty(value = "考勤组名称", position = 6)
    private String attendanceTypeName;

    @ApiModelProperty(value = "职位", position = 7)
    private String position;

    @ApiModelProperty(value = "打卡记录和状态", position = 8)
    private List<Map<Integer,List<AttendanceDayStatData>>> dayCheckTimeMapList;

    @ApiModelProperty(value = "开始时间,yyyy-MM-dd HH:mm:ss", required = true,position = 9)
    @NotBlank(message = "开始时间不能为空")
    private String beginTime;

    @ApiModelProperty(value = "结束时间,yyyy-MM-dd HH:mm:ss",  required = true,position = 10)
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = "统计人员ID列表", position = 11)
    private List<String> userList;

    @ApiModelProperty(value = "统计部门ID列表", position = 12)
    private List<String> orgList;

}
