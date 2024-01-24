package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 用印审批 - 根据类型 关联 合同|立项
 */
@Component
@Slf4j
public class FlwSealExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try {
            //用印审批 关联的合同|立项主键Id
            String thirdObjId = String.valueOf(execution.getVariable("thirdObjId"));
            //用印类型 立项|合同用印
            String thirdObjType = String.valueOf(execution.getVariable("thirdObjType"));
            //当前流程实例Id
            String processInstanceId = execution.getProcessInstanceId();
            //更新逻辑
            if(ObjectUtil.isNotEmpty(thirdObjId) && ObjectUtil.isNotEmpty(thirdObjType)){
                if("1".equals(thirdObjType)){  //1:合同 2：立项
                    String sql = "select ID,SEAL_PROCESS_INSTANCE_ID from BIZ_CRM_HETONG where ID = '"+ thirdObjId +"'";
                    Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
                    if(ObjectUtil.isNotEmpty(map)){
                        String newSealProcessInstanceId = ObjectUtil.isNotEmpty(map.get("SEAL_PROCESS_INSTANCE_ID")) ? map.get("SEAL_PROCESS_INSTANCE_ID") + "," + processInstanceId : processInstanceId;
                        Map<String,Object> params = new HashMap<>();
                        sql = " UPDATE BIZ_CRM_HETONG SET SEAL_PROCESS_INSTANCE_ID = :SEAL_PROCESS_INSTANCE_ID where ID = :ID";
                        params.put("SEAL_PROCESS_INSTANCE_ID",newSealProcessInstanceId);
                        params.put("ID",thirdObjId);
                        log.info("【合同用印】执行更新："+sql);
                        CommonHandleUtil.handleSqlExecution(sql,params);
                    }
                }else if("2".equals(thirdObjType)){  //1:合同 2：立项
                    String sql = "select ID,SEAL_PROCESS_INSTANCE_ID from BIZ_PROJECT where ID = '"+ thirdObjId +"'";
                    Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
                    if(ObjectUtil.isNotEmpty(map)){
                        String newSealProcessInstanceId = ObjectUtil.isNotEmpty(map.get("SEAL_PROCESS_INSTANCE_ID")) ? map.get("SEAL_PROCESS_INSTANCE_ID") + "," + processInstanceId : processInstanceId;
                        Map<String,Object> params = new HashMap<>();
                        sql = " UPDATE BIZ_PROJECT SET SEAL_PROCESS_INSTANCE_ID = :SEAL_PROCESS_INSTANCE_ID where ID = :ID";
                        params.put("SEAL_PROCESS_INSTANCE_ID",newSealProcessInstanceId);
                        params.put("ID",thirdObjId);
                        log.info("【立项用印】执行更新："+sql);
                        CommonHandleUtil.handleSqlExecution(sql,params);
                    }
                }
            }
        }catch (Exception e){
            log.debug(e.getMessage());
        }
    }
}
