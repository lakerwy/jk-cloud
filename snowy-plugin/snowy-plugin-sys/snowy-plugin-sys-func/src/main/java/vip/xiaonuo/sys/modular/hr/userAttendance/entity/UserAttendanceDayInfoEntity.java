package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import java.io.Serializable;

public class UserAttendanceDayInfoEntity implements Serializable {

    private static final long serialVersionUID = 3509600620084792630L;
    private String checkTime; //考勤时间
    private String checkType; //打卡类型
    private String abnormalFlag; //打卡是否有效


    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getAbnormalFlag() {
        return abnormalFlag;
    }

    public void setAbnormalFlag(String abnormalFlag) {
        this.abnormalFlag = abnormalFlag;
    }
}
