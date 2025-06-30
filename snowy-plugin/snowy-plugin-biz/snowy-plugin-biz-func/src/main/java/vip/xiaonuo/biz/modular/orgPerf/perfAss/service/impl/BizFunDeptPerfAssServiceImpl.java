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
import vip.xiaonuo.biz.modular.orgPerf.perfAss.entity.BizFunDeptPerfAss;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.mapper.BizFunDeptPerfAssMapper;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssAddParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssEditParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssIdParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.param.BizFunDeptPerfAssPageParam;
import vip.xiaonuo.biz.modular.orgPerf.perfAss.service.BizFunDeptPerfAssService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职能部门-绩效评估Service接口实现类
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
@Service
@DS("slave_1")
public class BizFunDeptPerfAssServiceImpl extends ServiceImpl<BizFunDeptPerfAssMapper, BizFunDeptPerfAss> implements BizFunDeptPerfAssService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Override
    public Page<BizFunDeptPerfAss> page(BizFunDeptPerfAssPageParam bizFunDeptPerfAssPageParam) {
        QueryWrapper<BizFunDeptPerfAss> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizFunDeptPerfAssPageParam.getSortField(), bizFunDeptPerfAssPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFunDeptPerfAssPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFunDeptPerfAssPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFunDeptPerfAssPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFunDeptPerfAss::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFunDeptPerfAssAddParam bizFunDeptPerfAssAddParam) {
        String goalId = bizFunDeptPerfAssAddParam.getGoalId();
        long count = this.count(new QueryWrapper<BizFunDeptPerfAss>().lambda().eq(BizFunDeptPerfAss::getGoalId, goalId)
                .eq(BizFunDeptPerfAss::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(count > 0) {
            throw new CommonException("存在审批中的绩效评估流程，不能再次发起此流程");
        }
        BizFunDeptPerfAss bizFunDeptPerfAss = BeanUtil.toBean(bizFunDeptPerfAssAddParam, BizFunDeptPerfAss.class);
        //走审批流程
        String processInstanceId = bizFunDeptPerfAssAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFunDeptPerfAss.setId(id);
            bizFunDeptPerfAss.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFunDeptPerfAss.setModelId(modelId);
            bizFunDeptPerfAss.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FUN_DEPT_PERF_ASS.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFunDeptPerfAss.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFunDeptPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFunDeptPerfAssEditParam bizFunDeptPerfAssEditParam) {
        String id = bizFunDeptPerfAssEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFunDeptPerfAssEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFunDeptPerfAssEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFunDeptPerfAssEditParam.setId(id);
            }
        }
        BizFunDeptPerfAss bizFunDeptPerfAss = this.queryEntity(bizFunDeptPerfAssEditParam.getId());
        BeanUtil.copyProperties(bizFunDeptPerfAssEditParam, bizFunDeptPerfAss);
        this.updateById(bizFunDeptPerfAss);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFunDeptPerfAssIdParam> bizFunDeptPerfAssIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFunDeptPerfAssIdParamList, BizFunDeptPerfAssIdParam::getId));
    }

    @Override
    public BizFunDeptPerfAss detail(BizFunDeptPerfAssIdParam bizFunDeptPerfAssIdParam) {
        return this.queryEntity(bizFunDeptPerfAssIdParam.getId());
    }

    @Override
    public BizFunDeptPerfAss queryEntity(String id) {
        BizFunDeptPerfAss bizFunDeptPerfAss = this.getById(id);
        if(ObjectUtil.isEmpty(bizFunDeptPerfAss)) {
            throw new CommonException("职能部门-绩效评估不存在，id值为：{}", id);
        }
        return bizFunDeptPerfAss;
    }
}
