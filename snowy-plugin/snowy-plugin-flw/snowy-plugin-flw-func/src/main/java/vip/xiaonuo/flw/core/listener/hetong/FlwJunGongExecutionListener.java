package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

import java.util.HashMap;
import java.util.Map;

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
            if(ObjectUtil.isNotEmpty(heTongId) && ObjectUtil.isNotEmpty(compleData)){
                String sql = "select ID,COMPLE_DATA from BIZ_CRM_HETONG where ID = '"+ heTongId +"'";
                Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
                log.info("执行查询："+sql);
                if(ObjectUtil.isNotEmpty(map)){
                    String newCompleData = ObjectUtil.isNotEmpty(map.get("COMPLE_DATA")) ? map.get("COMPLE_DATA") + ";" + compleData : compleData;
                    Map<String,Object> params = new HashMap<>();
                    sql = " UPDATE BIZ_CRM_HETONG SET `COMPLE_DATA` = :COMPLE_DATA where ID = :ID";
                    params.put("COMPLE_DATA",newCompleData);
                    params.put("ID",heTongId);
                    log.info("执行更新："+sql);
                    CommonHandleUtil.handleSqlExecution(sql,params);
                }
            }
            log.info("竣工流程-----反更新成功");
        }catch (Exception e){
            log.info("竣工流程-----" + e.getMessage());
        }
    }
}
