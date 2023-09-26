package vip.xiaonuo.flw.core.listener.custom;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.sys.api.SysAttendanceApi;

import javax.annotation.Resource;

@Slf4j
@Component
public class FlwResAttendanceApplyListener implements ExecutionListener {

    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void notify(DelegateExecution execution)throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        try{
//            if(StringUtils.isNotEmpty(processInstanceId)){
//                //审批通过
//                String sql = "UPDATE HR_USER_APPLY_ATTENDANCE SET STATUS='1' WHERE APPLY_TYPE = 5 AND PROCESS_INSTANCE_ID = '" + processInstanceId + "'" ;
//                CommonHandleUtil.handleSqlExecution(sql);
//            }
            String applyType = String.valueOf(execution.getVariable("applyType"));
            if(StrUtil.isNotEmpty(applyType)){
                if("5".equals(applyType)){
                    attendanceApi.resubmitAttendance(processInstanceId,"5");
                    log.info("处理考勤审批----流程实例id----"+processInstanceId);
                }
            }

        }catch (Exception e){
            log.info("处理考勤审批失败-----"+e.getMessage());
        }

    }
}
