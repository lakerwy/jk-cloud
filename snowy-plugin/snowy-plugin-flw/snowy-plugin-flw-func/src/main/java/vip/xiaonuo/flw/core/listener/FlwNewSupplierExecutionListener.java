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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonCryptogramUtil;
import vip.xiaonuo.dev.api.DevSmsApi;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.ten.api.TenApi;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * 招采供应商审核完成执行监听器 【供应商审核通过】
 *
 *@Author hxw
 *@Date 2024/5/9 11:24
 */
@Slf4j
@Component
public class FlwNewSupplierExecutionListener implements ExecutionListener {

    @Resource
    private DevSmsApi devSmsApi;

    @Resource
    private TenApi tenApi;

    /** 招采平台用户-默认密码 **/
    private static final String BP_USER_DEFAULT_PASSWORD = "123456";

    /** 招采平台用户-供应商注册审核通过-发送账号密码短信 **/
    private static final String SMS_YUNPIAN_TPL_CODE_ID = "5620650";

    @Override
    public void notify(DelegateExecution execution) {

        try {
            log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            /**
             * 内部推荐|供应商注册，均需新增注册用户
             *
             * if("1".equals(String.valueOf(execution.getVariable("source"))))
             */
             //1. 获取流程实例关联的业务信息
             String supplierId = String.valueOf(execution.getVariable("id"));
             if(ObjectUtil.isNotEmpty(supplierId)){
                 //1.1 查询供应商联系信息
                 String sql = "SELECT ID,BUSINESSADMIN_NAME,BUSINESSADMIN_PHONE FROM BP_RELATION WHERE DELETE_FLAG = 'NOT_DELETE' AND SUPPLIER_ID = '"+ supplierId +"'";
                 Map<String, Object> map = CommonHandleUtil.handleSqlQuery(sql, Boolean.FALSE);
                 if(ObjectUtil.isEmpty(map)){
                     log.error("执行监听器{}出现异常：{}", FlwNewSupplierExecutionListener.class.getName(), "未查到供应商Id为:"+supplierId+"的联系信息");
                 }
                 //1.2 获取账号 - 手机号
                 Object businessadminPhone = map.get("BUSINESSADMIN_PHONE");
                 if(ObjectUtil.isNotEmpty(businessadminPhone)){
                     //查询招采平台 - 用户表 是否存在账号  可能是自行申报时先注册用户 再进行申报
                     String sqlUser = "SELECT ID,ACCOUNT,PASSWORD FROM BP_USER WHERE DELETE_FLAG = 'NOT_DELETE' AND ACCOUNT = '"+ businessadminPhone +"' AND SUPPLIER_ID = '"+ supplierId +"'";
                     Map<String, Object> mapObject = CommonHandleUtil.handleSqlQuery(sqlUser, Boolean.FALSE);
                     //用户不存在则,新增招采平台用户
                     if(ObjectUtil.isEmpty(mapObject)){
                         /**
                          * 【重要】 生成密码（自动生成随机密码|默认密码）
                          */
                         String passWord = BP_USER_DEFAULT_PASSWORD;  //RandomUtil.randomNumbers(6);

                         //拼接sql
                         StringBuilder sqlkey = new StringBuilder();
                         StringBuilder sqlValue = new StringBuilder();
                         sqlkey.append("`ID` ,");
                         sqlValue.append("'").append(IdWorker.getIdStr()).append("' ,"); //手动生成新的主键Id
                         sqlkey.append("`NAME` ,");
                         sqlValue.append("'").append(map.get("BUSINESSADMIN_NAME")).append("' ,");
                         sqlkey.append("`PASSWORD` ,");
                         sqlValue.append("'").append(CommonCryptogramUtil.doHashValue(passWord)).append("' ,"); //生成加密密码
                         sqlkey.append("`ACCOUNT` ,");
                         sqlValue.append("'").append(businessadminPhone).append("' ,"); //用户账号
                         sqlkey.append("`PHONE` ,");
                         sqlValue.append("'").append(businessadminPhone).append("' ,"); //用户手机号
                         sqlkey.append("`USER_STATUS` ,");
                         sqlValue.append(" 'ENABLE' ,");
                         sqlkey.append("`SUPPLIER_ID` ,");
                         sqlValue.append("'").append(supplierId).append("' ,");
                         sqlkey.append("`TENANT_ID` ,");
                         sqlValue.append("'").append(tenApi.getCurrentTenId()).append("' ,");
                         sqlkey.append("`MEMO` ,");
                         sqlValue.append("'").append("创建时间：" + DateUtil.dateNew(new Date()) + ",账号为：" + businessadminPhone +",供应商入库审批完成时创建").append("' ,");
                         sqlkey.append("`DELETE_FLAG` ,");
                         sqlValue.append(" 'NOT_DELETE' ,");
                         sqlkey.append("`create_time` ,");
                         sqlValue.append("'").append(DateUtil.dateNew(new Date())).append("' ,");
                         sqlkey.append("`create_user`");
                         sqlValue.append("'").append(StpLoginUserUtil.getLoginUser().getId()).append("'");

                         String sqlInsert = "insert into BP_USER" +
                                 " (" +
                                 sqlkey.toString() +
                                 " ) " +
                                 " values (" +
                                 sqlValue +
                                 ")";
                         log.debug("数据插入sql{}----"+sqlInsert);
                         NodeRuntimeUtil.handleSqlExecution(sqlInsert);

//                         /**
//                          * 【重要】 TODO 使用云片执行发送验证码，将验证码作为短信内容的参数变量放入
//                          */
//                         String tpl_Id = SMS_YUNPIAN_TPL_CODE_ID;
//                         String tpl_Value = URLEncoder.encode("#code#") + "="  + URLEncoder.encode(passWord);
//                         devSmsApi.sendSmsYunPian("", businessadminPhone.toString(), tpl_Id, tpl_Value);
                     }
                 }
             }
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwNewSupplierExecutionListener.class.getName(), e.getMessage());
        }
    }
}
