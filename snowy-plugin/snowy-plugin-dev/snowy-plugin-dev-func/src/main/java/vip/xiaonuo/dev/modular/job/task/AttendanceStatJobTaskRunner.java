package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.sys.api.SysAttendanceApi;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Component
public class AttendanceStatJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void action() {
        try{
            log.info("迁移打卡数据到统计表[HR_USER_ATTENDANCE_STAT]--Time:"+ DateUtil.now());
            attendanceApi.attendanceStatJobHandle();
        }catch (Exception e){
            log.error("");
        }
    }
}
