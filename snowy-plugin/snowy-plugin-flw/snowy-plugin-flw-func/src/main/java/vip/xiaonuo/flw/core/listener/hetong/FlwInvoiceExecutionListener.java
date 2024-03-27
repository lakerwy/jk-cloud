package vip.xiaonuo.flw.core.listener.hetong;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

/**
 * 财务系统-发票-异动（作废|红冲）
 */
@Component
@Slf4j
public class FlwInvoiceExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        try {
            //异动类型
            String moveType = String.valueOf(execution.getVariable("moveType"));
            //发票-发票号码-主键Id
            String invoiceSaleBillId = String.valueOf(execution.getVariable("invoiceSaleBillId"));
            //当前流程实例Id
            String processInstanceId = execution.getProcessInstanceId();
            //更新逻辑
            if(ObjectUtil.isNotEmpty(moveType) && ObjectUtil.isNotEmpty(invoiceSaleBillId)){
                if("作废".equals(moveType)){
                    String sql = " UPDATE BIZ_INVOICE_SALE_BILL set FP_STATUS='作废',CANCEL_PROCESS_INSTANCE_ID='"+processInstanceId+"' where ID = '"+ invoiceSaleBillId +"'";
                    log.info("【销售发票异动-作废】执行更新："+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
                }else if("红冲".equals(moveType)){
                    String sql = " UPDATE BIZ_INVOICE_SALE_BILL set FP_STATUS='红冲',RED_PROCESS_INSTANCE_ID='"+processInstanceId+"' where ID = '"+ invoiceSaleBillId +"'";
                    log.info("【销售发票异动-红冲】执行更新："+sql);
                    CommonHandleUtil.handleSqlExecution(sql);
                }
            }
        }catch (Exception e){
            log.debug("【销售发票异动】"+ e.getMessage());
        }
    }
}
