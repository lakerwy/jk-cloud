package vip.xiaonuo.flw.core.listener.car;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 用车申请事件-完成节点
 */
@Slf4j
@Component
public class CarFinishExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
        String id = String.valueOf(execution.getVariable("id"));
        String driverId = String.valueOf(execution.getVariable("driverUserId"));
        String driverIdAgain = String.valueOf(execution.getVariable("driverIdAgain"));
        String vehicleId = String.valueOf(execution.getVariable("vehicleId"));
        String endMileage = String.valueOf(execution.getVariable("endMileage"));

        CarUtil.updateApplyStatus("4", id); //更新用车申请单状态为已完成
        CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
        CarUtil.updateDriverStatus("1", driverIdAgain); //更新司机状态为空闲

        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用
        try {
            CarUtil.updateCarTotalMileage(new BigDecimal(endMileage), vehicleId);// 更新车辆总行驶里程
        }catch (NumberFormatException e){

        }
    }
}
