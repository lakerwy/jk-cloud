package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.biz.api.BizExtApi;

import javax.annotation.Resource;

/**
 * 立项完成 - 数据同步到 项目管理
 */
@Slf4j
@Component
public class FlwBizProjectExecutionListener implements ExecutionListener {

    @Resource
    private BizExtApi bizExtApi;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try{
            String processInstanceId = execution.getProcessInstanceId();
            RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();   //主键(立项)

            //我方负责人
            String areaLeader = String.valueOf(execution.getVariable("areaLeader"));
            String areaLeaderId = String.valueOf(execution.getVariable("areaLeaderId"));
            //责任部门
            String dept = String.valueOf(execution.getVariable("dept"));
            String deptId = String.valueOf(execution.getVariable("_deptId"));

            bizExtApi.savePorProject(businessKey,areaLeader,areaLeaderId,dept,deptId);

            log.info("立项审批通过-系统自动同步-完成");
        }catch (Exception e){
            log.info("立项审批通过-系统自动同步-异常-----" + e.getMessage());
        }
    }
}
