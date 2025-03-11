package vip.xiaonuo.flw.core.listener.car;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * 用车申请事件-派车节点
 */
@Slf4j
@Component
public class CarDispatchingTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
//        String processInstanceId = delegateTask.getProcessInstanceId();
//        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey  = String.valueOf(delegateTask.getVariable("id"));
        log.info("CarDispatchingTaskListener监听器：businessKey:{}",businessKey);
        CarUtil.updateApplyStatus("2", businessKey);// 更新用车申请单状态为待派车
        log.info("更新用车申请单状态为待派车-----");
    }
}
