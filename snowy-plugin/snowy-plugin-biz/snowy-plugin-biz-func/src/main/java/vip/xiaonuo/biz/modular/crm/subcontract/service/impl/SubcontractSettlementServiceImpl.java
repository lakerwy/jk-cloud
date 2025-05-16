package vip.xiaonuo.biz.modular.crm.subcontract.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.Subcontract;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlementItem;
import vip.xiaonuo.biz.modular.crm.subcontract.enums.SubcontractSettlementEnum;
import vip.xiaonuo.biz.modular.crm.subcontract.param.*;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractService;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractSettlementItemService;
import vip.xiaonuo.biz.modular.crm.subcontract.util.MyUtil;
import vip.xiaonuo.biz.modular.crm.subcontract.vo.*;
import vip.xiaonuo.biz.modular.pis.entity.PisAllotment;
import vip.xiaonuo.biz.modular.pis.service.PisAllotmentService;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlement;
import vip.xiaonuo.biz.modular.crm.subcontract.mapper.SubcontractSettlementMapper;
import vip.xiaonuo.biz.modular.crm.subcontract.service.SubcontractSettlementService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分包合同结算审批Service接口实现类
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
@Service
@DS("slave_1")
public class SubcontractSettlementServiceImpl extends ServiceImpl<SubcontractSettlementMapper, SubcontractSettlement> implements SubcontractSettlementService {

    @Resource
    CommonCacheOperator commonCacheOperator;

    @Resource
    FlwCommonApi flwCommonApi;

    @Resource
    SubcontractSettlementItemService subcontractSettlementItemService;

    @Resource
    private PisAllotmentService pisAllotmentService;

    @Resource
    private SubcontractService subcontractService;

    @Override
    public Page<SubcontractSettlement> page(SubcontractSettlementPageParam subcontractSettlementPageParam) {
        QueryWrapper<SubcontractSettlement> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isAllNotEmpty(subcontractSettlementPageParam.getSortField(), subcontractSettlementPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(subcontractSettlementPageParam.getSortOrder());
            queryWrapper.orderBy(true, subcontractSettlementPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(subcontractSettlementPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(SubcontractSettlement::getCreateTime);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SubcontractSettlementAddParam subcontractSettlementAddParam) {
        SubcontractSettlement subcontractSettlement = BeanUtil.toBean(subcontractSettlementAddParam, SubcontractSettlement.class);
        //走审批流程
        String processInstanceId = subcontractSettlementAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            subcontractSettlement.setId(id);
            subcontractSettlement.setContractNo(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            subcontractSettlement.setModelId(modelId);
            subcontractSettlement.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_CRM_SUBCONTRACT_SETTLEMENT.getBizTable(),id,processInstanceId,dbName);
        }
        //不走审批流程
        else{
            subcontractSettlement.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(subcontractSettlement);
        if(CollUtil.isNotEmpty(subcontractSettlementAddParam.getSubcontractSettlementItemList())){
            List<SubcontractSettlementItem> items = BeanUtil.copyToList(subcontractSettlementAddParam.getSubcontractSettlementItemList(), SubcontractSettlementItem.class);
            items.forEach(x->{
                x.setSubcontractSettlementId(subcontractSettlement.getId());
            });
            subcontractSettlementItemService.saveBatch(items);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SubcontractSettlementEditParam subcontractSettlementEditParam) {
        String id = subcontractSettlementEditParam.getId();
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(subcontractSettlementEditParam.getProcessInstanceId())){
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(subcontractSettlementEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id=formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                subcontractSettlementEditParam.setId(id);
            }
        }
        SubcontractSettlement subcontractSettlement = this.queryEntity(subcontractSettlementEditParam.getId());
        BeanUtil.copyProperties(subcontractSettlementEditParam, subcontractSettlement);
        this.updateById(subcontractSettlement);
        // 本次申请结算分包合同
        if(CollUtil.isNotEmpty(subcontractSettlementEditParam.getSubcontractSettlementItemList())){
            //本次传入的数据
            List<SubcontractSettlementItem> subcontractSettlementItemList = subcontractSettlementEditParam.getSubcontractSettlementItemList();
            subcontractSettlementItemList.forEach(x->{
                x.setSubcontractSettlementId(subcontractSettlement.getId());
            });
            //获取历史数据
            List<SubcontractSettlementItem> oldList = subcontractSettlementItemService.list(
                    new QueryWrapper<SubcontractSettlementItem>().lambda().eq(SubcontractSettlementItem::getSubcontractSettlementId,subcontractSettlement.getId()));
            List<String> oldIds = oldList.stream().map(SubcontractSettlementItem::getId).collect(Collectors.toList());
            //待新增的
            List<SubcontractSettlementItem> addList = subcontractSettlementItemList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<SubcontractSettlementItem> updateList = subcontractSettlementItemList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(SubcontractSettlementItem::getId).collect(Collectors.toList());
            List<SubcontractSettlementItem> removeList = oldList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                subcontractSettlementItemService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                subcontractSettlementItemService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                subcontractSettlementItemService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<SubcontractSettlementIdParam> subcontractSettlementIdParamList) {
        List<String> delIds = CollStreamUtil.toList(subcontractSettlementIdParamList, SubcontractSettlementIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        // 删除分包合同结算明细
        if(CollUtil.isNotEmpty(delIds)){
            subcontractSettlementItemService.remove(new QueryWrapper<SubcontractSettlementItem>().lambda().in(SubcontractSettlementItem::getSubcontractSettlementId,delIds));
        }
    }

    @Override
    public SubcontractSettlementVo detail(SubcontractSettlementIdParam subcontractSettlementIdParam) {
        SubcontractSettlement subcontractSettlement = this.queryEntity(subcontractSettlementIdParam.getId());
        SubcontractSettlementVo subcontractSettlementVo = BeanUtil.copyProperties(subcontractSettlement, SubcontractSettlementVo.class);
        subcontractSettlementVo.setSubcontractSettlementItemList(subcontractSettlementItemService.list(new QueryWrapper<SubcontractSettlementItem>().lambda().eq(SubcontractSettlementItem::getSubcontractSettlementId, subcontractSettlement.getId())));
        return subcontractSettlementVo;
    }

    @Override
    public SubcontractSettlement queryEntity(String id) {
        SubcontractSettlement subcontractSettlement = this.getById(id);
        if(ObjectUtil.isEmpty(subcontractSettlement)) {
            throw new CommonException("分包合同结算审批不存在，id值为：{}", id);
        }
        return subcontractSettlement;
    }

    @Override
    public void check(AllotmentHtIdParam param) {
        // 获取该主合同下的所有审核通过的分包合同
        List<Subcontract> subcontractList = subcontractService.list(new LambdaQueryWrapper<Subcontract>()
                .eq(Subcontract::getHetongId, param.getHetongId())
                .eq(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(CollUtil.isEmpty(subcontractList)){
            return;
        }
        // 分包合同编号集合
        List<String> subcontractNumberList = subcontractList.stream().map(Subcontract::getContractNo).collect(Collectors.toList());
        // 审核中的生产院及子公司项目拨付流程
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(new LambdaQueryWrapper<PisAllotment>()
                .eq(PisAllotment::getStatus, FlwStatusEnum.ACTIVE_STATUS.getValue()));
        if(CollUtil.isEmpty(pisAllotmentList)){
            return;
        }
        // 还在审批中的分包合同编号集合
        List<String> activeSubcontractNumberList = CollUtil.newArrayList();
        pisAllotmentList.forEach(pisAllotment -> {
            subcontractNumberList.forEach(subcontractNumber -> {
                if(ObjectUtil.isNotEmpty(pisAllotment.getHJson()) ){
                    JSON json = JSONUtil.parse(pisAllotment.getHJson());
                    if(MyUtil.hasKeyValuePair(json, "subcontractNumber", subcontractNumber)){
                        activeSubcontractNumberList.add(subcontractNumber);
                    }
                }
            });
        });
        if(CollUtil.isNotEmpty(activeSubcontractNumberList)){
            throw new CommonException("主合同下有分包合同（{}），存在审批中的“生产院及子公司项目拨付”单据，请确认审核完成后再发起分包合同结算审批"
                    , CollUtil.join(subcontractNumberList, ","));
        }
    }

    @Override
    public List<AllotmentHisVo> allotmentHis(AllotmentHtIdParam param) {
        List<AllotmentHisVo> allotmentHisVoList = CollUtil.newArrayList();
        // 获取该主合同下的所有审核通过的分包合同
        List<Subcontract> subcontractList = subcontractService.list(new LambdaQueryWrapper<Subcontract>()
                .eq(Subcontract::getHetongId, param.getHetongId())
                .eq(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(CollUtil.isEmpty(subcontractList)){
            return allotmentHisVoList;
        }
        // 获取该主合同下的所有审核通过的生产院及子公司项目拨付流程
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(new LambdaQueryWrapper<PisAllotment>()
                .eq(PisAllotment::getHtId, param.getHetongId())
                .eq(PisAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(CollUtil.isEmpty(pisAllotmentList)){
            return allotmentHisVoList;
        }
        // 已拨付的分包合同
        pisAllotmentList.forEach(pisAllotment -> {
            JSONObject hJson = JSONUtil.parseObj(pisAllotment.getHJson());
            List<AllotmentInfo> allotmentInfos = parseHJson(hJson);
            for(AllotmentInfo allotmentInfo : allotmentInfos){
                subcontractList.forEach(subcontract -> {
                    if(subcontract.getContractNo().equals(allotmentInfo.getSubcontractNumber())){
                        AllotmentHisVo allotmentHisVo = new AllotmentHisVo();
                        allotmentHisVo.setSubcontractId(subcontract.getId());
                        allotmentHisVo.setSubcontractNo(subcontract.getContractNo());
                        allotmentHisVo.setSubcontractAmount(subcontract.getContractAmount());
                        allotmentHisVo.setPaymentDate(allotmentInfo.getPaymentDate());
                        allotmentHisVo.setAllotmentAmount(allotmentInfo.getPayCashier());
                        allotmentHisVo.setAllotmentSn(pisAllotment.getSn());
                        allotmentHisVo.setAllotmentProcessInstanceId(pisAllotment.getProcessInstanceId());
                        allotmentHisVo.setSubcontractProcessInstanceId(subcontract.getProcessInstanceId());
                        allotmentHisVoList.add(allotmentHisVo);
                    }
                });
            }
        });
        // 按分包合同编号排序
        allotmentHisVoList.sort(Comparator.comparing(AllotmentHisVo::getSubcontractNo,Comparator.nullsLast(Comparator.naturalOrder())));
        return allotmentHisVoList;
    }

    private static List<AllotmentInfo> parseHJson(JSONObject hJson) {
        List<AllotmentInfo> allotmentInfos = CollUtil.newArrayList();
        // 获取 tableListH 数组
        JSONArray jsonArray = hJson.getJSONArray("tableListH");
        for (int i = 0; i < jsonArray.size();  i++) {
            JSONObject outerObj = jsonArray.getJSONObject(i);
            // 分包合同编号
            String subcontractNumber = outerObj.getStr("subcontractNumber");
            // 付款日期
            String paymentDate = outerObj.getStr("paymentDate");
            // 出纳拨付金额
            BigDecimal payCashier = outerObj.getBigDecimal("payCashier");
            AllotmentInfo info = new AllotmentInfo();
            info.setSubcontractNumber(subcontractNumber);
            info.setPaymentDate(paymentDate);
            info.setPayCashier(payCashier);
            allotmentInfos.add(info);
        }
        Map<String, AllotmentInfo> resultMap = new HashMap<>();
        for (AllotmentInfo item : allotmentInfos) {
            resultMap.merge(
                item.getSubcontractNumber(),   // 使用subcontractNumber作为key
                item,              // 如果是第一次出现，直接放入
                (existing, newItem) -> {
                    // 合并逻辑：累加出纳拨付金额
                    BigDecimal oldPayCashier = existing.getPayCashier() == null ? BigDecimal.ZERO : existing.getPayCashier();
                    BigDecimal newPayCashier = newItem.getPayCashier() == null ? BigDecimal.ZERO : newItem.getPayCashier();
                    existing.setPayCashier(oldPayCashier.add(newPayCashier));
                    return existing;
                }
            );
        }
        List<AllotmentInfo> resultList = new ArrayList<>(resultMap.values());
        return resultList;
    }

    @Override
    public List<AllotmentVo> allotmentList(AllotmentHtIdParam param) {
        // 获取分包拨付信息
        List<AllotmentHisVo>  allotmentHisVoList = allotmentHis(param);
        List<AllotmentVo> settlementList = BeanUtil.copyToList(allotmentHisVoList, AllotmentVo.class);
        // 获取分包结算信息
        List<SubcontractSettlement> subcontractSettlementList = this.list(new LambdaQueryWrapper<SubcontractSettlement>()
                .eq(SubcontractSettlement::getHetongId, param.getHetongId())
                .in(SubcontractSettlement::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(), FlwStatusEnum.ACTIVE_STATUS.getValue()));
        // 未发起分包结算流程的情况
        if(CollUtil.isEmpty(subcontractSettlementList)){
            settlementList.forEach(x -> x.setSubcontractStatus(SubcontractSettlementEnum.NOT_START.getValue()));
            return settlementList;
        }
        Map<String, SubcontractSettlement> settlementMap = subcontractSettlementList.stream().collect(Collectors.toMap(SubcontractSettlement::getId,v1->v1));
        List<String> settlementIdList = subcontractSettlementList.stream().map(SubcontractSettlement::getId).collect(Collectors.toList());
        // 获取分包结算明细
        List<SubcontractSettlementItem> settlementItemList = subcontractSettlementItemService.list(new LambdaQueryWrapper<SubcontractSettlementItem>()
                .in(SubcontractSettlementItem::getSubcontractSettlementId, settlementIdList));
        Map<String, SubcontractSettlementItem> settlementItemMap = settlementItemList.stream().collect(Collectors.toMap(SubcontractSettlementItem::getSubcontractNo,v1->v1));
        for (AllotmentVo allotmentVo : settlementList) {
            SubcontractSettlementItem settlementItem = settlementItemMap.get(allotmentVo.getSubcontractNo());
            if(ObjectUtil.isEmpty(settlementItem)){
                allotmentVo.setSubcontractStatus(SubcontractSettlementEnum.NOT_START.getValue());
                continue;
            }
            SubcontractSettlement settlement = settlementMap.get(settlementItem.getSubcontractSettlementId());
            allotmentVo.setSettlementAmountTotal(settlementItem.getSettlementAmountTotal());
            allotmentVo.setSubcontractSettlementSn(settlement.getSn());
            allotmentVo.setSubcontractSettlementProcessInstanceId(settlement.getProcessInstanceId());
            allotmentVo.setSubcontractStatus(dealStatus(settlement.getStatus()));
        }
        return settlementList;
    }

    private String dealStatus(String status) {
        String subcontractStatus = null;
        switch (status) {
            case "0":
                subcontractStatus = SubcontractSettlementEnum.ACTIVE_STATUS.getValue();
                break;
            case "1":
                subcontractStatus =  SubcontractSettlementEnum.COMPLETED_STATUS.getValue();
                break;
            default:
                subcontractStatus =  SubcontractSettlementEnum.NOT_START.getValue();
                break;
        }
        return subcontractStatus;
    }

    @Override
    public List<SubcontractSettlementItemVo> notSettlementList(AllotmentHtIdParam param) {
        List<SubcontractSettlementItemVo> settlementItemVoList = CollUtil.newArrayList();
        // 获取该主合同下的所有审核通过的分包合同
        List<Subcontract> subcontractList = subcontractService.list(new LambdaQueryWrapper<Subcontract>()
                .eq(Subcontract::getHetongId, param.getHetongId())
                .eq(Subcontract::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(CollUtil.isEmpty(subcontractList)){
            return settlementItemVoList;
        }
        // 获取已结算的分包合同编号（审批中和审批完成）
        List<String> subcontractNoList = this.getSettlementSubcontractNo(param.getHetongId());
        if(CollUtil.isNotEmpty(subcontractNoList)){
            // subcontractList过滤掉subcontractNoList
            subcontractList = subcontractList.stream().filter(x->!subcontractNoList.contains(x.getContractNo())).collect(Collectors.toList());
        }
        if(CollUtil.isEmpty(subcontractList)){
            return settlementItemVoList;
        }
        // 获取该主合同下的所有审核通过的生产院及子公司项目拨付流程
        List<PisAllotment> pisAllotmentList = pisAllotmentService.list(new LambdaQueryWrapper<PisAllotment>()
                .eq(PisAllotment::getHtId, param.getHetongId())
                .eq(PisAllotment::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        if(CollUtil.isEmpty(pisAllotmentList)){
            return settlementItemVoList;
        }
        // 已拨付的分包合同，获取拨付金额并累加
        Map<String, BigDecimal> settlementAmountMap = new HashMap<>();
        for(PisAllotment pisAllotment : pisAllotmentList){
            JSONObject hJson = JSONUtil.parseObj(pisAllotment.getHJson());
            List<AllotmentInfo> allotmentInfos = parseHJson(hJson);
            for(AllotmentInfo allotmentInfo : allotmentInfos){
                subcontractList.forEach(subcontract -> {
                    if(subcontract.getContractNo().equals(allotmentInfo.getSubcontractNumber())){
                        BigDecimal allotmentAmount = allotmentInfo.getPayCashier() == null ? BigDecimal.ZERO : allotmentInfo.getPayCashier();
                        settlementAmountMap.merge(subcontract.getContractNo(), allotmentAmount, BigDecimal::add);
                    }
                });
            }
        }
        // subcontractList过滤掉未拨付的分包合同
        subcontractList = subcontractList.stream().filter(x-> settlementAmountMap.containsKey(x.getContractNo())).collect(Collectors.toList());
        for(Subcontract subcontract : subcontractList){
            SubcontractSettlementItemVo settlementItemVo = new SubcontractSettlementItemVo();
            settlementItemVo.setSubcontractId(subcontract.getId());
            settlementItemVo.setSubcontractNo(subcontract.getContractNo());
            settlementItemVo.setSubcontractName(subcontract.getProjectName());
            settlementItemVo.setCounterparty(subcontract.getCounterparty());
            settlementItemVo.setSubcontractAmount(subcontract.getContractAmount());
            settlementItemVo.setSettlementAmountTotal(settlementAmountMap.get(subcontract.getContractNo()));
            settlementItemVoList.add(settlementItemVo);
        }
        // 按分包合同编号排序
        settlementItemVoList.sort(Comparator.comparing(SubcontractSettlementItemVo::getSubcontractNo,Comparator.nullsLast(Comparator.naturalOrder())));
        return settlementItemVoList;
    }

    public List<String> getSettlementSubcontractNo(String hetongId){
        List<String> subcontractNoList = CollUtil.newArrayList();
        // 获取已结算的分包合同编号（审批中和审批完成）
        List<SubcontractSettlement> subcontractSettlementList = this.list(new LambdaQueryWrapper<SubcontractSettlement>().eq(SubcontractSettlement::getHetongId,hetongId)
                .in(SubcontractSettlement::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(), FlwStatusEnum.ACTIVE_STATUS)
                .select(SubcontractSettlement::getId));
        if(CollUtil.isNotEmpty(subcontractSettlementList)){
            List<String> settlementIdList = subcontractSettlementList.stream().map(SubcontractSettlement::getId).collect(Collectors.toList());
            List<SubcontractSettlementItem> settlementItemList = subcontractSettlementItemService.list(new LambdaQueryWrapper<SubcontractSettlementItem>()
                    .in(SubcontractSettlementItem::getSubcontractSettlementId, settlementIdList)
                    .select(SubcontractSettlementItem::getSubcontractNo));
            if(CollUtil.isNotEmpty(settlementItemList)){
                subcontractNoList = settlementItemList.stream().map(SubcontractSettlementItem::getSubcontractNo).collect(Collectors.toList());
            }
        }
        return subcontractNoList;
    }
}
