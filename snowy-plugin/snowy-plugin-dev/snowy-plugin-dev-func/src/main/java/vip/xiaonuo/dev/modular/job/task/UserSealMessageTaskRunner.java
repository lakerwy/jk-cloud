package vip.xiaonuo.dev.modular.job.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;
import vip.xiaonuo.dev.modular.sealMessage.service.SealMessageService;

import javax.annotation.Resource;

/**
 * 同步群杰印章用户信息
 */
@Slf4j
@Component
public class UserSealMessageTaskRunner implements CommonTimerTaskRunner {

    @Resource
    private SealMessageService sealMessageService;

    @Override
    public void action() {
        try {
            //每天凌晨一点同步群杰印章用户信息redis
            sealMessageService.queryAllSealBase();
            log.info("同步群杰印章用户信息成功");
        }catch (Exception e){
            log.debug("同步群杰印章用户信息" + e.getMessage());
        }

    }
}
