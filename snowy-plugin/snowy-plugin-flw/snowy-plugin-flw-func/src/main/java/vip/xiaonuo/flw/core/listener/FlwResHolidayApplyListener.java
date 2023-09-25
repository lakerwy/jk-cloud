package vip.xiaonuo.flw.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.sys.api.SysAttendanceApi;

import javax.annotation.Resource;

@Slf4j
@Component
public class FlwResHolidayApplyListener implements TaskListener {

    @Resource
    SysAttendanceApi attendanceApi;

    @Override
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        try{
            if(StringUtils.isNotEmpty(processInstanceId)){
                //审批通过
                String sql = "UPDATE HR_USER_APPLY_HOLIDAY SET STATUS='1' WHERE APPLY_TYPE = 5 AND PROCESS_INSTANCE_ID = '" + processInstanceId + "'" ;
                CommonHandleUtil.handleSqlExecution(sql);
            }
            //attendanceApi.resubmitAttendance(processInstanceId,"5");
            log.info("处理考勤审批！");
        }catch (Exception e){
            log.info("处理考勤审批失败-----"+e.getMessage());
        }

    }
}
