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
 * @author yong.ye
 * @date 2023/7/14 16:08
 */

@Slf4j
@Component
public class FlwHetongTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        try {
            String processInstanceId = delegateTask.getProcessInstanceId();
            RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            //获取合同记录--更新流程状态
            String sql = "UPDATE BIZ_CRM_HETONG SET `STATUS` = '1'  WHERE id = '"+businessKey+"'";
            CommonHandleUtil.handleSqlExecution(sql);
            log.info("更新合同-----");
        }catch (Exception e) {
            log.info("更新合同-----" + e.getMessage());
        }
    }
}
