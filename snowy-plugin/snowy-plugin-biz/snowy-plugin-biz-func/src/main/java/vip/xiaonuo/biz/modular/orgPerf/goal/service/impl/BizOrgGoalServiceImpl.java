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
import vip.xiaonuo.biz.modular.orgPerf.business.service.BizBusinessService;
import vip.xiaonuo.biz.modular.orgPerf.finance.service.BizFinanceService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.enums.BizOrgAssPeriodlEnum;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalItemService;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizOrgGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.product.service.BizProductService;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryListVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizOrgGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalService;

import javax.annotation.Resource;
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

    @Override
    public Page<BizOrgGoal> page(BizOrgGoalPageParam bizOrgGoalPageParam) {
        QueryWrapper<BizOrgGoal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BizOrgGoal::getAssOrg, bizOrgGoalPageParam.getAssOrg());
        queryWrapper.lambda().eq(BizOrgGoal::getAssPeriod, bizOrgGoalPageParam.getAssPeriod());
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
                .eq(BizOrgGoal::getAssPeriod, bizOrgGoalAddParam.getAssPeriod())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalAddParam.getOrgId());
        if(BizOrgAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalAddParam.getAssPeriod())){
            queryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalAddParam.getAssYear());
        }
        if(BizOrgAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalAddParam.getAssPeriod())){
            queryWrapper.eq(BizOrgGoal::getAssSeason, bizOrgGoalAddParam.getAssSeason());
        }
        long count = this.count(queryWrapper);
        if(count > 0){
            String assPeriod = ObjectUtil.isEmpty(bizOrgGoalAddParam.getAssSeason()) ? bizOrgGoalAddParam.getAssYear().toString()
                    : bizOrgGoalAddParam.getAssYear()+"-Q"+bizOrgGoalAddParam.getAssSeason();
            throw new CommonException("子公司/部门【{}】考核周期{}已存在", bizOrgGoalAddParam.getOrgName(), assPeriod);
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
                .eq(BizOrgGoal::getAssPeriod, bizOrgGoalEditParam.getAssPeriod())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalEditParam.getOrgId())
                .ne(BizOrgGoal::getId, bizOrgGoalEditParam.getId());
        if(BizOrgAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalEditParam.getAssPeriod())){
            queryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalEditParam.getAssYear());
        }
        if(BizOrgAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalEditParam.getAssPeriod())){
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
    public void delete(List<BizOrgGoalIdParam> bizOrgGoalIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizOrgGoalIdParamList, BizOrgGoalIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            bizOrgGoalItemService.remove(new QueryWrapper<BizOrgGoalItem>().lambda().in(BizOrgGoalItem::getGoalId,ids));
        }
    }

    @Override
    public BizOrgGoalVo detail(BizOrgGoalIdParam bizOrgGoalIdParam) {
        BizOrgGoal bizOrgGoal = this.queryEntity(bizOrgGoalIdParam.getId());
        BizOrgGoalVo bizOrgGoalVo = BeanUtil.copyProperties(bizOrgGoal, BizOrgGoalVo.class);
        // 查询明细
        List<BizOrgGoalItem> itemList = bizOrgGoalItemService.list(new LambdaQueryWrapper<BizOrgGoalItem>()
                .eq(BizOrgGoalItem::getGoalId, bizOrgGoalIdParam.getId()));
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
                .eq(BizOrgGoal::getAssPeriod, bizOrgGoalParam.getAssPeriod())
                .eq(BizOrgGoal::getOrgId, bizOrgGoalParam.getOrgId())
                .orderByDesc(BizOrgGoal::getCreateTime).last("limit 1");
        if(BizOrgAssPeriodlEnum.YEAR.getValue().equals(bizOrgGoalParam.getAssPeriod())){
            lambdaQueryWrapper.eq(BizOrgGoal::getAssYear, bizOrgGoalParam.getAssYear());
        }
        if(BizOrgAssPeriodlEnum.SEASON.getValue().equals(bizOrgGoalParam.getAssPeriod())){
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
                .eq(BizOrgGoal::getAssPeriod, bizOrgGoalEditOwnerParam.getAssPeriod())
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
}
