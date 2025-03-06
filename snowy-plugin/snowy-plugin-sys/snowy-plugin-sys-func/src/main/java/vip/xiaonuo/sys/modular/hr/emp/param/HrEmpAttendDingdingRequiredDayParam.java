package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 设置应出勤天数参数
 *
 * @author pzh
 * @date  2025/03/06 11:48
 **/
@Getter
@Setter
public class HrEmpAttendDingdingRequiredDayParam {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月（yyyyMM）", required = true)
    @NotBlank(message = "所属年月不能为空")
    private String yearMonth;

    /** 应计薪天数 */
    @ApiModelProperty(value = "应计薪天数", required = true)
    @NotNull(message = "应计薪天数不能为空")
    private Float requiredDay;
}
