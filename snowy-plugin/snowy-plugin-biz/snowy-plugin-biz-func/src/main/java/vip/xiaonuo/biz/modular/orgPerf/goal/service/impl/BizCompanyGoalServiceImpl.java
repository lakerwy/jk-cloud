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
import vip.xiaonuo.biz.modular.orgPerf.business.entity.BizBusiness;
import vip.xiaonuo.biz.modular.orgPerf.business.service.BizBusinessService;
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalKpiService;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizCompanyGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.product.entity.BizProduct;
import vip.xiaonuo.biz.modular.orgPerf.product.service.BizProductService;
import vip.xiaonuo.biz.modular.orgPerf.summary.enums.CompWaringEnum;
import vip.xiaonuo.biz.modular.orgPerf.summary.enums.CompanyAssEnum;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.util.SummaryUtil;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryDetailVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;
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

    @Resource
    private BizBusinessService bizBusinessService;

    @Resource
    private BizFinanceService bizFinanceService;

    @Resource
    private BizProductService bizProductService;

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
     * 1.新签合同总额：业务情况表，湖北建科科技集团有限公司对应最新月份“累计新签合同额”；
     * 2.收入总额：财务情况表，湖北建科科技集团有限公司对应最新月份“累计回款额”
     * 3.产值：产值情况表，统计年度内各部门生产产值求和，仅计算3月、6月、9月、12月份产值累计值，即3月累计产值为第一季度各部门产值之和，6月累计产值为各部门第一季度+第二季度产值之和，以此类推。其他月份产值累计值为空。
     * 4.利润总额：财务情况表，湖北建科科技集团有限公司对应最新月份“累计净利润”
     *
     * @param param
     * @return
     *
     */
    @Override
    public List<CompanySummaryListVo> companySummaryList(CompanySummaryListParam param) {
        List<CompanySummaryListVo> voList = bizCompanyGoalMapper.companySummaryList(COMPANY_ID);
        if(CollUtil.isEmpty(voList)){
            return voList;
        }
        List<String> goalIdList = voList.stream().map(CompanySummaryListVo::getId).distinct().collect(Collectors.toList());
        // 查询kpi
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .in(BizCompanyGoalKpi::getGoalId, goalIdList));
        // 获取目标值
        for(CompanySummaryListVo vo: voList){
            // 总分
            BigDecimal totalScore = BigDecimal.ZERO;
            // 当前考核年度的kpi
            List<BizCompanyGoalKpi> currKpiList = kpiList.stream().filter(x -> x.getAssYear().equals(vo.getAssYear())).collect(Collectors.toList());
            BizCompanyGoalKpi kpi1 = currKpiList.stream().filter(x -> CompanyAssEnum.NEW_CONTRACT_TOTAL.getValue().equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi1)){
                // 新签合同总额-目标值
                vo.setNewContractTotalGoal(kpi1.getGoalValue());
                // 新签合同总额-完成进度
                vo.setProValueTotalCompProgress(SummaryUtil.calcProgress(vo.getNewContractTotalComp(), vo.getNewContractTotalGoal()));
                // 得分
                BigDecimal score = SummaryUtil.calcScore(vo.getNewContractTotalComp(), vo.getNewContractTotalGoal(), kpi1.getWeight());
                // 计入总分
                totalScore = totalScore.add(score == null ? BigDecimal.ZERO : score);
            }
            BizCompanyGoalKpi kpi2 = currKpiList.stream().filter(x -> CompanyAssEnum.INCOME_TOTAL.getValue().equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi2)){
                // 收入总额-目标值
                vo.setIncomeTotalGoal(kpi2.getGoalValue());
                // 收入总额-完成进度
                vo.setProValueTotalCompProgress(SummaryUtil.calcProgress(vo.getIncomeTotalComp(), vo.getIncomeTotalGoal()));
                // 得分
                BigDecimal score = SummaryUtil.calcScore(vo.getIncomeTotalComp(), vo.getIncomeTotalGoal(), kpi2.getWeight());
                // 计入总分
                totalScore = totalScore.add(score == null ? BigDecimal.ZERO : score);
            }
            BizCompanyGoalKpi kpi3 = currKpiList.stream().filter(x -> CompanyAssEnum.PRO_VALUE.getValue().equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi3)){
                // 产值-目标值
                vo.setProValueTotalGoal(kpi3.getGoalValue());
                // 产值-完成进度
                vo.setProValueTotalCompProgress(SummaryUtil.calcProgress(vo.getProValueTotalComp(), vo.getProValueTotalGoal()));
                // 得分
                BigDecimal score = SummaryUtil.calcScore(vo.getProValueTotalComp(), vo.getProValueTotalGoal(), kpi3.getWeight());
                // 计入总分
                totalScore = totalScore.add(score == null ? BigDecimal.ZERO : score);
            }
            BizCompanyGoalKpi kpi4 = currKpiList.stream().filter(x -> CompanyAssEnum.PROFIT_TOTAL.getValue().equals(x.getAssName())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(kpi4)){
                // 利润总额-目标值
                vo.setProfitTotalGoal(kpi4.getGoalValue());
                // 利润总额-完成进度
                vo.setProfitTotalCompProgress(SummaryUtil.calcProgress(vo.getProfitTotalComp(), vo.getProfitTotalGoal()));
                // 得分
                BigDecimal score = SummaryUtil.calcScore(vo.getProfitTotalComp(), vo.getProfitTotalGoal(), kpi4.getWeight());
                // 计入总分
                totalScore = totalScore.add(score == null ? BigDecimal.ZERO : score);
            }
            // 总分
            vo.setTotalScore(totalScore);
        }
        return voList;
    }

    /**
     *
     * 1.新签合同总额：业务情况表，湖北建科科技集团有限公司对应月份“累计新签合同额”；
     * 2.收入总额：财务情况表，湖北建科科技集团有限公司对应月份“累计回款额”
     * 3.产值：产值情况表，统计年度内各部门生产产值求和，仅计算3月、6月、9月、12月份产值累计值，即3月累计产值为第一季度各部门产值之和，6月累计产值为各部门第一季度+第二季度产值之和，以此类推。其他月份产值累计值为空。
     * 4.利润总额：财务情况表，湖北建科科技集团有限公司对应月份“累计净利润”
     *
     */
    @Override
    public List<CompanySummaryDetailVo> companySummaryDetail(CompanySummaryDetailParam param) {
        List<CompanySummaryDetailVo> voList = CollUtil.newArrayList();
        List<Integer> monthList = CollUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<String> assNameList = CompanyAssEnum.getEnumValues();
        // 构造查询条件
        List<BizBusiness> list1 = CollUtil.newArrayList();
        List<BizFinance> list2 = CollUtil.newArrayList();
        List<BizProduct> list3 = CollUtil.newArrayList();
        LambdaQueryWrapper<BizBusiness> qw1 = new LambdaQueryWrapper<BizBusiness>().eq(BizBusiness::getOrgId, COMPANY_ID).eq(BizBusiness::getYear, param.getAssYear());
        LambdaQueryWrapper<BizFinance> qw2 = new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, COMPANY_ID).eq(BizFinance::getYear, param.getAssYear());
        LambdaQueryWrapper<BizProduct> qw3 = new LambdaQueryWrapper<BizProduct>().eq(BizProduct::getOrgId, COMPANY_ID).eq(BizProduct::getYear, param.getAssYear());
        // 月份
        if(ObjectUtil.isNotEmpty(param.getMonth())){
            monthList = CollUtil.newArrayList(param.getMonth());
            qw1.eq(BizBusiness::getMonth, param.getMonth());
            qw2.eq(BizFinance::getMonth, param.getMonth());
        }
        // 考核指标
        if(ObjectUtil.isNotEmpty(param.getAssName())){
            assNameList = CollUtil.newArrayList(param.getAssName());
            if(CompanyAssEnum.NEW_CONTRACT_TOTAL.getValue().equals(param.getAssName())){
                list1 = bizBusinessService.list(qw1);
            }
            if(CompanyAssEnum.INCOME_TOTAL.getValue().equals(param.getAssName()) || CompanyAssEnum.PROFIT_TOTAL.getValue().equals(param.getAssName())){
                list2 = bizFinanceService.list(qw2);
            }
            if(CompanyAssEnum.PRO_VALUE.getValue().equals(param.getAssName())){
                list3 = bizProductService.list(qw3);
            }
        }else {
            list1 = bizBusinessService.list(qw1);
            list2 = bizFinanceService.list(qw2);
            list3 = bizProductService.list(qw3);
        }
        // 查询目标-kpi
        List<BizCompanyGoalKpi> kpiList = bizCompanyGoalKpiService.list(new LambdaQueryWrapper<BizCompanyGoalKpi>()
                .eq(BizCompanyGoalKpi::getGoalId, param.getId()).in(BizCompanyGoalKpi::getAssName, assNameList));
        for(Integer month : monthList){
            for(String assName : assNameList){
                BizCompanyGoalKpi kpi = kpiList.stream().filter(x -> assName.equals(x.getAssName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(kpi)){
                    CompanySummaryDetailVo vo = new CompanySummaryDetailVo();
                    vo.setId(param.getId());
                    vo.setAssYear(param.getAssYear());
                    vo.setMonth(month);
                    vo.setAssName(assName);
                    vo.setAssRule(kpi.getAssRule());
                    vo.setStandard(kpi.getStandard());
                    vo.setWeight(kpi.getWeight());
                    vo.setGoalValue(kpi.getGoalValue());
                    if(CompanyAssEnum.NEW_CONTRACT_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list1.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizBusiness::getNewContractAmount).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(CompanyAssEnum.INCOME_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list2.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizFinance::getRecAmountAcc).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(CompanyAssEnum.PROFIT_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list2.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizFinance::getProfitAmountAcc).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(CompanyAssEnum.PRO_VALUE.getValue().equals(assName)){
                        BigDecimal season1 = list3.stream().filter(x -> x.getSeason() == 1).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season2 = list3.stream().filter(x -> x.getSeason() == 2).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season3 = list3.stream().filter(x -> x.getSeason() == 3).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season4 = list3.stream().filter(x -> x.getSeason() == 4).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        if(month == 3 && season1.compareTo(BigDecimal.ZERO) != 0){
                            vo.setCompValueTotal(season1);
                        } else if(month == 6 && season2.compareTo(BigDecimal.ZERO) != 0){
                            vo.setCompValueTotal(season1.add(season2));
                        } else if(month == 9 && season3.compareTo(BigDecimal.ZERO) != 0){
                            vo.setCompValueTotal(season1.add(season2).add(season3));
                        } else if(month == 12  && season4.compareTo(BigDecimal.ZERO) != 0){
                            vo.setCompValueTotal(season1.add(season2).add(season3).add(season4));
                        }
                    }
                    // 没有数据的明细不显示
                    if(vo.getCompValueTotal() == null){
                        continue;
                    }
                    // 进度
                    vo.setProgress(SummaryUtil.calcProgress(vo.getCompValueTotal(), kpi.getGoalValue()));
                    // 得分
                    vo.setScore(SummaryUtil.calcScore(vo.getCompValueTotal(),  kpi.getGoalValue(), kpi.getWeight()));
                    // 完成值预警
                    if(vo.getProgress() != null){
                        if(vo.getProgress().compareTo(new BigDecimal(80)) < 0) {
                            vo.setCompWarning(CompWaringEnum.RED_LIGHT.getValue());
                        } else if(vo.getProgress().compareTo(new BigDecimal(100)) < 0) {
                            vo.setCompWarning(CompWaringEnum.YELLOW_LIGHT.getValue());
                        }
                    }
                    voList.add(vo);
                }
            }
        }
        return voList;
    }
}
