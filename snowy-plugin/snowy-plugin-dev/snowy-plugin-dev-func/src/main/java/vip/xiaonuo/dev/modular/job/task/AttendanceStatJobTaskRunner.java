package vip.xiaonuo.dev.modular.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.sys.api.SysAttendanceApi;

import javax.annotation.Resource;


@Slf4j
@Component
public class AttendanceStatJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void action() {

    }
}
