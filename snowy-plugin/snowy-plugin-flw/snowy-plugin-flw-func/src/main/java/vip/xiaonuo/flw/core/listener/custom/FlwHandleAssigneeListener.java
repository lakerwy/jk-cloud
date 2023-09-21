package vip.xiaonuo.flw.core.listener.custom;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/20 15:21
 * 设置下一个节点的处理人
 */
@Slf4j
@Component
public class FlwHandleAssigneeListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String auditorId = String.valueOf(delegateTask.getVariable("auditorId"));
        delegateTask.setAssignee(auditorId);
    }
}
