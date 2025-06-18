package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalKpiService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizCompanyGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司绩效考核-目标登记Service接口实现类
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Service
@DS("slave_1")
public class BizCompanyGoalServiceImpl extends ServiceImpl<BizCompanyGoalMapper, BizCompanyGoal> implements BizCompanyGoalService {

    @Resource
    private BizCompanyGoalKpiService bizCompanyGoalKpiService;

    @Override
    public Page<BizCompanyGoal> page(BizCompanyGoalPageParam bizCompanyGoalPageParam) {
        QueryWrapper<BizCompanyGoal> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizCompanyGoalPageParam.getSearchKey())){
            queryWrapper.lambda().like(BizCompanyGoal::getAssYear, bizCompanyGoalPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(bizCompanyGoalPageParam.getAssYear())){
            queryWrapper.lambda().eq(BizCompanyGoal::getAssYear, bizCompanyGoalPageParam.getAssYear());
        }
        if(ObjectUtil.isAllNotEmpty(bizCompanyGoalPageParam.getSortField(), bizCompanyGoalPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizCompanyGoalPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizCompanyGoalPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizCompanyGoalPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizCompanyGoal::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCompanyGoalAddParam bizCompanyGoalAddParam) {
        BizCompanyGoal bizCompanyGoal = BeanUtil.toBean(bizCompanyGoalAddParam, BizCompanyGoal.class);
        this.save(bizCompanyGoal);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(bizCompanyGoalAddParam.getKpiList())){
            List<BizCompanyGoalKpi> bizCompanyGoalKpiList = BeanUtil.copyToList(bizCompanyGoalAddParam.getKpiList(), BizCompanyGoalKpi.class);
            bizCompanyGoalKpiList.forEach(x->{
                x.setGoalId(bizCompanyGoal.getId());
            });
            bizCompanyGoalKpiService.saveBatch(bizCompanyGoalKpiList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCompanyGoalEditParam bizCompanyGoalEditParam) {
        BizCompanyGoal bizCompanyGoal = this.queryEntity(bizCompanyGoalEditParam.getId());
        BeanUtil.copyProperties(bizCompanyGoalEditParam, bizCompanyGoal);
        this.updateById(bizCompanyGoal);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(bizCompanyGoalEditParam.getKpiList())){
            //本次传入的数据
            List<BizCompanyGoalKpi> costList = BeanUtil.copyToList(bizCompanyGoalEditParam.getKpiList(), BizCompanyGoalKpi.class);
            costList.forEach(x->{
                x.setGoalId(bizCompanyGoal.getId());
            });
            //获取历史数据
            List<BizCompanyGoalKpi> oldList = bizCompanyGoalKpiService.list(new QueryWrapper<BizCompanyGoalKpi>().lambda()
                    .eq(BizCompanyGoalKpi::getGoalId, bizCompanyGoal.getId()));
            List<String> oldIds = oldList.stream().map(BizCompanyGoalKpi::getId).collect(Collectors.toList());
            //待新增的
            List<BizCompanyGoalKpi> addList = costList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizCompanyGoalKpi> updateList = costList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizCompanyGoalKpi::getId).collect(Collectors.toList());
            List<BizCompanyGoalKpi> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizCompanyGoalKpiService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizCompanyGoalKpiService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizCompanyGoalKpiService.removeByIds(removeList);
            }
        }else{
            bizCompanyGoalKpiService.remove(new QueryWrapper<BizCompanyGoalKpi>().lambda().eq(BizCompanyGoalKpi::getGoalId,bizCompanyGoal.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizCompanyGoalIdParam> bizCompanyGoalIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizCompanyGoalIdParamList, BizCompanyGoalIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            bizCompanyGoalKpiService.remove(new QueryWrapper<BizCompanyGoalKpi>().lambda().in(BizCompanyGoalKpi::getGoalId,ids));
        }
    }

    @Override
    public BizCompanyGoal detail(BizCompanyGoalIdParam bizCompanyGoalIdParam) {
        return this.queryEntity(bizCompanyGoalIdParam.getId());
    }

    @Override
    public BizCompanyGoal queryEntity(String id) {
        BizCompanyGoal bizCompanyGoal = this.getById(id);
        if(ObjectUtil.isEmpty(bizCompanyGoal)) {
            throw new CommonException("公司绩效考核-目标登记不存在，id值为：{}", id);
        }
        return bizCompanyGoal;
    }
}
