package vip.xiaonuo.oa.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/14 10:25
 */
@Component
@Slf4j
public class RecordTask {

    @Scheduled(cron = "0 0 1 * * ?")
    public void preHandleAttnRecord(){
        /**
         * 1 、获取所有的用户
         * 2、 通过用户获取排班情况，在获取班次情况
         * 3、获取班次情况后，判断前一天是否是工作日需要打卡
         * 4、如果是工作日就获取打卡情况，处理异常打卡（主要是未打卡的，要生成一条未打卡记录）
         * 4、处理今天的数据
         *  如果是工作日，就生成预打卡记录
         */
    }
}
