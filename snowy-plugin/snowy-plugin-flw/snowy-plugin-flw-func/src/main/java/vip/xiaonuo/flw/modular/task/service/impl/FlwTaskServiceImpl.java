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
package vip.xiaonuo.flw.modular.task.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperties;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dev.api.IDingDingService;
import vip.xiaonuo.flw.core.enums.NodeApproveOperateTypeEnum;
import vip.xiaonuo.flw.core.listener.FlwGlobalCustomEventCenter;
import vip.xiaonuo.flw.core.node.FlwNode;
import vip.xiaonuo.flw.core.parser.ProcessParser;
import vip.xiaonuo.flw.core.util.NodeInfoUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.model.dto.FlwModelDTO;
import vip.xiaonuo.flw.modular.model.entity.FlwModel;
import vip.xiaonuo.flw.modular.model.enums.FlwModelStatusEnum;
import vip.xiaonuo.flw.modular.model.param.FlwModelIdParam;
import vip.xiaonuo.flw.modular.model.service.FlwModelService;
import vip.xiaonuo.flw.modular.process.param.FlwCommentParam;
import vip.xiaonuo.flw.modular.process.param.FlwProcessIdParam;
import vip.xiaonuo.flw.modular.process.result.FlwProcessDetailResult;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;
import vip.xiaonuo.flw.modular.task.param.*;
import vip.xiaonuo.flw.modular.task.result.*;
import vip.xiaonuo.flw.modular.task.service.FlwTaskService;
import vip.xiaonuo.sys.api.SysOrgApi;
import vip.xiaonuo.sys.api.SysUserApi;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.camunda.bpm.engine.task.IdentityLinkType.ASSIGNEE;
import static org.camunda.bpm.engine.task.IdentityLinkType.CANDIDATE;

/**
 * 任务Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/5/22 15:33
 */
@Service
@Slf4j
public class FlwTaskServiceImpl implements FlwTaskService {

    @Resource
    private SysOrgApi sysOrgApi;

    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private FlwProcessService flwProcessService;

    @Resource
    private FormRelationTableService formRelationTableService;

    @Resource
    private CommonCacheOperator commonCacheOperator;


    @Resource
    IDingDingService iDingDingService;

    @Resource
    FlwModelService flwModelService;

    /**
     * 快捷输入的保存key
     */
    private final static String INPUT_KEY_PRE = "INPUT_";

    /**
     * 异步任务线程池
     */
    public static Executor messageExec = Executors.newFixedThreadPool(20);

    @Override
    public Page<FlwTodoTaskResult> todoPage(FlwTaskPageTodoParam flwTaskPageTodoParam) {
        NodeRuntimeUtil.handleTenAuth();
        TaskQuery taskQuery = taskService.createTaskQuery().active().taskAssignee(StpUtil.getLoginIdAsString()).orderByTaskCreateTime().desc();
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getName())) {
//            taskQuery.processDefinitionName(flwTaskPageTodoParam.getName()).or()
//                    .processVariableValueLike("initiatorProcessAbstract", "%" + flwTaskPageTodoParam.getName() + "%");
            List<String> keys = flwProcessService.getNameList(flwTaskPageTodoParam.getName());
            if(CollUtil.isNotEmpty(keys)){
                taskQuery.processDefinitionKeyIn(keys.toArray(new String[0]));
            }
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getInitiator())) {
            taskQuery.processVariableValueEquals("initiator", flwTaskPageTodoParam.getInitiator());
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getSearchKey())) {
            taskQuery.processVariableValueLike("initiatorName", "%" + flwTaskPageTodoParam.getSearchKey() + "%").or()
                    .processVariableValueLike("initiatorProcessSn", "%" + flwTaskPageTodoParam.getSearchKey() + "%");
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getSn())) {
            taskQuery.processVariableValueLike("initiatorProcessSn", "%" + flwTaskPageTodoParam.getSn() + "%");
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getInitiatorProcessAbstract())) {
            taskQuery.processVariableValueLike("initiatorProcessAbstract", "%" + flwTaskPageTodoParam.getInitiatorProcessAbstract() + "%");
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageTodoParam.getInitiatorProcessTitle())) {
            taskQuery.processVariableValueLike("initiatorProcessTitle", "%" + flwTaskPageTodoParam.getInitiatorProcessTitle() + "%");
        }
        Page<FlwTodoTaskResult> defaultPage = CommonPageRequest.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<FlwTodoTaskResult> flwTodoTaskResultList = taskQuery.listPage(Convert.toInt((current - 1) * size), Convert.toInt(size)).stream()
                .map(task -> {
                    JSONObject variableJsonObject = JSONUtil.createObj();
                    historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list()
                            .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                                    historicVariableInstance.getValue()));
                    FlwTodoTaskResult flwTodoTaskResult = new FlwTodoTaskResult();
                    flwTodoTaskResult.setId(task.getId());
                    flwTodoTaskResult.setProcessInstanceId(task.getProcessInstanceId());
                    flwTodoTaskResult.setSn(variableJsonObject.getStr("initiatorProcessSn"));
                    flwTodoTaskResult.setTitle(variableJsonObject.getStr("initiatorProcessTitle"));
                    flwTodoTaskResult.setAbstractTitle(variableJsonObject.getStr("initiatorProcessAbstract"));
                    String processDefinitionId = task.getProcessDefinitionId();
                    Object o = commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId);
                    if (ObjectUtil.isNotEmpty(o)) {
                        flwTodoTaskResult.setProcessDefinitionName(String.valueOf(o));
                    } else {
                        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
                        if (ObjectUtil.isNotEmpty(processDefinition)) {
                            commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId, processDefinition.getName());
                            flwTodoTaskResult.setProcessDefinitionName(processDefinition.getName());
                        }
                    }
                    flwTodoTaskResult.setProcessDefinitionId(processDefinitionId);
                    flwTodoTaskResult.setCurrentActivityId(task.getTaskDefinitionKey());
                    flwTodoTaskResult.setCurrentActivityName(task.getName());
                    String taskAssignee = task.getAssignee();
                    if (ObjectUtil.isNotEmpty(taskAssignee)) {
                        JSONObject taskAssigneeUser = sysUserApi.getUserByIdWithoutException(taskAssignee);
                        if (ObjectUtil.isNotEmpty(taskAssigneeUser)) {
                            flwTodoTaskResult.setAssignees(taskAssigneeUser.getStr("name"));
                        }
                    } else {
                        String assignee = variableJsonObject.getStr("assignee" + StrUtil.UNDERLINE + task.getTaskDefinitionKey());
                        if (ObjectUtil.isNotEmpty(assignee)) {
                            List<String> userIdList = JSONUtil.toList(assignee, String.class);
                            flwTodoTaskResult.setAssignees(StrUtil.join("、", sysUserApi.getUserListByIdListWithoutException(userIdList).stream()
                                    .map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList())));
                        }
                    }
                    flwTodoTaskResult.setInitiator(variableJsonObject.getStr("initiator"));
                    flwTodoTaskResult.setInitiatorName(variableJsonObject.getStr("initiatorName"));
                    flwTodoTaskResult.setInitiatorOrgId(variableJsonObject.getStr("initiatorOrgId"));
                    flwTodoTaskResult.setInitiatorOrgName(variableJsonObject.getStr("initiatorOrgName"));
                    flwTodoTaskResult.setInitiatorPositionId(variableJsonObject.getStr("initiatorPositionId"));
                    flwTodoTaskResult.setInitiatorPositionName(variableJsonObject.getStr("initiatorPositionName"));
                    flwTodoTaskResult.setInitiatorTime(variableJsonObject.getStr("initiatorTime"));
                    flwTodoTaskResult.setTenantId(task.getTenantId());
                    return flwTodoTaskResult;
                }).collect(Collectors.toList());
        defaultPage.setTotal(taskQuery.count());
        defaultPage.setRecords(flwTodoTaskResultList);
        return defaultPage;
    }

    @Override
    public Page<FlwDoneTaskResult> donePage(FlwTaskPageDoneParam flwTaskPageDoneParam) {
        NodeRuntimeUtil.handleTenAuth();
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(StpUtil.getLoginIdAsString())
                .orderByHistoricTaskInstanceEndTime().desc();
        if (ObjectUtil.isNotEmpty(flwTaskPageDoneParam.getName())) {
//            historicTaskInstanceQuery.processDefinitionName(flwTaskPageDoneParam.getName());
            List<String> keys = flwProcessService.getNameList(flwTaskPageDoneParam.getName());
            if(CollUtil.isNotEmpty(keys)){
                historicTaskInstanceQuery.or().processDefinitionKey(keys.get(0));
            }
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageDoneParam.getInitiator())) {
            historicTaskInstanceQuery.processVariableValueEquals("initiator", flwTaskPageDoneParam.getInitiator());
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageDoneParam.getSn())) {
            historicTaskInstanceQuery.processVariableValueLike("initiatorProcessSn", "%" + flwTaskPageDoneParam.getSn() + "%");
        }
        if (ObjectUtil.isNotEmpty(flwTaskPageDoneParam.getInitiatorProcessAbstract())) {
            historicTaskInstanceQuery.processVariableValueLike("initiatorProcessAbstract", "%" + flwTaskPageDoneParam.getInitiatorProcessAbstract() + "%");
        }

        if (ObjectUtil.isNotEmpty(flwTaskPageDoneParam.getInitiatorProcessTitle())) {
            historicTaskInstanceQuery.processVariableValueLike("initiatorProcessTitle", "%" + flwTaskPageDoneParam.getInitiatorProcessTitle() + "%");
        }
        Page<FlwDoneTaskResult> defaultPage = CommonPageRequest.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<FlwDoneTaskResult> flwDoneTaskResultList = historicTaskInstanceQuery.listPage(Convert.toInt((current - 1) * size), Convert.toInt(size)).stream()
                .map(historicTaskInstance -> {
                    return getDoneTaskResult(historicTaskInstance);
                }).collect(Collectors.toList());
        defaultPage.setTotal(historicTaskInstanceQuery.count());
        defaultPage.setRecords(flwDoneTaskResultList);
        return defaultPage;

    }

    @NotNull
    private FlwDoneTaskResult getDoneTaskResult(HistoricTaskInstance historicTaskInstance) {
        JSONObject variableJsonObject = JSONUtil.createObj();
        historyService.createHistoricVariableInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).list()
                .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                        historicVariableInstance.getValue()));
        FlwDoneTaskResult flwDoneTaskResult = new FlwDoneTaskResult();
        flwDoneTaskResult.setId(historicTaskInstance.getId());
        flwDoneTaskResult.setProcessInstanceId(historicTaskInstance.getProcessInstanceId());
        flwDoneTaskResult.setSn(variableJsonObject.getStr("initiatorProcessSn"));
        flwDoneTaskResult.setTitle(variableJsonObject.getStr("initiatorProcessTitle"));
        flwDoneTaskResult.setAbstractTitle(variableJsonObject.getStr("initiatorProcessAbstract"));
        String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
        flwDoneTaskResult.setProcessDefinitionId(processDefinitionId);
        Object o = commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId);
        if (ObjectUtil.isNotEmpty(o)) {
            flwDoneTaskResult.setProcessDefinitionName(String.valueOf(o));
        } else {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            if (ObjectUtil.isNotEmpty(processDefinition)) {
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId, processDefinition.getName());
                flwDoneTaskResult.setProcessDefinitionName(processDefinition.getName());
            }
        }

        flwDoneTaskResult.setDoneActivityId(historicTaskInstance.getTaskDefinitionKey());
        flwDoneTaskResult.setDoneActivityName(historicTaskInstance.getName());
        flwDoneTaskResult.setDoneTime(DateUtil.formatDateTime(historicTaskInstance.getEndTime()));
        flwDoneTaskResult.setInitiator(variableJsonObject.getStr("initiator"));
        flwDoneTaskResult.setInitiatorName(variableJsonObject.getStr("initiatorName"));
        flwDoneTaskResult.setInitiatorOrgId(variableJsonObject.getStr("initiatorOrgId"));
        flwDoneTaskResult.setInitiatorOrgName(variableJsonObject.getStr("initiatorOrgName"));
        flwDoneTaskResult.setInitiatorPositionId(variableJsonObject.getStr("initiatorPositionId"));
        flwDoneTaskResult.setInitiatorPositionName(variableJsonObject.getStr("initiatorPositionName"));
        flwDoneTaskResult.setInitiatorTime(variableJsonObject.getStr("initiatorTime"));
        flwDoneTaskResult.setTenantId(historicTaskInstance.getTenantId());
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(flwDoneTaskResult.getProcessInstanceId()).singleResult();
        String state = historicProcessInstance.getState();
        switch (state) {
            case "ACTIVE":
                flwDoneTaskResult.setStateCode(state);
                flwDoneTaskResult.setStateText("审批中");
                break;
            case "SUSPENDED":
                flwDoneTaskResult.setStateCode(state);
                flwDoneTaskResult.setStateText("已挂起");
                break;
            case "COMPLETED":
                flwDoneTaskResult.setStateCode(state);
                flwDoneTaskResult.setStateText("已完成");
                break;
            case "EXTERNALLY_TERMINATED":
                String deleteReason = historicProcessInstance.getDeleteReason();
                switch (deleteReason) {
                    case "END":
                        flwDoneTaskResult.setStateCode(deleteReason);
                        flwDoneTaskResult.setStateText("已终止");
                        break;
                    case "REVOKE":
                        flwDoneTaskResult.setStateCode(deleteReason);
                        flwDoneTaskResult.setStateText("已撤回");
                        break;
                    case "REJECT":
                        flwDoneTaskResult.setStateCode(deleteReason);
                        flwDoneTaskResult.setStateText("已拒绝");
                        break;
                    default:
                        throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
                }
                break;
            case "INTERNALLY_TERMINATED":
                flwDoneTaskResult.setStateCode(state);
                flwDoneTaskResult.setStateText("内部终止");
                break;
            default:
                throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
        }
        return flwDoneTaskResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void adjust(FlwTaskAdjustParam flwTaskAdjustParam) {
        NodeRuntimeUtil.handleTenAuth();
        String taskId = flwTaskAdjustParam.getId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isEmpty(task)) {
            throw new CommonException("任务不存在，id值为：{}", taskId);
        }
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, task.getProcessInstanceId(), NodeApproveOperateTypeEnum.RESTART.getValue(),
                "重新提交", null, CollectionUtil.newArrayList());
        // 更新sql
        handleDataJson(task.getProcessInstanceId(), flwTaskAdjustParam.getDataJson());

        // 执行调整
        taskService.complete(task.getId());
    }

    //@Transactional(rollbackFor = Exception.class)
    @Override
    public void pass(FlwTaskPassParam flwTaskPassParam) {
        Task task = validTask(flwTaskPassParam.getId(), flwTaskPassParam.getComment());
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.PASS.getValue(), "同意申请",
                flwTaskPassParam.getComment(), flwTaskPassParam.getAttachmentList());
        // 更新sql
        handleDataJson(task.getProcessInstanceId(), flwTaskPassParam.getDataJson());

        // 执行通过
        taskService.complete(task.getId());
        commonCacheOperator.remove(RabbitConstant.RABBIT_KEY +processInstanceId+ taskId);

    }

    @Override
    public void save(FlwTaskSaveParam flwTaskSaveParam) {
        NodeRuntimeUtil.handleTenAuth();
        String taskId = flwTaskSaveParam.getId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isEmpty(task)) {
            throw new CommonException("任务不存在，id值为：{}", taskId);
        }
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariables(processInstanceId, JSONUtil.createObj().set("initiatorDataJson", flwTaskSaveParam.getDataJson()));
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void reject(FlwTaskRejectParam flwTaskRejectParam) {
        Task task = validTask(flwTaskRejectParam.getId(), flwTaskRejectParam.getComment());
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.REJECT.getValue(), "拒绝申请",
                flwTaskRejectParam.getComment(), flwTaskRejectParam.getAttachmentList());
        NodeRuntimeUtil.handleUserTaskRejectNotice((DelegateTask) task);
        // 更新sql
        handleDataJson(task.getProcessInstanceId(), flwTaskRejectParam.getDataJson());

        // 发布拒绝事件
        FlwGlobalCustomEventCenter.publishRejectEvent(processInstanceId);

        // 执行拒绝
        runtimeService.deleteProcessInstancesIfExists(CollectionUtil.newArrayList(processInstanceId), "REJECT",
                true, true, true);

        FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
        //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
        if (ObjectUtil.isNotEmpty(info)) {
            String sql = "update " + info.getTableName() + " set `status` = " + FlwStatusEnum.REJECT_STATUS.getValue() + " where ID = '" + info.getId() + "'";
            NodeRuntimeUtil.handleSqlExecution(sql);
            if (ObjectUtil.isNotEmpty(info.getBizTableName())) {
                sql = "update " + info.getBizTableName() + " set `status` = " + FlwStatusEnum.REJECT_STATUS.getValue() + " where FLW_ID = '" + info.getId() + "'";
                NodeRuntimeUtil.handleSqlExecution(sql);
            }
        }
        commonCacheOperator.remove(RabbitConstant.RABBIT_KEY + processInstanceId+taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void back(FlwTaskBackParam flwTaskBackParam) {
        Task task = validTask(flwTaskBackParam.getId(), flwTaskBackParam.getComment());
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String processDefinitionId = task.getProcessDefinitionId();
        AtomicReference<String> targetActivityId = new AtomicReference<>(new ArrayList<>(repositoryService.getBpmnModelInstance(processDefinitionId)
                .getModelElementById(taskDefinitionKey).getParentElement().getChildElementsByType(StartEvent.class)).get(0).getId());
        AtomicBoolean backToStart = new AtomicBoolean(true);
        UserTask userTask = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(taskDefinitionKey);
        ExtensionElements extensionElements = userTask.getExtensionElements();
        if (ObjectUtil.isNotEmpty(extensionElements)) {
            extensionElements.getChildElementsByType(CamundaProperties.class).forEach(camundaProperties ->
                    camundaProperties.getChildElementsByType(CamundaProperty.class).forEach(camundaProperty -> {
                        if (camundaProperty.getCamundaName().equals("configInfo")) {
                            JSONObject configInfo = JSONUtil.parseObj(camundaProperty.getCamundaValue());
                            String userTaskRejectType = configInfo.getStr("userTaskRejectType");
                            if (ObjectUtil.isNotEmpty(userTaskRejectType)) {
                                switch (userTaskRejectType) {
                                    case "AUTO_END":
                                        targetActivityId.set("-1");
                                        break;
                                    case "TO_START":
                                        try {
                                            NodeRuntimeUtil.handleUserTaskBackNotice((DelegateTask) task);
                                        } catch (Exception e) {
                                            log.error(e.getMessage());
                                        }
                                        break;
                                    case "USER_SELECT":
                                        if (ObjectUtil.isEmpty(flwTaskBackParam.getTargetActivityId())) {
                                            throw new CommonException("退回目标节点id不能为空，任务id为：{}", taskId);
                                        } else {
                                            backToStart.set(false);
                                            targetActivityId.set(flwTaskBackParam.getTargetActivityId());
                                        }
                                        break;
                                    default:
                                        throw new CommonException("不支持的任务节点退回类型：{}", task.getProcessInstanceId());
                                }
                            }
                        }
                    }));
        } else {
            throw new CommonException("数据错误，流程模型扩展属性不存在，id值为：{}", processInstanceId);
        }
        // 更新sql
        handleDataJson(processInstanceId, flwTaskBackParam.getDataJson());
        // 直接结束
        if (targetActivityId.get().equals("-1")) {
            // 添加审批记录
            NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.BACK.getValue(), "退回结束",
                    flwTaskBackParam.getComment(), flwTaskBackParam.getAttachmentList());
            // 发布拒绝事件
            FlwGlobalCustomEventCenter.publishRejectEvent(processInstanceId);

            runtimeService.deleteProcessInstancesIfExists(CollectionUtil.newArrayList(processInstanceId), "REJECT",
                    true, true, true);
        } else {
            // 创建流程实体修改构造器
            ProcessInstanceModificationBuilder processInstanceModificationBuilder = runtimeService
                    .createProcessInstanceModification(processInstanceId);
            // 取消当前所有正在执行的节点
            taskService.createTaskQuery().processInstanceId(processInstanceId).list().stream().map(Task::getTaskDefinitionKey)
                    .collect(Collectors.toSet()).forEach(processInstanceModificationBuilder::cancelAllForActivity);
            // 是否跳转到发起节点
            if (backToStart.get()) {
                // 添加审批记录
                NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.BACK.getValue(),
                        "退回节点", "退回到发起节点：" + flwTaskBackParam.getComment(), flwTaskBackParam.getAttachmentList());
                // 执行跳转
                processInstanceModificationBuilder.startAfterActivity(targetActivityId.get()).execute();
            } else {
                UserTask backUserTask = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetActivityId.get());
                // 添加审批记录
                NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.BACK.getValue(),
                        "退回节点", "退回到【" + backUserTask.getName() + "】节点：" + flwTaskBackParam.getComment(),
                        flwTaskBackParam.getAttachmentList());
                // 执行跳转
                Collection<SequenceFlow> incoming = backUserTask.getIncoming();
                if (incoming.size() != 1) {
                    throw new CommonException("流程模型数据错误，节点{}入口存在多个", userTask.getName());
                }
                processInstanceModificationBuilder.startTransition(incoming.iterator().next().getId()).execute();
            }
        }
        commonCacheOperator.remove(RabbitConstant.RABBIT_KEY +processInstanceId+ taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void turn(FlwTaskTurnParam flwTaskTurnParam, boolean validComment) {
        Task task;
        if (validComment) {
            task = validTask(flwTaskTurnParam.getId(), flwTaskTurnParam.getComment());
        } else {
            NodeRuntimeUtil.handleTenAuth();
            task = taskService.createTaskQuery().taskId(flwTaskTurnParam.getId()).singleResult();
            if (ObjectUtil.isEmpty(task)) {
                throw new CommonException("任务不存在，id值为：{}", flwTaskTurnParam.getId());
            }
        }
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        if (flwTaskTurnParam.isRecordComment()) {
            NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.TURN.getValue(),
                    "转办任务", "转办该任务给【" + sysUserApi.getUserByIdWithException(flwTaskTurnParam.getUserId()).getStr("name") + "】："
                            + flwTaskTurnParam.getComment(), flwTaskTurnParam.getAttachmentList());
        }
        // 更新sql
        handleDataJson(task.getProcessInstanceId(), flwTaskTurnParam.getDataJson());
        // 删除其办理人及候选人候选组
        taskService.getIdentityLinksForTask(taskId).forEach(identityLink -> {
            //获取人员类型
            String type = identityLink.getType();
            //删除其待办人
            if (ASSIGNEE.equals(type)) {
                String userId = identityLink.getUserId();
                if (ObjectUtil.isNotEmpty(userId)) {
                    taskService.deleteUserIdentityLink(taskId, null, type);
                }
            }
            //删除其候选人与候选组
            if (CANDIDATE.equals(type)) {
                String groupId = identityLink.getGroupId();
                if (ObjectUtil.isNotEmpty(groupId)) {
                    taskService.deleteCandidateGroup(taskId, groupId);
                }
                String userId = identityLink.getUserId();
                if (ObjectUtil.isNotEmpty(userId)) {
                    taskService.deleteCandidateUser(taskId, userId);
                }
            }
        });

        // 执行转办
        taskService.setAssignee(taskId, flwTaskTurnParam.getUserId());
        commonCacheOperator.remove(RabbitConstant.RABBIT_KEY + processInstanceId + taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void jump(FlwTaskJumpParam flwTaskJumpParam) {
        Task task = validTask(flwTaskJumpParam.getId(), flwTaskJumpParam.getComment());
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        String processDefinitionId = task.getProcessDefinitionId();
        String targetActivityId = flwTaskJumpParam.getTargetActivityId();
        UserTask jumpUserTask = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetActivityId);
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.JUMP.getValue(),
                "跳转节点", "跳转到【" + jumpUserTask.getName() + "】节点：" + flwTaskJumpParam.getComment(), flwTaskJumpParam.getAttachmentList());
        // 更新sql
        handleDataJson(task.getProcessInstanceId(), flwTaskJumpParam.getDataJson());
        // 执行跳转
        flwProcessService.doProcessInstanceModify(processInstanceId, jumpUserTask);
        commonCacheOperator.remove(RabbitConstant.RABBIT_KEY + processInstanceId + taskId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSign(FlwTaskAddSignParam flwTaskAddSignParam) {
        Task task = validTask(flwTaskAddSignParam.getId(), flwTaskAddSignParam.getComment());
        String currentActivityId = flwTaskAddSignParam.getCurrentActivityId();
        UserTask currentUserTask = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId()).getModelElementById(currentActivityId);
        ModelElementInstance currentMultiInstanceLoopCharacteristics = currentUserTask.getUniqueChildElementByType(MultiInstanceLoopCharacteristics.class);
        if (ObjectUtil.isEmpty(currentMultiInstanceLoopCharacteristics)) {
            throw new CommonException("模型数据错误，当前节点：{}多实例属性为空", currentUserTask.getName());
        }
        String currentIsSequential = currentMultiInstanceLoopCharacteristics.getAttributeValue("isSequential");
        if (ObjectUtil.isEmpty(currentIsSequential)) {
            throw new CommonException("模型数据错误，当前节点：{}多实例属性为空", currentUserTask.getName());
        }
        Boolean sequential = Convert.toBool(currentIsSequential);
        if (sequential) {
            throw new CommonException("当前节点：{}的节点类型不支持加签", currentUserTask.getName());
        }
        List<String> assigneeList = flwTaskAddSignParam.getAssigneeList();
        List<String> existAssigneeList = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).taskDefinitionKey(flwTaskAddSignParam
                .getCurrentActivityId()).list().stream().map(HistoricTaskInstance::getAssignee).collect(Collectors.toList());
        Collection<String> intersection = CollectionUtil.intersection(assigneeList, existAssigneeList);
        if (ObjectUtil.isNotEmpty(intersection)) {
            String existAssigneeNames = StrUtil.join("、", sysUserApi.getUserListByIdListWithoutException(CollectionUtil
                    .newArrayList(intersection)).stream().map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList()));
            throw new CommonException("被加签人员：{}已存在该任务中", existAssigneeNames);
        }
        String taskId = task.getId();
        String processInstanceId = task.getProcessInstanceId();
        String assigneeNames = StrUtil.join("、", sysUserApi.getUserListByIdListWithoutException(assigneeList).stream()
                .map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList()));
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment(taskId, processInstanceId, NodeApproveOperateTypeEnum.ADD_SIGN.getValue(),
                "人员加签", "加签人员：【" + assigneeNames + "】:" + flwTaskAddSignParam.getComment(), flwTaskAddSignParam.getAttachmentList());
        assigneeList.forEach(assignee -> runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                .startBeforeActivity(currentActivityId)
                .setVariable("assignee", assignee)
                .execute());
    }

    @Override
    public FlwTaskDetailResult detail(FlwTaskIdParam flwTaskIdParam) {
        FlwTaskDetailResult flwTaskDetailResult = new FlwTaskDetailResult();
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(flwTaskIdParam.getId()).singleResult();
        FlwProcessIdParam flwProcessIdParam = new FlwProcessIdParam();
        flwProcessIdParam.setId(historicTaskInstance.getProcessInstanceId());
        FlwProcessDetailResult flwProcessDetailResult = flwProcessService.detail(flwProcessIdParam);
        BeanUtil.copyProperties(flwProcessDetailResult, flwTaskDetailResult);
        flwTaskDetailResult.setTaskId(historicTaskInstance.getId());
        return flwTaskDetailResult;
    }


    @Override
    public List<FlwBackNodeResult> getCanBackNodeInfoList(FlwTaskGetBackNodeInfoParam flwTaskGetBackNodeInfoParam) {
        return historyService.createHistoricActivityInstanceQuery().processInstanceId(flwTaskGetBackNodeInfoParam.getProcessInstanceId())
                .finished().activityType(BpmnModelConstants.BPMN_ELEMENT_USER_TASK).orderByHistoricActivityInstanceStartTime().asc()
                .list().stream().filter(historicActivityInstance -> !historicActivityInstance.getActivityName().equals("EMPTY") &&
                        !historicActivityInstance.getActivityId().equals(flwTaskGetBackNodeInfoParam.getCurrentActivityId()))
                .map(historicActivityInstance -> {
                    FlwBackNodeResult flwBackNodeResult = new FlwBackNodeResult();
                    flwBackNodeResult.setId(historicActivityInstance.getActivityId());
                    flwBackNodeResult.setName(historicActivityInstance.getActivityName());
                    return flwBackNodeResult;
                }).collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator
                        .comparing(FlwBackNodeResult::getId))), ArrayList::new));
    }

    @Override
    public List<FlwJumpNodeResult> getCanJumpNodeInfoList(FlwTaskGetJumpNodeInfoParam flwTaskGetJumpNodeInfoParam) {
        FlwNode flwNode = flwProcessService.getProcessModelJsonNodeVariable(flwTaskGetJumpNodeInfoParam.getProcessInstanceId());
        return NodeInfoUtil.getUserTaskFlwNodeList(flwNode).stream().filter(tempFlwNode -> !tempFlwNode.getId()
                .equals(flwTaskGetJumpNodeInfoParam.getCurrentActivityId())).map(tempFlwNode -> {
            FlwJumpNodeResult flwJumpNodeResult = new FlwJumpNodeResult();
            flwJumpNodeResult.setId(tempFlwNode.getId());
            flwJumpNodeResult.setName(tempFlwNode.getTitle());
            return flwJumpNodeResult;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Tree<String>> orgTreeSelector() {
        return sysOrgApi.orgTreeSelector();
    }

    @SuppressWarnings("ALL")
    @Override
    public Page<FlwTaskUserResult> userSelector(FlwTaskSelectorUserParam flwTaskSelectorUserParam) {
        return BeanUtil.toBean(sysUserApi.userSelector(flwTaskSelectorUserParam.getOrgId(), flwTaskSelectorUserParam.getSearchKey()), Page.class);
    }

    /**
     * 校验任务
     *
     * @author xuyuxiang
     * @date 2023/5/17 21:54
     */
    public Task validTask(String taskId, String comment) {
        NodeRuntimeUtil.handleTenAuth();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isEmpty(task)) {
            throw new CommonException("任务不存在，id值为：{}", taskId);
        }
        String processInstanceId = task.getProcessInstanceId();
        FlwNode.FlwNodeConfigProp flwNodeConfigProp = flwProcessService.getProcessNodeConfigProp(processInstanceId);
        if (ObjectUtil.isEmpty(comment)) {
            Boolean enableCommentRequired = flwNodeConfigProp.getProcessEnableCommentRequired();
            if (enableCommentRequired) {
                throw new CommonException("该流程已配置意见必填，id值为：{}，请填写意见", processInstanceId);
            }
        }
        return task;
    }

    /**
     * 处理审批时的表单数据
     *
     * @author xuyuxiang
     * @date 2023/5/22 22:26
     */
    public void handleDataJson(String processInstanceId, String dataJson) {
        if (ObjectUtil.isNotEmpty(dataJson) && !JSONUtil.parseObj(dataJson).isEmpty()) {
            Object initiatorTableJson = runtimeService.getVariables(processInstanceId).get("initiatorTableJson");
            if (ObjectUtil.isNotEmpty(initiatorTableJson)) {
                JSONObject resultDataObj = NodeRuntimeUtil.handleDataJson(Convert.toStr(initiatorTableJson), dataJson,
                        historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
                                .singleResult().getBusinessKey());
                String updateDataSql = resultDataObj.getStr("sql");
                NodeRuntimeUtil.handleSqlExecution(updateDataSql);
            }
            runtimeService.setVariables(processInstanceId, JSONUtil.createObj().set("initiatorDataJson", dataJson));
            NodeRuntimeUtil.handleTableDataVariables(processInstanceId, initiatorTableJson, dataJson);
        }
    }

    /**
     * 获取某种类型的所有待办
     *
     * @param processDefinitionId
     * @param activityInstanceId
     * @return
     */
    @Override
    public List<FlwTodoTaskResult> todoListByType(String processDefinitionId, String activityInstanceId) {
        //NodeRuntimeUtil.handleTenAuth();
        TaskQuery taskQuery = taskService.createTaskQuery().active().processDefinitionId(processDefinitionId).taskDefinitionKey(activityInstanceId).orderByTaskCreateTime().desc();
        //Page<FlwTodoTaskResult> defaultPage = CommonPageRequest.defaultPage();
//        long current = defaultPage.getCurrent();
//        long size = defaultPage.getSize();
        //        defaultPage.setTotal(taskQuery.count());
//        defaultPage.setRecords(flwTodoTaskResultList);
        return taskQuery.list().stream()
                .map(task -> {
                    JSONObject variableJsonObject = JSONUtil.createObj();
                    historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list()
                            .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                                    historicVariableInstance.getValue()));
                    FlwTodoTaskResult flwTodoTaskResult = new FlwTodoTaskResult();
                    flwTodoTaskResult.setId(task.getId());
                    flwTodoTaskResult.setProcessInstanceId(task.getProcessInstanceId());
                    flwTodoTaskResult.setSn(variableJsonObject.getStr("initiatorProcessSn"));
                    flwTodoTaskResult.setTitle(variableJsonObject.getStr("initiatorProcessTitle"));
                    flwTodoTaskResult.setAbstractTitle(variableJsonObject.getStr("initiatorProcessAbstract"));
                    flwTodoTaskResult.setProcessDefinitionId(task.getProcessDefinitionId());
                    flwTodoTaskResult.setCurrentActivityId(task.getTaskDefinitionKey());
                    flwTodoTaskResult.setCurrentActivityName(task.getName());
                    String taskAssignee = task.getAssignee();
                    if (ObjectUtil.isNotEmpty(taskAssignee)) {
                        flwTodoTaskResult.setAssignees(taskAssignee);
//                        JSONObject taskAssigneeUser = sysUserApi.getUserByIdWithoutException(taskAssignee);
//                        if(ObjectUtil.isNotEmpty(taskAssigneeUser)) {
//                            flwTodoTaskResult.setAssignees(taskAssigneeUser.getStr("name"));
//                        }
                    } else {
                        String assignee = variableJsonObject.getStr("assignee" + StrUtil.UNDERLINE + task.getTaskDefinitionKey());
                        if (ObjectUtil.isNotEmpty(assignee)) {
                            List<String> userIdList = JSONUtil.toList(assignee, String.class);
                            flwTodoTaskResult.setAssignees(StrUtil.join("、", userIdList));
                        }
                    }
                    flwTodoTaskResult.setInitiator(variableJsonObject.getStr("initiator"));
                    flwTodoTaskResult.setInitiatorName(variableJsonObject.getStr("initiatorName"));
                    flwTodoTaskResult.setInitiatorOrgId(variableJsonObject.getStr("initiatorOrgId"));
                    flwTodoTaskResult.setInitiatorOrgName(variableJsonObject.getStr("initiatorOrgName"));
                    flwTodoTaskResult.setInitiatorPositionId(variableJsonObject.getStr("initiatorPositionId"));
                    flwTodoTaskResult.setInitiatorPositionName(variableJsonObject.getStr("initiatorPositionName"));
                    flwTodoTaskResult.setInitiatorTime(variableJsonObject.getStr("initiatorTime"));
                    flwTodoTaskResult.setTenantId(task.getTenantId());
                    return flwTodoTaskResult;
                }).collect(Collectors.toList());
    }

    @Override
    public void addComment(FlwCommentParam flwcommentParam) {
        //Task task = validTask(flwTaskPassParam.getId(), flwTaskPassParam.getComment());
        String taskId = flwcommentParam.getId();
        String processInstanceId = flwcommentParam.getProcessInstanceId();
        if (StrUtil.isNotEmpty(flwcommentParam.getId())) {
            Task task = taskService.createTaskQuery().taskId(flwcommentParam.getId()).singleResult();
            if (ObjectUtil.isNotEmpty(task)) {
                taskId = task.getId();
                processInstanceId = task.getProcessInstanceId();
            }
        }
        NodeRuntimeUtil.handleUserTaskCommentAndAttachment2(null, processInstanceId, NodeApproveOperateTypeEnum.COMMENT.getValue(), "追加评论",
                flwcommentParam.getComment(), flwcommentParam.getAttachmentList(), flwcommentParam.getFlag(), StpLoginUserUtil.getLoginUser().getId());
        List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        if (CollUtil.isNotEmpty(comments)) {
            Comment comment = comments.get(comments.size() - 1);
            String taskId1 = comment.getTaskId();
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId1).singleResult();
            Map<String, Object> values = new HashMap<>();
            values.put("title", "【评论知晓】");
            values.put("taskId", historicTaskInstance.getId());
//            values.put("stateCode",startTask.get)
            Set<String> collect = new HashSet<>();
            collect.add(comment.getUserId());
            try {
//                Map<String, Object> values = new HashMap<>();
//                values.put("title","【评论知晓】");
//                values.put("url",)
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(comment.getProcessInstanceId()).caseInstanceId(historicTaskInstance.getCaseInstanceId()).singleResult();
                values.put("stateCode", historicProcessInstance.getState());
                iDingDingService.customBatchSend(buildForms(processInstanceId, flwcommentParam.getComment()), collect, values);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @NotNull
    private List<OapiMessageCorpconversationAsyncsendV2Request.Form> buildForms(String processInstanceId, String comment) {
        HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService
                .createHistoricVariableInstanceQuery().processInstanceId(processInstanceId)
                .orderByVariableName().desc();
        List<HistoricVariableInstance> list = historicVariableInstanceQuery.list();
        if (CollUtil.isNotEmpty(list)) {
            String processDefinitionId = list.get(0).getProcessDefinitionId();
            Map<String, Object> taskMap = list.stream().filter(f -> ObjectUtil.isNotEmpty(f.getName()) && ObjectUtil.isNotEmpty(f.getValue())).collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue, (v1, v2) -> v2));
//        String processDefinitionName = historicProcessInstance.getProcessDefinitionName();
//        Map taskMap = jsonObject.get("taskMap", Map.class);
//        Object processDefinitionId = taskMap.get("processDefinitionId");
            //摘要
            Object initiatorProcessAbstract = taskMap.get("initiatorProcessAbstract");
            Object initiatorProcessSn = taskMap.get("initiatorProcessSn");
            Object initiatorTime = taskMap.get("initiatorTime");
            Object initiatorName = taskMap.get("initiatorName");
            OapiMessageCorpconversationAsyncsendV2Request.Form form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("流程名称： ");

            Object o = commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId);
            form.setValue(String.valueOf(o));
            List<OapiMessageCorpconversationAsyncsendV2Request.Form> forms = new ArrayList<>();
            forms.add(form);

            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("基本信息： ");
            form.setValue(String.valueOf(initiatorProcessAbstract));
            forms.add(form);

            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("流水号：");
            form.setValue(String.valueOf(initiatorProcessSn));
            forms.add(form);

            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("发起于：");
            form.setValue(initiatorTime + " | " + initiatorName);
            forms.add(form);

            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("评论人：");
            form.setValue(StpLoginUserUtil.getLoginUser().getName());
            forms.add(form);

            form = new OapiMessageCorpconversationAsyncsendV2Request.Form();
            form.setKey("评论内容为：");
            form.setValue(comment);

            forms.add(form);
            return forms;
        }
        return new ArrayList<>();
    }

    @Override
    public void transferTasks(FlwTaskTransferParam flwTaskTransferParam) {
        //todo 1、将流程模板中所有涉及到是自己的节点全部修改 （只有指定了具体人是自己的修改， 指定了部门的或者角色的不用管，
        // 用户删除以后，用户的状态会修改，根据部门或者角色查询的时候，该用户不会存在）
        // 2、 将自己的所有待办转交给指定人
        // 3、自己发起的不转交  （在流程监控中可以查询）
        // 4、自己已办的不需要转交
        //1 处理方式  修改act_ext_model 表  2 只需要重现发布已开启的。重新发布流程 调用deploy
        List<String> models = Arrays.stream(flwTaskTransferParam.getActExtModelIds().split(","))
                .collect(Collectors.toList());
        List<FlwModel> flwModels = flwModelService.listByIds(models);
        for (FlwModel flwModel : flwModels) {
            String processJson = flwModel.getProcessJson();
            if (ObjectUtil.isNotEmpty(processJson)) {
                FlwNode flwNode = JSONUtil.toBean(processJson, FlwNode.class);
                replaceParticipateInfoInNode(flwNode, flwTaskTransferParam);
                processJson = JSONUtil.toJsonStr(flwNode);
                flwModel.setProcessJson(processJson);
            }
            flwModelService.updateById(flwModel);
            if (flwModel.getModelStatus().equals(FlwModelStatusEnum.ENABLE.getValue())) {
                FlwModelIdParam flwModelIdParam = new FlwModelIdParam();
                flwModelIdParam.setId(flwModel.getId());
                flwModelService.deploy(flwModelIdParam);
            }
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(flwModel.getDeploymentId()).singleResult();
            //Set<String> ids = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinition.getKey()).active().list().stream().map(ProcessDefinition::getId).collect(Collectors.toSet());

            List<Task> list = taskService.createTaskQuery().active().taskAssignee(flwTaskTransferParam.getOldAssignee()).processDefinitionKey(processDefinition.getKey()).orderByTaskCreateTime().desc().list();
//            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            list.forEach(task -> taskService.setAssignee(task.getId(), flwTaskTransferParam.getNewAssignee()));
        }


    }

    private static void replaceParticipateInfoInNode(FlwNode flwNode, FlwTaskTransferParam flwTaskTransferParam) {
        if (flwNode != null && flwNode.getProperties() != null && flwNode.getProperties().getParticipateInfo() != null) {
            List<FlwNode.FlwNodeParticipateProp> participateInfoList = flwNode.getProperties().getParticipateInfo();
            for (FlwNode.FlwNodeParticipateProp participateProp : participateInfoList) {
                String values = participateProp.getValue();
                Set<String> valueList = Arrays.stream(values.split(","))
                        .collect(Collectors.toSet());
                if (valueList.contains(flwTaskTransferParam.getOldAssignee())) {
                    valueList.remove(flwTaskTransferParam.getOldAssignee());
                    valueList.add(flwTaskTransferParam.getNewAssignee());
                    participateProp.setValue(String.join(",", valueList));
                    Set<String> labelSet = Arrays.stream(participateProp.getLabel().split(",")).collect(Collectors.toSet());
                    labelSet.remove(flwTaskTransferParam.getOldAssigneeName());
                    labelSet.add(flwTaskTransferParam.getNewAssigneeName());
                    participateProp.setLabel(String.join(",", labelSet));
                }
            }
            flwNode.getProperties().setParticipateInfo(participateInfoList);
        }

        // 递归处理子节点和条件节点
        assert flwNode != null;
        if (flwNode.getChildNode() != null) {
            replaceParticipateInfoInNode(flwNode.getChildNode(), flwTaskTransferParam);
        }

        if (flwNode.getConditionNodeList() != null) {
            for (FlwNode conditionNode : flwNode.getConditionNodeList()) {
                replaceParticipateInfoInNode(conditionNode, flwTaskTransferParam);
            }
        }
    }

    @Override
    public List<FlwModelDTO> getTransferTasks(FlwTaskTransferPageParam flwTaskTransferPageParam) {
        List<FlwModel> allFlwModels = flwModelService.list();
        List<FlwModelDTO> transferTasks = new ArrayList<>();
        for (FlwModel flwModel : allFlwModels) {
            String processJson = flwModel.getProcessJson();
            if (ObjectUtil.isNotEmpty(processJson)) {
                //FlwNode flwNode = JSONUtil.toBean(processJson, FlwNode.class);
                BpmnModelInstance bpmnModelInstance = ProcessParser.buildBpmnModelInstance(processJson);
                Set<String> users = NodeRuntimeUtil.handleUserTaskParticipateInfo2(bpmnModelInstance);
//                if (checkNodeParticipateInfo(flwNode, flwTaskTransferPageParam.getOldAssignee())) {
//                    transferTasks.add(BeanUtil.toBean(flwModel, FlwModelDTO.class));
//                }
                users.forEach(str-> {
                    if(str.contains(flwTaskTransferPageParam.getOldAssignee())){
                        transferTasks.add(BeanUtil.toBean(flwModel, FlwModelDTO.class));
                    }
                });
            }
        }
        return transferTasks;
    }

    private boolean checkNodeParticipateInfo(FlwNode flwNode, String oldAssignee) {
        if (flwNode == null) {
            return false;
        }
        if (ObjectUtil.isEmpty(flwNode.getProperties())) {
            return false;
        }
        List<FlwNode.FlwNodeParticipateProp> participateInfo = flwNode.getProperties().getParticipateInfo();
        if (ObjectUtil.isNotEmpty(participateInfo)) {
            String participateInfoJson = JSONUtil.toJsonStr(participateInfo);
            List<String> processParticipateInfo = NodeRuntimeUtil.handleProcessParticipateInfoByType(participateInfoJson, "USER");
            if (ObjectUtil.isNotEmpty(processParticipateInfo) && processParticipateInfo.contains(oldAssignee)) {
                return true;
            }
        }
//        NodeRuntimeUtil.handleUserTaskParticipateInfo(delegateExecution);
        FlwNode childNode = flwNode.getChildNode();
        if (childNode != null) {
            return checkNodeParticipateInfo(childNode, oldAssignee);
        }

        return false;
    }

    @Override
    public List<Object> getListComments() {
        return commonCacheOperator.getList(INPUT_KEY_PRE + StpLoginUserUtil.getLoginUser().getId());
    }

    @Override
    public void putComments(String inputValue) {
        String key = INPUT_KEY_PRE + StpLoginUserUtil.getLoginUser().getId();
        List<Object> list = commonCacheOperator.getList(key);
        if (list.size() >= 5) {
            commonCacheOperator.removeListValue(key);
        }
        commonCacheOperator.putListExpire(key, inputValue, 7 * 24 * 60 * 60L);
    }

    @Override
    public  FlwTaskInfoResult getOneByProcessInstanceId(FlwProcessInstanceIdParam flwProcessInstanceIdParam){
        NodeRuntimeUtil.handleTenAuth();
        TaskQuery taskQuery = taskService.createTaskQuery().active().taskAssignee(StpUtil.getLoginIdAsString()).orderByTaskCreateTime().desc();
        taskQuery.processInstanceId(flwProcessInstanceIdParam.getProcessInstanceId());
        Task task = taskQuery.active().singleResult();
        FlwTaskInfoResult flwTaskInfoResult = new FlwTaskInfoResult();
        if(ObjectUtil.isNotNull(task)){
            JSONObject variableJsonObject = JSONUtil.createObj();
            historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list()
                    .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(),
                            historicVariableInstance.getValue()));
            flwTaskInfoResult.setId(task.getId());
            flwTaskInfoResult.setProcessInstanceId(task.getProcessInstanceId());
            flwTaskInfoResult.setSn(variableJsonObject.getStr("initiatorProcessSn"));
            flwTaskInfoResult.setTitle(variableJsonObject.getStr("initiatorProcessTitle"));
            flwTaskInfoResult.setAbstractTitle(variableJsonObject.getStr("initiatorProcessAbstract"));
            String processDefinitionId = task.getProcessDefinitionId();
            Object o = commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId);
            if (ObjectUtil.isNotEmpty(o)) {
                flwTaskInfoResult.setProcessDefinitionName(String.valueOf(o));
            } else {
                ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
                if (ObjectUtil.isNotEmpty(processDefinition)) {
                    commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_KEY_PREFIX + processDefinitionId, processDefinition.getName());
                    flwTaskInfoResult.setProcessDefinitionName(processDefinition.getName());
                }
            }
            flwTaskInfoResult.setProcessDefinitionId(processDefinitionId);
            flwTaskInfoResult.setCurrentActivityId(task.getTaskDefinitionKey());
            flwTaskInfoResult.setCurrentActivityName(task.getName());
            String taskAssignee = task.getAssignee();
            if (ObjectUtil.isNotEmpty(taskAssignee)) {
                JSONObject taskAssigneeUser = sysUserApi.getUserByIdWithoutException(taskAssignee);
                if (ObjectUtil.isNotEmpty(taskAssigneeUser)) {
                    flwTaskInfoResult.setAssignees(taskAssigneeUser.getStr("name"));
                }
            } else {
                String assignee = variableJsonObject.getStr("assignee" + StrUtil.UNDERLINE + task.getTaskDefinitionKey());
                if (ObjectUtil.isNotEmpty(assignee)) {
                    List<String> userIdList = JSONUtil.toList(assignee, String.class);
                    flwTaskInfoResult.setAssignees(StrUtil.join("、", sysUserApi.getUserListByIdListWithoutException(userIdList).stream()
                            .map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList())));
                }
            }
            flwTaskInfoResult.setInitiator(variableJsonObject.getStr("initiator"));
            flwTaskInfoResult.setInitiatorName(variableJsonObject.getStr("initiatorName"));
            flwTaskInfoResult.setInitiatorOrgId(variableJsonObject.getStr("initiatorOrgId"));
            flwTaskInfoResult.setInitiatorOrgName(variableJsonObject.getStr("initiatorOrgName"));
            flwTaskInfoResult.setInitiatorPositionId(variableJsonObject.getStr("initiatorPositionId"));
            flwTaskInfoResult.setInitiatorPositionName(variableJsonObject.getStr("initiatorPositionName"));
            flwTaskInfoResult.setInitiatorTime(variableJsonObject.getStr("initiatorTime"));
            flwTaskInfoResult.setTenantId(task.getTenantId());
            return flwTaskInfoResult;
        }else {
            List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(StpUtil.getLoginIdAsString()).processInstanceId(flwProcessInstanceIdParam.getProcessInstanceId())
                    .orderByHistoricTaskInstanceEndTime().desc().list();
            if(CollUtil.isNotEmpty(list)){
                FlwDoneTaskResult doneTaskResult = getDoneTaskResult(list.get(0));
                flwTaskInfoResult = BeanUtil.copyProperties(doneTaskResult, FlwTaskInfoResult.class);
            }
        }
        //return new FlwTodoTaskResult();
        return flwTaskInfoResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void urge(FlwTaskUrgeParam flwTaskUrgeParam) {
        Task task = taskService.createTaskQuery().processInstanceId(flwTaskUrgeParam.getId()).active().singleResult();
        String taskId = task.getId();
        if (ObjectUtil.isEmpty(task)) {
            throw new CommonException("任务不存在，id值为：{}", taskId);
        }
        String nowDate = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
        Object object = commonCacheOperator.get(FlwCacheConstant.FLW_URGE_KEY + taskId);
        if(ObjectUtil.isNotEmpty(object)){
            String date = String.valueOf(object);
            //时间处理
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
            LocalDateTime ldt = LocalDateTime.parse(date, formatter);
            Date urgeDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            long hours = DateUtil.between(new Date(),urgeDate, DateUnit.HOUR);
            if(hours < Integer.parseInt(FlwCacheConstant.FLW_URGE_HOURS)) return;
            NodeRuntimeUtil.handleUserTaskUrgeNotice((DelegateTask) task);
        }else{
            //处理催办流程通知
            NodeRuntimeUtil.handleUserTaskUrgeNotice((DelegateTask) task);
        }
        commonCacheOperator.put(FlwCacheConstant.FLW_URGE_KEY + taskId,nowDate);//写入缓存
    }

    @Override
    public String getFlwDiagram(FlwProcessInstanceIdParam flwProcessInstanceIdParam){
        // 生成流程图图片
        String processDefinitionId = runtimeService.createProcessInstanceQuery()
                .processInstanceId(flwProcessInstanceIdParam.getProcessInstanceId())
                .singleResult().getProcessDefinitionId();
        // 获取 BPMN 模型实例
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        // 创建字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 将 BPMN 模型实例渲染为图片并写入字节数组输出流
        Bpmn.writeModelToStream(outputStream, bpmnModelInstance);

        // 将字节数组输出流转换为字节数组
        byte[] imageBytes = outputStream.toByteArray();

        // 将字节数组转换为 Base64 字符串
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}
