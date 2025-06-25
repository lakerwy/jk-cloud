package vip.xiaonuo.biz.modular.orgPerf.perfAss.service.impl;

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
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizOrgPerfAss;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.mapper.BizOrgPerfAssMapper;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizOrgPerfAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizOrgPerfAssService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 子公司及生产部门绩效考核-绩效评估Service接口实现类
 *
 * @author pzh
 * @date  2025/06/25 15:01
 **/
@Service
@DS("slave_1")
public class BizOrgPerfAssServiceImpl extends ServiceImpl<BizOrgPerfAssMapper, BizOrgPerfAss> implements BizOrgPerfAssService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Override
    public Page<BizOrgPerfAss> page(BizOrgPerfAssPageParam bizOrgPerfAssPageParam) {
        QueryWrapper<BizOrgPerfAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizOrgPerfAssPageParam.getSortField(), bizOrgPerfAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizOrgPerfAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizOrgPerfAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizOrgPerfAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizOrgPerfAss::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizOrgPerfAssAddParam bizOrgPerfAssAddParam) {
        String goalId = bizOrgPerfAssAddParam.getGoalId();
        long count = this.count(new QueryWrapper<BizOrgPerfAss>().lambda().eq(BizOrgPerfAss::getGoalId, goalId)
                .eq(BizOrgPerfAss::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(count > 0) {
            throw new CommonException("存在审批中的绩效评估流程，不能再次发起此流程");
        }
        BizOrgPerfAss bizOrgPerfAss = BeanUtil.toBean(bizOrgPerfAssAddParam, BizOrgPerfAss.class);
        //走审批流程
        String processInstanceId = bizOrgPerfAssAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizOrgPerfAss.setId(id);
            bizOrgPerfAss.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizOrgPerfAss.setModelId(modelId);
            bizOrgPerfAss.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_ORG_PERF_ASS.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizOrgPerfAss.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizOrgPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizOrgPerfAssEditParam bizOrgPerfAssEditParam) {
        String id = bizOrgPerfAssEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizOrgPerfAssEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizOrgPerfAssEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizOrgPerfAssEditParam.setId(id);
            }
        }
        BizOrgPerfAss bizOrgPerfAss = this.queryEntity(bizOrgPerfAssEditParam.getId());
        BeanUtil.copyProperties(bizOrgPerfAssEditParam, bizOrgPerfAss);
        this.updateById(bizOrgPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizOrgPerfAssIdParam> bizOrgPerfAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizOrgPerfAssIdParamList, BizOrgPerfAssIdParam::getId));
    }

    @Override
    public BizOrgPerfAss detail(BizOrgPerfAssIdParam bizOrgPerfAssIdParam) {
        return this.queryEntity(bizOrgPerfAssIdParam.getId());
    }

    @Override
    public BizOrgPerfAss queryEntity(String id) {
        BizOrgPerfAss bizOrgPerfAss = this.getById(id);
        if(ObjectUtil.isEmpty(bizOrgPerfAss)) {
            throw new CommonException("子公司及生产部门绩效考核-绩效评估不存在，id值为：{}", id);
        }
        return bizOrgPerfAss;
    }
}
