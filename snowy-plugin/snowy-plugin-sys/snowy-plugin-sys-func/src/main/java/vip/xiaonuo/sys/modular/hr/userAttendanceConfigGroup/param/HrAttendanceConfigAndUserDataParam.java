package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 考勤班次组配置表编辑参数
 *
 * @author ljy
 * @date  2023/09/26 15:55
 **/
@Getter
@Setter
public class HrAttendanceConfigAndUserDataParam {

    /** 考勤组Group-主键 */
    @ApiModelProperty(value = "考勤组Group-主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID")
    private String leadId;

    /** 考勤人或部门信息 */
    @ApiModelProperty(value = "考勤人或部门信息")
    private List<HrAttendanceConfigAndUserData> HrAttendanceConfigAndUserData;
}
