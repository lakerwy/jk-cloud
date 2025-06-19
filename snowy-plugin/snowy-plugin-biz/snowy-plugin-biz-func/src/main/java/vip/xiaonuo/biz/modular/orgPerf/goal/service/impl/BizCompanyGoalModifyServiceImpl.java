package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizCompanyGoalModifyMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalModifyService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司绩效考核-目标调整Service接口实现类
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Service
@DS("slave_1")
public class BizCompanyGoalModifyServiceImpl extends ServiceImpl<BizCompanyGoalModifyMapper, BizCompanyGoalModify> implements BizCompanyGoalModifyService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Override
    public Page<BizCompanyGoalModify> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam) {
        QueryWrapper<BizCompanyGoalModify> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizCompanyGoalModifyPageParam.getSortField(), bizCompanyGoalModifyPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizCompanyGoalModifyPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizCompanyGoalModifyPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizCompanyGoalModifyPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizCompanyGoalModify::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCompanyGoalModifyAddParam bizCompanyGoalModifyAddParam) {
        String goalId = bizCompanyGoalModifyAddParam.getGoalId();
        long count = this.count(new QueryWrapper<BizCompanyGoalModify>().lambda().eq(BizCompanyGoalModify::getGoalId, goalId)
                .eq(BizCompanyGoalModify::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(count > 0) {
            throw new CommonException("存在审批中的目标调整流程，不能再次发起此流程");
        }
        BizCompanyGoalModify bizCompanyGoalModify = BeanUtil.toBean(bizCompanyGoalModifyAddParam, BizCompanyGoalModify.class);
        //走审批流程
        String processInstanceId = bizCompanyGoalModifyAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizCompanyGoalModify.setId(id);
            bizCompanyGoalModify.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizCompanyGoalModify.setModelId(modelId);
            bizCompanyGoalModify.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_COMPANY_GOAL_MODIFY.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizCompanyGoalModify.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizCompanyGoalModify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCompanyGoalModifyEditParam bizCompanyGoalModifyEditParam) {
        String id = bizCompanyGoalModifyEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizCompanyGoalModifyEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizCompanyGoalModifyEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizCompanyGoalModifyEditParam.setId(id);
            }
        }
        BizCompanyGoalModify bizCompanyGoalModify = this.queryEntity(bizCompanyGoalModifyEditParam.getId());
        BeanUtil.copyProperties(bizCompanyGoalModifyEditParam, bizCompanyGoalModify);
        this.updateById(bizCompanyGoalModify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizCompanyGoalModifyIdParam> bizCompanyGoalModifyIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizCompanyGoalModifyIdParamList, BizCompanyGoalModifyIdParam::getId));
    }

    @Override
    public BizCompanyGoalModify detail(BizCompanyGoalModifyIdParam bizCompanyGoalModifyIdParam) {
        return this.queryEntity(bizCompanyGoalModifyIdParam.getId());
    }

    @Override
    public BizCompanyGoalModify queryEntity(String id) {
        BizCompanyGoalModify bizCompanyGoalModify = this.getById(id);
        if(ObjectUtil.isEmpty(bizCompanyGoalModify)) {
            throw new CommonException("公司绩效考核-目标调整不存在，id值为：{}", id);
        }
        return bizCompanyGoalModify;
    }
}
