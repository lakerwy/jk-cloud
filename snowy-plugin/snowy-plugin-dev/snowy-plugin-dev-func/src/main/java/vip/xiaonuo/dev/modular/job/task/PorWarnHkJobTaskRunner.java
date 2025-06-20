package vip.xiaonuo.dev.modular.job.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.api.BizExtApi;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;

import javax.annotation.Resource;

/**
 * 回款风险预警
 */
@Slf4j
@Component
public class PorWarnHkJobTaskRunner implements CommonTimerTaskRunner {

    @Resource
    private BizExtApi bizExtApi;
    @Override
    public void action() {
        try {
            bizExtApi.doWarn("回款风险预警");
            log.info("回款风险预警 自动生成 成功");
        }catch (Exception e){
            log.debug("回款风险预警 自动生成" + e.getMessage());
        }
    }
}
