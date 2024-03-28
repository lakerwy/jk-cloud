package vip.xiaonuo.flw.core.listener.sub;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**  申请补贴流程通过后监听，修改状态 */
@Slf4j
public class ApplyRegCertDataFlowListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateTask){
        try{
            String id = String.valueOf(delegateTask.getVariable("id"));
            String processInstanceId = delegateTask.getProcessInstanceId();
            /** 更新状态申请状态 */
            String sql1 = "UPDATE APPLY_REG_CERT_DATA_FLOW SET  STATUS = '1' WHERE PROCESS_INSTANCE_ID = '" + processInstanceId + "'";
            CommonHandleUtil.handleSqlExecution(sql1);

            if(StringUtils.isEmpty(id)){
                log.error("ApplyRegCertDataFlowListener:无法获取ID - processInstanceId："+processInstanceId);
            }
            if(StringUtils.isNotEmpty(id)){
                String sql2 = "UPDATE REG_CERT_STATUS_DATA SET  IS_PASS = '1' WHERE ID = '" + id + "'";
                CommonHandleUtil.handleSqlExecution(sql2);
            }
        }catch (Exception e){

        }
    }
}
