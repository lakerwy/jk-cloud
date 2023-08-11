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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.flw.core.enums.NodeExecutionListenerKeyEnum;
import vip.xiaonuo.flw.core.enums.NodeTaskListenerKeyEnum;
import vip.xiaonuo.flw.core.util.CommonHandleUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局事件监听器
 *
 * @author xuyuxiang
 * @date 2022/5/26 10:01
 **/
@Configuration
@Slf4j
public class FlwGlobalEventListener implements JavaDelegate {

    @Resource
    FormRelationTableService formRelationTableService;

    /**
     * 执行监听器
     *
     * @author xuyuxiang
     * @date 2022/6/15 10:05
     **/
    @EventListener
    public void onExecutionEvent(DelegateExecution delegateExecution) {
        if (isProcessStart(delegateExecution)) {
            NodeRuntimeUtil.handleTableDataVariables(delegateExecution);
            NodeRuntimeUtil.handleTitleAndAbstract(delegateExecution);
            NodeRuntimeUtil.handleUserTaskParticipateInfo(delegateExecution);
        }
    }

    private void updateBizStatus(DelegateExecution delegateExecution) {
        //处理状态变更
        String processDefinitionId = delegateExecution.getProcessDefinitionId();
        RepositoryService repositoryService = SpringUtil.getBean(RepositoryService.class);
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        String deploymentId = processDefinition.getDeploymentId();
        FormRelationTable formRelationTable = new FormRelationTable();
        formRelationTable.setDefinitionId(processDefinitionId);
        formRelationTable.setDeploymentId(deploymentId);
        formRelationTable = formRelationTableService.getOne(new QueryWrapper<>(formRelationTable));
        if (null != formRelationTable) {
            delegateExecution.getProcessBusinessKey();
            String tableName = formRelationTable.getTableName();
            if (StrUtil.isNotBlank(tableName)) {
                String processBusinessKey = delegateExecution.getProcessBusinessKey();
                String sql = "update " + tableName + " set status = '1'  where ID = '" + processBusinessKey +"'";
                CommonHandleUtil.handleSqlExecution(sql);
            }

        }
    }

    /**
     * 任务监听器
     *
     * @author xuyuxiang
     * @date 2022/6/15 10:05
     **/
    @EventListener
    public void onTaskEvent(DelegateTask delegateTask) {
        if(isTaskCreate(delegateTask)) {
            boolean newProcessStart = ObjectUtil.isEmpty(delegateTask.getProcessEngineServices().getHistoryService()
                    .createHistoricProcessInstanceQuery().processInstanceId(delegateTask.getProcessInstanceId()).singleResult());
            if(isTaskStart(delegateTask)) {
                if(newProcessStart) {
                    // 自动完成第一个发起申请节点
                    NodeRuntimeUtil.handleUserTaskCommentAndAttachment(delegateTask.getId(),
                            delegateTask.getProcessInstanceId(), "START", "发起申请", null,
                            CollectionUtil.newArrayList());
                    delegateTask.getProcessEngineServices().getTaskService().complete(delegateTask.getId());
                }
            } else {
                NodeRuntimeUtil.handleUserTaskTypeAndDistinctType(delegateTask);
            }
        }
        if(isTaskAssignment(delegateTask)) {
            if(!isTaskStart(delegateTask)) {
                // 非申请节点则发送待办通知
                try {
                    NodeRuntimeUtil.handleUserTaskTodoNotice(delegateTask);
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 判断是否流程启动
     *
     * @author xuyuxiang
     * @date 2022/6/9 19:52
     */
    public static boolean isProcessStart(DelegateExecution delegateExecution) {
        String eventName = delegateExecution.getEventName();
        if(eventName.equals(NodeExecutionListenerKeyEnum.START.getValue().toLowerCase())) {
            String activityInstanceId = delegateExecution.getActivityInstanceId();
            return ObjectUtil.isEmpty(activityInstanceId);
        }
        return false;
    }

    /**
     * 判断任务是否为发起申请
     *
     * @author xuyuxiang
     * @date 2022/6/9 19:52
     */
    public static boolean isTaskStart(DelegateTask delegateTask) {
        List<FlowNode> flowNodeList = delegateTask.getBpmnModelElementInstance().getPreviousNodes().list();
        if(ObjectUtil.isNotEmpty(flowNodeList) && flowNodeList.size() == 1) {
            return flowNodeList.get(0).getElementType().getTypeName().equals(BpmnModelConstants.BPMN_ELEMENT_START_EVENT);
        }
        return false;
    }

    /**
     * 判断是否任务创建
     *
     * @author xuyuxiang
     * @date 2022/6/9 19:52
     */
    public static boolean isTaskCreate(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        return eventName.equals(NodeTaskListenerKeyEnum.CREATE.getValue().toLowerCase());
    }

    /**
     * 判断是否任务分配
     *
     * @author xuyuxiang
     * @date 2022/6/9 19:52
     */
    public static boolean isTaskAssignment(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        return eventName.equals(NodeTaskListenerKeyEnum.ASSIGNMENT.getValue().toLowerCase());
    }

    /**
     * 结束监听
     *
     * @author xuyuxiang
     * @date 2023/7/17 23:42
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {
        String eventName = delegateExecution.getEventName();
        if(eventName.equals(NodeExecutionListenerKeyEnum.END.getValue().toLowerCase())) {
            // 发送完成通知
            try {
                NodeRuntimeUtil.handleProcessCompleteNotice(delegateExecution);
                //updateBizStatus(delegateExecution);
                String processInstanceId = delegateExecution.getProcessInstanceId();
                //获取流程实例关联的业务信息
                FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
                //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
                if(ObjectUtil.isNotEmpty(info)){
                    String sql = "update " + info.getTableName() + " set `status` = " + FlwStatusEnum.COMPLETED_STATUS.getValue() + " where ID = '" + info.getId() +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                    if(ObjectUtil.isNotEmpty(info.getBizTableName())){
                        sql = "update " + info.getBizTableName() + " set `status` = " + FlwStatusEnum.COMPLETED_STATUS.getValue() + " where FLW_ID = '" + info.getId() +"'";
                        NodeRuntimeUtil.handleSqlExecution(sql);
                    }
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
            //            System.out.println("----------------------121212------------------");
            //log.debug("测试结束事件------------------------");
        }
    }



    //@EventListener
    public void onUpdateBizStatusEvent(DelegateExecution delegateExecution) {
        RuntimeService runtimeService = SpringUtil.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(delegateExecution.getProcessInstanceId()).singleResult();

        if(processInstance == null){
//            System.out.println("----------------------121212------------------");
            log.debug("测试结束事件------------------------");
            updateBizStatus(delegateExecution);
        }

    }

}
