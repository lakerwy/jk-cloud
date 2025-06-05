package vip.xiaonuo.biz.modular.crm.prjValueAllocation.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.entity.PrjValueAllocationItem;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.param.*;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.service.PrjValueAllocationItemService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.entity.PrjValueAllocation;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.mapper.PrjValueAllocationMapper;
import vip.xiaonuo.biz.modular.crm.prjValueAllocation.service.PrjValueAllocationService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目产值划分Service接口实现类
 *
 * @author pzh
 * @date  2025/06/04 16:35
 **/
@Service
@DS("slave_1")
public class PrjValueAllocationServiceImpl extends ServiceImpl<PrjValueAllocationMapper, PrjValueAllocation> implements PrjValueAllocationService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private FlwCommonApi flwCommonApi;

    @Resource
    private PrjValueAllocationItemService prjValueAllocationItemService;

    @Override
    public Page<PrjValueAllocation> page(PrjValueAllocationPageParam prjValueAllocationPageParam) {
        QueryWrapper<PrjValueAllocation> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(prjValueAllocationPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(PrjValueAllocation::getContractName, prjValueAllocationPageParam.getSearchKey())
                    .or().like(PrjValueAllocation::getContractNo, prjValueAllocationPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(prjValueAllocationPageParam.getSortField(), prjValueAllocationPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(prjValueAllocationPageParam.getSortOrder());
            queryWrapper.orderBy(true, prjValueAllocationPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(prjValueAllocationPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(PrjValueAllocation::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjValueAllocationAddParam prjValueAllocationAddParam) {
        // 校验
        if(CollUtil.isNotEmpty(prjValueAllocationAddParam.getPrjValueAllocationItemList())){
            List<PrjValueAllocationItem> items = BeanUtil.copyToList(prjValueAllocationAddParam.getPrjValueAllocationItemList(), PrjValueAllocationItem.class);
            this.check(items);
        }
        PrjValueAllocation prjValueAllocation = BeanUtil.toBean(prjValueAllocationAddParam, PrjValueAllocation.class);
        //走审批流程
        String processInstanceId = prjValueAllocationAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            prjValueAllocation.setId(id);
            prjValueAllocation.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            prjValueAllocation.setModelId(modelId);
            prjValueAllocation.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_PRJ_VALUE_ALLOCATION.getBizTable(),id,processInstanceId,dbName);
        }
        else{
            prjValueAllocation.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        if(CollUtil.isNotEmpty(prjValueAllocationAddParam.getPrjValueAllocationItemList())){
            List<PrjValueAllocationItem> items = BeanUtil.copyToList(prjValueAllocationAddParam.getPrjValueAllocationItemList(), PrjValueAllocationItem.class);
            items.forEach(x->{
                x.setAllocationId(prjValueAllocation.getId());
                x.setHetongId(prjValueAllocation.getHetongId());
            });
            prjValueAllocationItemService.saveBatch(items);
        }
        this.save(prjValueAllocation);
    }

    private void check(List<PrjValueAllocationItem> items) {
        for(PrjValueAllocationItem item : items){
            if(item.getAllocationRate() == null && item.getAllocationAmount() == null) {
                throw new CommonException("产值划分比例、产值划分金额二者至少填写一项");
            }
            if(item.getFeeRate() == null && item.getFeeAmount() == null) {
                throw new CommonException("费用比例、费用金额二者至少填写一项");
            }
        }
        // 按单位类型分组
        Map<String, Map<String, Long>> unitTypeToNameCount = items.stream().collect(
            Collectors.groupingBy(
                PrjValueAllocationItem::getUnitType,
                Collectors.groupingBy(PrjValueAllocationItem::getUnitName, Collectors.counting())
            )
        );
        unitTypeToNameCount.forEach((unitType,  nameMap) -> {
            nameMap.forEach((unitName,  count) -> {
//                if (count > 1) {
//                    throw new CommonException("单位名称不可重复");
//                }
                // 对当前单位类型+单位名称组合，检查费用类型是否重复
                Map<String, Long> feeTypeCount = items.stream()
                        .filter(obj -> unitType.equals(obj.getUnitType()) && unitName.equals(obj.getUnitName()))
                        .collect(Collectors.groupingBy(PrjValueAllocationItem::getFeeType, Collectors.counting()));
                feeTypeCount.forEach((feeType, feeCount) -> {
                    if (feeCount > 1) {
                        throw new CommonException("同一单位费用类型不可重复");
                    }
                });
            });
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjValueAllocationEditParam prjValueAllocationEditParam) {
        // 校验
        if(CollUtil.isNotEmpty(prjValueAllocationEditParam.getPrjValueAllocationItemList())){
            List<PrjValueAllocationItem> items = BeanUtil.copyToList(prjValueAllocationEditParam.getPrjValueAllocationItemList(), PrjValueAllocationItem.class);
            this.check(items);
        }
        String id = prjValueAllocationEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(prjValueAllocationEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(prjValueAllocationEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                prjValueAllocationEditParam.setId(id);
            }
        }
        PrjValueAllocation prjValueAllocation = this.queryEntity(prjValueAllocationEditParam.getId());
        BeanUtil.copyProperties(prjValueAllocationEditParam, prjValueAllocation);
        this.updateById(prjValueAllocation);
        // 产值划分明细
        if(CollUtil.isNotEmpty(prjValueAllocationEditParam.getPrjValueAllocationItemList())){
            //本次传入的数据
            List<PrjValueAllocationItem> itemList = BeanUtil.copyToList(prjValueAllocationEditParam.getPrjValueAllocationItemList(), PrjValueAllocationItem.class);
            itemList.forEach(x->{
                x.setAllocationId(prjValueAllocation.getId());
                x.setHetongId(prjValueAllocation.getHetongId());
            });
            //获取历史数据
            List<PrjValueAllocationItem> oldList = prjValueAllocationItemService.list(
                    new QueryWrapper<PrjValueAllocationItem>().lambda().eq(PrjValueAllocationItem::getAllocationId,prjValueAllocation.getId()));
            List<String> oldIds = oldList.stream().map(PrjValueAllocationItem::getId).collect(Collectors.toList());
            //待新增的
            List<PrjValueAllocationItem> addList = itemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<PrjValueAllocationItem> updateList = itemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(PrjValueAllocationItem::getId).collect(Collectors.toList());
            List<PrjValueAllocationItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                prjValueAllocationItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                prjValueAllocationItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                prjValueAllocationItemService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<PrjValueAllocationIdParam> prjValueAllocationIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(prjValueAllocationIdParamList, PrjValueAllocationIdParam::getId));
    }

    @Override
    public PrjValueAllocation detail(PrjValueAllocationIdParam prjValueAllocationIdParam) {
        return this.queryEntity(prjValueAllocationIdParam.getId());
    }

    @Override
    public PrjValueAllocation queryEntity(String id) {
        PrjValueAllocation prjValueAllocation = this.getById(id);
        if(ObjectUtil.isEmpty(prjValueAllocation)) {
            throw new CommonException("项目产值划分不存在，id值为：{}", id);
        }
        return prjValueAllocation;
    }
}
