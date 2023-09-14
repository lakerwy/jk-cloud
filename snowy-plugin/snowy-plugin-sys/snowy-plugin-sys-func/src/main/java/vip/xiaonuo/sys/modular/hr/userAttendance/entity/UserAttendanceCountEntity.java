package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;

public class UserAttendanceCountEntity implements Serializable {

    private static final long serialVersionUID = 525837701569619304L;

    private String userName;
    private String dayAttendanceStatus;
    private String dayDate;

    public String getDayAttendanceStatus() {
        return dayAttendanceStatus;
    }

    public void setDayAttendanceStatus(String dayAttendanceStatus) {
        this.dayAttendanceStatus = dayAttendanceStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }
}
