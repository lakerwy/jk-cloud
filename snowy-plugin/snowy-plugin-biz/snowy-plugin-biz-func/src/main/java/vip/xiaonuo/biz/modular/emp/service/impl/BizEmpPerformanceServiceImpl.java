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
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpTask;
import vip.xiaonuo.biz.modular.emp.param.*;
import vip.xiaonuo.biz.modular.emp.service.BizEmpTaskService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.emp.entity.BizEmpPerformance;
import vip.xiaonuo.biz.modular.emp.mapper.BizEmpPerformanceMapper;
import vip.xiaonuo.biz.modular.emp.service.BizEmpPerformanceService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 业务表-员工绩效考核-季度评分审批流程Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/19 08:45
 **/

@DS("slave_1")
@Service
public class BizEmpPerformanceServiceImpl extends ServiceImpl<BizEmpPerformanceMapper, BizEmpPerformance> implements BizEmpPerformanceService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizEmpTaskService bizEmpTaskService;

    @Override
    public Page<BizEmpPerformance> page(BizEmpPerformancePageParam bizEmpPerformancePageParam) {
        QueryWrapper<BizEmpPerformance> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getSn())) {
            queryWrapper.lambda().like(BizEmpPerformance::getSn, bizEmpPerformancePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserId())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getUserId, bizEmpPerformancePageParam.getUserId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getUserName())) {
            queryWrapper.lambda().like(BizEmpPerformance::getUserName, bizEmpPerformancePageParam.getUserName());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getOrgId())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getOrgId, bizEmpPerformancePageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getYear())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getYear, bizEmpPerformancePageParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getSeason())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getSeason, bizEmpPerformancePageParam.getSeason());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getEmpSeasonLevel())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getEmpSeasonLevel, bizEmpPerformancePageParam.getEmpSeasonLevel());
        }
        if(ObjectUtil.isNotEmpty(bizEmpPerformancePageParam.getStatus())) {
            queryWrapper.lambda().eq(BizEmpPerformance::getStatus, bizEmpPerformancePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(bizEmpPerformancePageParam.getSortField(), bizEmpPerformancePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizEmpPerformancePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizEmpPerformancePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizEmpPerformancePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizEmpPerformance::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizEmpPerformanceAddParam bizEmpPerformanceAddParam) {
        BizEmpPerformance bizEmpPerformance = BeanUtil.toBean(bizEmpPerformanceAddParam, BizEmpPerformance.class);
        //走合同审批流程
        String processInstanceId = bizEmpPerformanceAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizEmpPerformance.setId(id);
            bizEmpPerformance.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizEmpPerformance.setModelId(modelId);
            bizEmpPerformance.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_EMP_PERFORMANCE.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizEmpPerformance.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizEmpPerformance);
        //反写流程中的任务信息
        try{
            List<EmpTaskParam> paramList = JSONUtil.toList(bizEmpPerformanceAddParam.getEmpTaskJson(), EmpTaskParam.class);
            List<BizEmpTask> bizEmpTaskList = BeanUtil.copyToList(paramList,BizEmpTask.class);
            bizEmpTaskService.updateBatchById(bizEmpTaskList);
        }catch (Exception ex){
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizEmpPerformanceEditParam bizEmpPerformanceEditParam) {
        String id = bizEmpPerformanceEditParam.getId();
        //判断是否走合同审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizEmpPerformanceEditParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(bizEmpPerformanceEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizEmpPerformanceEditParam.setId(id);
            }
        }
        BizEmpPerformance bizEmpPerformance = this.queryEntity(bizEmpPerformanceEditParam.getId());
        BeanUtil.copyProperties(bizEmpPerformanceEditParam, bizEmpPerformance);
        this.updateById(bizEmpPerformance);
        //反写流程中的任务信息
        try{
            List<EmpTaskParam> paramList = JSONUtil.toList(bizEmpPerformanceEditParam.getEmpTaskJson(), EmpTaskParam.class);
            List<BizEmpTask> bizEmpTaskList = BeanUtil.copyToList(paramList,BizEmpTask.class);
            bizEmpTaskService.updateBatchById(bizEmpTaskList);
        }catch (Exception ex){
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizEmpPerformanceIdParam> bizEmpPerformanceIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizEmpPerformanceIdParamList, BizEmpPerformanceIdParam::getId));
    }

    @Override
    public BizEmpPerformance detail(BizEmpPerformanceIdParam bizEmpPerformanceIdParam) {
        return this.queryEntity(bizEmpPerformanceIdParam.getId());
    }

    @Override
    public BizEmpPerformance queryEntity(String id) {
        BizEmpPerformance bizEmpPerformance = this.getById(id);
        if(ObjectUtil.isEmpty(bizEmpPerformance)) {
            throw new CommonException("业务表-员工绩效考核-季度评分审批流程不存在，id值为：{}", id);
        }
        return bizEmpPerformance;
    }
}
