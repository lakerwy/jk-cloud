package vip.xiaonuo.dev.modular.job.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.modular.server.service.InitConfigService;

import javax.annotation.Resource;

@Slf4j
@Component
public class EPCJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    InitConfigService initConfigService;

    @Override
    public void action() {
        try{
            log.info("定时更新EPC代码！[EPCJobTaskRunner]--Time:"+ DateUtil.now());
            initConfigService.initSystemJob();
        }catch (Exception e){
            log.error("");
        }
    }
}
