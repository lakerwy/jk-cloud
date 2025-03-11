package vip.xiaonuo.flw.core.listener.car;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 用车申请事件-完成节点
 */
@Slf4j
@Component
public class CarFinishTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
//        String processInstanceId = delegateTask.getProcessInstanceId();
//        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
//        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        String businessKey = processInstance.getBusinessKey();
        String businessKey  = String.valueOf(delegateTask.getVariable("id"));
        String driverId = String.valueOf(delegateTask.getVariable("driverUserId"));
        String vehicleId = String.valueOf(delegateTask.getVariable("vehicleId"));
        String endMileage = String.valueOf(delegateTask.getVariable("endMileage"));
        CarUtil.updateApplyStatus("4", businessKey); //更新用车申请单状态为已完成
        CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
        try {
            CarUtil.updateCarTotalMileage(new BigDecimal(endMileage), vehicleId);// 更新车辆总行驶里程
        }catch (NumberFormatException e){

        }
        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用

        log.info("更新用车申请单状态为已完成-----");
    }
}
