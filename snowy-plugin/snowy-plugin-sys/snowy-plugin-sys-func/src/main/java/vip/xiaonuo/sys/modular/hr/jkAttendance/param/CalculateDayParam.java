package vip.xiaonuo.sys.modular.hr.jkAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CalculateDayParam {
    /** 开始时间 */
    @ApiModelProperty(value = "开始时间，年-月-日", required = true,position = 1)
    @NotBlank(message = "开始时间不能为空")
    private String beginTime;

    /** 类别 */
    @ApiModelProperty(value = "开始类别，1:上午，2:下午",required = true, position = 2)
    @NotBlank(message = "开始类别不能为空")
    private String beginType;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间,年-月-日",required = true, position = 3)
    @NotBlank(message = "结束时间不能为空")
    private String endTime;

    /** 类别 */
    @ApiModelProperty(value = "结束类别，1:上午，2:下午",required = true, position = 4)
    @NotBlank(message = "结束类别不能为空")
    private String endType;

}
