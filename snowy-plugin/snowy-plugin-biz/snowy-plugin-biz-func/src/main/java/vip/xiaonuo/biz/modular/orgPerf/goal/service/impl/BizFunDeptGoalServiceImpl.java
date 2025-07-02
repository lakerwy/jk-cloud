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
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.enums.BizAssPeriodlEnum;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalKpiService;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizFunDeptGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.rating.entity.BizFunDeptRating;
import vip.xiaonuo.biz.modular.orgPerf.rating.service.BizFunDeptRatingService;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.*;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryListVo;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.orgPerf.goal.mapper.BizFunDeptGoalMapper;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 职能部门绩效考核-目标登记Service接口实现类
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
@Service
@DS("slave_1")
public class BizFunDeptGoalServiceImpl extends ServiceImpl<BizFunDeptGoalMapper, BizFunDeptGoal> implements BizFunDeptGoalService {

    @Resource
    private BizFunDeptGoalKpiService bizFunDeptGoalKpiService;

    @Resource
    private BizFunDeptRatingService bizFunDeptRatingService;

    @Resource
    private BizFunDeptGoalMapper bizFunDeptGoalMapper;

    @Override
    public Page<BizFunDeptGoal> page(BizFunDeptGoalPageParam bizFunDeptGoalPageParam) {
        QueryWrapper<BizFunDeptGoal> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(bizFunDeptGoalPageParam.getSortField(), bizFunDeptGoalPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFunDeptGoalPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFunDeptGoalPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFunDeptGoalPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizFunDeptGoal::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFunDeptGoalAddParam bizFunDeptGoalAddParam) {
        BizFunDeptGoal bizFunDeptGoal = BeanUtil.toBean(bizFunDeptGoalAddParam, BizFunDeptGoal.class);
        this.save(bizFunDeptGoal);
        // 校验
        List<BizFunDeptGoalKpi> kpiList = null;
        if(CollUtil.isNotEmpty(bizFunDeptGoalAddParam.getKpiList())){
            kpiList = BeanUtil.copyToList(bizFunDeptGoalAddParam.getKpiList(), BizFunDeptGoalKpi.class);
            // 这里如果校验权重之和的话，那么添加目标调整时也需要校验
        }
        LambdaQueryWrapper<BizFunDeptGoal> lambdaQueryWrapper = new LambdaQueryWrapper<BizFunDeptGoal>()
                .eq(BizFunDeptGoal::getAssType, bizFunDeptGoalAddParam.getAssType())
                .eq(BizFunDeptGoal::getOrgId, bizFunDeptGoalAddParam.getOrgId());
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizFunDeptGoalAddParam.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssYear, bizFunDeptGoalAddParam.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizFunDeptGoalAddParam.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssSeason, bizFunDeptGoalAddParam.getAssSeason());
        }
        long count = this.count(lambdaQueryWrapper);
        if(count > 0){
            String assType = ObjectUtil.isEmpty(bizFunDeptGoalAddParam.getAssSeason()) ? bizFunDeptGoalAddParam.getAssYear().toString()
                    : bizFunDeptGoalAddParam.getAssYear()+"-Q"+bizFunDeptGoalAddParam.getAssSeason();
            throw new CommonException("子公司/部门【{}】考核周期{}已存在", bizFunDeptGoalAddParam.getOrgName(), assType);
        }
        BizFunDeptGoal bizFunDeptGoal1 = BeanUtil.toBean(bizFunDeptGoalAddParam, BizFunDeptGoal.class);
        this.save(bizFunDeptGoal1);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            kpiList.forEach(x->{
                x.setGoalId(bizFunDeptGoal1.getId());
            });
            bizFunDeptGoalKpiService.saveBatch(kpiList);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFunDeptGoalEditParam bizFunDeptGoalEditParam) {
        // 校验
        List<BizFunDeptGoalKpi> kpiList = null;
        if(CollUtil.isNotEmpty(bizFunDeptGoalEditParam.getKpiList())){
            kpiList = BeanUtil.copyToList(bizFunDeptGoalEditParam.getKpiList(), BizFunDeptGoalKpi.class);
        }
        LambdaQueryWrapper<BizFunDeptGoal> lambdaQueryWrapper = new LambdaQueryWrapper<BizFunDeptGoal>()
                .eq(BizFunDeptGoal::getAssType, bizFunDeptGoalEditParam.getAssType())
                .eq(BizFunDeptGoal::getOrgId, bizFunDeptGoalEditParam.getOrgId())
                .ne(BizFunDeptGoal::getId, bizFunDeptGoalEditParam.getId());
        if(BizAssPeriodlEnum.YEAR.getValue().equals(bizFunDeptGoalEditParam.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssYear, bizFunDeptGoalEditParam.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(bizFunDeptGoalEditParam.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssSeason, bizFunDeptGoalEditParam.getAssSeason());
        }
        long count = this.count(lambdaQueryWrapper);
        if(count > 0){
            String assType = ObjectUtil.isEmpty(bizFunDeptGoalEditParam.getAssSeason()) ? bizFunDeptGoalEditParam.getAssYear().toString()
                    : bizFunDeptGoalEditParam.getAssYear()+"-Q"+bizFunDeptGoalEditParam.getAssSeason();
            throw new CommonException("子公司/部门【{}】考核周期{}已存在", bizFunDeptGoalEditParam.getOrgName(), assType);
        }
        BizFunDeptGoal bizFunDeptGoal = this.queryEntity(bizFunDeptGoalEditParam.getId());
        BeanUtil.copyProperties(bizFunDeptGoalEditParam, bizFunDeptGoal);
        this.updateById(bizFunDeptGoal);
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            //本次传入的数据
            kpiList.forEach(x->{
                x.setGoalId(bizFunDeptGoal.getId());
            });
            //获取历史数据
            List<BizFunDeptGoalKpi> oldList = bizFunDeptGoalKpiService.list(new QueryWrapper<BizFunDeptGoalKpi>().lambda()
                    .eq(BizFunDeptGoalKpi::getGoalId, bizFunDeptGoal.getId()));
            List<String> oldIds = oldList.stream().map(BizFunDeptGoalKpi::getId).collect(Collectors.toList());
            //待新增的
            List<BizFunDeptGoalKpi> addList = kpiList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFunDeptGoalKpi> updateList = kpiList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizFunDeptGoalKpi::getId).collect(Collectors.toList());
            List<BizFunDeptGoalKpi> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFunDeptGoalKpiService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFunDeptGoalKpiService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFunDeptGoalKpiService.removeByIds(removeList);
            }
        }else{
            bizFunDeptGoalKpiService.remove(new QueryWrapper<BizFunDeptGoalKpi>().lambda().eq(BizFunDeptGoalKpi::getGoalId,bizFunDeptGoal.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFunDeptGoalIdParam> bizFunDeptGoalIdParamList) {
        List<String> ids = CollStreamUtil.toList(bizFunDeptGoalIdParamList, BizFunDeptGoalIdParam::getId);
        // 执行删除
        this.removeByIds(ids);
        if(CollUtil.isNotEmpty(ids)){
            bizFunDeptGoalKpiService.remove(new QueryWrapper<BizFunDeptGoalKpi>().lambda().in(BizFunDeptGoalKpi::getGoalId,ids));
        }
    }

    @Override
    public BizFunDeptGoalVo detail(BizFunDeptGoalIdParam bizFunDeptGoalIdParam) {
        BizFunDeptGoal bizFunDeptGoal = this.queryEntity(bizFunDeptGoalIdParam.getId());
        BizFunDeptGoalVo bizFunDeptGoalVo = BeanUtil.copyProperties(bizFunDeptGoal, BizFunDeptGoalVo.class);
        // 查询明细
        List<BizFunDeptGoalKpi> kpiList = bizFunDeptGoalKpiService.list(new LambdaQueryWrapper<BizFunDeptGoalKpi>()
                .eq(BizFunDeptGoalKpi::getGoalId, bizFunDeptGoalIdParam.getId()));
        bizFunDeptGoalVo.setKpiList(kpiList);
        return bizFunDeptGoalVo;
    }

    @Override
    public BizFunDeptGoal queryEntity(String id) {
        BizFunDeptGoal bizFunDeptGoal = this.getById(id);
        if(ObjectUtil.isEmpty(bizFunDeptGoal)) {
            throw new CommonException("职能部门绩效考核-目标登记不存在，id值为：{}", id);
        }
        return bizFunDeptGoal;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFunDeptGoal(String goalId, List<BizFunDeptGoalKpi> kpiList) {
        // 处理KPI指标-集合
        if(CollUtil.isNotEmpty(kpiList)){
            //本次传入的数据
            kpiList.forEach(x->{
                x.setGoalId(goalId);
            });
            //获取历史数据
            List<BizFunDeptGoalKpi> oldList = bizFunDeptGoalKpiService.list(new QueryWrapper<BizFunDeptGoalKpi>().lambda()
                    .eq(BizFunDeptGoalKpi::getGoalId, goalId));
            List<String> oldIds = oldList.stream().map(BizFunDeptGoalKpi::getId).collect(Collectors.toList());
            //待新增的
            List<BizFunDeptGoalKpi> addList = kpiList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFunDeptGoalKpi> updateList = kpiList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(BizFunDeptGoalKpi::getId).collect(Collectors.toList());
            List<BizFunDeptGoalKpi> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFunDeptGoalKpiService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFunDeptGoalKpiService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFunDeptGoalKpiService.removeByIds(removeList);
            }
        }else{
            bizFunDeptGoalKpiService.remove(new QueryWrapper<BizFunDeptGoalKpi>().lambda().eq(BizFunDeptGoalKpi::getGoalId,goalId));
        }
    }

    @Override
    public List<FunDeptSummaryListVo> funDeptList(FunDeptSummaryListParam param) {
        return bizFunDeptGoalMapper.funDeptList(param);
    }

    @Override
    public List<FunDeptSummaryDetailVo> funDeptDetail(FunDeptDetailParam param) {
        List<BizFunDeptGoalKpi> kpiList = bizFunDeptGoalKpiService.list(new LambdaQueryWrapper<BizFunDeptGoalKpi>().eq(BizFunDeptGoalKpi::getGoalId, param.getId()));
        List<FunDeptSummaryDetailVo> voList = BeanUtil.copyToList(kpiList, FunDeptSummaryDetailVo.class);
        if(CollUtil.isEmpty(voList)){
            return voList;
        }
        List<String> kpiIdList = kpiList.stream().map(BizFunDeptGoalKpi::getId).collect(Collectors.toList());
        // 查询考评得分、流程状态
        List<BizFunDeptRating> ratingList = bizFunDeptRatingService.list(new LambdaQueryWrapper<BizFunDeptRating>().in(BizFunDeptRating::getKpiId, kpiIdList));
        voList.forEach(vo -> {
            BizFunDeptRating bizFunDeptRating = ratingList.stream().filter(x -> vo.getId().equals(x.getKpiId())).findFirst().orElse(null);
            if(ObjectUtil.isNotEmpty(bizFunDeptRating)){
                vo.setScore(bizFunDeptRating.getLeaderAssScore());
                vo.setRatingStatus(bizFunDeptRating.getStatus());
            }
        });
        return voList;
    }

    @Override
    public void editPart(BizFunDeptGoalKpiEditPartParam bizFunDeptGoalEditParam) {
        bizFunDeptGoalKpiService.update(new LambdaUpdateWrapper<BizFunDeptGoalKpi>().eq(BizFunDeptGoalKpi::getId, bizFunDeptGoalEditParam.getId())
                .set(BizFunDeptGoalKpi::getActualCompDesc, bizFunDeptGoalEditParam.getActualCompDesc())
                .set(BizFunDeptGoalKpi::getActualCompDesc, bizFunDeptGoalEditParam.getAttach())
                .set(BizFunDeptGoalKpi::getProgress, bizFunDeptGoalEditParam.getProgress()));
    }

    @Override
    public void editOwner(BizFunDeptGoalEditOwnerParam param) {
        LambdaUpdateWrapper<BizFunDeptGoal> updateWrapper = new LambdaUpdateWrapper<BizFunDeptGoal>().eq(BizFunDeptGoal::getAssType, param.getAssType())
                .eq(BizFunDeptGoal::getOrgId, param.getOrgId())
                .set(BizFunDeptGoal::getOwner, param.getOwner())
                .set(BizFunDeptGoal::getOwnerId, param.getOwnerId());
        if(BizAssPeriodlEnum.YEAR.getValue().equals(param.getAssType())){
            updateWrapper.eq(BizFunDeptGoal::getAssYear, param.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(param.getAssType())){
            updateWrapper.eq(BizFunDeptGoal::getAssYear, param.getAssSeason());
        }
        this.update(updateWrapper);
    }

    @Override
    public BizFunDeptGoalVo getByCondition(BizFunDeptGoalParam param) {
        LambdaQueryWrapper<BizFunDeptGoal> lambdaQueryWrapper = new LambdaQueryWrapper<BizFunDeptGoal>()
                .eq(BizFunDeptGoal::getAssType, param.getAssType())
                .eq(BizFunDeptGoal::getOrgId, param.getOrgId())
                .orderByDesc(BizFunDeptGoal::getCreateTime).last("limit 1");
        if(BizAssPeriodlEnum.YEAR.getValue().equals(param.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssYear, param.getAssYear());
        }
        if(BizAssPeriodlEnum.SEASON.getValue().equals(param.getAssType())){
            lambdaQueryWrapper.eq(BizFunDeptGoal::getAssYear, param.getAssSeason());
        }
        BizFunDeptGoal BizFunDeptGoal = this.getOne(lambdaQueryWrapper);
        if(ObjectUtil.isEmpty(BizFunDeptGoal)){
            throw new CommonException("未找到对应的目标登记信息");
        }
        BizFunDeptGoalVo vo = BeanUtil.copyProperties(BizFunDeptGoal, BizFunDeptGoalVo.class);
        // 查询明细
        List<BizFunDeptGoalKpi> kpiList = bizFunDeptGoalKpiService.list(new LambdaQueryWrapper<BizFunDeptGoalKpi>()
                .eq(BizFunDeptGoalKpi::getGoalId, BizFunDeptGoal.getId()));
        vo.setKpiList(kpiList);
        return vo;
    }

}
