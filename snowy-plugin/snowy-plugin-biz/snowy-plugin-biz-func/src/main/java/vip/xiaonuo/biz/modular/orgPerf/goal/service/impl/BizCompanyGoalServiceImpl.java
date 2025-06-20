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
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizCompanyGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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

    @Resource
    private BizCompanyGoalMapper bizCompanyGoalMapper;

    // 湖北建科科技集团有限公司orgId
    private static final String COMPANY_ID = "1543842934270394368";

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
                x.setAssType(bizCompanyGoal.getAssType());
                x.setAssYear(bizCompanyGoal.getAssYear());
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
                x.setAssType(bizCompanyGoal.getAssType());
                x.setAssYear(bizCompanyGoal.getAssYear());
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
    public BizCompanyGoalVo detail(BizCompanyGoalIdParam bizCompanyGoalIdParam) {
        BizCompanyGoal bizCompanyGoal = this.queryEntity(bizCompanyGoalIdParam.getId());
        BizCompanyGoalVo bizCompanyGoalVo = BeanUtil.copyProperties(bizCompanyGoal, BizCompanyGoalVo.class);
        // 查询明细
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .eq(BizCompanyGoalKpi::getGoalId, bizCompanyGoalIdParam.getId()));
        bizCompanyGoalVo.setKpiList(kpiList);
        return bizCompanyGoalVo;
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
        BizCompanyGoalVo bizCompanyGoalVo = BeanUtil.copyProperties(bizCompanyGoal, BizCompanyGoalVo.class);
        // 查询明细
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .eq(BizCompanyGoalKpi::getGoalId, bizCompanyGoal.getId()));
        bizCompanyGoalVo.setKpiList(kpiList);
        return bizCompanyGoalVo;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateCompanyGoal(String goalId, List<BizCompanyGoalKpi> kpiList) {
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            //本次传入的数据
            kpiList.forEach(x->{
                x.setGoalId(goalId);
            });
            //获取历史数据
            List<BizCompanyGoalKpi> oldList = bizCompanyGoalKpiService.list(new QueryWrapper<BizCompanyGoalKpi>().lambda()
                    .eq(BizCompanyGoalKpi::getGoalId, goalId));
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
            bizCompanyGoalKpiService.remove(new QueryWrapper<BizCompanyGoalKpi>().lambda().eq(BizCompanyGoalKpi::getGoalId,goalId));
        }
    }

    /**
     *
     * 1.新签合同总额：业务情况表，湖北建科科技集团有限公司对应月份“累计新签合同额”；
     * 2.收入总额：财务情况表，湖北建科科技集团有限公司对应月份“累计回款额”
     * 3.产值：产值情况表，统计年度内各部门生产产值求和，仅计算3月、6月、9月、12月份产值累计值，即3月累计产值为第一季度各部门产值之和，6月累计产值为各部门第一季度+第二季度产值之和，以此类推。其他月份产值累计值为空。
     * 4.利润总额：财务情况表，湖北建科科技集团有限公司对应月份“累计净利润”
     *
     * @param companySummaryListParam
     * @return
     *
     */
    @Override
    public List<CompanySummaryListVo> companySummaryList(CompanySummaryListParam companySummaryListParam) {
        List<CompanySummaryListVo> voList = CollUtil.newArrayList();
        List<BizCompanyGoal> companyGoalList = this.list();
        if(CollUtil.isEmpty(companyGoalList)){
            return voList;
        }
        // 没有传考核年份，则取所有目标年份
        List<Integer> assYearList = new ArrayList<>();
        if(companySummaryListParam.getAssYear() == null){
            assYearList = companyGoalList.stream().map(BizCompanyGoal::getAssYear).distinct().collect(Collectors.toList());
        }else {
            assYearList.add(companySummaryListParam.getAssYear());
        }
        // 查询完成值
        voList = bizCompanyGoalMapper.companySummaryList(assYearList, COMPANY_ID);
        if(CollUtil.isEmpty(companyGoalList)){
            return voList;
        }
        List<String> goalIdList = companyGoalList.stream().map(BizCompanyGoal::getId).distinct().collect(Collectors.toList());
        // 查询kpi
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .in(BizCompanyGoalKpi::getGoalId, goalIdList));
        // 获取目标值
        for(CompanySummaryListVo vo: voList){
            // 总分
            BigDecimal totalScore = BigDecimal.ZERO;
            // 当前考核年度的kpi
            List<BizCompanyGoalKpi> currKpiList = kpiList.stream().filter(x -> x.getAssYear().equals(vo.getAssYear())).collect(Collectors.toList());
            BizCompanyGoalKpi kpi1 = currKpiList.stream().filter(x -> "新签合同总额".equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi1)){
                // 新签合同总额-目标值
                vo.setNewContractTotalGoal(kpi1.getGoalValue());
                // 新签合同总额-完成进度
                vo.setNewContractTotalCompProgress(vo.getNewContractTotalComp().divide(vo.getNewContractTotalGoal(), 4, RoundingMode.FLOOR).multiply(new BigDecimal(100)));
                // 计入总分
                totalScore = totalScore.add(vo.getNewContractTotalComp().multiply(kpi1.getWeight()).divide(vo.getNewContractTotalGoal(), 2, RoundingMode.FLOOR));
            }
            BizCompanyGoalKpi kpi2 = currKpiList.stream().filter(x -> "收入总额".equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi2)){
                // 收入总额-目标值
                vo.setIncomeTotalGoal(kpi2.getGoalValue());
                // 收入总额-完成进度
                vo.setIncomeTotalCompProgress(vo.getIncomeTotalComp().divide(vo.getIncomeTotalGoal(), 4, RoundingMode.FLOOR).multiply(new BigDecimal(100)));
                // 计入总分
                totalScore = totalScore.add(vo.getIncomeTotalComp().multiply(kpi2.getWeight()).divide(vo.getIncomeTotalGoal(), 2, RoundingMode.FLOOR));
            }
            BizCompanyGoalKpi kpi3 = currKpiList.stream().filter(x -> "产值".equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi3)){
                // 产值-目标值
                vo.setProValueTotalGoal(kpi3.getGoalValue());
                // 产值-完成进度
                vo.setProValueTotalCompProgress(vo.getProValueTotalComp().divide(vo.getProValueTotalGoal(), 4, RoundingMode.FLOOR).multiply(new BigDecimal(100)));
                // 计入总分
                totalScore = totalScore.add(vo.getProValueTotalComp().multiply(kpi3.getWeight()).divide(vo.getProValueTotalGoal(), 2, RoundingMode.FLOOR));
            }
            BizCompanyGoalKpi kpi4 = currKpiList.stream().filter(x -> "利润总额".equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi4)){
                // 利润总额-目标值
                vo.setProfitTotalGoal(kpi4.getGoalValue());
                // 利润总额-完成进度
                vo.setProfitTotalCompProgress(vo.getProfitTotalComp().divide(vo.getProfitTotalGoal(), 4, RoundingMode.FLOOR).multiply(new BigDecimal(100)));
                // 计入总分
                totalScore = totalScore.add(vo.getProValueTotalComp().multiply(kpi4.getWeight()).divide(vo.getProValueTotalGoal(), 2, RoundingMode.FLOOR));
            }
            // 总分
            vo.setTotalScore(totalScore);
        }
        return voList;

    }
}
