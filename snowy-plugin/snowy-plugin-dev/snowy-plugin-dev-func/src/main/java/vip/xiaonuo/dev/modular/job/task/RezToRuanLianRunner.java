package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.sys.api.SysAttendanceApi;

import javax.annotation.Resource;

@Slf4j
@Component
public class RezToRuanLianRunner implements CommonTimerTaskRunner {
    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void action() {
        try{
            log.info("推送会议预约信息到软联--Time:"+ DateUtil.now());
            attendanceApi.rezToRuanLianJobHandle();
        }catch (Exception e){
            log.error("");
        }
    }
}
