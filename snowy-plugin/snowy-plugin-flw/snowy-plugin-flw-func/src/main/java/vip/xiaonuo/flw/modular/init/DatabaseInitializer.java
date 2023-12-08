package vip.xiaonuo.flw.modular.init;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.listener.FlwGlobalCustomEventListener;
import vip.xiaonuo.flw.modular.model.entity.FlwListener;
import vip.xiaonuo.flw.modular.model.param.FlwListenerAddParam;
import vip.xiaonuo.flw.modular.model.service.FlwListenerService;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/8 10:38
 */
@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final FlwListenerService flwListenerService;

    public DatabaseInitializer(FlwListenerService flwListenerService) {
        this.flwListenerService = flwListenerService;
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, ExecutionListener> executionListenerMap = SpringUtil.getBeansOfType(ExecutionListener.class);
        if(ObjectUtil.isNotEmpty(executionListenerMap)){
//            Collection<ExecutionListener> values = executionListenerMap.values();
//            List<String> collect = values.stream().map(executionListener -> executionListener.getClass().getName()).collect(Collectors.toList());
            executionListenerMap.forEach((key,value)->{
                String name = value.getClass().getName();
                FlwListener one = flwListenerService.getOne(new LambdaQueryWrapper<FlwListener>().eq(FlwListener::getListenerClass, name));
                if(ObjectUtil.isNotNull(one)){
                    return;
                }
                FlwListenerAddParam flwListenerAddParam = new FlwListenerAddParam();
                flwListenerAddParam.setListenerClassName(key);
                flwListenerAddParam.setListenerClass(name);
                flwListenerAddParam.setListenerType("executionListener");
                flwListenerService.add(flwListenerAddParam);
            });
        }

        Map<String, FlwGlobalCustomEventListener> customExecutionListenerMap = SpringUtil.getBeansOfType(FlwGlobalCustomEventListener.class);
        if(ObjectUtil.isNotEmpty(customExecutionListenerMap)){
            customExecutionListenerMap.forEach((key,value)->{
                String name = value.getClass().getName();
                FlwListener one = flwListenerService.getOne(new LambdaQueryWrapper<FlwListener>().eq(FlwListener::getListenerClass, name));
                if(ObjectUtil.isNotNull(one)){
                    return;
                }
                FlwListenerAddParam flwListenerAddParam = new FlwListenerAddParam();
                flwListenerAddParam.setListenerClassName(key);
                flwListenerAddParam.setListenerClass(name);
                flwListenerAddParam.setListenerType("customEventListener");
                flwListenerService.add(flwListenerAddParam);
            });
        }

        Map<String, TaskListener> taskListenerMap = SpringUtil.getBeansOfType(TaskListener.class);
        if(ObjectUtil.isNotEmpty(taskListenerMap)){
            taskListenerMap.forEach((key,value)->{
                String name = value.getClass().getName();
                FlwListener one = flwListenerService.getOne(new LambdaQueryWrapper<FlwListener>().eq(FlwListener::getListenerClass, name));
                if(ObjectUtil.isNotNull(one)){
                    return;
                }
                FlwListenerAddParam flwListenerAddParam = new FlwListenerAddParam();
                flwListenerAddParam.setListenerClassName(key);
                flwListenerAddParam.setListenerClass(name);
                flwListenerAddParam.setListenerType("taskListener");
                flwListenerService.add(flwListenerAddParam);
            });
        }

    }
}
