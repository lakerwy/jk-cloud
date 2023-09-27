package vip.xiaonuo.flw.core.listener.custom;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 15:21
 * 设置下一个节点的处理人
 */
@Slf4j
@Component
public class FlwHandleAssigneeListener2 implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String auditorId = String.valueOf(delegateTask.getVariable("auditorId2"));
        delegateTask.setAssignee(auditorId);
    }
}
