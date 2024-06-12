package vip.xiaonuo.flw.core.listener.sub;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;


/**
 * 试用期考核责任流程|试用期转正考核流程 监听器
 */
@Slf4j
@Component
public class FlwTryExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution){
        try{
            //试用期考核责任流程|试用期转正考核流程 流程类别
            String flwType = String.valueOf(execution.getVariable("flwType"));
            //试用期人员 员工档案表主键Id
            String personnelId = String.valueOf(execution.getVariable("personnelId"));
            //当前流程实例Id
            String processInstanceId = execution.getProcessInstanceId();
            //更新总部花名册信息
            if(ObjectUtil.isNotEmpty(flwType) && ObjectUtil.isNotEmpty(personnelId)){
                if("tryDuty".equals(flwType)){
                    //试用期考核责任流程
                    String sql = "UPDATE PERSONNEL SET DUTY_PROCESS_INSTANCE_ID = '"+ processInstanceId +"' where ID = '"+ personnelId +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                }else if ("tryTurn".equals(flwType)){
                    //试用期转正考核流程
                    String sql = "UPDATE PERSONNEL SET EMPLOY_NATURE = '正式',TURN_PROCESS_INSTANCE_ID = '"+ processInstanceId +"' where ID = '"+ personnelId +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                }
            }
            log.info("试用期考核责任|转正完成-----");
        }catch (Exception e) {
            log.error("试用期考核责任|转正更新异常-----" + e.getMessage());
        }
    }
}
