package vip.xiaonuo.flw.core.listener.custom;

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
public class FlwResHolidayApplyListener implements ExecutionListener {

    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void notify(DelegateExecution execution)throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        try{
            //attendanceApi.resubmitAttendance(processInstanceId,"5");
            log.info("处理考勤审批！");
        }catch (Exception e){
            log.info("处理考勤审批失败-----"+e.getMessage());
        }

    }
}
