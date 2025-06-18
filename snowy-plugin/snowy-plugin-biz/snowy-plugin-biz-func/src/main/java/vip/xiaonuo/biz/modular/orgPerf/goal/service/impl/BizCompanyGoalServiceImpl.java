package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalKpiService;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizCompanyGoalVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizCompanyGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
            queryWrapper.lambda().orderByDesc(BizCompanyGoal::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizCompanyGoalAddParam bizCompanyGoalAddParam) {
        // 校验
        List<BizCompanyGoalKpi> kpiList = null;
        if(CollUtil.isNotEmpty(bizCompanyGoalAddParam.getKpiList())){
            kpiList = BeanUtil.copyToList(bizCompanyGoalAddParam.getKpiList(), BizCompanyGoalKpi.class);
//            BigDecimal weightTotal = kpiList.stream().map(BizCompanyGoalKpi::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
//            if(weightTotal.compareTo(new BigDecimal(100)) != 0){
//                throw new CommonException("KPI指标权重之和需等于100");
//            }
        }
        long count = this.count(new LambdaQueryWrapper<BizCompanyGoal>().eq(BizCompanyGoal::getAssYear, bizCompanyGoalAddParam.getAssYear()));
        if(count > 0){
            throw new CommonException("考核周期{}已存在", bizCompanyGoalAddParam.getAssYear());
        }
        BizCompanyGoal bizCompanyGoal = BeanUtil.toBean(bizCompanyGoalAddParam, BizCompanyGoal.class);
        this.save(bizCompanyGoal);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            kpiList.forEach(x->{
                x.setGoalId(bizCompanyGoal.getId());
            });
            bizCompanyGoalKpiService.saveBatch(kpiList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizCompanyGoalEditParam bizCompanyGoalEditParam) {
        // 校验
        List<BizCompanyGoalKpi> kpiList = null;
        if(CollUtil.isNotEmpty(bizCompanyGoalEditParam.getKpiList())){
            kpiList = BeanUtil.copyToList(bizCompanyGoalEditParam.getKpiList(), BizCompanyGoalKpi.class);
            BigDecimal weightTotal = kpiList.stream().map(BizCompanyGoalKpi::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
            if(weightTotal.compareTo(new BigDecimal(100)) != 0){
                throw new CommonException("KPI指标权重之和需等于100");
            }
        }
        long count = this.count(new LambdaQueryWrapper<BizCompanyGoal>().eq(BizCompanyGoal::getAssYear, bizCompanyGoalEditParam.getAssYear())
                .ne(BizCompanyGoal::getId, bizCompanyGoalEditParam.getId()));
        if(count > 0){
            throw new CommonException("考核周期{}已存在", bizCompanyGoalEditParam.getAssYear());
        }
        BizCompanyGoal bizCompanyGoal = this.queryEntity(bizCompanyGoalEditParam.getId());
        BeanUtil.copyProperties(bizCompanyGoalEditParam, bizCompanyGoal);
        this.updateById(bizCompanyGoal);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            //本次传入的数据
            kpiList.forEach(x->{
                x.setGoalId(bizCompanyGoal.getId());
            });
            //获取历史数据
            List<BizCompanyGoalKpi> oldList = bizCompanyGoalKpiService.list(new QueryWrapper<BizCompanyGoalKpi>().lambda()
                    .eq(BizCompanyGoalKpi::getGoalId, bizCompanyGoal.getId()));
            List<String> oldIds = oldList.stream().map(BizCompanyGoalKpi::getId).collect(Collectors.toList());
            //待新增的
            List<BizCompanyGoalKpi> addList = kpiList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizCompanyGoalKpi> updateList = kpiList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
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

    @Override
    public BizCompanyGoalVo getByAssYear(BizCompanyGoalParam bizCompanyGoalParam) {
        BizCompanyGoalVo bizCompanyGoalVo = new BizCompanyGoalVo();
        LambdaQueryWrapper<BizCompanyGoal> lambdaQueryWrapper = new LambdaQueryWrapper<BizCompanyGoal>()
                .orderByDesc(BizCompanyGoal::getCreateTime).last("limit 1");
        if(ObjectUtil.isNotEmpty(bizCompanyGoalParam.getAssType())){
            lambdaQueryWrapper.eq(BizCompanyGoal::getAssType, bizCompanyGoalParam.getAssType());
        }
        if(ObjectUtil.isNotEmpty(bizCompanyGoalParam.getAssYear())){
            lambdaQueryWrapper.eq(BizCompanyGoal::getAssYear, bizCompanyGoalParam.getAssYear());
        }
        BizCompanyGoal bizCompanyGoal = this.getOne(lambdaQueryWrapper);
        if(ObjectUtil.isEmpty(bizCompanyGoal)){
            throw new CommonException("未找到对应的目标登记信息");
        }
        // 查询明细
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .eq(BizCompanyGoalKpi::getGoalId, bizCompanyGoal.getId()));
        bizCompanyGoalVo.setKpiList(kpiList);
        return bizCompanyGoalVo;
    }
}
