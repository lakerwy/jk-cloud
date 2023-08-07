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
package vip.xiaonuo.biz.modular.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.org.entity.BizOrg;
import vip.xiaonuo.biz.modular.org.service.BizOrgService;
import vip.xiaonuo.biz.modular.task.Vo.DeptTaskResult;
import vip.xiaonuo.biz.modular.task.entity.BizTaskFlw;
import vip.xiaonuo.biz.modular.task.enums.BizTaskStatusEnum;
import vip.xiaonuo.biz.modular.task.param.*;
import vip.xiaonuo.biz.modular.task.service.BizTaskFlwService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.mapper.BizTaskMapper;
import vip.xiaonuo.biz.modular.task.service.BizTaskService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 业务表-绩效考核-任务表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/

@DS("slave_1")
@Service
public class BizTaskServiceImpl extends ServiceImpl<BizTaskMapper, BizTask> implements BizTaskService {

    @Resource
    private BizTaskFlwService bizTaskFlwService;
    @Resource
    CommonCacheOperator commonCacheOperator;


    @Override
    public List<DeptTaskResult> page(BizTaskPageParam bizTaskPageParam) {
        QueryWrapper<BizTask> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizTask::getOrgId, bizTaskPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getTaskName())) {
            queryWrapper.lambda().like(BizTask::getTaskName, bizTaskPageParam.getTaskName());
        }
        if(ObjectUtil.isNotEmpty(bizTaskPageParam.getYear())) {
            queryWrapper.lambda().like(BizTask::getYear, bizTaskPageParam.getYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizTaskPageParam.getSortField(), bizTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizTask::getCreateTime);
        }
        //直接查询所有
        bizTaskPageParam.setCurrent(1);
        bizTaskPageParam.setSize(Integer.MAX_VALUE);
        List<BizTask> result = this.page(CommonPageRequest.defaultPage(), queryWrapper).getRecords();
//        List<Tree<String>> bizOrgList = sysOrgApi.orgTreeSelector();
        List<DeptTaskResult> deptTaskResultList = new ArrayList<>();
        //构造结构
        if(CollUtil.isNotEmpty(result)) {
            List<String> orgResult = result.stream().map(x -> x.getOrgId()).distinct().collect(Collectors.toList());
            for (String orgId:orgResult) {
                DeptTaskResult deptTaskResult = new DeptTaskResult();
                deptTaskResult.setOrgId(orgId);
                String orgName = "数字化研究院";
                deptTaskResult.setOrgName(orgName);
                deptTaskResult.setTaskList(result.stream().filter(x->x.getOrgId().equals(orgId)).collect(Collectors.toList()));
                deptTaskResultList.add(deptTaskResult);
            }
        }
        return deptTaskResultList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizTaskAddParam bizTaskAddParam) {
        BizTask bizTask = BeanUtil.toBean(bizTaskAddParam, BizTask.class);
        this.save(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizTaskEditParam bizTaskEditParam) {
        BizTask bizTask = this.queryEntity(bizTaskEditParam.getId());
        BeanUtil.copyProperties(bizTaskEditParam, bizTask);
        this.updateById(bizTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizTaskIdParam> bizTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizTaskIdParamList, BizTaskIdParam::getId));
    }

    @Override
    public BizTask detail(BizTaskIdParam bizTaskIdParam) {
        BizTask bizTask = this.queryEntity(bizTaskIdParam.getId());
        String orgName = "数字化研究院";
        bizTask.setOrgName(orgName);
        return bizTask;
    }

    @Override
    public BizTask queryEntity(String id) {
        BizTask bizTask = this.getById(id);
        if(ObjectUtil.isEmpty(bizTask)) {
            throw new CommonException("业务表-绩效考核-任务表不存在，id值为：{}", id);
        }
        return bizTask;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwAdd(TaskFlwParam taskFlwParam) {
        //部门年度重点任务审批流程
        String processInstanceId = taskFlwParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            //第一步： 任务流程单（类似与其他的单一流程业务表）
            BizTaskFlw bizTaskFlw = new BizTaskFlw();
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizTaskFlw.setId(id);
            bizTaskFlw.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizTaskFlw.setModelId(modelId);
            bizTaskFlw.setProcessInstanceId(processInstanceId);
            bizTaskFlw.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizTaskFlw.setExtJson(JsonUtils.object2json(taskFlwParam));
            bizTaskFlwService.save(bizTaskFlw);

            //第二步： 存储流程表对应关系
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来(任务流程单表)
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.MASTER_DATASOURCE_NAME.toLowerCase()));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_TASK_FLW.getBizTable(),id,processInstanceId,dbName);

            //第三步： 处理绩效考核年度重要任务记录表（审批流程中一次提交多条年度重要任务）
            List<BizTask> bizTaskList = new ArrayList<>();
            for (FlwParam item : taskFlwParam.getFlwParamList()) {
                BizTask bizTask = new BizTask();
                BeanUtil.copyProperties(item, bizTask);
                bizTask.setFlwId(bizTaskFlw.getId());
                bizTask.setOrgId(taskFlwParam.getOrgId());
                bizTask.setOwer(taskFlwParam.getOwer());
                bizTask.setYear(taskFlwParam.getYear());
                bizTask.setStatus(BizTaskStatusEnum.ACTIVE_STATUS.getValue());
                bizTaskList.add(bizTask);
            }
            if(CollUtil.isNotEmpty(bizTaskList)) {
                this.saveBatch(bizTaskList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwEdit(TaskFlwParam taskFlwParam) {
        //任务流程清单主键Id，与流程表单对应
        String taskFlwId = "";
        //判断审批流程内的修改
        if(ObjectUtil.isNotEmpty(taskFlwParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(taskFlwParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                taskFlwId = formRelationTable.getStr("id");
            }
        }
        if(StrUtil.isNotEmpty(taskFlwId)){
            BizTaskFlw bizTaskFlw = bizTaskFlwService.queryEntity(taskFlwId);
            bizTaskFlw.setExtJson(JsonUtils.object2json(taskFlwParam));
            bizTaskFlwService.updateById(bizTaskFlw);
            //处理该流程的业务数据
            List<BizTask> oldBizTaskList = this.list(new QueryWrapper<BizTask>().lambda().eq(BizTask::getFlwId,taskFlwId));
            this.removeByIds(oldBizTaskList);
            //先删除旧的业务数据，再重新新增
            List<BizTask> newBizTaskList = new ArrayList<>();
            for (FlwParam item : taskFlwParam.getFlwParamList()) {
                BizTask bizTask = new BizTask();
                BeanUtil.copyProperties(item, bizTask);
                bizTask.setFlwId(bizTaskFlw.getId());
                bizTask.setOrgId(taskFlwParam.getOrgId());
                bizTask.setOwer(taskFlwParam.getOwer());
                bizTask.setYear(taskFlwParam.getYear());
                bizTask.setStatus(BizTaskStatusEnum.ACTIVE_STATUS.getValue());
                newBizTaskList.add(bizTask);
            }
            if(CollUtil.isNotEmpty(newBizTaskList)) {
                this.saveBatch(newBizTaskList);
            }
        }
    }
}
