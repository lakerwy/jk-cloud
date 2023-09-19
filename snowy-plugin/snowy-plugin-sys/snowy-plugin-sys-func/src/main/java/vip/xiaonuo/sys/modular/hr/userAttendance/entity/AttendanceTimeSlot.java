package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;
import java.util.Date;

public class AttendanceTimeSlot implements Serializable {
    private static final long serialVersionUID = -8207742516854399884L;

    private String dutyType;
    private Date beginTime;
    private Date endTime;
    private Date compareTime;
    private String workHours;
    private Integer orderNumber;
    private Date workBeginTime;//工作开始i时间
    private Date workEndTime;//工作结束时间

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

    public Date getCompareTime() {
        return compareTime;
    }

    public void setCompareTime(Date compareTime) {
        this.compareTime = compareTime;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getWorkBeginTime() {
        return workBeginTime;
    }

    public void setWorkBeginTime(Date workBeginTime) {
        this.workBeginTime = workBeginTime;
    }

    public Date getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }
}
