package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;
import java.util.Date;

public class AttendanceTimeSlot implements Serializable {
    private static final long serialVersionUID = -8207742516854399884L;

    private String dutyType;
    private Date beginTime;
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }
}
