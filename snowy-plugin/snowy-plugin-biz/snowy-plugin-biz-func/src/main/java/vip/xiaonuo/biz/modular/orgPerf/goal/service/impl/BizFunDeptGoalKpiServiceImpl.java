package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizFunDeptGoalKpiMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalKpiService;

import java.util.List;

/**
 * 职能部门绩效考核-考核指标Service接口实现类
 *
 * @author pzh
 * @date  2025/06/26 16:36
 **/
@Service
@DS("slave_1")
public class BizFunDeptGoalKpiServiceImpl extends ServiceImpl<BizFunDeptGoalKpiMapper, BizFunDeptGoalKpi> implements BizFunDeptGoalKpiService {

    @Override
    public Page<BizFunDeptGoalKpi> page(BizFunDeptGoalKpiPageParam bizFunDeptGoalKpiPageParam) {
        QueryWrapper<BizFunDeptGoalKpi> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizFunDeptGoalKpiPageParam.getSortField(), bizFunDeptGoalKpiPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFunDeptGoalKpiPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFunDeptGoalKpiPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFunDeptGoalKpiPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFunDeptGoalKpi::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFunDeptGoalKpiAddParam bizFunDeptGoalKpiAddParam) {
        BizFunDeptGoalKpi bizFunDeptGoalKpi = BeanUtil.toBean(bizFunDeptGoalKpiAddParam, BizFunDeptGoalKpi.class);
        this.save(bizFunDeptGoalKpi);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFunDeptGoalKpiEditParam bizFunDeptGoalKpiEditParam) {
        BizFunDeptGoalKpi bizFunDeptGoalKpi = this.queryEntity(bizFunDeptGoalKpiEditParam.getId());
        BeanUtil.copyProperties(bizFunDeptGoalKpiEditParam, bizFunDeptGoalKpi);
        this.updateById(bizFunDeptGoalKpi);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFunDeptGoalKpiIdParam> bizFunDeptGoalKpiIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizFunDeptGoalKpiIdParamList, BizFunDeptGoalKpiIdParam::getId));
    }

    @Override
    public BizFunDeptGoalKpi detail(BizFunDeptGoalKpiIdParam bizFunDeptGoalKpiIdParam) {
        return this.queryEntity(bizFunDeptGoalKpiIdParam.getId());
    }

    @Override
    public BizFunDeptGoalKpi queryEntity(String id) {
        BizFunDeptGoalKpi bizFunDeptGoalKpi = this.getById(id);
        if(ObjectUtil.isEmpty(bizFunDeptGoalKpi)) {
            throw new CommonException("职能部门绩效考核-考核指标不存在，id值为：{}", id);
        }
        return bizFunDeptGoalKpi;
    }
}
