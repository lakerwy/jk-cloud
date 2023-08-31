package vip.xiaonuo.flw.core.listener.recruit;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.listener.FlwGlobalCustomEventListener;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * @author yong.ye
 * @date 2023/8/30 9:52
 */

@Slf4j
@Component
public class FlwGlobalResumeFailListener implements FlwGlobalCustomEventListener {

    @Override
    public void publishRejectEvent(HistoricProcessInstance historicProcessInstance) {
            try{
                String processInstanceId = historicProcessInstance.getId();
                TaskService taskService = SpringUtil.getBean(TaskService.class);
                Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
                String nodeName = task.getName();
                HistoryService historyService = SpringUtil.getBean(HistoryService.class);

                JSONObject variableJsonObject = JSONUtil.createObj();
                historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list()
                        .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                                historicVariableInstance.getValue()));

                //获取主键Id
                String id = String.valueOf(variableJsonObject.getStr("taskId"));

                //流程中的所有 拒绝 - 淘汰
                if(StrUtil.isNotEmpty(id)){
                    String STATUS_STAGE = "1";


                    //淘汰  复试阶段
                    String sql = "UPDATE BIZ_RECRUIT_RESUME SET STATUS='2',STATUS_STAGE= '" + STATUS_STAGE + "' WHERE ID = '" + id + "'";
                    CommonHandleUtil.handleSqlExecution(sql);
                }
                log.info("流程中的拒绝操作【淘汰 2  阶段 3】-更新简历状态-----");
            }catch (Exception e){
                log.info("流程中的拒绝操作【淘汰 2  阶段 3】-更新简历状态-----"+e.getMessage());
            }

    }

    @Override
    public void publishCloseEvent(HistoricProcessInstance historicProcessInstance) {

    }

    @Override
    public void publishRevokeEvent(HistoricProcessInstance historicProcessInstance) {

    }

    @Override
    public void publishDeleteEvent(HistoricProcessInstance historicProcessInstance) {

    }
}
