package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;

public class UserAttendanceConfigEntity implements Serializable {
    private static final long serialVersionUID = 673991343992811511L;

    private Integer orderNumber;
    private String dutyType;
    private String attendanceTypeName;
    private String BeginMinTime;
    private String checkTime;
    private String endMinTime;
    private String workHours;

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public String getAttendanceTypeName() {
        return attendanceTypeName;
    }

    public void setAttendanceTypeName(String attendanceTypeName) {
        this.attendanceTypeName = attendanceTypeName;
    }

    public String getBeginMinTime() {
        return BeginMinTime;
    }

    public void setBeginMinTime(String beginMinTime) {
        BeginMinTime = beginMinTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getEndMinTime() {
        return endMinTime;
    }

    public void setEndMinTime(String endMinTime) {
        this.endMinTime = endMinTime;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }
}
