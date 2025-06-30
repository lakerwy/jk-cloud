package vip.xiaonuo.biz.modular.orgPerf.rating.service.impl;

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
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizFunDeptPerfAss;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.rating.entity.BizFunDeptRating;
import vip.xiaonuo.biz.modular.orgPerf.rating.mapper.BizFunDeptRatingMapper;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingAddParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingEditParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingIdParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.param.BizFunDeptRatingPageParam;
import vip.xiaonuo.biz.modular.orgPerf.rating.service.BizFunDeptRatingService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职能部门-绩效评分Service接口实现类
 *
 * @author pzh
 * @date  2025/06/30 16:37
 **/
@Service
@DS("slave_1")
public class BizFunDeptRatingServiceImpl extends ServiceImpl<BizFunDeptRatingMapper, BizFunDeptRating> implements BizFunDeptRatingService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Override
    public Page<BizFunDeptRating> page(BizFunDeptRatingPageParam bizFunDeptRatingPageParam) {
        QueryWrapper<BizFunDeptRating> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizFunDeptRatingPageParam.getSortField(), bizFunDeptRatingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFunDeptRatingPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFunDeptRatingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFunDeptRatingPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFunDeptRating::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFunDeptRatingAddParam bizFunDeptRatingAddParam) {
        long count = this.count(new QueryWrapper<BizFunDeptRating>().lambda().eq(BizFunDeptRating::getKpiId, bizFunDeptRatingAddParam.getKpiId())
                .eq(BizFunDeptRating::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(count > 0) {
            throw new CommonException("存在审批中的绩效评分流程，不能再次发起此流程");
        }
        BizFunDeptRating bizFunDeptRating = BeanUtil.toBean(bizFunDeptRatingAddParam, BizFunDeptRating.class);
        //走审批流程
        String processInstanceId = bizFunDeptRatingAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFunDeptRating.setId(id);
            bizFunDeptRating.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFunDeptRating.setModelId(modelId);
            bizFunDeptRating.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FUN_DEPT_RATING.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFunDeptRating.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFunDeptRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFunDeptRatingEditParam bizFunDeptRatingEditParam) {
        String id = bizFunDeptRatingEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFunDeptRatingEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFunDeptRatingEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFunDeptRatingEditParam.setId(id);
            }
        }
        BizFunDeptRating bizFunDeptRating = this.queryEntity(bizFunDeptRatingEditParam.getId());
        BeanUtil.copyProperties(bizFunDeptRatingEditParam, bizFunDeptRating);
        this.updateById(bizFunDeptRating);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFunDeptRatingIdParam> bizFunDeptRatingIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFunDeptRatingIdParamList, BizFunDeptRatingIdParam::getId));
    }

    @Override
    public BizFunDeptRating detail(BizFunDeptRatingIdParam bizFunDeptRatingIdParam) {
        return this.queryEntity(bizFunDeptRatingIdParam.getId());
    }

    @Override
    public BizFunDeptRating queryEntity(String id) {
        BizFunDeptRating bizFunDeptRating = this.getById(id);
        if(ObjectUtil.isEmpty(bizFunDeptRating)) {
            throw new CommonException("职能部门-绩效评分不存在，id值为：{}", id);
        }
        return bizFunDeptRating;
    }
}
