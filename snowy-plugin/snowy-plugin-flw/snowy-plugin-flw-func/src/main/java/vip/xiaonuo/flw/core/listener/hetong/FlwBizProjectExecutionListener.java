package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.sys.api.SysExtApi;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 立项完成 - 数据同步到 项目管理
 */
@Slf4j
@Component
public class FlwBizProjectExecutionListener implements ExecutionListener {

    @Resource
    private SysExtApi sysExtApi;

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
            if(ObjectUtil.isNotEmpty(areaLeader) && ObjectUtil.isEmpty(areaLeaderId)){
                areaLeaderId = sysExtApi.getUserIdByName(areaLeader);
            }
            if(ObjectUtil.isNotEmpty(dept) && ObjectUtil.isEmpty(deptId)){
                deptId = sysExtApi.getOrgIdByName(dept);
            }

            String sql = " insert into BIZ_POR_PROJECT(ID,PROJECT_ID,DUTY_DEPT_ID,DUTY_DEPT_NAME,DUTY_ID,DUTY_NAME,PROJECT_STATUS,CREATE_TIME,CREATE_USER,MEMO) VALUES(${ID},${PROJECT_ID},${DUTY_DEPT_ID},${DUTY_DEPT_NAME},${DUTY_ID},${DUTY_NAME},${PROJECT_STATUS},${CREATE_TIME},${CREATE_USER},${MEMO}) ";
            Map<String,Object> params = new HashMap<>();
            params.put("ID", IdWorker.getIdStr());
            params.put("PROJECT_ID", businessKey);
            params.put("DUTY_DEPT_ID", deptId);
            params.put("DUTY_DEPT_NAME", dept);
            params.put("DUTY_ID", areaLeaderId);
            params.put("DUTY_NAME", areaLeader);
            params.put("PROJECT_STATUS", "未开始");
            params.put("CREATE_TIME", DateTime.now());
            params.put("CREATE_USER", "-1");
            params.put("MEMO", "立项审批通过-系统自动同步");
            log.info("立项审批通过-系统自动同步："+sql);
            CommonHandleUtil.handleSqlExecution(sql,params);
            log.info("立项审批通过-系统自动同步-完成");
        }catch (Exception e){
            log.info("立项审批通过-系统自动同步-异常-----" + e.getMessage());
        }
    }
}
