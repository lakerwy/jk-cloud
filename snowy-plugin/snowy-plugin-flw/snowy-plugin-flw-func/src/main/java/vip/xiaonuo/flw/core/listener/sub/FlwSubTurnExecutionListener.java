package vip.xiaonuo.flw.core.listener.sub;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;

@Slf4j
@Component
public class FlwSubTurnExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution){
        try{
            //转正|离职
            String moveType = String.valueOf(execution.getVariable("moveType"));
            //转正|离职 员工档案表主键Id
            String fileId = String.valueOf(execution.getVariable("subPersonnelId"));
            //离职日期
            String resignationTime = String.valueOf(execution.getVariable("resignationTime"));

            if(ObjectUtil.isNotEmpty(moveType) && ObjectUtil.isNotEmpty(fileId)){
                if("0".equals(moveType)){
                    //转正
                    String sql = "UPDATE HR_SUB_PERSONNEL SET `EMPLOY_NATURE` = '正式' where ID = '"+ fileId +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                }else if ("1".equals(moveType)){
                    //离职
                    String sql = "UPDATE HR_SUB_PERSONNEL SET `HR_STATUS` = '1',`RESIGNATION_TIME` = '"+ resignationTime +"', `WATCH_INSTANCE_ID` = '"+execution.getProcessInstanceId()+"' where ID = '"+ fileId +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                }else{
                }
            }
        }catch (Exception e) {
            log.info("分公司员工转正|离职更新异常-----" + e.getMessage());
        }
    }
}
