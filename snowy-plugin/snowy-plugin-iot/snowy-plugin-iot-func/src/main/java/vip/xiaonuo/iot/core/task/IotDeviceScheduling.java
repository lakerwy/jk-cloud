package vip.xiaonuo.iot.core.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import vip.xiaonuo.iot.core.config.MqttProperties;
import vip.xiaonuo.iot.modular.device.service.IotDeviceService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/6 14:20
 * 定时任务
 * 编程方式动态设置 @Scheduled 注解的 cron 表达式
 * 关灯
 */
@Configuration
@EnableScheduling
public class IotDeviceScheduling implements SchedulingConfigurer {

    @Resource
    private IotDeviceService iotDeviceService;

    @Resource
    private MqttProperties mqttProperties;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(this::doOffTask, mqttProperties.getOffCronExpression());
        taskRegistrar.addCronTask(this::doOnTask, mqttProperties.getOnCronExpression());
    }

    /**
     * 关灯任务
     * 工作日18:00关灯
     */
    protected void doOffTask(){
        iotDeviceService.scheduling(0);
    }

    /**
     * 开灯任务
     * 工作日每天8点10分开灯
     */
    protected void doOnTask(){
        iotDeviceService.scheduling(1);
    }
}
