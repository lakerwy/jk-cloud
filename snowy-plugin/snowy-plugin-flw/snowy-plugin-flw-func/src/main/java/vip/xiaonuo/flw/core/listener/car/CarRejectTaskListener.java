package vip.xiaonuo.flw.core.listener.car;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

/**
 * 用车申请事件
 */
@Slf4j
@Component
public class CarRejectTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();

        CarUtil.updateStatus(5,businessKey);
        log.info("更新车辆状态为已派车-----");
    }
}
