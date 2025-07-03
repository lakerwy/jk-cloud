package vip.xiaonuo.biz.modular.orgPerf.goal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.orgPerf.business.entity.BizBusiness;
import vip.xiaonuo.biz.modular.orgPerf.business.service.BizBusinessService;
import vip.xiaonuo.biz.modular.orgPerf.finance.entity.BizFinance;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.enums.BizAssPeriodlEnum;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalItemService;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizOrgGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.product.entity.BizProduct;
import vip.xiaonuo.biz.modular.orgPerf.product.service.BizProductService;
import vip.xiaonuo.biz.modular.orgPerf.summary.enums.CompWaringEnum;
import vip.xiaonuo.biz.modular.orgPerf.summary.enums.CompanyAssEnum;
import vip.xiaonuo.biz.modular.orgPerf.summary.enums.OrgAssEnum;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.util.SummaryUtil;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryDetailVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryListVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizOrgGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 子公司及生产部门绩效考核-目标登记Service接口实现类
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Service
@DS("slave_1")
public class BizOrgGoalServiceImpl extends ServiceImpl<BizOrgGoalMapper, BizOrgGoal> implements BizOrgGoalService {

    @Resource
    private BizOrgGoalItemService bizOrgGoalItemService;

    @Resource
    private BizBusinessService bizBusinessService;

    @Resource
    private BizFinanceService bizFinanceService;

    @Resource
    private BizProductService bizProductService;

    @Resource
    private BizOrgGoalMapper bizOrgGoalMapper;

    // 数字公司名称
    private static final String COMPANY_NAME = "湖北建科数字信息技术有限公司";

    @Override
    public Page<BizOrgGoal> page(BizOrgGoalPageParam bizOrgGoalPageParam) {
        QueryWrapper<BizOrgGoal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizOrgGoal::getAssOrg, bizOrgGoalPageParam.getAssOrg());
        queryWrapper.lambda().eq(BizOrgGoal::getAssType, bizOrgGoalPageParam.getAssType());
        if(ObjectUtil.isNotEmpty(bizOrgGoalPageParam.getSearchKey())){
            queryWrapper.lambda().like(BizOrgGoal::getOrgName, bizOrgGoalPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(bizOrgGoalPageParam.getOrgId())){
            queryWrapper.lambda().eq(BizOrgGoal::getOrgId, bizOrgGoalPageParam.getOrgId());
        }
        if(ObjectUtil.isNotEmpty(bizOrgGoalPageParam.getAssYear())){
            queryWrapper.lambda().eq(BizOrgGoal::getAssYear, bizOrgGoalPageParam.getAssYear());
        }
        if(ObjectUtil.isNotEmpty(bizOrgGoalPageParam.getAssSeason())){
            queryWrapper.lambda().eq(BizOrgGoal::getAssSeason, bizOrgGoalPageParam.getAssSeason());
        }
        if(ObjectUtil.isAllNotEmpty(bizOrgGoalPageParam.getSortField(), bizOrgGoalPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizOrgGoalPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizOrgGoalPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizOrgGoalPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizOrgGoal::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizOrgGoalAddParam bizOrgGoalAddParam) {
        // 校验
        List<BizOrgGoalItem> itemList = null;
        if(CollUtil.isNotEmpty(bizOrgGoalAddParam.getItemList())){
            itemList = BeanUtil.copyToList(bizOrgGoalAddParam.getItemList(), BizOrgGoalItem.class);
        }
        // 部门、考核周期同时唯一
        LambdaQueryWrapper<BizOrgGoal> queryWrapper = new LambdaQueryWrapper<BizOrgGoal>()
                .eq(BizOrgGoal::getAssOrg, bizOrgGoalAddParam.getAssOrg())
                .eq(BizOrgGoal::getAssType, bizOrgGoalAddParam.getAssType())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalAddParam.getOrgId());
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalAddParam.getAssType())){
            queryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalAddParam.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalAddParam.getAssType())){
            queryWrapper.eq(BizOrgGoal::getAssSeason, bizOrgGoalAddParam.getAssSeason());
        }
        long count = this.count(queryWrapper);
        if(count > 0){
            String assType = ObjectUtil.isEmpty(bizOrgGoalAddParam.getAssSeason()) ? bizOrgGoalAddParam.getAssYear().toString()
                    : bizOrgGoalAddParam.getAssYear()+"-Q"+bizOrgGoalAddParam.getAssSeason();
            throw new CommonException("子公司/部门【{}】考核周期{}已存在", bizOrgGoalAddParam.getOrgName(), assType);
        }
        BizOrgGoal bizOrgGoal = BeanUtil.toBean(bizOrgGoalAddParam, BizOrgGoal.class);
        this.save(bizOrgGoal);
        // 处理基本项指标-集合
        if(CollUtil.isNotEmpty(itemList)){
            itemList.forEach(x->{
                x.setGoalId(bizOrgGoal.getId());
            });
            bizOrgGoalItemService.saveBatch(itemList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizOrgGoalEditParam bizOrgGoalEditParam) {
        // 校验
        List<BizOrgGoalItem> itemList = null;
        if(CollUtil.isNotEmpty(bizOrgGoalEditParam.getItemList())){
            itemList = BeanUtil.copyToList(bizOrgGoalEditParam.getItemList(), BizOrgGoalItem.class);
        }
        // 部门、考核周期同时唯一
        LambdaQueryWrapper<BizOrgGoal> queryWrapper = new LambdaQueryWrapper<BizOrgGoal>()
                .eq(BizOrgGoal::getAssOrg, bizOrgGoalEditParam.getAssOrg())
                .eq(BizOrgGoal::getAssType, bizOrgGoalEditParam.getAssType())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalEditParam.getOrgId())
                .ne(BizOrgGoal::getId, bizOrgGoalEditParam.getId());
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalEditParam.getAssType())){
            queryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalEditParam.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalEditParam.getAssType())){
            queryWrapper.eq(BizOrgGoal::getAssSeason, bizOrgGoalEditParam.getAssSeason());
        }
        long count = this.count(queryWrapper);
        if(count > 0){
            String assPeriod = ObjectUtil.isEmpty(bizOrgGoalEditParam.getAssSeason()) ? bizOrgGoalEditParam.getAssYear().toString()
                    : bizOrgGoalEditParam.getAssYear()+"-Q"+bizOrgGoalEditParam.getAssSeason();
            throw new CommonException("子公司/部门【{}】考核周期{}已存在", bizOrgGoalEditParam.getOrgName(), assPeriod);
        }
        BizOrgGoal bizOrgGoal = this.queryEntity(bizOrgGoalEditParam.getId());
        BeanUtil.copyProperties(bizOrgGoalEditParam, bizOrgGoal);
        this.updateById(bizOrgGoal);
        // 处理基本项指标-集合
        if(CollUtil.isNotEmpty(itemList)){
            //本次传入的数据
            itemList.forEach(x->{
                x.setGoalId(bizOrgGoal.getId());
            });
            //获取历史数据
            List<BizOrgGoalItem> oldList = bizOrgGoalItemService.list(new QueryWrapper<BizOrgGoalItem>().lambda()
                    .eq(BizOrgGoalItem::getGoalId, bizOrgGoal.getId()));
            List<String> oldIds = oldList.stream().map(BizOrgGoalItem::getId).collect(Collectors.toList());
            //待新增的
            List<BizOrgGoalItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizOrgGoalItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizOrgGoalItem::getId).collect(Collectors.toList());
            List<BizOrgGoalItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizOrgGoalItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizOrgGoalItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizOrgGoalItemService.removeByIds(removeList);
            }
        }else{
            bizOrgGoalItemService.remove(new QueryWrapper<BizOrgGoalItem>().lambda().eq(BizOrgGoalItem::getGoalId, bizOrgGoal.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizGoalIdParam> bizGoalIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizGoalIdParamList, BizGoalIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            bizOrgGoalItemService.remove(new QueryWrapper<BizOrgGoalItem>().lambda().in(BizOrgGoalItem::getGoalId,ids));
        }
    }

    @Override
    public BizOrgGoalVo detail(BizGoalIdParam bizGoalIdParam) {
        BizOrgGoal bizOrgGoal = this.queryEntity(bizGoalIdParam.getId());
        BizOrgGoalVo bizOrgGoalVo = BeanUtil.copyProperties(bizOrgGoal, BizOrgGoalVo.class);
        // 查询明细
        List<BizOrgGoalItem> itemList = bizOrgGoalItemService.list(new LambdaQueryWrapper<BizOrgGoalItem>()
                .eq(BizOrgGoalItem::getGoalId, bizGoalIdParam.getId()));
        bizOrgGoalVo.setItemList(itemList);
        return bizOrgGoalVo;
    }

    @Override
    public BizOrgGoal queryEntity(String id) {
        BizOrgGoal bizOrgGoal = this.getById(id);
        if(ObjectUtil.isEmpty(bizOrgGoal)) {
            throw new CommonException("子公司及生产部门绩效考核-目标登记不存在，id值为：{}", id);
        }
        return bizOrgGoal;
    }

    @Override
    public BizOrgGoalVo getByCondition(BizOrgGoalParam bizOrgGoalParam) {
        LambdaQueryWrapper<BizOrgGoal> lambdaQueryWrapper = new LambdaQueryWrapper<BizOrgGoal>()
                .eq(BizOrgGoal::getAssOrg, bizOrgGoalParam.getAssOrg())
                .eq(BizOrgGoal::getAssType, bizOrgGoalParam.getAssType())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalParam.getOrgId())
                .orderByDesc(BizOrgGoal::getCreateTime).last("limit 1");
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalParam.getAssType())){
            lambdaQueryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalParam.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalParam.getAssType())){
            lambdaQueryWrapper.eq(BizOrgGoal::getAssSeason, bizOrgGoalParam.getAssSeason());
        }
        BizOrgGoal bizOrgGoal = this.getOne(lambdaQueryWrapper);
        if(ObjectUtil.isEmpty(bizOrgGoal)){
            throw new CommonException("未找到对应的目标登记信息");
        }
        BizOrgGoalVo bizOrgGoalVo = BeanUtil.copyProperties(bizOrgGoal, BizOrgGoalVo.class);
        // 查询明细
        List<BizOrgGoalItem> itemList = bizOrgGoalItemService.list(new LambdaQueryWrapper<BizOrgGoalItem>()
                .eq(BizOrgGoalItem::getGoalId, bizOrgGoal.getId()));
        bizOrgGoalVo.setItemList(itemList);
        return bizOrgGoalVo;
    }

    @Override
    public void editOwner(BizOrgGoalEditOwnerParam bizOrgGoalEditOwnerParam) {
        this.update(new LambdaUpdateWrapper<BizOrgGoal>().eq(BizOrgGoal::getAssOrg, bizOrgGoalEditOwnerParam.getAssOrg())
                .eq(BizOrgGoal::getAssType, bizOrgGoalEditOwnerParam.getAssType())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalEditOwnerParam.getOrgId())
                .set(BizOrgGoal::getOwner, bizOrgGoalEditOwnerParam.getOwner())
                .set(BizOrgGoal::getOwnerId, bizOrgGoalEditOwnerParam.getOwnerId()));
    }

    @Override
    public void updateOrgGoal(String goalId, List<BizOrgGoalItem> itemList) {
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(itemList)){
            //本次传入的数据
            itemList.forEach(x->{
                x.setGoalId(goalId);
            });
            //获取历史数据
            List<BizOrgGoalItem> oldList = bizOrgGoalItemService.list(new QueryWrapper<BizOrgGoalItem>().lambda()
                    .eq(BizOrgGoalItem::getGoalId, goalId));
            List<String> oldIds = oldList.stream().map(BizOrgGoalItem::getId).collect(Collectors.toList());
            //待新增的
            List<BizOrgGoalItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizOrgGoalItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizOrgGoalItem::getId).collect(Collectors.toList());
            List<BizOrgGoalItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizOrgGoalItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizOrgGoalItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizOrgGoalItemService.removeByIds(removeList);
            }
        }else{
            bizOrgGoalItemService.remove(new QueryWrapper<BizOrgGoalItem>().lambda().eq(BizOrgGoalItem::getGoalId,goalId));
        }
    }


    @Override
    public List<OrgSummaryListVo> orgSummaryList(OrgSummaryListParam orgSummaryListParam) {
        return bizOrgGoalMapper.orgSummaryList(orgSummaryListParam);
    }

    @Override
    public List<OrgSummaryDetailVo> orgSummaryDetail(OrgSummaryDetailParam param) {
        List<OrgSummaryDetailVo> voList = CollUtil.newArrayList();
        List<Integer> monthList = CollUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        List<String> assNameList = OrgAssEnum.getEnumValues();
        // 构造查询条件
        List<BizBusiness> list1 = CollUtil.newArrayList();
        List<BizFinance> list2 = CollUtil.newArrayList();
        List<BizProduct> list3 = CollUtil.newArrayList();
        // 获取目标登记
        BizOrgGoal bizOrgGoal = this.queryEntity(param.getId());
        LambdaQueryWrapper<BizBusiness> qw1 = new LambdaQueryWrapper<BizBusiness>().eq(BizBusiness::getOrgId, bizOrgGoal.getOrgId()).eq(BizBusiness::getYear, bizOrgGoal.getAssYear());
        LambdaQueryWrapper<BizFinance> qw2 = new LambdaQueryWrapper<BizFinance>().eq(BizFinance::getOrgId, bizOrgGoal.getOrgId()).eq(BizFinance::getYear, bizOrgGoal.getAssYear());
        LambdaQueryWrapper<BizProduct> qw3 = new LambdaQueryWrapper<BizProduct>().eq(BizProduct::getOrgId, bizOrgGoal.getOrgId()).eq(BizProduct::getYear, bizOrgGoal.getAssYear());
        // 月份
        if(ObjectUtil.isNotEmpty(param.getMonth())){
            monthList = CollUtil.newArrayList(param.getMonth());
            qw1.eq(BizBusiness::getMonth, param.getMonth());
            qw2.eq(BizFinance::getMonth, param.getMonth());
        }
        // 考核指标
        if(ObjectUtil.isNotEmpty(param.getAssName())){
            assNameList = CollUtil.newArrayList(param.getAssName());
            if(OrgAssEnum.NEW_CONTRACT_TOTAL.getValue().equals(param.getAssName())){
                list1 = bizBusinessService.list(qw1);
            }
            if(OrgAssEnum.INCOME_TOTAL.getValue().equals(param.getAssName()) || OrgAssEnum.PROFIT_TOTAL.getValue().equals(param.getAssName())
                || OrgAssEnum.BUDGET_EXEC_RATE.getValue().equals(param.getAssName())){
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
        // 查询目标明细
        List<BizOrgGoalItem> itemList = bizOrgGoalItemService.list(new LambdaQueryWrapper<BizOrgGoalItem>()
                .eq(BizOrgGoalItem::getGoalId, param.getId()).in(BizOrgGoalItem::getAssName, assNameList));
        for(Integer month : monthList){
            for(String assName : assNameList){
                BizOrgGoalItem item = itemList.stream().filter(x -> assName.equals(x.getAssName())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(item)){
                    OrgSummaryDetailVo vo = new OrgSummaryDetailVo();
//                    vo.setId(param.getId());
                    vo.setAssYear(bizOrgGoal.getAssYear());
                    vo.setMonth(month);
                    vo.setAssName(assName);
                    vo.setAssRule(item.getAssRule());
                    vo.setStandard(item.getStandard());
                    vo.setWeight(item.getWeight());
                    vo.setGoalValue(item.getGoalValue());
                    vo.setFromDept(item.getFromDept());
                    vo.setFromDeptId(item.getFromDeptId());
                    if(OrgAssEnum.NEW_CONTRACT_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list1.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizBusiness::getNewContractAmount).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(OrgAssEnum.INCOME_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list2.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizFinance::getRecAmountAcc).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(OrgAssEnum.PROFIT_TOTAL.getValue().equals(assName)){
                        BigDecimal compValueTotal = list2.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizFinance::getProfitAmountAcc).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(OrgAssEnum.BUDGET_EXEC_RATE.getValue().equals(assName)){
                        BigDecimal compValueTotal = list2.stream().filter(x -> month.equals(x.getMonth())).findFirst().map(BizFinance::getBudgetExecRate).orElse(null);
                        vo.setCompValueTotal(compValueTotal);
                    }
                    if(OrgAssEnum.PRO_VALUE.getValue().equals(assName)){
                        BigDecimal season1 = list3.stream().filter(x -> x.getSeason() == 1).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season2 = list3.stream().filter(x -> x.getSeason() == 2).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season3 = list3.stream().filter(x -> x.getSeason() == 3).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        BigDecimal season4 = list3.stream().filter(x -> x.getSeason() == 4).findFirst().map(BizProduct::getProductAmount).orElse(BigDecimal.ZERO);
                        if(month == 3){
                            vo.setCompValueTotal(season1);
                        } else if(month == 6){
                            vo.setCompValueTotal(season1.add(season2));
                        } else if(month == 9){
                            vo.setCompValueTotal(season1.add(season2).add(season3));
                        } else if(month == 12){
                            vo.setCompValueTotal(season1.add(season2).add(season3).add(season4));
                        }
                    }
                    // 没有数据的明细不显示
//                    if(vo.getCompValueTotal() == null){
//                        continue;
//                    }
                    // 预算执行率的进度、得分、完成值预警单独计算
                    if(OrgAssEnum.BUDGET_EXEC_RATE.getValue().equals(assName)){
                        vo.setProgress(vo.getCompValueTotal()); // 进度=实际完成值（累计）
                        vo.setScore(SummaryUtil.calcBudgetExecRateScore(vo.getCompValueTotal(), item.getGoalValue(), item.getWeight()));
                        // 当预算执行率的实际完成值（累计）＞N/12*100%时，则红灯预警，N为对应月份数
                        if(vo.getProgress() != null && vo.getCompValueTotal() != null){
                            BigDecimal value = new BigDecimal(month).multiply(new BigDecimal(100)).divide(new BigDecimal(12), 6, RoundingMode.FLOOR);
                            if(vo.getCompValueTotal().compareTo(value) > 0){
                                vo.setCompWarning(CompWaringEnum.RED_LIGHT.getValue());
                            }
                        }
                    } else {
                        vo.setProgress(SummaryUtil.calcProgress(vo.getCompValueTotal(), item.getGoalValue()));
                        if(COMPANY_NAME.equals(bizOrgGoal.getOrgName()) && OrgAssEnum.PROFIT_TOTAL.getValue().equals(assName)){
                            vo.setScore(SummaryUtil.calcDigitalCompProfitScore(vo.getCompValueTotal(), item.getGoalValue(), item.getWeight()));
                        }else {
                            vo.setScore(SummaryUtil.calcScore(vo.getCompValueTotal(),  item.getGoalValue(), item.getWeight()));
                        }
                        if(vo.getProgress() != null){
                            if(vo.getProgress().compareTo(new BigDecimal(80)) < 0) {
                                vo.setCompWarning(CompWaringEnum.RED_LIGHT.getValue());
                            } else if(vo.getProgress().compareTo(new BigDecimal(100)) < 0) {
                                vo.setCompWarning(CompWaringEnum.YELLOW_LIGHT.getValue());
                            }
                        }
                    }
                    voList.add(vo);
                }
            }
        }
        return voList;
    }
}
