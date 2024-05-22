package vip.xiaonuo.dev.modular.job.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.timer.CommonTimerTaskRunner;

import javax.annotation.Resource;

/**
 * 同步群杰印章用户信息
 */
@Slf4j
@Component
public class UserSealMessageTaskRunner implements CommonTimerTaskRunner {


    @Override
    public void action() {
       /* try {
            //同步群杰印章用户信息
            sysSealApi.queryAllSealBase();
            log.info("同步群杰印章用户信息成功");
        }catch (Exception e){
            log.debug("同步群杰印章用户信息" + e.getMessage());
        }*/

    }
}
