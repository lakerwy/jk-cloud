package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class AttendanceConfigCacheEntity implements Serializable {
    private static final long serialVersionUID = 3713201478309050344L;

    private String attendanceTypeId;
    private List<UserAttendanceConfigEntity> userAttendanceConfigEntityList;
    private String userIds;
}
