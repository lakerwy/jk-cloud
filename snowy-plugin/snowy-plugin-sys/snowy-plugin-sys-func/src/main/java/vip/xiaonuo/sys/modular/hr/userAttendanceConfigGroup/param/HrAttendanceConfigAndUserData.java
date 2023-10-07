package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrAttendanceConfigAndUserData {
    /** 公司部门或人员ID */
    @ApiModelProperty(value = "公司部门或人员ID列表，逗号隔开，如：1,2,3,5")
    private String employIds;

    /** 部门或人员类别 */
    @ApiModelProperty(value = "部门或人员类别：1 部门，2 人员")
    private String employType;

    /** 当前的人或者部门是否生效这个考勤班次：0 -- 生效，1 未生效 */
    @ApiModelProperty(value = "当前的人或者部门是否生效这个考勤班次：0生效, 1未生效，默认为0")
    private Integer isValid;
}
