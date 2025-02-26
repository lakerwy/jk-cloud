package vip.xiaonuo.biz.modular.prj.exp.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.exp.entity.*;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormTravelMapper;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.service.*;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormFeeTotal;
import vip.xiaonuo.biz.modular.prj.exp.vo.ExpMonthTotal;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormFeeMapper;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 费用报销（二期）- 费用报销单Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:14
 **/
@Service
@DS("slave_1")
public class BizFormFeeServiceImpl extends ServiceImpl<BizFormFeeMapper, BizFormFee> implements BizFormFeeService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizFormFeeItemService bizFormFeeItemService;

    @Resource
    private BizFormFeeCostService bizFormFeeCostService;

    @Resource
    private BizFormFeeMapper bizFormFeeMapper;

    @Resource
    private BizFormTravelMapper bizFormTravelMapper;

    @Resource
    private BizFormTravelService bizFormTravelService;

    @Resource
    private BizFormBackService bizFormBackService;

    @Override
    public Page<BizFormFee> page(BizFormFeePageParam bizFormFeePageParam) {
        QueryWrapper<BizFormFee> queryWrapper = getBizFormFeeQueryWrapper(bizFormFeePageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormFeeAddParam bizFormFeeAddParam) {
        BizFormFee bizFormFee = BeanUtil.toBean(bizFormFeeAddParam, BizFormFee.class);
        //走审批流程
        String processInstanceId = bizFormFeeAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFormFee.setId(id);
            bizFormFee.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFormFee.setModelId(modelId);
            bizFormFee.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FORM_FEE.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFormFee.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFormFee);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizFormFeeAddParam.getItemList())){
            List<BizFormFeeItem> itemList = BeanUtil.copyToList(bizFormFeeAddParam.getItemList(), BizFormFeeItem.class);
            itemList.forEach(x->{
                x.setFormFeeId(bizFormFee.getId());
            });
            bizFormFeeItemService.saveBatch(itemList);
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizFormFeeAddParam.getCostList())){
            List<BizFormFeeCost> costList = BeanUtil.copyToList(bizFormFeeAddParam.getCostList(), BizFormFeeCost.class);
            costList.forEach(x->{
                x.setFormFeeId(bizFormFee.getId());
            });
            bizFormFeeCostService.saveBatch(costList);
        }
        this.save(bizFormFee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormFeeEditParam bizFormFeeEditParam) {
        String id = bizFormFeeEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFormFeeEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFormFeeEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFormFeeEditParam.setId(id);
            }
        }
        BizFormFee bizFormFee = this.queryEntity(bizFormFeeEditParam.getId());
        BeanUtil.copyProperties(bizFormFeeEditParam, bizFormFee);
        this.updateById(bizFormFee);

        //处理费用明细
        if(CollUtil.isNotEmpty(bizFormFeeEditParam.getItemList())){
            //本次传入的数据
            List<BizFormFeeItem> itemList = BeanUtil.copyToList(bizFormFeeEditParam.getItemList(), BizFormFeeItem.class);
            itemList.forEach(x->{
                x.setFormFeeId(bizFormFee.getId());
            });
            //获取历史数据
            List<BizFormFeeItem> oldList = bizFormFeeItemService.list(
                    new QueryWrapper<BizFormFeeItem>().lambda().eq(BizFormFeeItem::getFormFeeId,bizFormFee.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizFormFeeItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFormFeeItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizFormFeeItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFormFeeItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFormFeeItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFormFeeItemService.removeByIds(removeList);
            }
        }else{
            bizFormFeeItemService.remove(new QueryWrapper<BizFormFeeItem>().lambda().eq(BizFormFeeItem::getFormFeeId,bizFormFee.getId()));
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizFormFeeEditParam.getCostList())){
            //本次传入的数据
            List<BizFormFeeCost> costList = BeanUtil.copyToList(bizFormFeeEditParam.getCostList(), BizFormFeeCost.class);
            costList.forEach(x->{
                x.setFormFeeId(bizFormFee.getId());
            });
            //获取历史数据
            List<BizFormFeeCost> oldList = bizFormFeeCostService.list(
                    new QueryWrapper<BizFormFeeCost>().lambda().eq(BizFormFeeCost::getFormFeeId,bizFormFee.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizFormFeeCost> addList = costList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFormFeeCost> updateList = costList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizFormFeeCost> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFormFeeCostService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFormFeeCostService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFormFeeCostService.removeByIds(removeList);
            }
        }else{
            bizFormFeeCostService.remove(new QueryWrapper<BizFormFeeCost>().lambda().eq(BizFormFeeCost::getFormFeeId,bizFormFee.getId()));
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormFeeIdParam> bizFormFeeIdParamList) {
        // 执行删除
        List<String> delIds = CollStreamUtil.toList(bizFormFeeIdParamList, BizFormFeeIdParam::getId);
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            bizFormFeeItemService.remove(new QueryWrapper<BizFormFeeItem>().lambda().in(BizFormFeeItem::getFormFeeId,delIds));
            bizFormFeeCostService.remove(new QueryWrapper<BizFormFeeCost>().lambda().in(BizFormFeeCost::getFormFeeId,delIds));
        }
    }

    @Override
    public BizFormFee detail(BizFormFeeIdParam bizFormFeeIdParam) {
        return this.queryEntity(bizFormFeeIdParam.getId());
    }

    @Override
    public BizFormFee queryEntity(String id) {
        BizFormFee bizFormFee = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormFee)) {
            throw new CommonException("费用报销（二期）- 费用报销单不存在，id值为：{}", id);
        }
        return bizFormFee;
    }

    @Override
    public BizFormFeeTotal pageTotal(BizFormFeePageParam bizFormFeePageParam) {
        QueryWrapper<BizFormFee> queryWrapper = getBizFormFeeQueryWrapper(bizFormFeePageParam);
        List<BizFormFee> dataList = this.list(queryWrapper);
        BigDecimal totalApplyAmountTotal = dataList.stream().map(BizFormFee::getApplyAmountTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyAmountTotal = dataList.stream().map(BizFormFee::getVerifyAmountTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyOffsetAmount = dataList.stream().map(BizFormFee::getVerifyOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyPayAmount = dataList.stream().map(BizFormFee::getVerifyPayAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalCnPayAmount = dataList.stream().map(BizFormFee::getCnPayAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BizFormFeeTotal result = new BizFormFeeTotal();
        result.setApplyAmountTotal(totalApplyAmountTotal);
        result.setVerifyAmountTotal(totalVerifyAmountTotal);
        result.setVerifyOffsetAmountTotal(totalVerifyOffsetAmount);
        result.setVerifyPayAmountTotal(totalVerifyPayAmount);
        result.setCnPayAmountTotal(totalCnPayAmount);
        return result;
    }

    @Override
    public List<ExpMonthTotal> monthTotal(ExpMonthParam expMonthParam) {
        //2025-02
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(expMonthParam.getYearMonth(), formatter);
        //2025-02-01
        LocalDate firstDayOfYearMonth = yearMonth.atDay(1);
        //2025-03
        YearMonth nextYearMonth = yearMonth.plusMonths(1);
        //2025-03-01
        LocalDate firstDayOfNextYearMonth = nextYearMonth.atDay(1);

        List<ExpMonthTotal> result = new ArrayList<>();
        if("FEE".equals(expMonthParam.getExpType())){
            QueryWrapper<BizFormFee> feeQueryWrapper = new QueryWrapper<>();
            feeQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
            feeQueryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
            feeQueryWrapper.in("b.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
            feeQueryWrapper.ge("b.EXP_DATE",firstDayOfYearMonth);  //>=
            feeQueryWrapper.lt("b.EXP_DATE",firstDayOfNextYearMonth); //<
            if(ObjectUtil.isNotEmpty(expMonthParam.getCompany())){
                feeQueryWrapper.eq("b.COMPANY",expMonthParam.getCompany());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getOrgId())){
                feeQueryWrapper.eq("b.ORG_ID",expMonthParam.getOrgId());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getOrgName())){
                feeQueryWrapper.like("b.ORG_NAME",expMonthParam.getOrgName());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getExpItem())){
                feeQueryWrapper.eq("a.EXP_ITEM",expMonthParam.getExpItem());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getSecondExpItem())){
                feeQueryWrapper.eq("a.SECOND_EXP_ITEM",expMonthParam.getSecondExpItem());
            }

            // 校验数据范围
            List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
            if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
                feeQueryWrapper.in("b.ORG_ID", loginUserDataScope);
            } else {
                feeQueryWrapper.eq("b.EXP_ID", StpUtil.getLoginIdAsString());
            }

            feeQueryWrapper.orderByAsc("b.COMPANY").orderByAsc("b.ORG_NAME").orderByAsc("a.EXP_ITEM").orderByAsc("a.SECOND_EXP_ITEM");
            result = bizFormFeeMapper.getFeeCostMonthTotal(feeQueryWrapper);

        }else if ("TRAVEL".equals(expMonthParam.getExpType())){
            QueryWrapper<BizFormTravel> travelQueryWrapper = new QueryWrapper<>();
            travelQueryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
            travelQueryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
            travelQueryWrapper.in("b.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
            travelQueryWrapper.ge("b.EXP_DATE",firstDayOfYearMonth);  //>=
            travelQueryWrapper.lt("b.EXP_DATE",firstDayOfNextYearMonth); //<
            if(ObjectUtil.isNotEmpty(expMonthParam.getCompany())){
                travelQueryWrapper.eq("b.COMPANY",expMonthParam.getCompany());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getOrgId())){
                travelQueryWrapper.eq("b.ORG_ID",expMonthParam.getOrgId());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getOrgName())){
                travelQueryWrapper.like("b.ORG_NAME",expMonthParam.getOrgName());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getExpItem())){
                travelQueryWrapper.eq("a.EXP_ITEM",expMonthParam.getExpItem());
            }
            if(ObjectUtil.isNotEmpty(expMonthParam.getSecondExpItem())){
                travelQueryWrapper.eq("a.SECOND_EXP_ITEM",expMonthParam.getSecondExpItem());
            }

            // 校验数据范围
            List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
            if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
                travelQueryWrapper.in("b.ORG_ID", loginUserDataScope);
            } else {
                travelQueryWrapper.eq("b.EXP_ID", StpUtil.getLoginIdAsString());
            }

            travelQueryWrapper.orderByAsc("b.COMPANY").orderByAsc("b.ORG_NAME").orderByAsc("a.EXP_ITEM").orderByAsc("a.SECOND_EXP_ITEM");
            result = bizFormTravelMapper.getTravelCostMonthTotal(travelQueryWrapper);

        }else {
            throw new CommonException("不支持的报销类型");
        }
        return result;
    }

    @Override
    public void check(ExpCheckParam checkParam) {
        //有选择借支单 校验
        if(ObjectUtil.isNotEmpty(checkParam.getCashId())){
            List<BizFormFee> feeList = this.list(new LambdaQueryWrapper<BizFormFee>()
                    .eq(BizFormFee::getCashId,checkParam.getCashId()).in(BizFormFee::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue()));
            if(CollUtil.isNotEmpty(feeList)){
                throw new CommonException("借支单{}还存在审核中的费用报销单，请等待审核完成后再使用借支单{}，或使用其余借支单！",checkParam.getCashSn(),checkParam.getCashSn());
            }
            List<BizFormTravel> travelList = bizFormTravelService.list(new LambdaQueryWrapper<BizFormTravel>()
                    .eq(BizFormTravel::getCashId,checkParam.getCashId()).in(BizFormTravel::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue()));
            if(CollUtil.isNotEmpty(travelList)){
                throw new CommonException("借支单{}还存在审核中的差旅报销单，请等待审核完成后再使用借支单{}，或使用其余借支单！",checkParam.getCashSn(),checkParam.getCashSn());
            }
            List<BizFormBack> backList = bizFormBackService.list(new LambdaQueryWrapper<BizFormBack>()
                    .eq(BizFormBack::getCashId,checkParam.getCashId()).in(BizFormBack::getStatus,FlwStatusEnum.ACTIVE_STATUS.getValue()));
            if(CollUtil.isNotEmpty(backList)){
                throw new CommonException("借支单{}还存在审核中的归还申请，请等待审核完成后再使用借支单{}，或使用其余借支单！",checkParam.getCashSn(),checkParam.getCashSn());
            }
        }
    }


    private static QueryWrapper<BizFormFee> getBizFormFeeQueryWrapper(BizFormFeePageParam bizFormFeePageParam) {
        QueryWrapper<BizFormFee> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizFormFee::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getCompany())) {
            queryWrapper.lambda().eq(BizFormFee::getCompany, bizFormFeePageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getOrgName())) {
            queryWrapper.lambda().like(BizFormFee::getOrgName, bizFormFeePageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getStartExpDate()) && ObjectUtil.isNotEmpty(bizFormFeePageParam.getEndExpDate())) {
            queryWrapper.lambda().between(BizFormFee::getExpDate, bizFormFeePageParam.getStartExpDate(), bizFormFeePageParam.getEndExpDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getExpId())) {
            queryWrapper.lambda().eq(BizFormFee::getExpId, bizFormFeePageParam.getExpId());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getExpName())) {
            queryWrapper.lambda().like(BizFormFee::getExpName, bizFormFeePageParam.getExpName());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getCashId())) {
            queryWrapper.lambda().eq(BizFormFee::getCashId, bizFormFeePageParam.getCashId());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getCashSn())) {
            queryWrapper.lambda().like(BizFormFee::getCashSn, bizFormFeePageParam.getCashSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getContractNo())) {
            queryWrapper.lambda().like(BizFormFee::getContractNo, bizFormFeePageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getDevNo())) {
            queryWrapper.lambda().like(BizFormFee::getDevNo, bizFormFeePageParam.getDevNo());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getSn())) {
            queryWrapper.lambda().like(BizFormFee::getSn, bizFormFeePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getStatus())) {
            queryWrapper.lambda().eq(BizFormFee::getStatus, bizFormFeePageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizFormFeePageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizFormFee::getSn, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getCompany, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getOrgName, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getExpName, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getCashSn, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getContractNo, bizFormFeePageParam.getSearchKey())
                    .or().like(BizFormFee::getDevNo, bizFormFeePageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizFormFee::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizFormFee::getExpId, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizFormFeePageParam.getSortField(), bizFormFeePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormFeePageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormFeePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormFeePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizFormFee::getExpDate).orderByDesc(BizFormFee::getSn);
        }
        return queryWrapper;
    }
}
