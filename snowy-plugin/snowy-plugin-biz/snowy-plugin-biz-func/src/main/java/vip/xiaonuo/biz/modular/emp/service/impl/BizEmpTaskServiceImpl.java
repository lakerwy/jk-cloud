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
package vip.xiaonuo.biz.modular.emp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTaskFlw;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskFlwService;
import vip.xiaonuo.biz.modular.inslibrary.entity.BizInsLinkman;
import vip.xiaonuo.biz.modular.task.entity.BizTask;
import vip.xiaonuo.biz.modular.task.entity.BizTaskFlw;
import vip.xiaonuo.biz.modular.task.enums.BizTaskStatusEnum;
import vip.xiaonuo.biz.modular.task.param.FlwParam;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpTaskMapper;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务表-员工绩效考核-季度任务表Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/19 08:47
 **/
@DS("slave_1")
@Service
public class BizEmpTaskServiceImpl extends ServiceImpl<BizEmpTaskMapper, BizEmpTask> implements BizEmpTaskService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizEmpTaskFlwService bizEmpTaskFlwService;

    @Override
    public Page<BizEmpTask> page(BizEmpTaskPageParam bizEmpTaskPageParam) {
        QueryWrapper<BizEmpTask> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getUserId())) {
            queryWrapper.lambda().eq(BizEmpTask::getUserId, bizEmpTaskPageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getUserName())) {
            queryWrapper.lambda().like(BizEmpTask::getUserName, bizEmpTaskPageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizEmpTask::getOrgId, bizEmpTaskPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getYear())) {
            queryWrapper.lambda().eq(BizEmpTask::getYear, bizEmpTaskPageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getSeason())) {
            queryWrapper.lambda().eq(BizEmpTask::getSeason, bizEmpTaskPageParam.getSeason());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getTitle())) {
            queryWrapper.lambda().like(BizEmpTask::getTitle, bizEmpTaskPageParam.getTitle());
        }
        if(ObjectUtil.isNotEmpty(bizEmpTaskPageParam.getGoal())) {
            queryWrapper.lambda().like(BizEmpTask::getGoal, bizEmpTaskPageParam.getGoal());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpTaskPageParam.getSortField(), bizEmpTaskPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpTaskPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpTaskPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpTaskPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizEmpTask::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizEmpTaskAddParam bizEmpTaskAddParam) {
        BizEmpTask bizEmpTask = BeanUtil.toBean(bizEmpTaskAddParam, BizEmpTask.class);
        this.save(bizEmpTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpTaskEditParam bizEmpTaskEditParam) {
        BizEmpTask bizEmpTask = this.queryEntity(bizEmpTaskEditParam.getId());
        BeanUtil.copyProperties(bizEmpTaskEditParam, bizEmpTask);
        this.updateById(bizEmpTask);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpTaskIdParam> bizEmpTaskIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizEmpTaskIdParamList, BizEmpTaskIdParam::getId));
    }

    @Override
    public BizEmpTask detail(BizEmpTaskIdParam bizEmpTaskIdParam) {
        return this.queryEntity(bizEmpTaskIdParam.getId());
    }

    @Override
    public BizEmpTask queryEntity(String id) {
        BizEmpTask bizEmpTask = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpTask)) {
            throw new CommonException("业务表-员工绩效考核-季度任务表不存在，id值为：{}", id);
        }
        return bizEmpTask;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void flwAdd(EmpTaskFlwParam empTaskFlwParam) {
        //流程审批员工季度任务
        String processInstanceId = empTaskFlwParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            //第一步： 任务流程
            BizEmpTaskFlw bizEmpTaskFlw = new BizEmpTaskFlw();
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizEmpTaskFlw.setId(id);
            bizEmpTaskFlw.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizEmpTaskFlw.setModelId(modelId);
            bizEmpTaskFlw.setProcessInstanceId(processInstanceId);
            bizEmpTaskFlw.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizEmpTaskFlw.setExtJson(JsonUtils.object2json(empTaskFlwParam));
            bizEmpTaskFlwService.save(bizEmpTaskFlw);

            //第二步： 存储流程表对应关系
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来(任务流程单表)
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save2(modelId, BizCommonEnum.BIZ_EMP_TASK_FLW.getBizTable(),id,processInstanceId,dbName,BizCommonEnum.BIZ_EMP_TASK.getBizTable());

            //第三步： 处理员工绩效考核季度重要任务记录表（审批流程中一次提交多条季度重要任务）
            List<BizEmpTask> bizEmpTaskList = BeanUtil.copyToList(empTaskFlwParam.getParamList(),BizEmpTask.class);
            bizEmpTaskList.forEach(x->{
                x.setUserId(empTaskFlwParam.getUserId());
                x.setUserName(empTaskFlwParam.getUserName());
                x.setOrgId(empTaskFlwParam.getOrgId());
                x.setYear(empTaskFlwParam.getYear());
                x.setSeason(empTaskFlwParam.getSeason());
                x.setStatus(BizTaskStatusEnum.ACTIVE_STATUS.getValue());
                x.setFlwId(bizEmpTaskFlw.getId());
            });
            if(CollUtil.isNotEmpty(bizEmpTaskList)) {
                this.saveBatch(bizEmpTaskList);
            }
        }
    }
}
