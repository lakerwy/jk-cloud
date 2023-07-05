package vip.xiaonuo.flw.core.listener;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * 工程设计校审完成事件
 */
@Slf4j
@Component
public class FlwAuditTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();

        //获取工程校审记录总数 ====审定次数
        String sql = "UPDATE BIZ_AUDIT_LISTS SET COUNTS =  (SELECT COUNT(1) FROM BIZ_AUDIT_LISTS_DETAILS WHERE AUDIT_ID = '" + businessKey + "'), STATUS = '1' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
        log.info("更新工程设计校审-----");
    }
}
