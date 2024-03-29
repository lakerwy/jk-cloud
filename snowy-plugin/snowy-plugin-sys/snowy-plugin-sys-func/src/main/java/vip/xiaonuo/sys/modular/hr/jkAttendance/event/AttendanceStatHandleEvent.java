package vip.xiaonuo.sys.modular.hr.jkAttendance.event;

import org.springframework.context.ApplicationEvent;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.HrUserAttendanceStatEvent;

/**
 * 打卡记录event
 * */
public class AttendanceStatHandleEvent extends ApplicationEvent {
    public AttendanceStatHandleEvent(HrUserAttendanceStatEvent hrUserAttendanceStatEvent) {
        super(hrUserAttendanceStatEvent);
    }
}
