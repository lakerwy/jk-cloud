package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizOrgPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizOrgPerfAssService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizGoalModifyMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizGoalModifyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 组织绩效考核-目标调整Service接口实现类
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Service
@DS("slave_1")
public class BizGoalModifyServiceImpl extends ServiceImpl<BizGoalModifyMapper, BizGoalModify> implements BizGoalModifyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Resource
    private BizOrgPerfAssService bizOrgPerfAssService;

    @Override
    public Page<BizGoalModify> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam) {
        QueryWrapper<BizGoalModify> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizCompanyGoalModifyPageParam.getSortField(), bizCompanyGoalModifyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizCompanyGoalModifyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizCompanyGoalModifyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizCompanyGoalModifyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizGoalModify::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizGoalModifyAddParam bizGoalModifyAddParam) {
        String goalId = bizGoalModifyAddParam.getGoalId();
        long modifyCount = this.count(new LambdaQueryWrapper<BizGoalModify>().eq(BizGoalModify::getGoalId, goalId)
                .eq(BizGoalModify::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(modifyCount > 0) {
            throw new CommonException("存在审批中的目标调整流程，不能再次发起此流程");
        }
        long assCount = bizOrgPerfAssService.count(new LambdaQueryWrapper<BizOrgPerfAss>().eq(BizOrgPerfAss::getGoalId, goalId)
                .in(BizOrgPerfAss::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue(), FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(assCount > 0) {
            throw new CommonException("存在审批中或已完成的绩效评估流程，不能发起此流程");
        }
        BizGoalModify bizGoalModify = BeanUtil.toBean(bizGoalModifyAddParam, BizGoalModify.class);
        //走审批流程
        String processInstanceId = bizGoalModifyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizGoalModify.setId(id);
            bizGoalModify.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizGoalModify.setModelId(modelId);
            bizGoalModify.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_GOAL_MODIFY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizGoalModify.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizGoalModify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizGoalModifyEditParam bizGoalModifyEditParam) {
        String id = bizGoalModifyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizGoalModifyEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizGoalModifyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizGoalModifyEditParam.setId(id);
            }
        }
        BizGoalModify bizGoalModify = this.queryEntity(bizGoalModifyEditParam.getId());
        BeanUtil.copyProperties(bizGoalModifyEditParam, bizGoalModify);
        this.updateById(bizGoalModify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizGoalModifyIdParam> bizGoalModifyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizGoalModifyIdParamList, BizGoalModifyIdParam::getId));
    }

    @Override
    public BizGoalModify detail(BizGoalModifyIdParam bizGoalModifyIdParam) {
        return this.queryEntity(bizGoalModifyIdParam.getId());
    }

    @Override
    public BizGoalModify queryEntity(String id) {
        BizGoalModify bizGoalModify = this.getById(id);
        if(ObjectUtil.isEmpty(bizGoalModify)) {
            throw new CommonException("组织绩效考核-目标调整不存在，id值为：{}", id);
        }
        return bizGoalModify;
    }
}
