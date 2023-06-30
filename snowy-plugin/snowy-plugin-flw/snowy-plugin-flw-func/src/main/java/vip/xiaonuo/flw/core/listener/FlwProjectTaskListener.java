package vip.xiaonuo.flw.core.listener;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

@Slf4j
@Component
public class FlwProjectTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        //根据businessKey 从project中获取APPROVAL_NO 单号 更新立项登记的立项情况 BIZ_PROJECT_REGISTER 的 is_Approval
        //update BIZ_PROJECT_REGISTER set is_Approval = "已立项" where id = (select APPROVAL_NO  from BIZ_PROJECT where id = businessKey)
        String sql = "update BIZ_PROJECT_REGISTER set is_Approval = \"已立项\" where id = (select APPROVAL_NO  from BIZ_PROJECT where ID = " + businessKey + ")";
        CommonHandleUtil.handleSqlExecution(sql);
        log.info("更新立项申请表字段is_Approval-----");
    }
}
