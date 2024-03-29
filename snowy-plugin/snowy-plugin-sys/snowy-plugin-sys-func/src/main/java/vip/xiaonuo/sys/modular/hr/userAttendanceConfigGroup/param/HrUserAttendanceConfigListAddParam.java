package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
public class HrUserAttendanceConfigListAddParam {

    @ApiModelProperty(value = "班次负责人", position = 1,required = true)
    @NotBlank(message = "leadId不能为空")
    String leadId;

    /** 考勤班次类别组ID */
    @ApiModelProperty(value = "考勤班次类别组ID", position = 2,required = true)
    @NotBlank(message = "考勤组ID不能为空")
    private String attendanceTypeId;

    @ApiModelProperty(value = "班次数据", position = 3)
    List<HrUserAttendanceConfigAddParam>  hrUserAttendanceConfigAddParamList;
}
