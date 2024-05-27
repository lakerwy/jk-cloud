package vip.xiaonuo.dev.modular.job.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.modular.sealApply.service.SealApplyService;
import vip.xiaonuo.dev.modular.sealMessage.service.SealMessageService;

import javax.annotation.Resource;

/**
 * 同步云玺印章用户信息
 */
@Slf4j
@Component
public class UserSealApplyTaskRunner implements CommonTimerTaskRunner {

    @Resource
    private SealApplyService sealApplyService;

    @Override
    public void action() {
        try {
            //每天凌晨一点半同步云玺印章用户设备信息到redis
            sealApplyService.queryAllSealApply();
            log.info("同步云玺印章用户信息成功");
        }catch (Exception e){
            log.debug("同步云玺印章用户信息" + e.getMessage());
        }

    }
}
