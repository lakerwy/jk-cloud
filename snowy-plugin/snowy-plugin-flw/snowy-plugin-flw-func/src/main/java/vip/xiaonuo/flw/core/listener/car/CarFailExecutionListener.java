package vip.xiaonuo.flw.core.listener.car;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.flw.core.listener.FlwGlobalCustomEventListener;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;

/**
 * 用车申请-执行监听器
 */
@Slf4j
@Component
public class CarFailExecutionListener implements FlwGlobalCustomEventListener {

    @Resource
    private HistoryService historyService;

    @Override
    public void publishRejectEvent(HistoricProcessInstance historicProcessInstance, String taskId) {
        try {
            log.info(">>> 自定义事件执行监听器：拒绝事件触发，流程id为：{}", historicProcessInstance.getId());
            //获取流程表单数据
            Object dataJson = SpringUtil.getBean(TaskService.class).getVariable(taskId, "initiatorDataJson");
            JSONObject json = JSONUtil.parseObj(dataJson);
//            String processInstanceId = historicProcessInstance.getId();
            String id = String.valueOf(json.get("id"));
            String vehicleId = String.valueOf(json.get("vehicleId"));
            String driverId = String.valueOf(json.get("driverId"));
            CarUtil.updateApplyStatus("6", id); //更新用车申请单状态为已拒绝
            CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
            CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用
            log.info("更新用车申请单状态为已取消|已拒绝-----");
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", CarFailExecutionListener.class.getName(), e.getMessage());
        }
    }

    @Override
    public void publishCloseEvent(HistoricProcessInstance historicProcessInstance) {
        log.info(">>> 自定义事件执行监听器：终止事件触发，流程id为：{}", historicProcessInstance.getId());
        //获取流程表单数据
        JSONObject variableJsonObject = JSONUtil.createObj();
        historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list()
                .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                        historicVariableInstance.getValue()));
        String id = String.valueOf(variableJsonObject.get("id"));
        String vehicleId = String.valueOf(variableJsonObject.get("vehicleId"));
        String driverId = String.valueOf(variableJsonObject.get("driverId"));
        CarUtil.updateApplyStatus("5", id); //更新用车申请单状态为已取消
        CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用
        log.info("更新用车申请单状态为已取消|已拒绝-----");
    }

    @Override
    public void publishRevokeEvent(HistoricProcessInstance historicProcessInstance) {
        log.info(">>> 自定义事件执行监听器：撤回事件触发，流程id为：{}", historicProcessInstance.getId());
        //获取流程表单数据
        JSONObject variableJsonObject = JSONUtil.createObj();
        historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list()
                .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                        historicVariableInstance.getValue()));
        String id = String.valueOf(variableJsonObject.get("id"));
        String vehicleId = String.valueOf(variableJsonObject.get("vehicleId"));
        String driverId = String.valueOf(variableJsonObject.get("driverId"));
        CarUtil.updateApplyStatus("5", id); //更新用车申请单状态为已取消
        CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用
        log.info("更新用车申请单状态为已取消|已拒绝-----");
    }

    @Override
    public void publishDeleteEvent(HistoricProcessInstance historicProcessInstance) {
        log.info(">>> 自定义事件执行监听器：删除事件触发，流程id为：{}", historicProcessInstance.getId());
        //获取流程表单数据
        JSONObject variableJsonObject = JSONUtil.createObj();
        historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).list()
                .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                        historicVariableInstance.getValue()));
        String id = String.valueOf(variableJsonObject.get("id"));
        String vehicleId = String.valueOf(variableJsonObject.get("vehicleId"));
        String driverId = String.valueOf(variableJsonObject.get("driverId"));
        CarUtil.updateApplyStatus("5", id); //更新用车申请单状态为已取消
        CarUtil.updateDriverStatus("1", driverId); //更新司机状态为空闲
        CarUtil.updateCarStatus("1" , vehicleId);// 更新车辆状态为可用
        log.info("更新用车申请单状态为已取消|已拒绝-----");
    }
}
