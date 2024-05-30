/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.flw.core.listener;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Component;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.dev.api.DevSmsApi;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 招采供应商审核完成执行监听器 【供应商审核不通过】
 *
 *@Author hxw
 *@Date 2024/5/9 11:24
 */
@Slf4j
@Component
public class FlwSupplierFailExecutionListener implements FlwGlobalCustomEventListener {
    @Resource
    private DevSmsApi devSmsApi;

//    @Resource
//    private TaskService taskService;

    /** 招采平台用户-供应商注册审核通过-发送账号密码短信 **/
    private static final String SMS_YUNPIAN_TPL_CODE_ID = "5620650";

    /**
     * 发送通知
     * @param supplierId
     * @param reason
     */
    public void sendMsg(String supplierId,String reason) {
        try {
            /**
             * 审核不通过，发送短信通知供应商
             */
             //3. 发送消息通知供应商
             if(ObjectUtil.isNotEmpty(supplierId)){
                 //1.1 查询供应商联系信息
                 String sql = "SELECT ID,BUSINESSADMIN_NAME,BUSINESSADMIN_PHONE FROM BP_RELATION WHERE DELETE_FLAG = 'NOT_DELETE' AND SUPPLIER_ID = '"+ supplierId +"'";
                 Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
                 if(ObjectUtil.isEmpty(map)){
                     log.error("执行监听器{}出现异常：{}", FlwSupplierFailExecutionListener.class.getName(), "未查到供应商Id为:"+supplierId+"的联系信息");
                 }
                 //1.2 获取账号 - 手机号
                 Object businessadminPhone = map.get("BUSINESSADMIN_PHONE");
                 if(ObjectUtil.isNotEmpty(businessadminPhone)){
                     /**
                      * 【重要】 TODO 使用云片执行发送验证码，将验证码作为短信内容的参数变量放入
                      */
                     String tpl_Id = SMS_YUNPIAN_TPL_CODE_ID;
                     String tpl_Value = URLEncoder.encode("#code#") + "="  + URLEncoder.encode(reason);
                     devSmsApi.sendSmsYunPian("", businessadminPhone.toString(), tpl_Id, tpl_Value);
                 }
             }
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwSupplierFailExecutionListener.class.getName(), e.getMessage());
        }
    }

    @Override
    public void publishRejectEvent(HistoricProcessInstance historicProcessInstance,String taskId) {
//        String processInstanceId = historicProcessInstance.getId();
        String supplierId = historicProcessInstance.getBusinessKey();
        String reason = "审批不通过";
        sendMsg(supplierId,reason);
    }

    @Override
    public void publishCloseEvent(HistoricProcessInstance historicProcessInstance) {

    }

    @Override
    public void publishRevokeEvent(HistoricProcessInstance historicProcessInstance) {

    }

    @Override
    public void publishDeleteEvent(HistoricProcessInstance historicProcessInstance) {

    }
}
