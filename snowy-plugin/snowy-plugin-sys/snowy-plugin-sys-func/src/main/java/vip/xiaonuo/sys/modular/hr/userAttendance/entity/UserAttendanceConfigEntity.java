package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAttendanceConfigEntity implements Serializable {
    private static final long serialVersionUID = 673991343992811511L;

    private Integer orderNumber;
    private String dutyType;
    private String attendanceTypeName;
    private String BeginMinTime;
    private String checkTime;
    private String endMinTime;
    private String workHours;
}
