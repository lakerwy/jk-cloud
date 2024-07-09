package vip.xiaonuo.im.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.im.modular.tencent.service.TencentImService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/5 11:05
 * 初始化用户的Im账号
 */
@Component
@Slf4j
public class InitImAccountTask {

    @Resource
    private TencentImService tencentImService;


//    @PostConstruct
//    public void init() {
//       scheduleTask();
//    }

    @Scheduled(cron = "0 0 1 01 01 ?")
    protected void doTask(){
        tencentImService.initUserSign();
    }
}
