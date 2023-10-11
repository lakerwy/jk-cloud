package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;
import java.util.Date;

public class UserAttendanceCountEntity implements Serializable {

    private static final long serialVersionUID = 525837701569619304L;

    private String userName;
    private String dayAttendanceStatus;
    private String dayDate;
    private Date checkTime;

    public UserAttendanceCountEntity(){}

    public UserAttendanceCountEntity(String userName,String dayAttendanceStatus,String dayDate,Date checkTime){
        this.userName = userName;
        this.dayAttendanceStatus = dayAttendanceStatus;
        this.dayDate = dayDate;
        this.checkTime = checkTime;
    }

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

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
}
