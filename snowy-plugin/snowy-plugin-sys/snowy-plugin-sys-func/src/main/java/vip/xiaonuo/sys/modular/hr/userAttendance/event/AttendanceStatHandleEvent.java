package vip.xiaonuo.sys.modular.hr.userAttendance.event;

import org.springframework.context.ApplicationEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStatEvent;

/**
 * 打卡记录event
 * */
public class AttendanceStatHandleEvent extends ApplicationEvent {
    public AttendanceStatHandleEvent(HrUserAttendanceStatEvent hrUserAttendanceStatEvent) {
        super(hrUserAttendanceStatEvent);
    }
}
