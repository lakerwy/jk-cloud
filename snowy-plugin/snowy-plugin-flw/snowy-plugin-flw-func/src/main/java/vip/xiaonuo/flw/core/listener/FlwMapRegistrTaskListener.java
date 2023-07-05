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
 * 出图记录
 */
@Slf4j
@Component
public class FlwMapRegistrTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String businessKey = processInstance.getBusinessKey();
        //更新出图记录
        String sql = "UPDATE BIZ_MAP_REGISTER SET STATUS = '1' WHERE  ID = '" + businessKey + "'";
        CommonHandleUtil.handleSqlExecution(sql);
        log.info("更新出图记录----");
    }
}
