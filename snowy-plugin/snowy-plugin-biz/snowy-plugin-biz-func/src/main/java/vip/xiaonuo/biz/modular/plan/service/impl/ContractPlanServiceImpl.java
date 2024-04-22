/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.plan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.plan.entity.ContractBidDept;
import vip.xiaonuo.biz.modular.plan.entity.ContractUnit;
import vip.xiaonuo.biz.modular.plan.entity.ContractUnitCost;
import vip.xiaonuo.biz.modular.plan.service.ContractBidDeptService;
import vip.xiaonuo.biz.modular.plan.service.ContractUnitCostService;
import vip.xiaonuo.biz.modular.plan.service.ContractUnitService;
import vip.xiaonuo.biz.modular.plan.vo.ContractPlanVo;
import vip.xiaonuo.biz.modular.plan.vo.ContractUnitVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.plan.entity.ContractPlan;
import vip.xiaonuo.biz.modular.plan.mapper.ContractPlanMapper;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanAddParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanEditParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanIdParam;
import vip.xiaonuo.biz.modular.plan.param.ContractPlanPageParam;
import vip.xiaonuo.biz.modular.plan.service.ContractPlanService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合约规划 - 申请流程Service接口实现类
 *
 * @author yong.ye
 * @date  2024/04/22 11:41
 **/
@Service
@DS("slave_1")
public class ContractPlanServiceImpl extends ServiceImpl<ContractPlanMapper, ContractPlan> implements ContractPlanService {

    @Resource
    private ContractBidDeptService contractBidDeptService;
    @Resource
    private ContractUnitService contractUnitService;
    @Resource
    private ContractUnitCostService contractUnitCostService;
    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Override
    public Page<ContractPlanVo> page(ContractPlanPageParam contractPlanPageParam) {
        QueryWrapper<ContractPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(ContractPlan::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getPrjContact())) {
            queryWrapper.lambda().like(ContractPlan::getPrjContact, contractPlanPageParam.getPrjContact());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getPrjSubject())) {
            queryWrapper.lambda().eq(ContractPlan::getPrjSubject, contractPlanPageParam.getPrjSubject());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBidder())) {
            queryWrapper.lambda().like(ContractPlan::getBidder, contractPlanPageParam.getBidder());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBiddContact())) {
            queryWrapper.lambda().like(ContractPlan::getBiddContact, contractPlanPageParam.getBiddContact());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBiddUnit())) {
            queryWrapper.lambda().like(ContractPlan::getBiddUnit, contractPlanPageParam.getBiddUnit());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBiddUnitContact())) {
            queryWrapper.lambda().like(ContractPlan::getBiddUnitContact, contractPlanPageParam.getBiddUnitContact());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getPrjName())) {
            queryWrapper.lambda().like(ContractPlan::getPrjName, contractPlanPageParam.getPrjName());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBiddType())) {
            queryWrapper.lambda().eq(ContractPlan::getBiddType, contractPlanPageParam.getBiddType());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getPrjContent())) {
            queryWrapper.lambda().like(ContractPlan::getPrjContent, contractPlanPageParam.getPrjContent());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getIsAccept())) {
            queryWrapper.lambda().eq(ContractPlan::getIsAccept, contractPlanPageParam.getIsAccept());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBelongRegion())) {
            queryWrapper.lambda().like(ContractPlan::getBelongRegion, contractPlanPageParam.getBelongRegion());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getBidSubject())) {
            queryWrapper.lambda().like(ContractPlan::getBidSubject, contractPlanPageParam.getBidSubject());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getIsMain())) {
            queryWrapper.lambda().eq(ContractPlan::getIsMain, contractPlanPageParam.getIsMain());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getSn())) {
            queryWrapper.lambda().like(ContractPlan::getSn, contractPlanPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(contractPlanPageParam.getStatus())) {
            queryWrapper.lambda().eq(ContractPlan::getStatus, contractPlanPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(contractPlanPageParam.getSortField(), contractPlanPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(contractPlanPageParam.getSortOrder());
            queryWrapper.orderBy(true, contractPlanPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(contractPlanPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(ContractPlan::getCreateTime);
        }
        Page<ContractPlan> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ContractPlanVo> contractPlanVos = BeanUtil.copyToList(result.getRecords(), ContractPlanVo.class);
//        contractPlanVos.forEach(item->{
//            //投标组织
//            List<ContractBidDept> bidDeptList = contractBidDeptService.list(new QueryWrapper<ContractBidDept>().lambda().eq(ContractBidDept::getBizContractPlanId,item.getId()).orderByAsc(ContractBidDept::getDeptType));
//            item.setBidDeptList(bidDeptList);
//            //项目承包方
//            List<ContractUnit> unitList = contractUnitService.list(new QueryWrapper<ContractUnit>().lambda().eq(ContractUnit::getBizContractPlanId,item.getId()).orderByAsc(ContractUnit::getPrjUnit).orderByAsc(ContractUnit::getDeptType));
//            List<ContractUnitVo> unitVoList = BeanUtil.copyToList(unitList, ContractUnitVo.class);
//            unitVoList.forEach(obj->{
//                //项目承包方-成本
//                List<ContractUnitCost> unitCostList = contractUnitCostService.list(new QueryWrapper<ContractUnitCost>().lambda().eq(ContractUnitCost::getBizContractUnitId,obj.getId()).orderByAsc(ContractUnitCost::getItemNo));
//                obj.setUnitCostList(unitCostList);
//            });
//            item.setUnitList(unitVoList);
//        });
        Page<ContractPlanVo> newResult = new Page<>();
        newResult.setRecords(contractPlanVos);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ContractPlanAddParam contractPlanAddParam) {
        ContractPlan contractPlan = BeanUtil.toBean(contractPlanAddParam, ContractPlan.class);
        //走审批流程
        String processInstanceId = contractPlanAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            contractPlan.setId(id);
            contractPlan.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            contractPlan.setModelId(modelId);
            contractPlan.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_CONTRACT_PLAN.getBizTable(),id,processInstanceId,dbName);
        }else{
            contractPlan.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(contractPlan);
        //投标组织
        if (CollUtil.isNotEmpty(contractPlanAddParam.getBidDeptList())) {
            List<ContractBidDept> tmpList = contractPlanAddParam.getBidDeptList();
            tmpList.forEach(v -> {
                v.setBizContractPlanId(contractPlan.getId());
            });
            contractBidDeptService.saveBatch(tmpList);
        }
        //申报-项目承包方
        if(CollUtil.isNotEmpty(contractPlanAddParam.getUnitList())){
            //项目承包方-成本
            List<ContractUnitCost> addUnitCostList = new ArrayList<>();
            contractPlanAddParam.getUnitList().forEach(item->{
                if(ObjectUtil.isEmpty(item.getId())){
                    item.setId(IdWorker.getIdStr());              //项目承包方-主键Id
                }
                item.setBizContractPlanId(contractPlan.getId());  //项目承包方-外键Id
                //承包方-成本
                if(CollUtil.isNotEmpty(item.getUnitCostList())){
                    item.getUnitCostList().forEach(obj->{
                        obj.setBizContractUnitId(item.getId());  //项目承包方-外键Id
                        obj.setBizContractPlanId(contractPlan.getId()); //合约规划-外键Id
                    });
                    addUnitCostList.addAll(item.getUnitCostList());
                }
            });
            List<ContractUnit> addUnitList = BeanUtil.copyToList(contractPlanAddParam.getUnitList(),ContractUnit.class);
            if(CollUtil.isNotEmpty(addUnitList)){
                contractUnitService.saveBatch(addUnitList);
            }
            if(CollUtil.isNotEmpty(addUnitCostList)){
                contractUnitCostService.saveBatch(addUnitCostList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ContractPlanEditParam contractPlanEditParam) {
        String id = contractPlanEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(contractPlanEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(contractPlanEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                contractPlanEditParam.setId(id);
            }
        }
        ContractPlan contractPlan = this.queryEntity(contractPlanEditParam.getId());
        BeanUtil.copyProperties(contractPlanEditParam, contractPlan);
        this.updateById(contractPlan);

        //【投标组织】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(contractPlanEditParam.getBidDeptList())) {
            contractPlanEditParam.getBidDeptList().forEach(x->x.setBizContractPlanId(contractPlan.getId()));
            //新增|修改|删除
            List<ContractBidDept> oldList = contractBidDeptService.list(new QueryWrapper<ContractBidDept>().lambda().eq(ContractBidDept::getBizContractPlanId, contractPlan.getId()));
            List<String> oldIds = oldList.stream().map(ContractBidDept::getId).collect(Collectors.toList());
            //待更新的
            List<ContractBidDept> updateList = contractPlanEditParam.getBidDeptList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<ContractBidDept> addList = contractPlanEditParam.getBidDeptList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(ContractBidDept::getId).collect(Collectors.toList());
            List<ContractBidDept> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                contractBidDeptService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                contractBidDeptService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                contractBidDeptService.removeByIds(removeList);
            }
        }else{
            contractBidDeptService.remove(new QueryWrapper<ContractBidDept>().lambda().eq(ContractBidDept::getBizContractPlanId, contractPlan.getId()));
        }

        //【申报-项目承包方】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(contractPlanEditParam.getUnitList())) {
            /**
             *   项目承包方-数据初处理
             */
            List<ContractUnitCost> addUnitCostList = new ArrayList<>();
            contractPlanEditParam.getUnitList().forEach(item->{
                if(ObjectUtil.isEmpty(item.getId())){
                    item.setId(IdWorker.getIdStr());              //项目承包方-主键Id
                }
                item.setBizContractPlanId(contractPlan.getId());  //项目承包方-外键Id
                //承包方-成本
                if(CollUtil.isNotEmpty(item.getUnitCostList())){
                    item.getUnitCostList().forEach(obj->{
                        obj.setBizContractUnitId(item.getId());  //项目承包方-外键Id
                        obj.setBizContractPlanId(contractPlan.getId()); //合约规划-外键Id
                    });
                    addUnitCostList.addAll(item.getUnitCostList());
                }
            });
            /**
             * 项目承包方
             */
            List<ContractUnit> tmpContractUnitList = BeanUtil.copyToList(contractPlanEditParam.getUnitList(),ContractUnit.class);
            //新增|修改|删除
            List<ContractUnit> oldList = contractUnitService.list(new QueryWrapper<ContractUnit>().lambda().eq(ContractUnit::getBizContractPlanId, contractPlan.getId()));
            List<String> oldIds = oldList.stream().map(ContractUnit::getId).collect(Collectors.toList());
            //待更新的
            List<ContractUnit> updateList = tmpContractUnitList.stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<ContractUnit> addList = tmpContractUnitList.stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(ContractUnit::getId).collect(Collectors.toList());
            List<ContractUnit> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                contractUnitService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                contractUnitService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                contractUnitService.removeByIds(removeList);
            }
            /**
             * 处理 - 项目承包方 - 成本（全删-再增）
             */
            //申报-项目承包方-成本
            QueryWrapper<ContractUnitCost> unitCostQueryWrapper = new QueryWrapper<>();
            unitCostQueryWrapper.lambda().in(ContractUnitCost::getBizContractPlanId,contractPlan.getId());
            contractUnitCostService.remove(unitCostQueryWrapper);
            contractUnitCostService.saveBatch(addUnitCostList);
        }else{
            contractUnitService.remove(new QueryWrapper<ContractUnit>().lambda().eq(ContractUnit::getBizContractPlanId, contractPlan.getId()));
            contractUnitCostService.remove(new QueryWrapper<ContractUnitCost>().lambda().eq(ContractUnitCost::getBizContractPlanId, contractPlan.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ContractPlanIdParam> contractPlanIdParamList) {
        List<String> delIds = CollStreamUtil.toList(contractPlanIdParamList, ContractPlanIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            //投标组织
            QueryWrapper<ContractBidDept> bidDeptQueryWrapper = new QueryWrapper<>();
            bidDeptQueryWrapper.lambda().in(ContractBidDept::getBizContractPlanId,delIds);
            contractBidDeptService.remove(bidDeptQueryWrapper);
            //申报-项目承包方
            QueryWrapper<ContractUnit> unitQueryWrapper = new QueryWrapper<>();
            unitQueryWrapper.lambda().in(ContractUnit::getBizContractPlanId,delIds);
            contractUnitService.remove(unitQueryWrapper);
            //申报-项目承包方-成本
            QueryWrapper<ContractUnitCost> unitCostQueryWrapper = new QueryWrapper<>();
            unitCostQueryWrapper.lambda().in(ContractUnitCost::getBizContractPlanId,delIds);
            contractUnitCostService.remove(unitCostQueryWrapper);
        }
    }

    @Override
    public ContractPlanVo detail(ContractPlanIdParam contractPlanIdParam) {
        ContractPlan contractPlan = this.queryEntity(contractPlanIdParam.getId());
        ContractPlanVo contractPlanVo = BeanUtil.copyProperties(contractPlan, ContractPlanVo.class);
        //投标组织
        List<ContractBidDept> bidDeptList = contractBidDeptService.list(new QueryWrapper<ContractBidDept>().lambda().eq(ContractBidDept::getBizContractPlanId,contractPlan.getId()).orderByAsc(ContractBidDept::getDeptType));
        contractPlanVo.setBidDeptList(bidDeptList);
        //项目承包方
        List<ContractUnit> unitList = contractUnitService.list(new QueryWrapper<ContractUnit>().lambda().eq(ContractUnit::getBizContractPlanId,contractPlan.getId()).orderByAsc(ContractUnit::getPrjUnit).orderByAsc(ContractUnit::getDeptType));
        List<ContractUnitVo> unitVoList = BeanUtil.copyToList(unitList, ContractUnitVo.class);
        unitVoList.forEach(obj->{
            //项目承包方-成本
            List<ContractUnitCost> unitCostList = contractUnitCostService.list(new QueryWrapper<ContractUnitCost>().lambda().eq(ContractUnitCost::getBizContractUnitId,obj.getId()).orderByAsc(ContractUnitCost::getItemNo));
            obj.setUnitCostList(unitCostList);
        });
        contractPlanVo.setUnitList(unitVoList);
        return contractPlanVo;
    }

    @Override
    public ContractPlan queryEntity(String id) {
        ContractPlan contractPlan = this.getById(id);
        if(ObjectUtil.isEmpty(contractPlan)) {
            throw new CommonException("合约规划 - 申请流程不存在，id值为：{}", id);
        }
        return contractPlan;
    }
}
