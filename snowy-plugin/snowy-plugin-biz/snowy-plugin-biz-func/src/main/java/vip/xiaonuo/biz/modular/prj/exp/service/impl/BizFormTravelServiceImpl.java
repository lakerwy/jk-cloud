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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.auth.core.util.StpLoginUserUtil;
import vip.xiaonuo.biz.modular.prj.exp.entity.*;
import vip.xiaonuo.biz.modular.prj.exp.param.*;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelCostService;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelItemService;
import vip.xiaonuo.biz.modular.prj.exp.vo.BizFormTravelTotal;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.exp.mapper.BizFormTravelMapper;
import vip.xiaonuo.biz.modular.prj.exp.service.BizFormTravelService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 费用报销（二期）- 差旅费用报销单Service接口实现类
 *
 * @author yong.ye
 * @date  2025/02/24 15:17
 **/
@Service
@DS("slave_1")
public class BizFormTravelServiceImpl extends ServiceImpl<BizFormTravelMapper, BizFormTravel> implements BizFormTravelService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizFormTravelItemService bizFormTravelItemService;

    @Resource
    private BizFormTravelCostService bizFormTravelCostService;

    @Override
    public Page<BizFormTravel> page(BizFormTravelPageParam bizFormTravelPageParam) {
        QueryWrapper<BizFormTravel> queryWrapper = getBizFormTravelQueryWrapper(bizFormTravelPageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFormTravelAddParam bizFormTravelAddParam) {
        BizFormTravel bizFormTravel = BeanUtil.toBean(bizFormTravelAddParam, BizFormTravel.class);
        //走审批流程
        String processInstanceId = bizFormTravelAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizFormTravel.setId(id);
            bizFormTravel.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizFormTravel.setModelId(modelId);
            bizFormTravel.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_FORM_TRAVEL.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizFormTravel.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(bizFormTravel);
        //处理费用明细
        if(CollUtil.isNotEmpty(bizFormTravelAddParam.getItemList())){
            List<BizFormTravelItem> itemList = BeanUtil.copyToList(bizFormTravelAddParam.getItemList(), BizFormTravelItem.class);
            itemList.forEach(x->{
                x.setFormTravelId(bizFormTravel.getId());
            });
            bizFormTravelItemService.saveBatch(itemList);
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizFormTravelAddParam.getCostList())){
            List<BizFormTravelCost> costList = BeanUtil.copyToList(bizFormTravelAddParam.getCostList(), BizFormTravelCost.class);
            costList.forEach(x->{
                x.setFormTravelId(bizFormTravel.getId());
            });
            bizFormTravelCostService.saveBatch(costList);
        }
        this.save(bizFormTravel);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFormTravelEditParam bizFormTravelEditParam) {
        String id = bizFormTravelEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(bizFormTravelEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(bizFormTravelEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                bizFormTravelEditParam.setId(id);
            }
        }
        BizFormTravel bizFormTravel = this.queryEntity(bizFormTravelEditParam.getId());
        BeanUtil.copyProperties(bizFormTravelEditParam, bizFormTravel);
        this.updateById(bizFormTravel);

        //处理费用明细
        if(CollUtil.isNotEmpty(bizFormTravelEditParam.getItemList())){
            //本次传入的数据
            List<BizFormTravelItem> itemList = BeanUtil.copyToList(bizFormTravelEditParam.getItemList(), BizFormTravelItem.class);
            itemList.forEach(x->{
                x.setFormTravelId(bizFormTravel.getId());
            });
            //获取历史数据
            List<BizFormTravelItem> oldList = bizFormTravelItemService.list(
                    new QueryWrapper<BizFormTravelItem>().lambda().eq(BizFormTravelItem::getFormTravelId,bizFormTravel.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizFormTravelItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFormTravelItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizFormTravelItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFormTravelItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFormTravelItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFormTravelItemService.removeByIds(removeList);
            }
        }else{
            bizFormTravelItemService.remove(new QueryWrapper<BizFormTravelItem>().lambda().eq(BizFormTravelItem::getFormTravelId,bizFormTravel.getId()));
        }
        //处理成本一览表
        if(CollUtil.isNotEmpty(bizFormTravelEditParam.getCostList())){
            //本次传入的数据
            List<BizFormTravelCost> costList = BeanUtil.copyToList(bizFormTravelEditParam.getCostList(), BizFormTravelCost.class);
            costList.forEach(x->{
                x.setFormTravelId(bizFormTravel.getId());
            });
            //获取历史数据
            List<BizFormTravelCost> oldList = bizFormTravelCostService.list(
                    new QueryWrapper<BizFormTravelCost>().lambda().eq(BizFormTravelCost::getFormTravelId,bizFormTravel.getId()));
            List<String> oldIds = oldList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizFormTravelCost> addList = costList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizFormTravelCost> updateList = costList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizFormTravelCost> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizFormTravelCostService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizFormTravelCostService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizFormTravelCostService.removeByIds(removeList);
            }
        }else{
            bizFormTravelCostService.remove(new QueryWrapper<BizFormTravelCost>().lambda().eq(BizFormTravelCost::getFormTravelId,bizFormTravel.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizFormTravelIdParam> bizFormTravelIdParamList) {
        // 执行删除
        List<String> delIds = CollStreamUtil.toList(bizFormTravelIdParamList, BizFormTravelIdParam::getId);
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            bizFormTravelItemService.remove(new QueryWrapper<BizFormTravelItem>().lambda().in(BizFormTravelItem::getFormTravelId,delIds));
            bizFormTravelCostService.remove(new QueryWrapper<BizFormTravelCost>().lambda().in(BizFormTravelCost::getFormTravelId,delIds));
        }
    }

    @Override
    public BizFormTravel detail(BizFormTravelIdParam bizFormTravelIdParam) {
        return this.queryEntity(bizFormTravelIdParam.getId());
    }

    @Override
    public BizFormTravel queryEntity(String id) {
        BizFormTravel bizFormTravel = this.getById(id);
        if(ObjectUtil.isEmpty(bizFormTravel)) {
            throw new CommonException("费用报销（二期）- 差旅费用报销单不存在，id值为：{}", id);
        }
        return bizFormTravel;
    }

    @Override
    public BizFormTravelTotal pageTotal(BizFormTravelPageParam bizFormTravelPageParam) {
        QueryWrapper<BizFormTravel> queryWrapper = getBizFormTravelQueryWrapper(bizFormTravelPageParam);
        List<BizFormTravel> dataList = this.list(queryWrapper);
        BigDecimal totalApplyAmountTotal = dataList.stream().map(BizFormTravel::getApplyAmountTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyAmountTotal = dataList.stream().map(BizFormTravel::getVerifyAmountTotal).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyOffsetAmount = dataList.stream().map(BizFormTravel::getVerifyOffsetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalVerifyPayAmount = dataList.stream().map(BizFormTravel::getVerifyPayAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal totalCnPayAmount = dataList.stream().map(BizFormTravel::getCnPayAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BizFormTravelTotal result = new BizFormTravelTotal();
        result.setApplyAmountTotal(totalApplyAmountTotal);
        result.setVerifyAmountTotal(totalVerifyAmountTotal);
        result.setVerifyOffsetAmountTotal(totalVerifyOffsetAmount);
        result.setVerifyPayAmountTotal(totalVerifyPayAmount);
        result.setCnPayAmountTotal(totalCnPayAmount);
        return result;
    }


    /**
     * 查询参数
     * @param bizFormTravelPageParam
     * @return
     */
    private static QueryWrapper<BizFormTravel> getBizFormTravelQueryWrapper(BizFormTravelPageParam bizFormTravelPageParam) {
        QueryWrapper<BizFormTravel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(BizFormTravel::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getCompany())) {
            queryWrapper.lambda().eq(BizFormTravel::getCompany, bizFormTravelPageParam.getCompany());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getOrgName())) {
            queryWrapper.lambda().like(BizFormTravel::getOrgName, bizFormTravelPageParam.getOrgName());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getStartExpDate()) && ObjectUtil.isNotEmpty(bizFormTravelPageParam.getEndExpDate())) {
            queryWrapper.lambda().between(BizFormTravel::getExpDate, bizFormTravelPageParam.getStartExpDate(), bizFormTravelPageParam.getEndExpDate());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getExpId())) {
            queryWrapper.lambda().eq(BizFormTravel::getExpId, bizFormTravelPageParam.getExpId());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getExpName())) {
            queryWrapper.lambda().like(BizFormTravel::getExpName, bizFormTravelPageParam.getExpName());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getCashId())) {
            queryWrapper.lambda().eq(BizFormTravel::getCashId, bizFormTravelPageParam.getCashId());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getCashSn())) {
            queryWrapper.lambda().like(BizFormTravel::getCashSn, bizFormTravelPageParam.getCashSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getContractNo())) {
            queryWrapper.lambda().like(BizFormTravel::getContractNo, bizFormTravelPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getDevNo())) {
            queryWrapper.lambda().like(BizFormTravel::getDevNo, bizFormTravelPageParam.getDevNo());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getSn())) {
            queryWrapper.lambda().like(BizFormTravel::getSn, bizFormTravelPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizFormTravel::getStatus, bizFormTravelPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizFormTravelPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizFormTravel::getSn, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getCompany, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getOrgName, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getCashSn, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getExpName, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getContractNo, bizFormTravelPageParam.getSearchKey())
                    .or().like(BizFormTravel::getDevNo, bizFormTravelPageParam.getSearchKey()));
        }

        // 校验数据范围
        List<String> loginUserDataScope = StpLoginUserUtil.getLoginUserDataScope();
        if(ObjectUtil.isNotEmpty(loginUserDataScope)) {
            queryWrapper.lambda().in(BizFormTravel::getOrgId, loginUserDataScope);
        } else {
            queryWrapper.lambda().eq(BizFormTravel::getExpId, StpUtil.getLoginIdAsString());
        }

        if(ObjectUtil.isAllNotEmpty(bizFormTravelPageParam.getSortField(), bizFormTravelPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizFormTravelPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizFormTravelPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizFormTravelPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(BizFormTravel::getExpDate).orderByDesc(BizFormTravel::getSn);
        }
        return queryWrapper;
    }
}
