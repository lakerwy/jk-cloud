package vip.xiaonuo.im.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.im.modular.tencent.service.TencentImService;

import javax.annotation.PostConstruct;
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

    /**
     * 此处不能初始化在项目启动时，使用  @PostConstruct 来初始化数据
     * 因initUserSign 使用了openfeign  ,如果此时调用时，项目未启动，初始化会失败
     */
//    @PostConstruct
//    public void init() {
//        doTask();
//    }

//    @Scheduled(cron = "0 0 1 01 01 ?")
    @Scheduled(initialDelay = 18000, fixedDelay = Long.MAX_VALUE)
    protected void doTask(){
        tencentImService.initUserSign();
    }
}
