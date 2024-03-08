package vip.xiaonuo.flw.core.listener.dingDingSchedule;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.dev.api.DevDfcApi;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import java.util.Map;

@Slf4j
@Component
public class VisitScheduleListener implements ExecutionListener {

    DevDfcApi devDfcApi;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        try{
            if(devDfcApi == null){
                devDfcApi = SpringUtil.getBean(DevDfcApi.class);
            }
            String sql = "select T.GO_TIME AS GO_TIME,T.BACK_TIME as BACK_TIME,T.CREATE_USER as CREATE_USER,T.PERSON as PERSON,T.PURPOSE as PURPOSE,T.CREATE_USER as CREATE_USER,T.PROCESS_INSTANCE_ID as PROCESS_INSTANCE_ID" +
                    " from BIZ_VISIT T where PROCESS_INSTANCE_ID = '"+processInstanceId+"'";
            log.info("离职人员查询----"+sql);
            Map<String,Object> map= CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
            if(ObjectUtil.isEmpty(map)){
                log.error("未查询到外访数据！流程实例id:"+processInstanceId);
                return;
            }
            //authThirdApi.getUnionIdByUserId();
            devDfcApi.addDingDingScheduleHandler(map);
            log.info("处理外访审批，新建日程----流程实例id:----"+processInstanceId);
        }catch (Exception e){
            log.info("处理外访审批，新建日程失败！-----e:"+e.getMessage());
        }
    }
}
