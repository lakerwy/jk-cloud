package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity.HrUserAttendanceConfig;

import java.util.List;

@Setter
@Getter
public class UserAtdConfigMap {
    @ApiModelProperty(value = "考勤组ID", position = 1)
    private String atdGroupID;

    @ApiModelProperty(value = "考勤组名称", position = 2)
    private String atdGroupName;

    @ApiModelProperty(value = "用户名称", position = 3)
    private List<HrUserAttendanceConfig> configList;


}
