package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.sys.api.DingTalkApi;

import javax.annotation.Resource;


@Slf4j
@Component
public class SyncAttendJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    DingTalkApi dingTalkApi;

    @Override
    public void action() {
        try{
            log.info("同步钉钉考勤数据(上一个月)--Time:"+ DateUtil.now());
            dingTalkApi.syncLastMonthAttend();
        }catch (Exception e){
            log.error("同步钉钉考勤数据错误：{}", e.getMessage());
        }
    }
}
