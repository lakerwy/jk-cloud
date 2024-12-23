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
package vip.xiaonuo.flw.modular.process.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.*;
import org.camunda.bpm.engine.migration.MigrationInstructionBuilder;
import org.camunda.bpm.engine.migration.MigrationPlanBuilder;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceModificationBuilder;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.MultiInstanceLoopCharacteristics;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.pojo.SaBaseLoginUser;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.consts.RabbitConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.common.prop.CommonProperties;
import vip.xiaonuo.common.util.CommonTimeFormatUtil;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.core.enums.NodeApproveOperateTypeEnum;
import vip.xiaonuo.flw.core.node.FlwNode;
import vip.xiaonuo.flw.core.listener.FlwGlobalCustomEventCenter;
import vip.xiaonuo.flw.core.util.NodeInfoUtil;
import vip.xiaonuo.flw.core.util.NodeRuntimeUtil;
import vip.xiaonuo.flw.modular.model.entity.FlwModel;
import vip.xiaonuo.flw.modular.model.enums.FlwModelFormTypeEnum;
import vip.xiaonuo.flw.modular.model.enums.FlwModelStatusEnum;
import vip.xiaonuo.flw.modular.model.service.FlwModelService;
import vip.xiaonuo.flw.modular.process.enums.BoardTypeEnum;
import vip.xiaonuo.flw.modular.process.mapper.FlwCommonMapper;
import vip.xiaonuo.flw.modular.process.param.*;
import vip.xiaonuo.flw.modular.process.result.*;
import vip.xiaonuo.flw.modular.process.service.FlwProcessService;
import vip.xiaonuo.flw.modular.process.vo.*;
import vip.xiaonuo.flw.modular.relation.entity.FlwRelation;
import vip.xiaonuo.flw.modular.relation.enums.FlwRelationCategoryEnum;
import vip.xiaonuo.flw.modular.relation.service.FlwRelationService;
import vip.xiaonuo.flw.modular.relationtable.entity.FormRelationTable;
import vip.xiaonuo.flw.modular.relationtable.service.FormRelationTableService;
import vip.xiaonuo.flw.modular.system.entity.ExtSystem;
import vip.xiaonuo.flw.modular.system.service.ExtSystemService;
import vip.xiaonuo.flw.modular.task.param.FlwTaskTurnParam;
import vip.xiaonuo.flw.modular.task.result.FlwTaskAttachmentResult;
import vip.xiaonuo.flw.modular.task.result.FlwTaskDetailResult;
import vip.xiaonuo.flw.modular.task.service.FlwTaskService;
import vip.xiaonuo.flw.modular.template.service.FlwTemplateSnService;
import vip.xiaonuo.sys.api.SysOrgApi;
import vip.xiaonuo.sys.api.SysPositionApi;
import vip.xiaonuo.sys.api.SysUserApi;
import vip.xiaonuo.ten.api.TenApi;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程Service接口实现类
 *
 * @author xuyuxiang
 * @date 2022/5/11 15:52
 **/
@Service
public class FlwProcessServiceImpl implements FlwProcessService {

    private static final Logger log = LoggerFactory.getLogger(FlwProcessServiceImpl.class);
    @Resource
    private SysUserApi sysUserApi;

    @Resource
    private SysOrgApi sysOrgApi;

    @Resource
    private SysPositionApi sysPositionApi;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private FlwModelService flwModelService;

    @Resource
    private FlwTaskService flwTaskService;

    @Resource
    private FlwRelationService flwRelationService;

    @Resource
    private FormRelationTableService formRelationTableService;

    @Resource
    private FlwTemplateSnService flwTemplateSnService;

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private CommonProperties commonProperties;

    @Resource
    FlwCommonMapper flwCommonMapper;

    @Resource
    private ExtSystemService extSystemService;

    @Resource
    private TenApi tenApi;

    @Override
    public void saveDraft(FlwProcessSaveDraftParam flwProcessSaveDraftParam) {
        FlwRelation flwRelation = new FlwRelation();
        String id = flwProcessSaveDraftParam.getId();
        if(ObjectUtil.isNotEmpty(id)) {
            flwRelation = flwRelationService.getById(id);
            if(ObjectUtil.isEmpty(flwRelation)) {
                throw new CommonException("草稿不存在，id值为：{}", id);
            }
        }
        FlwModel flwModel = flwModelService.queryEntity(flwProcessSaveDraftParam.getModelId());
        flwRelation.setObjectId(StpUtil.getLoginIdAsString());
        flwRelation.setTargetId(flwModel.getId());
        if(ObjectUtil.isEmpty(id)) {
            flwRelation.setExtJson(JSONUtil.toJsonStr(JSONUtil.createObj().set("createTime", DateUtil.now())
                    .set("title", flwModel.getName())
                    .set("dataJson", flwProcessSaveDraftParam.getDataJson())));
        } else {
            flwRelation.setExtJson(JSONUtil.toJsonStr(JSONUtil.parseObj(flwRelation.getExtJson()).set("dataJson",
                    flwProcessSaveDraftParam.getDataJson())));
        }
        flwRelation.setCategory(FlwRelationCategoryEnum.FLW_USER_TO_DRAFT.getValue());
        flwRelationService.saveOrUpdate(flwRelation);
    }

//    @Transactional(rollbackFor = Exception.class)
    @Override
    @DSTransactional
    public String start(FlwProcessStartParam flwProcessStartParam) {
        try {
            NodeRuntimeUtil.handleTenAuth();
            String modelId = flwProcessStartParam.getModelId();
            FlwModel flwModel = flwModelService.queryEntity(modelId);
            if (flwModel.getModelStatus().equals(FlwModelStatusEnum.DISABLED.getValue())) {
                throw new CommonException("模型未启用，名称为：{}", flwModel.getName());
            }
            JSONObject jsonObject = JSONUtil.createObj();
            SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
            jsonObject.set("initiator", ObjectUtil.isEmpty(flwProcessStartParam.getInitiator()) ?
                    loginUser.getId() : flwProcessStartParam.getInitiator());
            jsonObject.set("initiatorName", ObjectUtil.isEmpty(flwProcessStartParam.getInitiatorName()) ?
                    loginUser.getName() : flwProcessStartParam.getInitiatorName());
            jsonObject.set("initiatorOrgId", ObjectUtil.isEmpty(flwProcessStartParam.getInitiatorOrgId()) ?
                    loginUser.getOrgId() : flwProcessStartParam.getInitiatorOrgId());
            jsonObject.set("initiatorOrgName", ObjectUtil.isEmpty(flwProcessStartParam.getInitiatorOrgName()) ?
                    loginUser.getOrgName() : flwProcessStartParam.getInitiatorOrgName());
            jsonObject.set("initiatorPositionId", ObjectUtil.isEmpty(flwProcessStartParam.getInitiatorPositionId()) ?
                    loginUser.getPositionId() : flwProcessStartParam.getInitiatorPositionId());
            jsonObject.set("initiatorPositionName", ObjectUtil.isEmpty(flwProcessStartParam.getInitiatorPositionName()) ?
                    loginUser.getPositionName() : flwProcessStartParam.getInitiatorPositionName());
            jsonObject.set("initiatorTime", DateUtil.now());
            jsonObject.set("initiatorModelInfo", JSONUtil.createObj().set("initiatorModelId", flwModel.getId())
                    .set("initiatorModelJson", flwModel.getProcessJson()).set("initiatorFormJson", flwModel.getFormJson())
                    .set("initiatorFormType", flwModel.getFormType()));
            String initiatorSelectData = flwProcessStartParam.getInitiatorSelectData();
            if(ObjectUtil.isNotEmpty(initiatorSelectData)){
                jsonObject.set("initiatorSelectData", initiatorSelectData);
            }
            ProcessInstance processInstance = null;
            //FlwModel flwModel = flwModelService.getById(modelId);
            String sn = getSn(flwModel);
            String parentTablePrimaryId = IdWorker.getIdStr();
            if(flwModel.getFormType().equals(FlwModelFormTypeEnum.DESIGN.getValue())) {
                if(ObjectUtil.isEmpty(flwModel.getTableJson())) {
                    throw new CommonException("模型错误，设计表单类型的模型数据库表JSON不能为空");
                }
                if(ObjectUtil.isNotEmpty(flwProcessStartParam.getDataJson())) {
                    //String parentTablePrimaryId = IdWorker.getIdStr();
                    JSONObject resultDataObj = NodeRuntimeUtil.handleInitiatorDataJson(flwModel.getTableJson(),
                            flwProcessStartParam.getDataJson(), parentTablePrimaryId);
                    String initiatorSql = resultDataObj.getStr("sql");
                    String initiatorDataJson = resultDataObj.getStr("data");
                    jsonObject.set("initiatorSql", initiatorSql);
                    jsonObject.set("initiatorTableJson", flwModel.getTableJson());
                    jsonObject.set("initiatorDataJson", JSONUtil.parse(initiatorDataJson).toString());
                    //设置sn
                    jsonObject.set("initiatorProcessSn",sn);
                    runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), parentTablePrimaryId, jsonObject);
                    NodeRuntimeUtil.handleSqlExecution(initiatorSql);
                } else {
                    processInstance = runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), jsonObject);
                }
            } else {
                if(ObjectUtil.isNotEmpty(flwProcessStartParam.getDataJson())) {
                    if(!JSONUtil.isTypeJSONObject(flwProcessStartParam.getDataJson())) {
                        throw new CommonException("填写的数据请使用JSON格式");
                    }
                    String dataJson = flwProcessStartParam.getDataJson();
                    JSONObject entries = JSONUtil.parseObj(dataJson);
                    entries.set("businessKey",parentTablePrimaryId);
                    entries.set("id", parentTablePrimaryId);
//                if(!modelId.equals("1674588959370481665")) {
//                    entries.set("id", parentTablePrimaryId);
//                }
                    entries.set("sn", sn);
                    entries.set("applyUserId",loginUser.getId());
                    entries.set("applyUser",loginUser.getName());
                    entries.set("applyTime",DateUtil.now());
                    flwProcessStartParam.setDataJson(JSONUtil.toJsonStr(entries));
                    jsonObject.set("initiatorDataJson", JSONUtil.toJsonStr(entries));
                    //设置sn
                    jsonObject.set("initiatorProcessSn",sn);
                }
                processInstance = runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), parentTablePrimaryId ,jsonObject);
            }
            if(ObjectUtil.isNotEmpty(processInstance)){
                String processInstanceId = processInstance.getProcessInstanceId();
                //将id 和sn 存入缓存中，保证每个模块的业务都可以获取到，获取完成之后清空缓存，避免占用内存
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_ID_KEY+processInstanceId,parentTablePrimaryId);
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId,sn);
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY+processInstanceId,modelId);
                return processInstanceId;
            }
        }catch (Exception e){
            throw new CommonException("流程启动失败",e);
        }
        return null;
    }

    @Override
    public Page<FlwProcessMyDraftResult> myDraftPage(FlwProcessMyDraftPageParam flwProcessMyDraftPageParam) {
        QueryWrapper<FlwRelation> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(flwProcessMyDraftPageParam.getName())) {
            queryWrapper.like("EXT_JSON", JSONUtil.toJsonStr(JSONUtil.createObj().set("title", flwProcessMyDraftPageParam.getName())));
        }
        queryWrapper.eq("CATEGORY", FlwRelationCategoryEnum.FLW_USER_TO_DRAFT.getValue())
                .eq("OBJECT_ID", StpLoginUserUtil.getLoginUser().getId());

        if (StrUtil.isNotEmpty(flwProcessMyDraftPageParam.getModelId())) {
            queryWrapper.eq("TARGET_ID", flwProcessMyDraftPageParam.getModelId());
        }
        // 转换为 LambdaQueryWrapper
        LambdaQueryWrapper<FlwRelation> lambdaQueryWrapper = queryWrapper.lambda();
        Page<FlwRelation> page = flwRelationService.pageList(lambdaQueryWrapper);
        return new Page<FlwProcessMyDraftResult>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(page.getRecords().stream().map(flwRelation -> {
            String extJson = flwRelation.getExtJson();
            JSONObject jsonObject = JSONUtil.parseObj(extJson);
            String title = jsonObject.getStr("title");
            String createTime = jsonObject.getStr("createTime");
            FlwProcessMyDraftResult flwProcessMyDraftResult = new FlwProcessMyDraftResult();
            flwProcessMyDraftResult.setId(flwRelation.getId());
            flwProcessMyDraftResult.setModelId(flwRelation.getTargetId());
            flwProcessMyDraftResult.setDataJson(extJson);
            flwProcessMyDraftResult.setTitle(title);
            flwProcessMyDraftResult.setCreateTime(createTime);
            return flwProcessMyDraftResult;
        }).collect(Collectors.toList()));
    }

    @Override
    public FlwProcessDraftDetailResult draftDetail(FlwProcessDraftIdParam flwProcessDraftIdParam) {
        String id = flwProcessDraftIdParam.getId();
        FlwRelation flwRelation = flwRelationService.getById(id);
        if(ObjectUtil.isEmpty(flwRelation)) {
            throw new CommonException("草稿不存在，id值为：{}", id);
        }
        FlwModel flwModel = flwModelService.queryEntity(flwRelation.getTargetId());
        FlwProcessDraftDetailResult flwProcessDraftDetailResult = new FlwProcessDraftDetailResult();
        flwProcessDraftDetailResult.setId(id);
        flwProcessDraftDetailResult.setModelId(flwModel.getId());
        flwProcessDraftDetailResult.setFormType(flwModel.getFormType());
        flwProcessDraftDetailResult.setProcessJson(flwModel.getProcessJson());
        flwProcessDraftDetailResult.setFormJson(flwModel.getFormJson());
        flwProcessDraftDetailResult.setDataJson(JSONUtil.parseObj(flwRelation.getExtJson()).getStr("dataJson"));
        return flwProcessDraftDetailResult;
    }

    @Override
    public void deleteDraft(List<FlwProcessDraftIdParam> flwProcessDraftIdParamList) {
        List<String> draftIdList = flwProcessDraftIdParamList.stream().map(FlwProcessDraftIdParam::getId).collect(Collectors.toList());
        if(ObjectUtil.isNotEmpty(draftIdList)) {
            flwRelationService.removeBatchByIds(draftIdList);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<FlwProcessResult> monitorPage(FlwProcessMonitorPageParam flwProcessMonitorPageParam) {
        NodeRuntimeUtil.handleTenAuth();
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().desc();
        if(ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getInitiator())) {
            historicProcessInstanceQuery.startedBy(flwProcessMonitorPageParam.getInitiator());
        }
        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getSearchKey())) {
            historicProcessInstanceQuery.variableValueLike("initiatorName", "%" + flwProcessMonitorPageParam.getSearchKey() + "%");
        }
        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getSn())) {
            historicProcessInstanceQuery.variableValueLike("initiatorProcessSn", "%" + flwProcessMonitorPageParam.getSn() + "%");
        }

        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getInitiatorProcessAbstract())) {
            historicProcessInstanceQuery.variableValueLike("initiatorProcessAbstract", "%" + flwProcessMonitorPageParam.getInitiatorProcessAbstract() + "%");
        }

        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getInitiatorProcessTitle())) {
            historicProcessInstanceQuery.variableValueLike("initiatorProcessTitle", "%" + flwProcessMonitorPageParam.getInitiatorProcessTitle() + "%");
        }

        //如果增减了状态，那就强行拼接一个时间限制，获取最近6个月，优化查询效率
        if (!flwProcessMonitorPageParam.isCloseDate() || StrUtil.isNotEmpty(flwProcessMonitorPageParam.getStateCode())) {
            Date startDate = ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getStartOptDate()) ?
                    DateUtil.parseDate(flwProcessMonitorPageParam.getStartOptDate()) :
                    DateUtil.date(DateUtil.offsetMonth(DateUtil.date(System.currentTimeMillis()), -3).getTime());

            if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getEndOptDate())) {
                historicProcessInstanceQuery.startedAfter(startDate)
                        .startedBefore(DateUtil.parseDate(flwProcessMonitorPageParam.getEndOptDate()));
            } else {
                historicProcessInstanceQuery.startedAfter(startDate);
            }
        }

//        historicProcessInstanceQuery.or().processDefinitionNameLike("%" + flwProcessMonitorPageParam.getName()+ "%");
        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getName())) {
            List<String> keys  = getNameList(flwProcessMonitorPageParam.getName());
            if(CollUtil.isNotEmpty(keys)){
                historicProcessInstanceQuery.processDefinitionKeyIn(keys.toArray(new String[0]));
            }else{
                historicProcessInstanceQuery.processDefinitionKeyIn(new String[]{"-1"});
            }
        }
        if (ObjectUtil.isNotEmpty(flwProcessMonitorPageParam.getProcessInstanceIdList())) {
            historicProcessInstanceQuery.processInstanceIds(CollectionUtil.newHashSet(flwProcessMonitorPageParam.getProcessInstanceIdList()));
        }
        if(ObjectUtil.isAllNotEmpty(flwProcessMonitorPageParam.getVariableValueKey(),flwProcessMonitorPageParam.getVariableValue())){
            historicProcessInstanceQuery.variableValueEquals(flwProcessMonitorPageParam.getVariableValueKey(),flwProcessMonitorPageParam.getVariableValue());
        }
        SaBaseLoginUser loginUser = StpLoginUserUtil.getLoginUser();
        //合作伙伴的只能看自己部门的
        if(!flwProcessMonitorPageParam.getCopyFlag() && ("PARTNER").equals(loginUser.getCategory())){
            historicProcessInstanceQuery.variableValueEquals("initiatorOrgId",loginUser.getOrgId());
        }else{
            //查看权限【数据权限过滤开启】
            if(flwProcessMonitorPageParam.getIsDataScope()) {
                List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
                if (ObjectUtil.isNotEmpty(loginUserDataScope)) {
                    historicProcessInstanceQuery = historicProcessInstanceQuery.or();
                    for (String orgId : loginUserDataScope) {
                        historicProcessInstanceQuery = historicProcessInstanceQuery.variableValueEquals("initiatorOrgId", orgId);
                    }
                    historicProcessInstanceQuery = historicProcessInstanceQuery.endOr();
                } else {
                    historicProcessInstanceQuery.variableValueEquals("initiator", loginUser.getOrgId());
                }
            }
        }


        Page<FlwProcessResult> defaultPage = CommonPageRequest.defaultPage();
        getMyFlwProcessResults2(defaultPage,historicProcessInstanceQuery,flwProcessMonitorPageParam.getStateCode(),flwProcessMonitorPageParam.getAssignees());
        return defaultPage;

//        long current = defaultPage.getCurrent();
//        long size = defaultPage.getSize();
//        int fromIndex = Convert.toInt((current - 1) * size);
//        int toIndex = 0;
//        if(StrUtil.isNotEmpty(flwProcessMonitorPageParam.getStateCode())){
//            List<HistoricProcessInstance> flwProcessResultList = getMyFlwProcessResults(historicProcessInstanceQuery,flwProcessMonitorPageParam.getStateCode());
//            defaultPage.setTotal(flwProcessResultList.size());
//            toIndex = Math.min(fromIndex + Convert.toInt(size), Convert.toInt(flwProcessResultList.size()));
//            List<HistoricProcessInstance> historicProcessInstances = flwProcessResultList.subList(fromIndex, toIndex);
//            return defaultPage.setRecords(getCurrentPageData(historicProcessInstances));
//        }else {
//            List<HistoricProcessInstance> flwProcessResultList = historicProcessInstanceQuery
//                    .listPage(fromIndex, Convert.toInt(size)).stream().collect(Collectors.toList());
//            defaultPage.setTotal(historicProcessInstanceQuery.count());
//            defaultPage.setRecords(getCurrentPageData(flwProcessResultList));
//            return defaultPage;
//        }
    }

    @NotNull
    private List<HistoricProcessInstance> getMyFlwProcessResults(HistoricProcessInstanceQuery historicProcessInstanceQuery, String stateCode) {
        String deleteReasonCode;
        if(StrUtil.isNotEmpty(stateCode) && (stateCode.equals("END") || stateCode.equals("REVOKE") || stateCode.equals("REJECT"))){
            deleteReasonCode = stateCode;
            stateCode = "EXTERNALLY_TERMINATED";
        } else {
            deleteReasonCode = null;
        }
        String finalStateCode = stateCode;
        return historicProcessInstanceQuery
                .list().stream()
                .filter(historicProcessInstance -> historicProcessInstance.getState().equals(finalStateCode))
                .filter(historicProcessInstance -> {
                    if (StrUtil.isNotEmpty(deleteReasonCode)) {
                        return historicProcessInstance.getDeleteReason().equals(deleteReasonCode);
                    }
                    return true;
                }).collect(Collectors.toList());
    }

    /**
     * 查询结果集（new）
     * @param historicProcessInstanceQuery 流程查询条件
     * @param stateCode 流程状态
     * @param stateCode 当前办理人
     * @return
     */
    private void getMyFlwProcessResults2(Page<FlwProcessResult> defaultPage, HistoricProcessInstanceQuery historicProcessInstanceQuery, String stateCode, String assignees) {
        //页面参数-分页
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        int fromIndex = Convert.toInt((current - 1) * size);
        int toIndex = 0;

        //初步查询
        List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
        if(ObjectUtil.isNotEmpty(assignees)){
            if(ObjectUtil.isNotEmpty(stateCode)){
                historicProcessInstanceList = getMyFlwProcessResults(historicProcessInstanceQuery,stateCode);
            }else{
                historicProcessInstanceList = historicProcessInstanceQuery.list();
            }
            List<FlwProcessResult> flwProcessResultPage = new ArrayList<>();
            defaultPage.setTotal(0);
            if(CollUtil.isNotEmpty(historicProcessInstanceList)){
                List<FlwProcessResult> flwProcessResultList = getFlwProcessResultTempData(historicProcessInstanceList).stream().filter(x->x.getAssignees().contains(assignees)).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(flwProcessResultList)){
                    defaultPage.setTotal(flwProcessResultList.size());
                    toIndex = Math.min(fromIndex + Convert.toInt(size), Convert.toInt(flwProcessResultList.size()));
                    List<FlwProcessResult> tempPageResult = flwProcessResultList.subList(fromIndex,toIndex);
                    if(CollUtil.isNotEmpty(tempPageResult)) {
                        List<String> processInstanceIds = tempPageResult.stream().map(x->x.getId()).collect(Collectors.toList());
                        List<HistoricProcessInstance> temp = historicProcessInstanceList.stream().filter(x->processInstanceIds.contains(x.getId())).collect(Collectors.toList());
                        flwProcessResultPage = getCurrentPageData(temp);
                    }
                }
            }
            defaultPage.setRecords(flwProcessResultPage);
        }else{
            if(ObjectUtil.isNotEmpty(stateCode)) {
                historicProcessInstanceList = getMyFlwProcessResults(historicProcessInstanceQuery,stateCode);
                defaultPage.setTotal(historicProcessInstanceList.size());
                toIndex = Math.min(fromIndex + Convert.toInt(size), Convert.toInt(historicProcessInstanceList.size()));
                List<HistoricProcessInstance> historicProcessInstances = historicProcessInstanceList.subList(fromIndex, toIndex);
                defaultPage.setRecords(getCurrentPageData(historicProcessInstances));
            }else{
                historicProcessInstanceList = historicProcessInstanceQuery
                        .listPage(fromIndex, Convert.toInt(size)).stream().collect(Collectors.toList());
                defaultPage.setTotal(historicProcessInstanceQuery.count());
                defaultPage.setRecords(getCurrentPageData(historicProcessInstanceList));
            }
        }
    }


    public List<FlwProcessResult> getCurrentPageData(List<HistoricProcessInstance> historicProcessInstanceList){
        List<String> processInstanceIds = historicProcessInstanceList.stream().map(x->x.getId()).collect(Collectors.toList());
        if(CollUtil.isEmpty(processInstanceIds)) return null;
        List<HistoricVariableInstance> historicVariableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceIdIn(processInstanceIds.toArray(new String[0])).list();
        List<FlwProcessResult> results = new ArrayList<>();
        historicProcessInstanceList.forEach(historicProcessInstance -> {
            FlwProcessResult flwProcessResult = new FlwProcessResult();
            flwProcessResult.setId(historicProcessInstance.getId());
            List<HistoricVariableInstance> variableInstanceList = historicVariableInstanceList.stream().filter(x->x.getProcessInstanceId().equals(historicProcessInstance.getId())).collect(Collectors.toList());
            flwProcessResult.setSn(getVariableValue("initiatorProcessSn",variableInstanceList));
            flwProcessResult.setTitle(getVariableValue("initiatorProcessTitle",variableInstanceList));
            flwProcessResult.setAbstractTitle(getVariableValue("initiatorProcessAbstract",variableInstanceList));
            flwProcessResult.setBusinessKey(historicProcessInstance.getBusinessKey());
            String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            flwProcessResult.setProcessDefinitionId(processDefinitionId);
            flwProcessResult.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            flwProcessResult.setProcessDefinitionVersion(StrUtil.format("V{}.0", historicProcessInstance.getProcessDefinitionVersion()));
            flwProcessResult.setStartTime(DateUtil.formatDateTime(historicProcessInstance.getStartTime()));
            flwProcessResult.setEndTime(ObjectUtil.isNotEmpty(historicProcessInstance.getEndTime()) ?
                    DateUtil.formatDateTime(historicProcessInstance.getEndTime()) : "-");
            if (ObjectUtil.isNotEmpty(historicProcessInstance.getDurationInMillis())) {
                flwProcessResult.setDurationInfo(CommonTimeFormatUtil
                        .formatSeconds(historicProcessInstance.getDurationInMillis() / 1000));
            } else {
                flwProcessResult.setDurationInfo("-");
            }
            flwProcessResult.setInitiator(getVariableValue("initiator",variableInstanceList));
            flwProcessResult.setInitiatorName(getVariableValue("initiatorName",variableInstanceList));
            flwProcessResult.setInitiatorOrgId(getVariableValue("initiatorOrgId",variableInstanceList));
            flwProcessResult.setInitiatorOrgName(getVariableValue("initiatorOrgName",variableInstanceList));
            flwProcessResult.setInitiatorPositionId(getVariableValue("initiatorPositionId",variableInstanceList));
            flwProcessResult.setInitiatorPositionName(getVariableValue("initiatorPositionName",variableInstanceList));
            String state = historicProcessInstance.getState();
            switch (state) {
                case "ACTIVE":
                    flwProcessResult.setStateCode(state);
                    flwProcessResult.setStateText("审批中");
                    break;
                case "SUSPENDED":
                    flwProcessResult.setStateCode(state);
                    flwProcessResult.setStateText("已挂起");
                    break;
                case "COMPLETED":
                    flwProcessResult.setStateCode(state);
                    flwProcessResult.setStateText("已完成");
                    break;
                case "EXTERNALLY_TERMINATED":
                    String deleteReason = historicProcessInstance.getDeleteReason();
                    switch (deleteReason) {
                        case "END":
                            flwProcessResult.setStateCode(deleteReason);
                            flwProcessResult.setStateText("已终止");
                            break;
                        case "REVOKE":
                            flwProcessResult.setStateCode(deleteReason);
                            flwProcessResult.setStateText("已撤回");
                            break;
                        case "REJECT":
                            flwProcessResult.setStateCode(deleteReason);
                            flwProcessResult.setStateText("已拒绝");
                            break;
                        default:
                            throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
                    }
                    break;
                case "INTERNALLY_TERMINATED":
                    flwProcessResult.setStateCode(state);
                    flwProcessResult.setStateText("内部终止");
                    break;
                default:
                    throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
            }
            if(state.equals("ACTIVE")) {
                Set<String> currentActivityNames = CollectionUtil.newHashSet();
                List<JSONObject> currentActivityNameJsonObjectList = CollectionUtil.newArrayList();
                taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).list().forEach(task -> {
                    String assignee = task.getAssignee();
                    if(ObjectUtil.isNotEmpty(assignee)) {
                        currentActivityNameJsonObjectList.add(JSONUtil.createObj().set("taskName", task.getName()).set("assignee", assignee));
                    }
                });
                final List<String>[] assigneeNameList = new List[]{CollectionUtil.newArrayList()};
                currentActivityNameJsonObjectList.stream().collect(Collectors.groupingBy(jsonObject -> jsonObject.getStr("taskName"))).forEach((key, value) -> {
                    List<String> assigneeList = value.stream().map(jsonObject -> jsonObject.getStr("assignee")).collect(Collectors.toList());
                    assigneeNameList[0] = sysUserApi.getUserListByIdListWithoutException(CollectionUtil.reverse(assigneeList)).stream()
                            .map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList());
                    currentActivityNames.add(key + StrUtil.BRACKET_START + StrUtil.join("、", assigneeNameList[0]) + StrUtil.BRACKET_END);
                });
                flwProcessResult.setCurrentActivityNames(StrUtil.join("、", CollectionUtil.newArrayList(currentActivityNames)));
                flwProcessResult.setAssignees(StrUtil.join("、", assigneeNameList[0]));
                getNextAssigneeName(historicProcessInstance.getId(),flwProcessResult);
            } else {
                flwProcessResult.setCurrentActivityNames("-");
                flwProcessResult.setAssignees("-");
                flwProcessResult.setNextActivityNames("-");
                flwProcessResult.setNextAssignees("-");
            }
            results.add(flwProcessResult);
        });
        return results;
    }

    private void getNextAssigneeName(String processInstanceId, FlwProcessResult flwProcessResult){
        flwProcessResult.setNextActivityNames("-");
        flwProcessResult.setNextAssignees("-");
        // 根据流程实例Id获取当前活动任务
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if(CollUtil.isEmpty(tasks)){
            return;
        }
        String modelIdVariable = this.getProcessModelIdVariable(processInstanceId);
        FlwModel flwModel = flwModelService.getById(modelIdVariable);
        if(ObjectUtil.isNull(flwModel)){
            return;
        }
//        ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        Task activeTask = tasks.get(tasks.size()-1);
        // 获取后续流程节点
        FlwNodeResult nextFlwNodeResult = NodeInfoUtil.getAllNextFlwNodes(flwModel.getProcessJson(),activeTask.getTaskDefinitionKey());
        if(ObjectUtil.isNotEmpty(nextFlwNodeResult) && CollUtil.isNotEmpty(nextFlwNodeResult.getNextFlwNodeList())){
            // 获取后续第一个流程节点
            FlwNodeResult.FlwNodeVo nextFlwNodeVo = nextFlwNodeResult.getNextFlwNodeList().get(0);
            String participateInfo = JSON.toJSONString(nextFlwNodeResult.getNextFlwNodeList().get(0).getParticipateInfo());
            List<String> participateInfoList = NodeRuntimeUtil.handleUserTaskParticipateInfo(processInstanceId,participateInfo,activeTask.getId());
            if(CollUtil.isNotEmpty(participateInfoList)) {
                List<JSONObject> nextAssigneeJson = sysUserApi.getUserListByIdListWithoutException(participateInfoList);
                List<String> nextAssigneeList = nextAssigneeJson.stream().map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList());
                flwProcessResult.setNextAssignees(String.join("、", nextAssigneeList));
                flwProcessResult.setNextActivityNames(nextFlwNodeVo.getTitle() + StrUtil.BRACKET_START + String.join("、", nextAssigneeList) + StrUtil.BRACKET_END);
            }
        }
    }

    /**
     * 针对 当前办理人 数据获取
     * @param historicProcessInstanceList
     * @return
     */
    public List<FlwProcessResult> getFlwProcessResultTempData(List<HistoricProcessInstance> historicProcessInstanceList){
        List<FlwProcessResult> results = new ArrayList<>();
        if(CollUtil.isEmpty(historicProcessInstanceList)) return results;
        historicProcessInstanceList.forEach(historicProcessInstance -> {
            FlwProcessResult flwProcessResult = new FlwProcessResult();
            flwProcessResult.setId(historicProcessInstance.getId());
            String state = historicProcessInstance.getState();
            if(state.equals("ACTIVE")) {
                Set<String> currentActivityNames = CollectionUtil.newHashSet();
                List<JSONObject> currentActivityNameJsonObjectList = CollectionUtil.newArrayList();
                taskService.createTaskQuery().processInstanceId(historicProcessInstance.getId()).list().forEach(task -> {
                    String assignee = task.getAssignee();
                    if(ObjectUtil.isNotEmpty(assignee)) {
                        currentActivityNameJsonObjectList.add(JSONUtil.createObj().set("taskName", task.getName()).set("assignee", assignee));
                    }
                });
                final List<String>[] assigneeNameList = new List[]{CollectionUtil.newArrayList()};
                currentActivityNameJsonObjectList.stream().collect(Collectors.groupingBy(jsonObject -> jsonObject.getStr("taskName"))).forEach((key, value) -> {
                    List<String> assigneeList = value.stream().map(jsonObject -> jsonObject.getStr("assignee")).collect(Collectors.toList());
                    assigneeNameList[0] = sysUserApi.getUserListByIdListWithoutException(CollectionUtil.reverse(assigneeList)).stream()
                            .map(jsonObject -> jsonObject.getStr("name")).collect(Collectors.toList());
                    currentActivityNames.add(key + StrUtil.BRACKET_START + StrUtil.join("、", assigneeNameList[0]) + StrUtil.BRACKET_END);
                });
                flwProcessResult.setCurrentActivityNames(StrUtil.join("、", CollectionUtil.newArrayList(currentActivityNames)));
                flwProcessResult.setAssignees(StrUtil.join("、", assigneeNameList[0]));
            } else {
                flwProcessResult.setCurrentActivityNames("-");
                flwProcessResult.setAssignees("-");
            }
            results.add(flwProcessResult);
        });
        return results;
    }

    public String getVariableValue(String name,List<HistoricVariableInstance> variableInstanceList) {
        if(CollUtil.isEmpty(variableInstanceList)) return "";
        HistoricVariableInstance historicVariableInstance = variableInstanceList.stream().filter(x -> name.equals(x.getName())).findFirst().orElse(null);
        return ObjectUtil.isNotEmpty(historicVariableInstance) && ObjectUtil.isNotEmpty(historicVariableInstance.getValue()) ? historicVariableInstance.getValue().toString() : "";
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FlwProcessIdParam> flwProcessIdParamList) {
        NodeRuntimeUtil.handleTenAuth();
        List<String> processInstanceIdList = flwProcessIdParamList.stream()
                .map(FlwProcessIdParam::getId).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(processInstanceIdList)) {
            // 发布删除事件
            processInstanceIdList.forEach(FlwGlobalCustomEventCenter::publishDeleteEvent);
            processInstanceIdList.forEach(id->{
                FlwGlobalCustomEventCenter.publishDeleteEvent(id);
                commonCacheOperator.removeKeys(RabbitConstant.RABBIT_KEY+id);
            });
            runtimeService.deleteProcessInstancesIfExists(processInstanceIdList, "DELETE",
                    true, true, true);
            historyService.deleteHistoricProcessInstancesIfExists(processInstanceIdList);
            flwRelationService.remove(new LambdaQueryWrapper<FlwRelation>().in(FlwRelation::getObjectId, processInstanceIdList));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void end(List<FlwProcessIdParam> flwProcessIdParamList) {
        NodeRuntimeUtil.handleTenAuth();
        List<String> processInstanceIdList = flwProcessIdParamList.stream()
                .map(FlwProcessIdParam::getId).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(processInstanceIdList)) {
            processInstanceIdList.forEach(processInstanceId -> {
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                if (ObjectUtil.isNull(historicProcessInstance)) {
                    throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
                }
                if(!historicProcessInstance.getState().equals("ACTIVE")) {
                    throw new CommonException("该流程实例不在运行中无法终止，id值为：{}", processInstanceId);
                }
                // 发布终止事件
                FlwGlobalCustomEventCenter.publishCloseEvent(processInstanceId);
                commonCacheOperator.removeKeys(RabbitConstant.RABBIT_KEY+processInstanceId);
            });
            runtimeService.deleteProcessInstancesIfExists(processInstanceIdList, "END",
                    true, true, true);
            processInstanceIdList.forEach(processInstanceId->{
                FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
                //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
                if(ObjectUtil.isNotEmpty(info)){
                    String sql = "update " + info.getTableName() + " set `status` = " + FlwStatusEnum.END_STATUS.getValue() + " where ID = '" + info.getId()+"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                    if(ObjectUtil.isNotEmpty(info.getBizTableName())){
                        sql = "update " + info.getBizTableName() + " set `status` = " + FlwStatusEnum.END_STATUS.getValue() + " where FLW_ID = '" + info.getId() +"'";
                        NodeRuntimeUtil.handleSqlExecution(sql);
                    }
                }
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void revoke(List<FlwProcessIdParam> flwProcessIdParamList) {
        NodeRuntimeUtil.handleTenAuth();
        List<String> processInstanceIdList = flwProcessIdParamList.stream()
                .map(FlwProcessIdParam::getId).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(processInstanceIdList)) {
            processInstanceIdList.forEach(processInstanceId -> {
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                if (ObjectUtil.isNull(historicProcessInstance)) {
                    throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
                }
                if(!historicProcessInstance.getState().equals("ACTIVE")) {
                    throw new CommonException("该流程实例不在运行中无法撤回，id值为：{}", processInstanceId);
                }
                FlwNode.FlwNodeConfigProp flwNodeConfigProp = getProcessNodeConfigProp(processInstanceId);
                Boolean enableRevoke = flwNodeConfigProp.getProcessEnableRevoke();
                if(!enableRevoke) {
                    throw new CommonException("该流程已配置不允许撤回，id值为：{}", processInstanceId);
                }
                // 发布撤回事件
                FlwGlobalCustomEventCenter.publishRevokeEvent(processInstanceId);
                runtimeService.deleteProcessInstanceIfExists(processInstanceId, "REVOKE",
                        true, true, true, true);
                    //获取流程实例关联的业务信息
                FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
                    //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
                if(ObjectUtil.isNotEmpty(info)){
                    String sql = "update " + info.getTableName() + " set `status` = " + FlwStatusEnum.REVOKE_STATUS.getValue() + " where ID = '" + info.getId() +"'";
                    NodeRuntimeUtil.handleSqlExecution(sql);
                    if(ObjectUtil.isNotEmpty(info.getBizTableName())){
                        sql = "update " + info.getBizTableName() + " set `status` = " + FlwStatusEnum.REVOKE_STATUS.getValue() + " where FLW_ID = '" + info.getId() +"'";
                        NodeRuntimeUtil.handleSqlExecution(sql);
                    }
                }
                commonCacheOperator.removeKeys(RabbitConstant.RABBIT_KEY+processInstanceId);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void suspend(List<FlwProcessIdParam> flwProcessIdParamList) {
        NodeRuntimeUtil.handleTenAuth();
        List<String> processInstanceIdList = flwProcessIdParamList.stream()
                .map(FlwProcessIdParam::getId).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(processInstanceIdList)) {
            processInstanceIdList.forEach(processInstanceId -> {
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                if (ObjectUtil.isNull(historicProcessInstance)) {
                    throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
                }
                if(!historicProcessInstance.getState().equals("ACTIVE")) {
                    throw new CommonException("该流程实例不在运行中无法挂起，id值为：{}", processInstanceId);
                }
                runtimeService.suspendProcessInstanceById(processInstanceId);
                commonCacheOperator.removeKeys(RabbitConstant.RABBIT_KEY+processInstanceId);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void active(List<FlwProcessIdParam> flwProcessIdParamList) {
        NodeRuntimeUtil.handleTenAuth();
        List<String> processInstanceIdList = flwProcessIdParamList.stream()
                .map(FlwProcessIdParam::getId).collect(Collectors.toList());
        if (ObjectUtil.isNotEmpty(processInstanceIdList)) {
            processInstanceIdList.forEach(processInstanceId -> {
                HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                if (ObjectUtil.isNull(historicProcessInstance)) {
                    throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
                }
                if(!historicProcessInstance.getState().equals("SUSPENDED")) {
                    throw new CommonException("该流程实例不在挂起状态无法激活，id值为：{}", processInstanceId);
                }
                runtimeService.activateProcessInstanceById(processInstanceId);
            });
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void turn(FlwProcessTurnParam flwProcessTurnParam) {
        NodeRuntimeUtil.handleTenAuth();
        String processInstanceId = flwProcessTurnParam.getId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (ObjectUtil.isNull(historicProcessInstance)) {
            throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
        }
        if(!historicProcessInstance.getState().equals("ACTIVE")) {
            throw new CommonException("该流程实例不在运行中无法转办，id值为：{}", processInstanceId);
        }
        taskService.createTaskQuery().processInstanceId(processInstanceId).list().stream().map(Task::getId)
                .collect(Collectors.toList()).forEach(taskId -> {
            FlwTaskTurnParam flwTaskTurnParam = new FlwTaskTurnParam();
            flwTaskTurnParam.setId(taskId);
            flwTaskTurnParam.setUserId(flwProcessTurnParam.getUserId());
            flwTaskTurnParam.setRecordComment(false);
            flwTaskService.turn(flwTaskTurnParam, false);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void jump(FlwProcessJumpParam flwProcessJumpParam) {
        NodeRuntimeUtil.handleTenAuth();
        String processInstanceId = flwProcessJumpParam.getId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (ObjectUtil.isNull(historicProcessInstance)) {
            throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
        }
        if(!historicProcessInstance.getState().equals("ACTIVE")) {
            throw new CommonException("该流程实例不在运行中无法跳转，id值为：{}", processInstanceId);
        }
        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        String targetActivityId = flwProcessJumpParam.getTargetActivityId();
        UserTask jumpUserTask = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetActivityId);
        // 执行跳转
        this.doProcessInstanceModify(processInstanceId, jumpUserTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void restart(FlwProcessRestartParam flwProcessRestartParam) {
        NodeRuntimeUtil.handleTenAuth();
        String processInstanceId = flwProcessRestartParam.getId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (ObjectUtil.isNull(historicProcessInstance)) {
            throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
        }
        if(!historicProcessInstance.getState().equals("COMPLETED") && !historicProcessInstance.getState().equals("EXTERNALLY_TERMINATED")
                && !historicProcessInstance.getState().equals("INTERNALLY_TERMINATED")) {
            throw new CommonException("该流程实例不在结束、终止、撤回、拒绝状态无法复活，id值为：{}", processInstanceId);
        }
        String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        String targetActivityId = flwProcessRestartParam.getTargetActivityId();
        UserTask restartUserTask = repositoryService.getBpmnModelInstance(processDefinitionId).getModelElementById(targetActivityId);
        Collection<SequenceFlow> incoming = restartUserTask.getIncoming();
        if(incoming.size() != 1) {
            throw new CommonException("流程模型数据错误，节点{}入口存在多个", restartUserTask.getName());
        }
        runtimeService.restartProcessInstances(processDefinitionId)
                .processInstanceIds(processInstanceId)
                .startTransition(incoming.iterator().next().getId())
                .execute();
        FlwProcessIdParam flwProcessIdParam = new FlwProcessIdParam();
        flwProcessIdParam.setId(processInstanceId);
        this.delete(CollectionUtil.newArrayList(flwProcessIdParam));
        FormRelationTable info = formRelationTableService.getInfoByProcessInstanceId(processInstanceId);
            //更新对应表的流程状态  update table_name set status = 2   ,主要要区分主表还是业务表
        if (ObjectUtil.isNotEmpty(info)) {
            String sql = "update " + info.getTableName() + " set `status` = " + FlwStatusEnum.ACTIVE_STATUS.getValue() + " where ID = '" + info.getId() +"'";
            NodeRuntimeUtil.handleSqlExecution(sql);
            if(ObjectUtil.isNotEmpty(info.getBizTableName())){
                sql = "update " + info.getBizTableName() + " set `status` = " + FlwStatusEnum.ACTIVE_STATUS.getValue() + " where FLW_ID = '" + info.getId() +"'";
                NodeRuntimeUtil.handleSqlExecution(sql);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void migrate(FlwProcessMigrateParam flwProcessMigrateParam) {
        // TODO 流程迁移存在部分问题，待后续完善
        NodeRuntimeUtil.handleTenAuth();
        String processInstanceId = flwProcessMigrateParam.getId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String targetActivityId = flwProcessMigrateParam.getTargetActivityId();
        if (ObjectUtil.isNull(historicProcessInstance)) {
            throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
        }
        if(!historicProcessInstance.getState().equals("ACTIVE")) {
            throw new CommonException("该流程实例不在运行中无法迁移，id值为：{}", processInstanceId);
        }
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        /*if(taskList.size() != 1) {
            throw new CommonException("流程实例当前处于多实例节点无法迁移，id值为：{}", processInstanceId);
        }*/
        Set<String> taskDefinitionKeySet = taskList.stream().map(Task::getTaskDefinitionKey)
                .collect(Collectors.toSet());
        if(taskDefinitionKeySet.size() != 1) {
            throw new CommonException("流程实例当前处于多实例节点无法迁移，id值为：{}", processInstanceId);
        }
        String taskDefinitionKey = taskDefinitionKeySet.iterator().next();
        FlwModel processUseModel = this.getProcessLastModel(processInstanceId);
        JSONObject jsonObject = JSONUtil.parseObj(runtimeService.getVariables(processInstanceId));
        jsonObject.set("initiatorModelInfo", JSONUtil.createObj().set("initiatorModelId",
                processUseModel.getId()).set("initiatorModelJson", processUseModel.getProcessJson()).set("initiatorFormJson",
                processUseModel.getFormJson()) .set("initiatorFormType", processUseModel.getFormType()));
        MigrationPlanBuilder migrationPlanBuilder = runtimeService.createMigrationPlan(historicProcessInstance.getProcessDefinitionId(),
                processUseModel.getDefinitionId())
                .setVariables(jsonObject);
        UserTask currentUserTask = repositoryService.getBpmnModelInstance(historicProcessInstance.getProcessDefinitionId()).getModelElementById(taskDefinitionKey);
        UserTask migrateUserTask = repositoryService.getBpmnModelInstance(processUseModel.getDefinitionId()).getModelElementById(targetActivityId);
        if(!isTaskStart(currentUserTask) && !isTaskStart(migrateUserTask)) {
            ModelElementInstance currentMultiInstanceLoopCharacteristics = currentUserTask.getUniqueChildElementByType(MultiInstanceLoopCharacteristics.class);
            if(ObjectUtil.isEmpty(currentMultiInstanceLoopCharacteristics)) {
                throw new CommonException("模型数据错误，当前节点：{}多实例属性为空", currentUserTask.getName());
            }
            ModelElementInstance migrateMultiInstanceLoopCharacteristics = migrateUserTask.getUniqueChildElementByType(MultiInstanceLoopCharacteristics.class);
            if(ObjectUtil.isEmpty(migrateMultiInstanceLoopCharacteristics)) {
                throw new CommonException("模型数据错误，目标节点：{}多实例属性为空", currentUserTask.getName());
            }
            String currentIsSequential = currentMultiInstanceLoopCharacteristics.getAttributeValue("isSequential");
            String targetIsSequential = migrateMultiInstanceLoopCharacteristics.getAttributeValue("isSequential");
            if(!currentIsSequential.equals(targetIsSequential)) {
                throw new CommonException("迁移失败，当前节点和目标节点多实例属性不一致");
            }
        }
        MigrationInstructionBuilder migrationInstructionBuilder;
        if(isTaskStart(migrateUserTask)) {
            migrationInstructionBuilder = migrationPlanBuilder.mapActivities(taskDefinitionKey, targetActivityId);
        } else {
            migrationInstructionBuilder = migrationPlanBuilder.mapActivities(taskDefinitionKey, targetActivityId)
                    .mapActivities(taskDefinitionKey +  "#multiInstanceBody", targetActivityId + "#multiInstanceBody");
        }
        runtimeService.newMigration(migrationInstructionBuilder.build()).processInstanceIds(processInstanceId).execute();
        taskList.forEach(task -> {
            try {
                boolean hasTask = taskService.createTaskQuery().taskId(task.getId()).list().size() > 0;
                if(hasTask) {
                    taskService.complete(task.getId());
                }
            } catch (Exception ignored) {
                // 忽略或签任务的办理错误
            }
        });
    }

    @Override
    public Page<FlwProcessVariableResult> variablePage(FlwProcessIdParam flwProcessIdParam) {
        NodeRuntimeUtil.handleTenAuth();
        HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService
                .createHistoricVariableInstanceQuery().processInstanceId(flwProcessIdParam.getId())
                .orderByVariableName().desc();
        Page<FlwProcessVariableResult> defaultPage = CommonPageRequest.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        List<FlwProcessVariableResult> flwProcessVariableResultList = historicVariableInstanceQuery
                .listPage(Convert.toInt((current - 1) * size), Convert.toInt(size)).stream()
                .map(historicVariableInstance -> {
                    FlwProcessVariableResult flwProcessVariableResult = new FlwProcessVariableResult();
                    flwProcessVariableResult.setId(historicVariableInstance.getId());
                    flwProcessVariableResult.setProcessInstanceId(historicVariableInstance.getProcessInstanceId());
                    flwProcessVariableResult.setExecutionId(historicVariableInstance.getExecutionId());
                    flwProcessVariableResult.setTypeName(historicVariableInstance.getTypeName());
                    flwProcessVariableResult.setName(historicVariableInstance.getName());
                    flwProcessVariableResult.setValue(Convert.toStr(historicVariableInstance.getValue()));
                    return flwProcessVariableResult;
                })
                .collect(Collectors.toList());
        defaultPage.setTotal(historicVariableInstanceQuery.count());
        defaultPage.setRecords(flwProcessVariableResultList);
        return defaultPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void variableUpdateBatch(FlwProcessVariableUpdateParam flwProcessVariableUpdateParam) {
        NodeRuntimeUtil.handleTenAuth();
        String processInstanceId = flwProcessVariableUpdateParam.getProcessInstanceId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        if (ObjectUtil.isNull(historicProcessInstance)) {
            throw new CommonException("流程实例不存在，id值为：{}", processInstanceId);
        }
        if(!historicProcessInstance.getState().equals("ACTIVE")) {
            throw new CommonException("该流程实例不在运行中无法设置变量，id值为：{}", processInstanceId);
        }
        flwProcessVariableUpdateParam.getVariableInfoList().forEach(flwProcessVariable -> {
            String executionId = flwProcessVariable.getExecutionId();
            List<Execution> executionList = runtimeService.createExecutionQuery().executionId(executionId).list();
            if(ObjectUtil.isEmpty(executionList)) {
                throw new CommonException("执行实例不存在或已结束，无法设置变量，id值为：{}", executionId);
            }
            String variableValue = flwProcessVariable.getVariableValue();
            Object resultValue;
            String typeName = flwProcessVariable.getTypeName().toLowerCase();
            switch (typeName) {
                case "string":
                    resultValue = Convert.toStr(variableValue);
                    break;
                case "integer":
                    resultValue = Convert.toInt(variableValue);
                    break;
                case "boolean":
                    resultValue = Convert.toBool(variableValue);
                    break;
                case "long":
                    resultValue = Convert.toLong(variableValue);
                    break;
                case "double":
                    resultValue = Convert.toDouble(variableValue);
                    break;
                case "object":
                    if (JSONUtil.isTypeJSONObject(variableValue)) {
                        resultValue = JSONUtil.parseObj(variableValue);
                    } else if (JSONUtil.isTypeJSONArray(variableValue)) {
                        resultValue = JSONUtil.parseArray(variableValue);
                    } else if (ObjectUtil.isNotEmpty(Convert.toFloat(variableValue))) {
                        resultValue = Convert.toFloat(variableValue);
                    } else if (variableValue.length() == 1) {
                        resultValue = Convert.toChar(variableValue);
                    } else {
                        throw new CommonException("变量类型：{}不支持该变量值：", typeName, variableValue);
                    }
                    break;
                default:
                    throw new CommonException("不支持的变量类型：{}", typeName);
            }
            runtimeService.setVariable(flwProcessVariable.getExecutionId(), flwProcessVariable.getVariableKey(), resultValue);
        });
    }

    @Override
    public FlwProcessDetailResult detail(FlwProcessIdParam flwProcessIdParam) {
        NodeRuntimeUtil.handleTenAuth();
        FlwProcessDetailResult flwProcessDetailResult = new FlwProcessDetailResult();
        String processInstanceId = flwProcessIdParam.getId();
        String modelIdVariable = this.getProcessModelIdVariable(processInstanceId);
        flwProcessDetailResult.setModelId(modelIdVariable);
        FlwModel flwModel = flwModelService.getById(modelIdVariable);
        if(ObjectUtil.isNotNull(flwModel)){
            flwProcessDetailResult.setFlwDiagram(flwModel.getFlwDiagram());
        }
        flwProcessDetailResult.setProcessInstanceId(processInstanceId);
        flwProcessDetailResult.setFormType(this.getProcessFormTypeVariable(processInstanceId));
        flwProcessDetailResult.setInitiatorModelJson(this.getProcessModelJsonVariable(processInstanceId));
        flwProcessDetailResult.setInitiatorFormJson(this.getProcessFormJsonVariable(processInstanceId));
        flwProcessDetailResult.setInitiatorDataJson(this.getProcessDataJsonVariable(processInstanceId));
        flwProcessDetailResult.setInitiatorSelectData(this.getProcessSelectDataJsonVariable(processInstanceId));
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        List<Comment> processInstanceCommentList = taskService.getProcessInstanceComments(processInstanceId);
        List<FlwTaskDetailResult.FlwProcessComment> commonList = CollectionUtil.newArrayList();
        historicTaskInstanceList.forEach(historicTaskInstance -> {
            getTaskCommentList(processInstanceCommentList, historicTaskInstance.getId()).forEach(taskComment -> {
                FlwTaskDetailResult.FlwProcessComment flwProcessComment = new FlwTaskDetailResult.FlwProcessComment();
                flwProcessComment.setTaskId(historicTaskInstance.getId());
                JSONObject commonObject = JSONUtil.parseObj(taskComment.getFullMessage());
                String operateType = commonObject.getStr("OPERATE_TYPE");
                if(NodeApproveOperateTypeEnum.COMMENT.getValue().equals(operateType)){
                    flwProcessComment.setTaskName("评论");
                    flwProcessComment.setCommentId(taskComment.getId());
                }else {
                    flwProcessComment.setTaskName(historicTaskInstance.getName());
                }
                flwProcessComment.setActivityId(historicTaskInstance.getTaskDefinitionKey());
                flwProcessComment.setUserId(taskComment.getUserId());
                flwProcessComment.setUserName(commonObject.getStr("USER_NAME"));
                flwProcessComment.setOperateType(commonObject.getStr("OPERATE_TYPE"));
                flwProcessComment.setOperateText(commonObject.getStr("OPERATE_TEXT"));
                flwProcessComment.setComment(ObjectUtil.isNotEmpty(commonObject.getStr("COMMENT"))?commonObject.getStr("COMMENT") : "无");
                flwProcessComment.setApproveTime(DateUtil.formatDateTime(taskComment.getTime()));
                flwProcessComment.setAttachmentList(taskService.getTaskAttachments(historicTaskInstance.getId()).stream().map(attachment -> {
                  FlwTaskAttachmentResult flwTaskAttachmentResult = new FlwTaskAttachmentResult();
                  flwTaskAttachmentResult.setAttachmentName(attachment.getName());
                  flwTaskAttachmentResult.setAttachmentUrl(attachment.getUrl());
                  flwTaskAttachmentResult.setPreviewUrl(commonProperties.getBackendUrl()+attachment.getUrl());
                  return flwTaskAttachmentResult;
                }).collect(Collectors.toList()));
                commonList.add(flwProcessComment);
            });
        });
        getNoTaskIdCommentList(processInstanceCommentList,historicTaskInstanceList.get(0).getProcessInstanceId()).forEach(taskComment -> {
            FlwTaskDetailResult.FlwProcessComment flwProcessComment = new FlwTaskDetailResult.FlwProcessComment();
            flwProcessComment.setTaskId(historicTaskInstanceList.get(0).getId());
            JSONObject commonObject = JSONUtil.parseObj(taskComment.getFullMessage());
            String operateType = commonObject.getStr("OPERATE_TYPE");
            if(NodeApproveOperateTypeEnum.COMMENT.getValue().equals(operateType)){
                flwProcessComment.setTaskName("评论");
                flwProcessComment.setCommentId(taskComment.getId());
            }else {
                flwProcessComment.setTaskName(historicTaskInstanceList.get(0).getName());
            }
            flwProcessComment.setActivityId(historicTaskInstanceList.get(0).getTaskDefinitionKey());
            flwProcessComment.setUserId(taskComment.getUserId());
            flwProcessComment.setUserName(commonObject.getStr("USER_NAME"));
            flwProcessComment.setOperateType(commonObject.getStr("OPERATE_TYPE"));
            flwProcessComment.setOperateText(commonObject.getStr("OPERATE_TEXT"));
            flwProcessComment.setComment(ObjectUtil.isNotEmpty(commonObject.getStr("COMMENT"))?commonObject.getStr("COMMENT") : "无");
            flwProcessComment.setApproveTime(DateUtil.formatDateTime(taskComment.getTime()));
            flwProcessComment.setAttachmentList(taskService.getProcessInstanceAttachments(historicTaskInstanceList.get(0).getProcessInstanceId()).stream().filter(f->taskComment.getId().equals(f.getDescription())).map(attachment -> {
                FlwTaskAttachmentResult flwTaskAttachmentResult = new FlwTaskAttachmentResult();
                flwTaskAttachmentResult.setAttachmentName(attachment.getName());
                flwTaskAttachmentResult.setAttachmentUrl(attachment.getUrl());
                flwTaskAttachmentResult.setPreviewUrl(commonProperties.getBackendUrl()+attachment.getUrl());
                return flwTaskAttachmentResult;
            }).collect(Collectors.toList()));
            commonList.add(flwProcessComment);
        });
        CollectionUtil.sort(commonList, Comparator.comparing(FlwTaskDetailResult.FlwProcessComment::getApproveTime));
        countTime(commonList);
        flwProcessDetailResult.setCommentList(commonList);

        /** 补充流程状态 **/
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String state = historicProcessInstance.getState();
        switch (state) {
            case "ACTIVE":
                flwProcessDetailResult.setStateCode(state);
                flwProcessDetailResult.setStateText("审批中");
                break;
            case "SUSPENDED":
                flwProcessDetailResult.setStateCode(state);
                flwProcessDetailResult.setStateText("已挂起");
                break;
            case "COMPLETED":
                flwProcessDetailResult.setStateCode(state);
                flwProcessDetailResult.setStateText("已完成");
                break;
            case "EXTERNALLY_TERMINATED":
                String deleteReason = historicProcessInstance.getDeleteReason();
                switch (deleteReason) {
                    case "END":
                        flwProcessDetailResult.setStateCode(deleteReason);
                        flwProcessDetailResult.setStateText("已终止");
                        break;
                    case "REVOKE":
                        flwProcessDetailResult.setStateCode(deleteReason);
                        flwProcessDetailResult.setStateText("已撤回");
                        break;
                    case "REJECT":
                        flwProcessDetailResult.setStateCode(deleteReason);
                        flwProcessDetailResult.setStateText("已拒绝");
                        break;
                    default:
                        throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
                }
                break;
            case "INTERNALLY_TERMINATED":
                flwProcessDetailResult.setStateCode(state);
                flwProcessDetailResult.setStateText("内部终止");
                break;
            default:
                throw new CommonException("流程实例状态错误，id值为：{}", historicProcessInstance.getId());
        }

        //获取当前的审批节点的后续节点【审核中流程】
        if(CollUtil.isNotEmpty(commonList) && state.equals("ACTIVE")){
            //获取当前审批节点【非评论】-已排序
            List<FlwTaskDetailResult.FlwProcessComment> oldFlwNodeList = commonList.stream().filter(x -> ObjectUtil.isEmpty(x.getCommentId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(oldFlwNodeList)){
                String activityId = oldFlwNodeList.get(oldFlwNodeList.size()-1).getActivityId();
                FlwNodeResult nextFlwNodeResult = NodeInfoUtil.getAllNextFlwNodes(flwModel.getProcessJson(),activityId);
                flwProcessDetailResult.setNextFlwNodeResult(nextFlwNodeResult);
            }
        }

        return flwProcessDetailResult;
    }

    private void countTime(List<FlwTaskDetailResult.FlwProcessComment> commonList){
        if(CollectionUtil.isEmpty(commonList)){
            return;
        }
        for(int i=0;i<commonList.size();i++){
            if(i==0){
                continue;
            }
            Long minute = DateUtil.between(CommonTimeFormatUtil.strToDate(commonList.get(i-1).getApproveTime()),DateUtil.date(), DateUnit.MINUTE);
            commonList.get(i).setApproveExpendHours(minute > 0 && minute/60 > 0 ? minute/60+"小时"+ minute%60+"分钟" : minute%60 > 0.0 ? minute%60+"分钟" :"秒批");
            if(StringUtils.isNotEmpty(commonList.get(i).getApproveTime())){
                minute = DateUtil.between(CommonTimeFormatUtil.strToDate(commonList.get(i-1).getApproveTime()),CommonTimeFormatUtil.strToDate(commonList.get(i).getApproveTime()), DateUnit.MINUTE);
                commonList.get(i).setApproveExpendHours(minute > 0 && minute/60 > 0 ? minute/60+"小时"+ minute%60+"分钟" : minute%60 > 0.0 ? minute%60+"分钟" :"秒批");
                continue;
            }
        }
    }

    @Override
    public JSONObject getProcessModelInfoVariable(String processInstanceId) {
        HistoricVariableInstance initiatorModelInfo = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).variableName("initiatorModelInfo").singleResult();
        if (ObjectUtil.isEmpty(initiatorModelInfo)) {
            throw new CommonException("数据错误，流程模型initiatorModelInfo数据快照不存在，id值为：{}", processInstanceId);
        }
        Object initiatorModelInfoValue = initiatorModelInfo.getValue();
        if (ObjectUtil.isEmpty(initiatorModelInfoValue)) {
            throw new CommonException("数据错误，流程模型initiatorModelInfo数据快照不存在，id值为：{}", processInstanceId);
        }
        return JSONUtil.parseObj(initiatorModelInfoValue);
    }

    @Override
    public String getProcessModelIdVariable(String processInstanceId) {
        return this.getProcessModelInfoVariable(processInstanceId).getStr("initiatorModelId");
    }

    @Override
    public String getProcessModelJsonVariable(String processInstanceId) {
        return this.getProcessModelInfoVariable(processInstanceId).getStr("initiatorModelJson");
    }

    @Override
    public FlwNode getProcessModelJsonNodeVariable(String processInstanceId) {
        return JSONUtil.toBean(this.getProcessModelJsonVariable(processInstanceId), FlwNode.class);
    }

    @Override
    public String getProcessFormJsonVariable(String processInstanceId) {
        return this.getProcessModelInfoVariable(processInstanceId).getStr("initiatorFormJson");
    }

    @Override
    public String getProcessFormTypeVariable(String processInstanceId) {
        return this.getProcessModelInfoVariable(processInstanceId).getStr("initiatorFormType");
    }

    @Override
    public FlwModel getProcessLastModel(String processInstanceId) {
        String processModelIdVariable = this.getProcessModelIdVariable(processInstanceId);
        if (ObjectUtil.isEmpty(processModelIdVariable)) {
            throw new CommonException("数据错误，流程模型initiatorModelId数据快照不存在，id值为：{}", processInstanceId);
        }
        return flwModelService.validModel(processModelIdVariable);
    }

    @Override
    public String getProcessDataJsonVariable(String processInstanceId) {
        HistoricVariableInstance initiatorDataJson = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).variableName("initiatorDataJson").singleResult();
        if (ObjectUtil.isNotEmpty(initiatorDataJson)) {
            return Convert.toStr(initiatorDataJson.getValue());
        }
        return null;
    }

    public String getProcessSelectDataJsonVariable(String processInstanceId) {
        HistoricVariableInstance initiatorDataJson = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId).variableName("initiatorSelectData").singleResult();
        if (ObjectUtil.isNotEmpty(initiatorDataJson)) {
            return Convert.toStr(initiatorDataJson.getValue());
        }
        return null;
    }

    @Override
    public FlwNode.FlwNodeConfigProp getProcessNodeConfigProp(String processInstanceId) {
        return this.getProcessModelJsonNodeVariable(processInstanceId).getProperties().getConfigInfo();
    }

    @Override
    public void doCopy(String processInstanceId, List<String> userIdList) {
        List<FlwRelation> flwRelationList = CollectionUtil.newArrayList();
        String extJson = JSONUtil.toJsonStr(JSONUtil.createObj().set("IS_READ", false));
        userIdList.forEach(targetId -> {
            FlwRelation flwRelation = new FlwRelation();
            flwRelation.setObjectId(processInstanceId);
            flwRelation.setTargetId(targetId);
            flwRelation.setCategory(FlwRelationCategoryEnum.FLW_COPY_PROCESS_TO_USER.getValue());
            flwRelation.setExtJson(extJson);
            flwRelationList.add(flwRelation);
        });
       flwRelationService.saveBatch(flwRelationList);
    }

    @Override
    public List<String> getMyCopyUnreadProcessInstanceIdList() {
        return flwRelationService.list(new LambdaQueryWrapper<FlwRelation>().eq(FlwRelation::getTargetId, StpUtil.getLoginIdAsString())
                .eq(FlwRelation::getCategory, FlwRelationCategoryEnum.FLW_COPY_PROCESS_TO_USER.getValue())
                .eq(FlwRelation::getExtJson, JSONUtil.toJsonStr(JSONUtil.createObj().set("IS_READ", false))))
                .stream().map(FlwRelation::getObjectId).collect(Collectors.toList());
    }

    @Override
    public void readMyCopyProcess(List<FlwProcessIdParam> flwProcessIdParamList) {
        List<String> processIdList = flwProcessIdParamList.stream().map(FlwProcessIdParam::getId).collect(Collectors.toList());
        flwRelationService.update(new LambdaUpdateWrapper<FlwRelation>().eq(FlwRelation::getTargetId,
                StpUtil.getLoginIdAsString()).in(FlwRelation::getObjectId, processIdList).set(FlwRelation::getExtJson,
                JSONUtil.toJsonStr(JSONUtil.createObj().set("IS_READ", true))));
    }

    @Override
    public List<String> getMyCopyHasReadProcessInstanceIdList() {
        return flwRelationService.list(new LambdaQueryWrapper<FlwRelation>().eq(FlwRelation::getTargetId, StpUtil.getLoginIdAsString())
                .eq(FlwRelation::getCategory, FlwRelationCategoryEnum.FLW_COPY_PROCESS_TO_USER.getValue())
                .eq(FlwRelation::getExtJson, JSONUtil.toJsonStr(JSONUtil.createObj().set("IS_READ", true))))
                .stream().map(FlwRelation::getObjectId).collect(Collectors.toList());
    }

    @Override
    public void deleteMyHasReadProcess(List<FlwProcessIdParam> flwProcessIdParamList) {
        List<String> processIdList = flwProcessIdParamList.stream().map(FlwProcessIdParam::getId).collect(Collectors.toList());
        flwRelationService.remove(new LambdaUpdateWrapper<FlwRelation>().eq(FlwRelation::getTargetId,
                StpUtil.getLoginIdAsString()).in(FlwRelation::getObjectId, processIdList));
    }

    @Override
    public List<FlwProcessJumpNodeResult> getCanJumpNodeInfoList(FlwProcessIdParam flwProcessIdParam) {
        FlwNode flwNode = this.getProcessModelJsonNodeVariable(flwProcessIdParam.getId());
        return NodeInfoUtil.getUserTaskFlwNodeList(flwNode).stream().map(tempFlwNode -> {
            FlwProcessJumpNodeResult flwProcessJumpNodeResult = new FlwProcessJumpNodeResult();
            flwProcessJumpNodeResult.setId(tempFlwNode.getId());
            flwProcessJumpNodeResult.setName(tempFlwNode.getTitle());
            return flwProcessJumpNodeResult;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FlwProcessRestartNodeResult> getCanRestartNodeInfoList(FlwProcessIdParam flwProcessIdParam) {
        FlwNode flwNode = this.getProcessModelJsonNodeVariable(flwProcessIdParam.getId());
        return NodeInfoUtil.getUserTaskFlwNodeList(flwNode).stream().map(tempFlwNode -> {
            FlwProcessRestartNodeResult flwProcessRestartNodeResult = new FlwProcessRestartNodeResult();
            flwProcessRestartNodeResult.setId(tempFlwNode.getId());
            flwProcessRestartNodeResult.setName(tempFlwNode.getTitle());
            return flwProcessRestartNodeResult;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FlwProcessRestartNodeResult> getCanMigrateNodeInfoList(FlwProcessIdParam flwProcessIdParam) {
        FlwModel processUseModel = this.getProcessLastModel(flwProcessIdParam.getId());
        FlwNode flwNode = JSONUtil.toBean(processUseModel.getProcessJson(), FlwNode.class);
        return NodeInfoUtil.getUserTaskFlwNodeList(flwNode).stream().map(tempFlwNode -> {
            FlwProcessRestartNodeResult flwProcessRestartNodeResult = new FlwProcessRestartNodeResult();
            flwProcessRestartNodeResult.setId(tempFlwNode.getId());
            flwProcessRestartNodeResult.setName(tempFlwNode.getTitle());
            return flwProcessRestartNodeResult;
        }).collect(Collectors.toList());
    }

    /**
     * 执行流程实例修改
     *
     * @author xuyuxiang
     * @date 2023/5/23 18:13
     **/
    @Override
    public void doProcessInstanceModify(String processInstanceId, UserTask userTask) {
        // 创建流程实体修改构造器
        ProcessInstanceModificationBuilder processInstanceModificationBuilder = runtimeService
                .createProcessInstanceModification(processInstanceId);
        // 取消当前所有正在执行的节点
        taskService.createTaskQuery().processInstanceId(processInstanceId).list().stream().map(Task::getTaskDefinitionKey)
                .collect(Collectors.toSet()).forEach(processInstanceModificationBuilder::cancelAllForActivity);
        Collection<SequenceFlow> incoming = userTask.getIncoming();
        if(incoming.size() != 1) {
            throw new CommonException("流程模型数据错误，节点{}入口存在多个", userTask.getName());
        }
        // 执行
        processInstanceModificationBuilder.startTransition(incoming.iterator().next().getId()).execute();
    }

    @Override
    public List<Tree<String>> orgTreeSelector() {
        return sysOrgApi.orgTreeSelector();
    }


    @SuppressWarnings("ALL")
    @Override
    public Page<FlwProcessUserResult> userSelector(FlwProcessSelectorUserParam flwProcessSelectorUserParam) {
        return BeanUtil.toBean(sysUserApi.userSelector(flwProcessSelectorUserParam.getOrgId(), flwProcessSelectorUserParam.getSearchKey()), Page.class);

    }
    /**
     * 获取任务审批记录
     *
     * @author xuyuxiang
     * @date 2020/8/13 16:35
     **/
    public List<Comment> getTaskCommentList(List<Comment> originDataList, String taskId) {
        return originDataList.stream().filter(comment -> StrUtil.isNotEmpty(comment.getTaskId()) && comment.getTaskId().equals(taskId)).collect(Collectors.toList());
    }

    /**
     * 获取任务审批记录
     *
     * @author xuyuxiang
     * @date 2020/8/13 16:35
     **/
    public List<Comment> getNoTaskIdCommentList(List<Comment> originDataList, String processInstanceId) {
        return originDataList.stream().filter(comment -> StrUtil.isEmpty(comment.getTaskId()) && StrUtil.isNotEmpty(comment.getProcessInstanceId()) && comment.getProcessInstanceId().equals(processInstanceId)).collect(Collectors.toList());
    }

    /**
     * 判断任务是否为发起申请
     *
     * @author xuyuxiang
     * @date 2022/6/9 19:52
     */
    public static boolean isTaskStart(UserTask userTask) {
        List<FlowNode> flowNodeList = userTask.getPreviousNodes().list();
        if(ObjectUtil.isNotEmpty(flowNodeList) && flowNodeList.size() == 1) {
            return flowNodeList.get(0).getElementType().getTypeName().equals(BpmnModelConstants.BPMN_ELEMENT_START_EVENT);
        }
        return false;
    }

    public void handDataSave(String dataJson,String sn, String modelId, String definitionId, String deploymentId,String processInstanceId,String parentTablePrimaryId){
        /**1 根据modelID 获取到对应的业务 表明和 服务名
         **2
        **/
        //RepositoryService repositoryService = SpringUtil.getBean(RepositoryService.class);
        FormRelationTable formRelationTable = new FormRelationTable();
        formRelationTable.setActModelId(modelId);
        formRelationTable.setDefinitionId(definitionId);
        formRelationTable.setDeploymentId(deploymentId);
        Map<String,Object> dataMap = JSONUtil.toBean(dataJson, Map.class);

        dataMap.put("processInstanceId",processInstanceId);
        dataMap.put("modelId",modelId);
        //getSn(modelId);
        dataMap.put("sn",sn);

        formRelationTable = formRelationTableService.getOne(new QueryWrapper<>(formRelationTable));
        if(ObjectUtil.isNotNull(formRelationTable)){
            formRelationTableService.saveNewData(dataMap,formRelationTable.getTableName(),parentTablePrimaryId);
            //formRelationTableService.save(modelId,);
            if(modelId.equals("1674588959370481665")){
                String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
                formRelationTableService.save(modelId, BizCommonEnum.BIZ_AUDIT_LISTS.getBizTable(),parentTablePrimaryId,processInstanceId,dbName);
            }
//            BizCommon common = BizCommon.findByCommonTable(formRelationTable.getTableName());
//            if(ObjectUtil.isNotNull(common)){
//                String resourceService = common.getResourceService();
//                Object bean = SpringUtil.getBean(resourceService+".class");
//            }
        }
    }
    public  synchronized  String getSn(FlwModel flwModel) {
        if(ObjectUtil.isNotNull(flwModel)){
            String processSnTemplate = "";
            String processJson = flwModel.getProcessJson();
            if(StrUtil.isNotBlank(processJson)){
                JSONObject entries = JSONUtil.parseObj(processJson);
                if(ObjectUtil.isNotEmpty(entries)){
                    JSONObject properties = entries.getJSONObject("properties");
                    if(ObjectUtil.isNotEmpty(properties)){
                        JSONObject configInfo = properties.getJSONObject("configInfo");
                        if(ObjectUtil.isNotEmpty(configInfo)){
                            //str = configInfo.getStr("processStartTaskFormUrl");
                            // 流水号模板id
                            String processSnTemplateId = configInfo.getStr("processSnTemplateId");

                            // 流水号内容
                            processSnTemplate = flwTemplateSnService.getTemplateValueBydId(processSnTemplateId);
                        }
                    }
                }

            }
            return processSnTemplate;
        }
        return "";
    }

    @Override
    public String getSn(String modelId){
        FlwModel flwModel = flwModelService.queryEntity(modelId);
        return  getSn(flwModel);
    }

    @Override
    public FlwModel modelDetail(FlwProcessModelIdParam flwProcessModelIdParam) {
        return flwModelService.queryEntity(flwProcessModelIdParam.getId());
    }

    @Override
    public Map<String,Long> count(Boolean flag) {
        HashMap<String, Long> hashMap = new HashMap<>();
        TaskQuery taskQuery = taskService.createTaskQuery().active().taskAssignee(StpUtil.getLoginIdAsString()).orderByTaskCreateTime().desc();
        long todoPageCount = taskQuery.count();
        hashMap.put("todoCount",todoPageCount);
        if(flag) {
            HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(StpUtil.getLoginIdAsString())
                    .orderByHistoricTaskInstanceEndTime().desc();
            long doPageCount = historicTaskInstanceQuery.count();
            hashMap.put("doCount", doPageCount);
            HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                    .startedBy(StpUtil.getLoginIdAsString())
                    .orderByProcessInstanceStartTime().desc();
            long myPageCount = historicProcessInstanceQuery.count();
            hashMap.put("myCount", myPageCount);
            List<String> myCopyUnreadProcessInstanceIdList = getMyCopyUnreadProcessInstanceIdList();
            if (ObjectUtil.isNotEmpty(myCopyUnreadProcessInstanceIdList)) {
                HistoricProcessInstanceQuery historicProcessInstanceQuery1 = historyService.createHistoricProcessInstanceQuery().
                        processInstanceIds(CollectionUtil.newHashSet(myCopyUnreadProcessInstanceIdList));
                long count = historicProcessInstanceQuery1.count();
                hashMap.put("readCount", count);
            } else {
                hashMap.put("readCount", 0L);
            }
        }
        //readCount 抄送事宜
        return hashMap;
    }

    @Override
    public Map<String,String> getInstance(String processInstanceId) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).active().singleResult();
        if(ObjectUtil.isEmpty(task)){
            return new HashMap<>();
        }
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        String startUserId = historicProcessInstance.getStartUserId();
        Map<String,String> resultMap= new HashMap<>();
        resultMap.put("startUserId",startUserId);
        resultMap.put("assignee",task.getAssignee());
        return resultMap;
    }

    @Override
    public List<String> getNameList(String name){
        /**
         * 如果全匹配就返回全匹配的key,未找到就模糊匹配
         */
        try {
            String key = flwCommonMapper.getKey(name);
            if (StrUtil.isNotEmpty(key)) {
                return CollectionUtil.newArrayList(key);
            }
            return flwCommonMapper.getNameList(name);
        }catch (Exception e) {
            return flwCommonMapper.getNameList(name);
        }
    }

    @Override
    @DSTransactional
    public String purchasePlanStart(FlwProcessStartParam flwProcessStartParam) {
        try {
            NodeRuntimeUtil.handleTenAuth();
            String modelId = flwProcessStartParam.getModelId();
            FlwModel flwModel = flwModelService.queryEntity(modelId);
            if (flwModel.getModelStatus().equals(FlwModelStatusEnum.DISABLED.getValue())) {
                throw new CommonException("模型未启用，名称为：{}", flwModel.getName());
            }
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("initiator", flwProcessStartParam.getInitiator());
            jsonObject.set("initiatorName", flwProcessStartParam.getInitiatorName());
            jsonObject.set("initiatorOrgId", flwProcessStartParam.getInitiatorOrgId());
            jsonObject.set("initiatorOrgName", flwProcessStartParam.getInitiatorOrgName());
            jsonObject.set("initiatorPositionId", flwProcessStartParam.getInitiatorPositionId());
            jsonObject.set("initiatorPositionName", flwProcessStartParam.getInitiatorPositionName());
            jsonObject.set("initiatorTime", DateUtil.now());
            jsonObject.set("initiatorModelInfo", JSONUtil.createObj().set("initiatorModelId", flwModel.getId())
                    .set("initiatorModelJson", flwModel.getProcessJson()).set("initiatorFormJson", flwModel.getFormJson())
                    .set("initiatorFormType", flwModel.getFormType()));
            ProcessInstance processInstance = null;
            //FlwModel flwModel = flwModelService.getById(modelId);
            String sn = getSn(flwModel);
            String parentTablePrimaryId = IdWorker.getIdStr();
            if(flwModel.getFormType().equals(FlwModelFormTypeEnum.DESIGN.getValue())) {
                if(ObjectUtil.isEmpty(flwModel.getTableJson())) {
                    throw new CommonException("模型错误，设计表单类型的模型数据库表JSON不能为空");
                }
                if(ObjectUtil.isNotEmpty(flwProcessStartParam.getDataJson())) {
                    //String parentTablePrimaryId = IdWorker.getIdStr();
                    JSONObject resultDataObj = NodeRuntimeUtil.handleInitiatorDataJson(flwModel.getTableJson(),
                            flwProcessStartParam.getDataJson(), parentTablePrimaryId);
                    String initiatorSql = resultDataObj.getStr("sql");
                    String initiatorDataJson = resultDataObj.getStr("data");
                    jsonObject.set("initiatorSql", initiatorSql);
                    jsonObject.set("initiatorTableJson", flwModel.getTableJson());
                    jsonObject.set("initiatorDataJson", JSONUtil.parse(initiatorDataJson).toString());
                    //设置sn
                    jsonObject.set("initiatorProcessSn",sn);
                    runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), parentTablePrimaryId, jsonObject);
                    NodeRuntimeUtil.handleSqlExecution(initiatorSql);
                } else {
                    processInstance = runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), jsonObject);
                }
            } else {
                if(ObjectUtil.isNotEmpty(flwProcessStartParam.getDataJson())) {
                    if(!JSONUtil.isTypeJSONObject(flwProcessStartParam.getDataJson())) {
                        throw new CommonException("填写的数据请使用JSON格式");
                    }
                    String dataJson = flwProcessStartParam.getDataJson();
                    JSONObject entries = JSONUtil.parseObj(dataJson);
                    entries.set("businessKey",parentTablePrimaryId);
                    entries.set("id", parentTablePrimaryId);
//                if(!modelId.equals("1674588959370481665")) {
//                    entries.set("id", parentTablePrimaryId);
//                }
                    entries.set("sn", sn);
                    entries.set("applyUserId",flwProcessStartParam.getInitiator());
                    entries.set("applyUser",flwProcessStartParam.getInitiatorName());
                    entries.set("applyTime",DateUtil.now());
                    flwProcessStartParam.setDataJson(JSONUtil.toJsonStr(entries));
                    jsonObject.set("initiatorDataJson", JSONUtil.toJsonStr(entries));
                    //设置sn
                    jsonObject.set("initiatorProcessSn",sn);
                }
                processInstance = runtimeService.startProcessInstanceById(flwModel.getDefinitionId(), parentTablePrimaryId ,jsonObject);
            }

            //EPC系统采购计划和申请才记录
            if(ObjectUtil.isNotEmpty(JSONUtil.parseObj(flwProcessStartParam.getDataJson()).get("purchasingCode"))) {
                //存入系统表
                ExtSystem extSystem = new ExtSystem();
                extSystem.setTenantId(tenApi.getCurrentTenId());
                extSystem.setFromId(String.valueOf(JSONUtil.parseObj(flwProcessStartParam.getDataJson()).get("planId")));
                extSystem.setFromExtJson(JSONUtil.toJsonStr(JSONUtil.parseObj(flwProcessStartParam.getDataJson()).set("processInstanceId", ObjectUtil.isNotEmpty(processInstance) ? processInstance.getProcessInstanceId() : null)));
                extSystem.setSystemName(String.valueOf(JSONUtil.parseObj(flwProcessStartParam.getDataJson()).get("purchasingCode")));
                extSystemService.save(extSystem);
            }

            if(ObjectUtil.isNotEmpty(processInstance)){
                String processInstanceId = processInstance.getProcessInstanceId();
                //将id 和sn 存入缓存中，保证每个模块的业务都可以获取到，获取完成之后清空缓存，避免占用内存
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_ID_KEY+processInstanceId,parentTablePrimaryId);
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId,sn);
                commonCacheOperator.put(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY+processInstanceId,modelId);
                return processInstanceId;
            }
        }catch (Exception e){
            throw new CommonException("流程启动失败",e);
        }
        return null;
    }


    @Override
    public BoardTotalVo board(FlwBoardParam flwBoardParam) {
        BoardTotalVo result = new BoardTotalVo();
        Date nowDate = new Date();  //当前时间
        Date startDate = getStartDate(nowDate,flwBoardParam.getType());  //数据范围开始时间
        Date endDate = nowDate;     //数据范围截止时间
        Date preStartDate = getStartDate(startDate,flwBoardParam.getType()); //再前一期
        Date preEndDate = startDate;

        //全部流程：[startDate,endDate]数据范围
        List<FlwBoardResult> boardDataList = getFlwBoardData(startDate,endDate,true);
        //耗时明细 - 趋势分析
        LineChart lineChart = getLineChart(boardDataList);
        //耗时明细 - 部门明细 [默认给全部]
        List<BoardDept> tmpBoardDeptList = getBoardDept(boardDataList);
        tmpBoardDeptList.sort(Comparator.comparing(BoardDept::getFlwNums).reversed());
        List<BoardDept> boardDeptList = tmpBoardDeptList;
        if(ObjectUtil.isNotEmpty(flwBoardParam.getTopNums())){
            boardDeptList = tmpBoardDeptList.size() > flwBoardParam.getTopNums() ? tmpBoardDeptList.subList(0,flwBoardParam.getTopNums()) : tmpBoardDeptList;
        }

        //待办流程: 所有待办信息
        List<FlwTodoBoardResult> toDoBoardDataList = getToDoBoardData(false);
        List<BoardTodo> tmpFlwToDoEmp = getFlwToDoEmp(startDate, endDate, preStartDate, preEndDate, toDoBoardDataList);
        //待办量排行前5
        int topNums = 5;
        if(ObjectUtil.isNotEmpty(flwBoardParam.getTopNums())){
            topNums = flwBoardParam.getTopNums();
        }
        List<BoardTodoNum> tmpBoardTodoNumList = BeanUtil.copyToList(tmpFlwToDoEmp,BoardTodoNum.class);
        tmpBoardTodoNumList.sort(Comparator.comparing(BoardTodoNum::getToDoNums).reversed());
        List<BoardTodoNum> boardTodoNumList = tmpBoardTodoNumList.size() > topNums ? tmpBoardTodoNumList.subList(0,topNums) : tmpBoardTodoNumList;
        //待办耗时排行前5
        List<BoardTodoHour> tmpBoardTodoHourList = BeanUtil.copyToList(tmpFlwToDoEmp, BoardTodoHour.class);
        tmpBoardTodoHourList.sort(Comparator.comparing(BoardTodoHour::getDealHours).reversed());
        List<BoardTodoHour> boardTodoHourList = tmpBoardTodoHourList.size() > topNums ? tmpBoardTodoHourList.subList(0,topNums) : tmpBoardTodoHourList;

        result.setLineChart(lineChart);
        result.setDeptList(boardDeptList);
        result.setTodoNumList(boardTodoNumList);
        result.setTodoHourList(boardTodoHourList);
        return result;
    }

    @Override
    public Page<FlwBoardResult> boardPage(FlwBoardParam flwBoardParam) {
        Date nowDate = new Date();  //当前时间
        Date startDate = getStartDate(nowDate,flwBoardParam.getType());  //数据范围开始时间
        Date endDate = nowDate;
        //全部流程：[startDate,endDate]数据范围
        List<FlwBoardResult> boardDataList = getFlwBoardData(startDate,endDate,false);
        //超过48h
        boardDataList.sort(Comparator.comparing(FlwBoardResult::getBoardTime).reversed());
        List<FlwBoardResult> tmpData = boardDataList.stream().filter(x->ObjectUtil.isNotEmpty(x.getBoardTime()) && x.getBoardTime() > 48).collect(Collectors.toList());
        //页面参数-分页
        Page<FlwBoardResult> defaultPage = CommonPageRequest.defaultPage();
        long current = defaultPage.getCurrent();
        long size = defaultPage.getSize();
        int fromIndex = Convert.toInt((current - 1) * size);
        int toIndex = Math.min(fromIndex + Convert.toInt(size), Convert.toInt(tmpData.size()));
        List<FlwBoardResult> currentPageData = tmpData.subList(fromIndex, toIndex);
        getCurrentBoardPage(currentPageData);
        defaultPage.setRecords(currentPageData);
        defaultPage.setTotal(tmpData.size());
        return defaultPage;
    }

    /**
     *  根据当前时间，获取 七天前  一月前  今年初 日期
     * @param nowDate 当前时间
     * @param type 类型 1 近七天  2 近一月  3 近三个月
     * @return
     */
    public Date getStartDate(Date nowDate,String type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        if(BoardTypeEnum.SEVEN_DAY.getValue().equals(type)){
            // 将日期向前推七天
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        } else if (BoardTypeEnum.MONTH.getValue().equals(type)) {
            // 将日期向前推一月
            calendar.add(Calendar.MONTH, -1);
        } else if (BoardTypeEnum.THREE_MONTH.getValue().equals(type)) {
            // 将日期向前推3个月
            calendar.add(Calendar.MONTH, -3);
        }
        return calendar.getTime();
    }


    /**
     * 耗时明细 - 趋势分析
     * @param boardResultList
     * @return
     */
    public LineChart getLineChart(List<FlwBoardResult> boardResultList) {
        if(CollUtil.isEmpty(boardResultList)) return null;
        //统计维度(日期)
        Map<Date,List<FlwBoardResult>> groupFlw = boardResultList.stream().collect(Collectors.groupingBy(FlwBoardResult::getBoardDate,LinkedHashMap::new,Collectors.toList()));
        LineChart result = new LineChart();
        List<String> xAxis = new ArrayList<>();
        List<Long> firstData = new ArrayList<>();
        List<Double> secondData = new ArrayList<>();
        for (Map.Entry<Date,List<FlwBoardResult>> entry:groupFlw.entrySet()) {
            List<FlwBoardResult> tmpList = entry.getValue();
            //耗时
            List<Double> tmpTimeList = tmpList.stream().map(x -> x.getBoardTime()).collect(Collectors.toList());
            Double tmpHours = sumTime(tmpTimeList);
            //总单量
            long tmpCount = tmpList.size();
            xAxis.add(DateUtil.formatDate(entry.getKey()));
            firstData.add(tmpCount);
            secondData.add( tmpCount > 0 ? (tmpHours/tmpCount) : 0d);
        }
        result.setXAxis(xAxis);
        result.setFirstData(firstData);
        result.setSecondData(secondData);
        return result;
    }

    /**
     * 耗时明细 - 部门明细
     * @param boardResultList
     * @return
     */
    public List<BoardDept> getBoardDept(List<FlwBoardResult> boardResultList) {
        if(CollUtil.isEmpty(boardResultList)) return null;
        List<BoardDept> result = new ArrayList<>();
        //统计维度（发起人部门）
        Map<String,List<FlwBoardResult>> groupFlw = boardResultList.stream().filter(x->ObjectUtil.isNotEmpty(x.getInitiatorOrgName()))
                .collect(Collectors.groupingBy(FlwBoardResult::getInitiatorOrgName));
        for (Map.Entry<String,List<FlwBoardResult>> entry:groupFlw.entrySet()) {
            List<FlwBoardResult> tmpList = entry.getValue();
            //耗时
            List<Double> tmpTimeList = tmpList.stream().map(x -> x.getBoardTime()).collect(Collectors.toList());
            Double tmpHours = sumTime(tmpTimeList);
            //总单量
            long tmpCount = tmpList.size();
            BoardDept tmpBoardDept = new BoardDept();
            tmpBoardDept.setDeptName(entry.getKey());
            tmpBoardDept.setFlwNums(tmpCount);
            tmpBoardDept.setAvgHours(tmpCount > 0 ? (tmpHours/tmpCount) : 0d);
            result.add(tmpBoardDept);
        }
        return result;
    }


    /**
     * 全部流程 - 信息补全
     * @param startDate 流程发起 起始时间
     * @param endDate 流程发起 截止时间
     * @param includeVariable 是否需要指定的流程参数
     * @return
     */
    public List<FlwBoardResult> getFlwBoardData(Date startDate,Date endDate,Boolean includeVariable){
        long start = System.currentTimeMillis();
        System.out.println("全部流程耗时：开始时间 - "+start);
        //框定流程数据范围
        List<HistoricProcessInstance> historicProcessInstanceList = historyService.createHistoricProcessInstanceQuery()
                .startedAfter(startDate).startedBefore(endDate).orderByProcessInstanceStartTime().asc().list();
        if(CollUtil.isEmpty(historicProcessInstanceList)) return null;
        //补充相关流程参数
        List<FlwBoardResult> results = new ArrayList<>();
        Date tmpNowDate = new Date(); //当前时间
        historicProcessInstanceList.forEach(historicProcessInstance -> {
            FlwBoardResult flwBoardResult = new FlwBoardResult();
            flwBoardResult.setId(historicProcessInstance.getId());
            flwBoardResult.setStartTime(DateUtil.formatDateTime(historicProcessInstance.getStartTime()));
            flwBoardResult.setEndTime(ObjectUtil.isNotEmpty(historicProcessInstance.getEndTime()) ?
                    DateUtil.formatDateTime(historicProcessInstance.getEndTime()) : "-");
            String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            flwBoardResult.setProcessDefinitionId(processDefinitionId);
            flwBoardResult.setProcessDefinitionName(historicProcessInstance.getProcessDefinitionName());
            // 创建一个新的 SimpleDateFormat 用于输出新的格式 yyyy-MM-dd
            try {
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
                // 将 Date 格式化为 yyyy-MM-dd 格式的字符串
                String formattedDateStr = newFormat.format(historicProcessInstance.getStartTime());
                // 将格式化后的字符串转换回 Date 对象
                Date yyyyMMddDate = newFormat.parse(formattedDateStr);
                flwBoardResult.setBoardDate(yyyyMMddDate);  //流程发起所属日期
            }catch (Exception e){
            }
            Double hours = 0d;
            if (ObjectUtil.isNotEmpty(historicProcessInstance.getDurationInMillis())){  //毫秒
                // 转换为小时 (1 小时 = 3600000 毫秒)
                hours = historicProcessInstance.getDurationInMillis() / 3600000.0;
                flwBoardResult.setBoardTime(hours);
            }else{
                hours = (tmpNowDate.getTime()-historicProcessInstance.getStartTime().getTime()) / 3600000.0;
                flwBoardResult.setBoardTime(hours); //流程耗时
            }
            String state = historicProcessInstance.getState();
            switch (state) {
                case "ACTIVE":
                    flwBoardResult.setStateCode(state);
                    flwBoardResult.setStateText("审批中");
                    break;
                case "SUSPENDED":
                    flwBoardResult.setStateCode(state);
                    flwBoardResult.setStateText("已挂起");
                    break;
                case "COMPLETED":
                    flwBoardResult.setStateCode(state);
                    flwBoardResult.setStateText("已完成");
                    break;
                case "EXTERNALLY_TERMINATED":
                    String deleteReason = historicProcessInstance.getDeleteReason();
                    switch (deleteReason) {
                        case "END":
                            flwBoardResult.setStateCode(deleteReason);
                            flwBoardResult.setStateText("已终止");
                            break;
                        case "REVOKE":
                            flwBoardResult.setStateCode(deleteReason);
                            flwBoardResult.setStateText("已撤回");
                            break;
                        case "REJECT":
                            flwBoardResult.setStateCode(deleteReason);
                            flwBoardResult.setStateText("已拒绝");
                            break;
                        default:
                            flwBoardResult.setStateCode(deleteReason);
                            flwBoardResult.setStateText("EXTERNALLY_TERMINATED - 未知");
                    }
                    break;
                case "INTERNALLY_TERMINATED":
                    flwBoardResult.setStateCode(state);
                    flwBoardResult.setStateText("内部终止");
                    break;
                default:
                    flwBoardResult.setStateCode(state);
                    flwBoardResult.setStateText("未知");
            }
            //需要特定流程参数
            if(includeVariable) {
                List<HistoricVariableInstance> variableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(historicProcessInstance.getId()).variableNameIn("initiatorOrgName").list();
//                flwBoardResult.setSn(getVariableValue("initiatorProcessSn", variableInstanceList));
//                flwBoardResult.setTitle(getVariableValue("initiatorProcessTitle", variableInstanceList));
//                flwBoardResult.setAbstractTitle(getVariableValue("initiatorProcessAbstract", variableInstanceList));
//                flwBoardResult.setInitiator(getVariableValue("initiator", variableInstanceList));
//                flwBoardResult.setInitiatorName(getVariableValue("initiatorName", variableInstanceList));
//                flwBoardResult.setInitiatorOrgId(getVariableValue("initiatorOrgId", variableInstanceList));
                flwBoardResult.setInitiatorOrgName(getVariableValue("initiatorOrgName", variableInstanceList));
            }
            results.add(flwBoardResult);
        });
        System.out.println("全部流程耗时：- "+ (System.currentTimeMillis() - start));
        return results;
    }

    /**
     * 待办流程 - 全部
     * @param includeVariable 是否需要携带流程参数
     * @return
     */
    public List<FlwTodoBoardResult> getToDoBoardData(Boolean includeVariable){
        //框定待办数据范围[待办列表里的所有]
        List<Task> taskList = taskService.createTaskQuery().active().list();
        if(CollUtil.isEmpty(taskList)) return null;
        //补充相关信息
        Date tmpNowDate = new Date(); //当前时间
        List<FlwTodoBoardResult> result = new ArrayList<>();
        long start = System.currentTimeMillis();
        System.out.println("待办耗时：开始时间 - "+start);
        //框定所有审批人[去重]
        List<String> userIds = taskList.stream().filter(x->ObjectUtil.isNotEmpty(x.getAssignee())).map(x->x.getAssignee()).distinct().collect(Collectors.toList());
        List<JSONObject> userJsonObjectList = getUserListByIds(userIds,10);
        List<JSONObject> finalUserJsonObjectList = userJsonObjectList;
        taskList.forEach(task -> {
            FlwTodoBoardResult flwToDoBoardResult = new FlwTodoBoardResult();
            flwToDoBoardResult.setId(task.getId());
            flwToDoBoardResult.setProcessInstanceId(task.getProcessInstanceId());
            flwToDoBoardResult.setTaskCreateTime(task.getCreateTime());
            Double hours = (tmpNowDate.getTime()-task.getCreateTime().getTime()) / 3600000.0;     //流程耗时
            flwToDoBoardResult.setBoardTime(hours);
            flwToDoBoardResult.setAssigneeId(task.getAssignee());
            if (ObjectUtil.isNotEmpty(task.getAssignee())) {
                // 使用 stream 过滤出 id 为 targetId 的 JSONObject
                Optional<JSONObject> taskAssigneeUser = finalUserJsonObjectList.stream()
                        .filter(json -> json.getStr("id").equals(task.getAssignee())).findFirst(); // 只获取第一个匹配的对象
                if (taskAssigneeUser.isPresent()) {
                    flwToDoBoardResult.setAssignees(taskAssigneeUser.get().getStr("name"));
                }
            }
            //需要特定参数
            if(includeVariable) {
                JSONObject variableJsonObject = JSONUtil.createObj();
                historyService.createHistoricVariableInstanceQuery().processInstanceId(task.getProcessInstanceId()).list()
                        .forEach(historicVariableInstance -> variableJsonObject.set(historicVariableInstance.getName(), historicVariableInstance.getValue()));
                flwToDoBoardResult.setSn(variableJsonObject.getStr("initiatorProcessSn"));
                flwToDoBoardResult.setInitiatorTime(variableJsonObject.getStr("initiatorTime"));
                flwToDoBoardResult.setTitle(variableJsonObject.getStr("initiatorProcessTitle"));
                flwToDoBoardResult.setAbstractTitle(variableJsonObject.getStr("initiatorProcessAbstract"));
            }
            result.add(flwToDoBoardResult);
        });
        System.out.println("待办耗时：- "+ (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * 待办趋势
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @param preStartDate 前一期开始时间
     * @param preEndDate   前一期结束时间
     * @param dataList     待办数据集（全部）
     * @return
     */
    public List<BoardTodo> getFlwToDoEmp(Date startDate,Date endDate,Date preStartDate,Date preEndDate,List<FlwTodoBoardResult> dataList){
        if(CollUtil.isEmpty(dataList)) return null;
        List<BoardTodo> result = new ArrayList<>();
        //按照 id + name 分组
        Map<List<String>,List<FlwTodoBoardResult>> groupFlw = dataList.stream()
                .filter(x->ObjectUtil.isNotEmpty(x.getAssigneeId()) && ObjectUtil.isNotEmpty(x.getAssignees()))
                .collect(Collectors.groupingBy(obj-> Arrays.asList(obj.getAssigneeId(), obj.getAssignees())));
        for (Map.Entry<List<String>,List<FlwTodoBoardResult>> entry:groupFlw.entrySet()) {
            List<FlwTodoBoardResult> tmpList = entry.getValue();
            //耗时
            List<Double> tmpTimeList = tmpList.stream().map(x -> x.getBoardTime()).collect(Collectors.toList());
            Double tmpHours = sumTime(tmpTimeList);
            BoardTodo tmpBoardTodo = new BoardTodo();
            tmpBoardTodo.setUserId(entry.getKey().get(0));
            tmpBoardTodo.setUserName(entry.getKey().get(1));
            tmpBoardTodo.setToDoNums((long) tmpList.size());
            tmpBoardTodo.setDealHours(tmpHours);
            //[startDate,endDate] 与 上一次对比
            List<FlwTodoBoardResult> nowList = tmpList.stream().filter(x -> (x.getTaskCreateTime().after(startDate) && x.getTaskCreateTime().before(endDate))).collect(Collectors.toList());
            List<FlwTodoBoardResult> preList = tmpList.stream().filter(x -> (x.getTaskCreateTime().after(preStartDate) && x.getTaskCreateTime().before(preEndDate))).collect(Collectors.toList());
            int nowListCount = nowList.size();
            int preListCount = preList.size();
            tmpBoardTodo.setToDoNumSate(nowListCount > preListCount ? 1 : (nowListCount < preListCount ? -1 : 0));
            Double nowHours = sumTime(nowList.stream().map(x -> x.getBoardTime()).collect(Collectors.toList()));
            Double preHours = sumTime(preList.stream().map(x -> x.getBoardTime()).collect(Collectors.toList()));
            tmpBoardTodo.setDealHoursSate(nowHours > preHours ? 1 : (nowHours < preHours ? -1 : 0));
            result.add(tmpBoardTodo);
        }
        return result;
    }


    /**
     * 当前页数据 - 补充流程参数
     * @param dataList
     */
    public void getCurrentBoardPage(List<FlwBoardResult> dataList){
        if(CollUtil.isNotEmpty(dataList)){
            dataList.forEach(item->{
                List<HistoricVariableInstance> variableInstanceList = historyService.createHistoricVariableInstanceQuery().processInstanceId(item.getId()).list();
                item.setSn(getVariableValue("initiatorProcessSn", variableInstanceList));
                item.setTitle(getVariableValue("initiatorProcessTitle", variableInstanceList));
                item.setAbstractTitle(getVariableValue("initiatorProcessAbstract", variableInstanceList));
                item.setInitiator(getVariableValue("initiator", variableInstanceList));
                item.setInitiatorName(getVariableValue("initiatorName", variableInstanceList));
                item.setInitiatorOrgId(getVariableValue("initiatorOrgId", variableInstanceList));
                item.setInitiatorOrgName(getVariableValue("initiatorOrgName", variableInstanceList));
            });
        }
    }


    /**
     * 耗时合计
     * @param dataList 时间集合
     * @return
     */
    public Double sumTime(List<Double> dataList ){
        //耗时合计
        Double tmpHours = 0d;
        if(CollUtil.isEmpty(dataList)) return tmpHours;
        for(int i=0;i<dataList.size();i++){
            Double tmpI = dataList.get(i);
            if(ObjectUtil.isNotEmpty(tmpI)) {
                tmpHours += tmpI;
            }
        }
        return tmpHours;
    }


    /**
     * 分批请求用户信息
     * @param userIds 用户id集合
     * @param timeCount 批次大小
     * @return
     */
    public List<JSONObject> getUserListByIds(List<String> userIds,int timeCount){
        List<JSONObject> userJsonObjectList = new ArrayList<>();
        if (CollUtil.isNotEmpty(userIds)) {
            // 将 userIds 列表分割成多个小批次
            int totalSize = userIds.size();
            for (int i = 0; i < totalSize; i += timeCount) {
                // 获取每一批次的子列表
                int end = Math.min(i + timeCount, totalSize);
                List<String> subList = userIds.subList(i, end);
                // 获取当前批次的用户信息
                List<JSONObject> batchResult = sysUserApi.getUserListByIdListWithoutException(subList);
                // 合并当前批次的结果
                if (ObjectUtil.isNotEmpty(batchResult)) {
                    userJsonObjectList.addAll(batchResult);
                }
            }
        }
        return userJsonObjectList;
    }

    @Override
    public void updateFlwProcessParam(FlwProcessParamUpdate flwProcessParamUpdate) {
        String modelId = flwProcessParamUpdate.getModelId();
        FlwModel flwModel = flwModelService.queryEntity(modelId);
        if(CollUtil.isNotEmpty(flwProcessParamUpdate.getProcessInstanceIdList())){
            for(String processInstanceId : flwProcessParamUpdate.getProcessInstanceIdList()){
                JSONObject jsonObject = JSONUtil.parseObj(runtimeService.getVariables(processInstanceId));
                jsonObject.set("initiatorModelInfo", JSONUtil.createObj().set("initiatorModelId",
                        flwModel.getId()).set("initiatorModelJson", flwModel.getProcessJson()).set("initiatorFormJson",
                        flwModel.getFormJson()) .set("initiatorFormType", flwModel.getFormType()));
                runtimeService.setVariables(processInstanceId, jsonObject);
            }
        }
    }

}
