package vip.xiaonuo.sys.modular.hr.jkAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AttendanceConfigCacheEntity implements Serializable {
    private static final long serialVersionUID = 3713201478309050344L;

    @ApiModelProperty(value = "考勤组ID", position = 1)
    private String attendanceTypeId;
    private List<UserAttendanceConfigEntity> userAttendanceConfigEntityList;
    @ApiModelProperty(value = "人员ID", position = 3)
    private String userIds;
}
