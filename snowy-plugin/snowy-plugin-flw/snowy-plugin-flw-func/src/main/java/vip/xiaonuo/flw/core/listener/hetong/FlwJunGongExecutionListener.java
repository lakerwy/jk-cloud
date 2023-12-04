package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * 竣工 流程 - 反写数据到合同
 */
@Slf4j
@Component
public class FlwJunGongExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try{
            //所关联的合同主键Id
            String heTongId = String.valueOf(execution.getVariable("heTongId"));
            //竣工材料附件
            String compleData = String.valueOf(execution.getVariable("compleData"));
            if(ObjectUtil.isNotEmpty(heTongId)){
                String sql = "UPDATE BIZ_CRM_HETONG SET `COMPLE_DATA` = '" + compleData +"' where ID = '"+ heTongId +"'";
                CommonHandleUtil.handleSqlExecution(sql);
            }
        }catch (Exception e){
            log.info("分公司员工转正|离职更新异常-----" + e.getMessage());
        }
    }
}
