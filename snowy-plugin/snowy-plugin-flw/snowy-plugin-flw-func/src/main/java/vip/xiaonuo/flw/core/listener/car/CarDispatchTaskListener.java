package vip.xiaonuo.flw.core.listener.car;

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
public class CarDispatchTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        String driverUserId = String.valueOf(delegateTask.getVariable("driverUserId"));
        String vehicleId = String.valueOf(delegateTask.getVariable("vehicleId"));

        CarUtil.updateApplyStatus("3", businessKey);// 更新用车申请单状态为已派车
        CarUtil.updateDriverStatus("1", driverUserId); //更新司机状态为出车
        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为出车
        log.info("更新用车申请单状态为已派车-----");
    }
}
