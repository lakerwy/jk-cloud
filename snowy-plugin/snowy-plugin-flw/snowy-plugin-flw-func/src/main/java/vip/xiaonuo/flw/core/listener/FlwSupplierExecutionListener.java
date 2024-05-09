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
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;
import vip.xiaonuo.ten.api.TenApi;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 招采供应商审核完成执行监听器
 *
 * @author huangxiwu
 * @date 2024/5/9 08:51
 */
@Slf4j
@Component
public class FlwSupplierExecutionListener implements ExecutionListener {

    @Resource
    FormRelationTableService formRelationTableService;

    private static final TenApi tenApi = SpringUtil.getBean(TenApi.class);

    @Override
    public void notify(DelegateExecution execution) {
        try {
            log.info("shen>>> 执行监听器：{}事件触发，流程id为：{}，当前活动节点为：{}",
                    execution.getEventName(), execution.getProcessInstanceId(), execution.getCurrentActivityName());
            String processInstanceId = execution.getProcessInstanceId();

            //获取流程实例关联的业务信息
            FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
            String supplierId = String.valueOf(execution.getVariable("SUPPLIER_ID"));
            String name = String.valueOf(execution.getVariable("BUSINESSADMIN_NAME"));
            String phone = String.valueOf(execution.getVariable("BUSINESSADMIN_PHONE"));

            //新增招采平台用户
            if(ObjectUtil.isNotEmpty(info)){
                //拼接sql
                StringBuilder sqlkey = new StringBuilder();
                StringBuilder sqlValue = new StringBuilder();
                sqlkey.append("`NAME` ,");
                sqlValue.append("'").append(name).append("' ,");
                sqlkey.append("`ACCOUNT` ,");
                sqlValue.append("'").append(phone).append("' ,");
                sqlkey.append("`USER_STATUS` ,");
                sqlValue.append(" 'ENABLE' ,");
                sqlkey.append("`SUPPLIER_ID` ,");
                sqlValue.append("'").append(supplierId).append("' ,");
                sqlkey.append("`TENANT_ID` ,");
                sqlValue.append("'").append(tenApi.getCurrentTenId()).append("' ,");
                sqlkey.append("`DELETE_FLAG` ,");
                sqlValue.append(" 'NOT_DELETE' ,");
                sqlkey.append("`create_time` ,");
                sqlValue.append("'").append(DateUtil.dateNew(new Date())).append("' ,");
                sqlkey.append("`create_user`");
                sqlValue.append("'").append(StpLoginUserUtil.getLoginUser().getId()).append("'");

                String sql = "insert into BP_USER" +
                        " (" +
                        sqlkey.toString() +
                        " ) " +
                        " values (" +
                        sqlValue +
                        ")";
                log.debug("数据插入sql{}----"+sql);
                NodeRuntimeUtil.handleSqlExecution(sql);
            }
        } catch (Exception e) {
            throw new CommonException("执行监听器{}出现异常：{}", FlwSupplierExecutionListener.class.getName(), e.getMessage());
        }
    }
}
