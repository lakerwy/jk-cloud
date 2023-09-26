package vip.xiaonuo.sys.modular.hr.userAttendanceConfigGroup.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HrAttendanceConfigAndUserData {
    /** 公司部门或人员ID */
    @ApiModelProperty(value = "公司部门或人员ID")
    private String employId;

    /** 部门或人员类别 */
    @ApiModelProperty(value = "部门或人员类别：1 部门，2 人员")
    private String employType;

    /** Group 考勤组ID */
    @ApiModelProperty(value = "考勤组ID")
    private String attendanceTypeId;

    /** 班次生效开始时间  */
    @ApiModelProperty(value = "班次生效开始时间")
    private Date beginTime;

    /** 班次生效结束时间  */
    @ApiModelProperty(value = "班次生效结束时间")
    private Date endTime;

    /** 当前的人或者部门是否生效这个考勤班次：0 -- 生效，1 未生效 */
    @ApiModelProperty(value = "当前的人或者部门是否生效这个考勤班次：0生效, 1未生效")
    private Integer isValid;
}
