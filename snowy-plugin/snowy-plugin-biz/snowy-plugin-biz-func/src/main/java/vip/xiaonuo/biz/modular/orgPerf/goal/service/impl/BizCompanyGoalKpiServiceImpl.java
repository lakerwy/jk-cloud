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
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizCompanyGoalKpiMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalKpiService;

import java.util.List;

/**
 * 公司绩效考核-考核指标Service接口实现类
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Service
@DS("slave_1")
public class BizCompanyGoalKpiServiceImpl extends ServiceImpl<BizCompanyGoalKpiMapper, BizCompanyGoalKpi> implements BizCompanyGoalKpiService {

    @Override
    public Page<BizCompanyGoalKpi> page(BizCompanyGoalKpiPageParam bizCompanyGoalKpiPageParam) {
        QueryWrapper<BizCompanyGoalKpi> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizCompanyGoalKpiPageParam.getSortField(), bizCompanyGoalKpiPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizCompanyGoalKpiPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizCompanyGoalKpiPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizCompanyGoalKpiPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizCompanyGoalKpi::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCompanyGoalKpiAddParam bizCompanyGoalKpiAddParam) {
        BizCompanyGoalKpi bizCompanyGoalKpi = BeanUtil.toBean(bizCompanyGoalKpiAddParam, BizCompanyGoalKpi.class);
        this.save(bizCompanyGoalKpi);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCompanyGoalKpiEditParam bizCompanyGoalKpiEditParam) {
        BizCompanyGoalKpi bizCompanyGoalKpi = this.queryEntity(bizCompanyGoalKpiEditParam.getId());
        BeanUtil.copyProperties(bizCompanyGoalKpiEditParam, bizCompanyGoalKpi);
        this.updateById(bizCompanyGoalKpi);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizCompanyGoalKpiIdParam> bizCompanyGoalKpiIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(bizCompanyGoalKpiIdParamList, BizCompanyGoalKpiIdParam::getId));
    }

    @Override
    public BizCompanyGoalKpi detail(BizCompanyGoalKpiIdParam bizCompanyGoalKpiIdParam) {
        return this.queryEntity(bizCompanyGoalKpiIdParam.getId());
    }

    @Override
    public BizCompanyGoalKpi queryEntity(String id) {
        BizCompanyGoalKpi bizCompanyGoalKpi = this.getById(id);
        if(ObjectUtil.isEmpty(bizCompanyGoalKpi)) {
            throw new CommonException("公司绩效考核-考核指标不存在，id值为：{}", id);
        }
        return bizCompanyGoalKpi;
    }
}
