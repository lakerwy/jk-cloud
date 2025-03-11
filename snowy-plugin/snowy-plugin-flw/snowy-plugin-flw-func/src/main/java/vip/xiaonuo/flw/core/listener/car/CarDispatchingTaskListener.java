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
        String processInstanceId = delegateTask.getProcessInstanceId();
        log.info("CarDispatchingTaskListener监听器：processInstanceId:{}",processInstanceId);
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        log.info("CarDispatchingTaskListener监听器：runtimeService:{}",runtimeService == null);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        log.info("CarDispatchingTaskListener监听器：processInstance:{}",processInstance == null);
        String businessKey = null;
        if(ObjectUtil.isNotEmpty(processInstance)){
            businessKey = processInstance.getBusinessKey();
        }else {
            businessKey = String.valueOf(delegateTask.getVariable("id"));
        }
        log.info("CarDispatchingTaskListener监听器：businessKey:{}",businessKey);
//        String driverId = String.valueOf(delegateTask.getVariable("driverId"));
//        String vehicleId = String.valueOf(delegateTask.getVariable("vehicleId"));
        CarUtil.updateApplyStatus("2", businessKey);// 更新用车申请单状态为待派车
//        CarUtil.updateDriverStatus("2", driverId); //更新司机状态为出车
//        CarUtil.updateCarStatus("2" , vehicleId);// 更新车辆状态为出车
        log.info("更新用车申请单状态为已派车-----");
    }
}
