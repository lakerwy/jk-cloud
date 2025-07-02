package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.util.ObjectUtil;
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
            Object obj_areaLeader = execution.getVariable("areaLeader");
            Object obj_areaLeaderId = execution.getVariable("areaLeaderId");
            Object obj_dept = execution.getVariable("dept");
            Object obj_deptId = execution.getVariable("_deptId");

            String areaLeader = ObjectUtil.isNotEmpty(obj_areaLeader) ? String.valueOf(obj_areaLeader) : null ;
            String areaLeaderId = ObjectUtil.isNotEmpty(obj_areaLeaderId) ? String.valueOf(obj_areaLeaderId) : null ;
            //责任部门
            String dept = ObjectUtil.isNotEmpty(obj_dept) ? String.valueOf(obj_dept) : null ;
            String deptId = ObjectUtil.isNotEmpty(obj_deptId) ? String.valueOf(obj_deptId) : null ;

            bizExtApi.savePorProject(businessKey,areaLeader,areaLeaderId,dept,deptId);

            log.info("立项审批通过-系统自动同步-完成");
        }catch (Exception e){
            log.info("立项审批通过-系统自动同步-异常-----" + e.getMessage());
        }
    }
}
