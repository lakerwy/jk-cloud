package vip.xiaonuo.flw.core.listener.sub;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.sys.api.SysExtApi;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 试用期考核责任流程|试用期转正考核流程 监听器
 */
@Slf4j
@Component
public class FlwRegSubsidyExecutionListener implements ExecutionListener {

    @Resource
    private SysExtApi sysExtApi;

    @Override
    public void notify(DelegateExecution execution){
        try{
            //获取流程中参数
            String personnelId = String.valueOf(execution.getVariable("personnelId"));
            String idCardNumber = String.valueOf(execution.getVariable("idCardNumber"));
            String name = String.valueOf(execution.getVariable("name"));
            String resultJson = String.valueOf(execution.getVariable("resultJson"));
            //当前流程实例Id
            String processInstanceId = execution.getProcessInstanceId();
            RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            String businessKey = processInstance.getBusinessKey();
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("personnelId",personnelId);
            paramMap.put("idCardNumber",idCardNumber);
            paramMap.put("name",name);
            paramMap.put("resultJson",resultJson);
            paramMap.put("regSubsidyId",businessKey);
            Boolean isSucc = sysExtApi.saveRegSubsidyResult(paramMap);
            log.info("注册补贴审批完成-----数据处理" + (isSucc ? "成功":"失败"));
        }catch (Exception e) {
            log.error("注册补贴审批异常-----" + e.getMessage());
        }
    }
}
