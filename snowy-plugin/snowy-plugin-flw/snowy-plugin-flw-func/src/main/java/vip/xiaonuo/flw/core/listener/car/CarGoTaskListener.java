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
public class CarGoTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        String driverUserId = String.valueOf(delegateTask.getVariable("driverUserId"));

        CarUtil.updateStatus(6,businessKey);  //更新车辆状态
        CarUtil.updateDriverStatus(2,driverUserId); //更新司机状态
        log.info("更新车辆状态为已发车-----");
    }
}
