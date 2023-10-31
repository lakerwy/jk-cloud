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
package vip.xiaonuo.biz.modular.prj.plan.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlan;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanCollection;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;
import vip.xiaonuo.biz.modular.prj.plan.mapper.BizPrjPlanMapper;
import vip.xiaonuo.biz.modular.prj.plan.param.*;
import vip.xiaonuo.biz.modular.prj.plan.service.BizPrjPlanCollectionService;
import vip.xiaonuo.biz.modular.prj.plan.service.BizPrjPlanInvoiceService;
import vip.xiaonuo.biz.modular.prj.plan.service.BizPrjPlanService;
import vip.xiaonuo.biz.modular.prj.plan.vo.BizPrjPlanVo;
import vip.xiaonuo.biz.modular.prj.plan.vo.PlanInvoiceVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FormRelationTableApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 业务表-项目计划付款单Service接口实现类
 *
 * @author yong.ye
 * @date  2023/09/28 11:25
 **/

@DS("slave_1")
@Service
public class BizPrjPlanServiceImpl extends ServiceImpl<BizPrjPlanMapper, BizPrjPlan> implements BizPrjPlanService {

    @Resource
    private CommonCacheOperator commonCacheOperator;

    @Resource
    private BizPrjPlanInvoiceService bizPrjPlanInvoiceService;

    @Resource
    private BizPrjPlanCollectionService bizPrjPlanCollectionService;

    @Resource
    private BizPrjPlanMapper bizPrjPlanMapper;

    @Override
    public Page<BizPrjPlanVo> page(BizPrjPlanPageParam bizPrjPlanPageParam) {
        QueryWrapper<BizPrjPlan> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getHzfCode())) {
            queryWrapper.lambda().like(BizPrjPlan::getHzfCode, bizPrjPlanPageParam.getHzfCode());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getHzfName())) {
            queryWrapper.lambda().like(BizPrjPlan::getHzfName, bizPrjPlanPageParam.getHzfName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getStartApplyDate()) && ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getEndApplyDate())) {
            queryWrapper.lambda().between(BizPrjPlan::getApplyDate, bizPrjPlanPageParam.getStartApplyDate(), bizPrjPlanPageParam.getEndApplyDate());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getPlanNo())) {
            queryWrapper.lambda().like(BizPrjPlan::getPlanNo, bizPrjPlanPageParam.getPlanNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getContractNo())) {
            queryWrapper.lambda().eq(BizPrjPlan::getContractNo, bizPrjPlanPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getContractName())) {
            queryWrapper.lambda().like(BizPrjPlan::getContractName, bizPrjPlanPageParam.getContractName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(BizPrjPlan::getSealDate, bizPrjPlanPageParam.getStartSealDate(), bizPrjPlanPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getCtmCode())) {
            queryWrapper.lambda().like(BizPrjPlan::getCtmCode, bizPrjPlanPageParam.getCtmCode());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getCtmName())) {
            queryWrapper.lambda().like(BizPrjPlan::getCtmName, bizPrjPlanPageParam.getCtmName());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getSn())) {
            queryWrapper.lambda().eq(BizPrjPlan::getSn, bizPrjPlanPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getStatus())) {
            queryWrapper.lambda().eq(BizPrjPlan::getStatus, bizPrjPlanPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(bizPrjPlanPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(BizPrjPlan::getPlanNo,bizPrjPlanPageParam.getSearchKey())
                    .or().like(BizPrjPlan::getHzfCode,bizPrjPlanPageParam.getSearchKey())
                    .or().like(BizPrjPlan::getHzfName,bizPrjPlanPageParam.getSearchKey())
                    .or().like(BizPrjPlan::getContractNo,bizPrjPlanPageParam.getSearchKey())
                    .or().like(BizPrjPlan::getContractName,bizPrjPlanPageParam.getSearchKey())
                    .or().like(BizPrjPlan::getSn,bizPrjPlanPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(bizPrjPlanPageParam.getSortField(), bizPrjPlanPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(bizPrjPlanPageParam.getSortOrder());
            queryWrapper.orderBy(true, bizPrjPlanPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(bizPrjPlanPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(BizPrjPlan::getId);
        }
        Page<BizPrjPlanVo> result = bizPrjPlanMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        for (BizPrjPlanVo vo:result.getRecords()) {
            vo.setPlanInvoiceList(bizPrjPlanInvoiceService.list(new QueryWrapper<BizPrjPlanInvoice>().lambda().eq(BizPrjPlanInvoice::getPlanId,vo.getId())));
            vo.setPlanCollectionList(bizPrjPlanCollectionService.list(new QueryWrapper<BizPrjPlanCollection>().lambda().eq(BizPrjPlanCollection::getPlanId,vo.getId())));
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PrjPlanParam prjPlanParam) {
        BizPrjPlan bizPrjPlan = BeanUtil.toBean(prjPlanParam, BizPrjPlan.class);
        //走审批流程
        String processInstanceId = prjPlanParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            bizPrjPlan.setId(id);
            bizPrjPlan.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            bizPrjPlan.setModelId(modelId);
            bizPrjPlan.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            bizPrjPlan.setPlanNo(bizPrjPlan.getSn());
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            formRelationTableApi.save(modelId, BizCommonEnum.BIZ_PRJ_PLAN.getBizTable(),id,processInstanceId,dbName);
        }else{
            bizPrjPlan.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        bizPrjPlan.setExtJson(JsonUtils.object2json(prjPlanParam));
        bizPrjPlan.setApplyDate(DateTime.now());
        this.save(bizPrjPlan);
        //处理发票
        if(CollUtil.isNotEmpty(prjPlanParam.getPlanInvoiceList())){
            List<BizPrjPlanInvoice> prjPlanInvoiceList = prjPlanParam.getPlanInvoiceList();
            prjPlanInvoiceList.forEach(x->{
                x.setPlanId(bizPrjPlan.getId());
            });
            bizPrjPlanInvoiceService.saveBatch(prjPlanInvoiceList);
        }
        //处理收款单位
        if(CollUtil.isNotEmpty(prjPlanParam.getPlanCollectionList())){
            List<BizPrjPlanCollection> prjPlanCollectionList = prjPlanParam.getPlanCollectionList();
            prjPlanCollectionList.forEach(x->{
                x.setPlanId(bizPrjPlan.getId());
            });
            bizPrjPlanCollectionService.saveBatch(prjPlanCollectionList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(PrjPlanParam prjPlanParam) {
        String id = prjPlanParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(prjPlanParam.getProcessInstanceId())){
            FormRelationTableApi formRelationTableApi = SpringUtil.getBean(FormRelationTableApi.class);
            JSONObject formRelationTable = formRelationTableApi.getInfoByProcessInstanceId(prjPlanParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                prjPlanParam.setId(id);
            }
        }
        BizPrjPlan bizPrjPlan = this.queryEntity(prjPlanParam.getId());
        BeanUtil.copyProperties(prjPlanParam, bizPrjPlan);
        bizPrjPlan.setExtJson(JsonUtils.object2json(prjPlanParam));
        this.updateById(bizPrjPlan);
        //处理发票
        if(CollUtil.isNotEmpty(prjPlanParam.getPlanInvoiceList())){
            //本次传入的数据
            List<BizPrjPlanInvoice> prjPlanInvoiceList = prjPlanParam.getPlanInvoiceList();
            prjPlanInvoiceList.forEach(x->{
                x.setPlanId(bizPrjPlan.getId());
            });
            //获取历史数据
            List<BizPrjPlanInvoice> oldPlanInvoiceList = bizPrjPlanInvoiceService.list(
                    new QueryWrapper<BizPrjPlanInvoice>().lambda().eq(BizPrjPlanInvoice::getPlanId,bizPrjPlan.getId()));
            List<String> oldIds = oldPlanInvoiceList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizPrjPlanInvoice> addList = prjPlanInvoiceList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizPrjPlanInvoice> updateList = prjPlanInvoiceList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizPrjPlanInvoice> removeList = oldPlanInvoiceList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizPrjPlanInvoiceService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizPrjPlanInvoiceService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizPrjPlanInvoiceService.removeByIds(removeList);
            }
        }
        //处理收款单位
        if(CollUtil.isNotEmpty(prjPlanParam.getPlanCollectionList())){
            List<BizPrjPlanCollection> prjPlanCollectionList = prjPlanParam.getPlanCollectionList();
            prjPlanCollectionList.forEach(x->{
                x.setPlanId(bizPrjPlan.getId());
            });
            //获取历史数据
            List<BizPrjPlanCollection> oldPlanCollectionList = bizPrjPlanCollectionService.list(
                    new QueryWrapper<BizPrjPlanCollection>().lambda().eq(BizPrjPlanCollection::getPlanId,bizPrjPlan.getId()));
            List<String> oldIds = oldPlanCollectionList.stream().map(x->x.getId()).collect(Collectors.toList());
            //待新增的
            List<BizPrjPlanCollection> addList = prjPlanCollectionList.stream().filter(x->!oldIds.contains(x.getId())).collect(Collectors.toList());
            //待更新的
            List<BizPrjPlanCollection> updateList = prjPlanCollectionList.stream().filter(x->oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(x->x.getId()).collect(Collectors.toList());
            List<BizPrjPlanCollection> removeList = oldPlanCollectionList.stream().filter(x->!updateIds.contains(x.getId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(addList)){
                bizPrjPlanCollectionService.saveBatch(addList);
            }
            if(CollUtil.isNotEmpty(updateList)){
                bizPrjPlanCollectionService.updateBatchById(updateList);
            }
            if(CollUtil.isNotEmpty(removeList)){
                bizPrjPlanCollectionService.removeByIds(removeList);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<BizPrjPlanIdParam> bizPrjPlanIdParamList) {
        // 执行删除
        List<String> ids = CollStreamUtil.toList(bizPrjPlanIdParamList, BizPrjPlanIdParam::getId);
        this.removeByIds(ids);
        // 删除发票
        bizPrjPlanInvoiceService.remove(new QueryWrapper<BizPrjPlanInvoice>().lambda().in(BizPrjPlanInvoice::getPlanId,ids));
        // 删除单位
        bizPrjPlanCollectionService.remove(new QueryWrapper<BizPrjPlanCollection>().lambda().in(BizPrjPlanCollection::getPlanId,ids));
    }

    @Override
    public BizPrjPlan detail(BizPrjPlanIdParam bizPrjPlanIdParam) {
        return this.queryEntity(bizPrjPlanIdParam.getId());
    }

    @Override
    public BizPrjPlan queryEntity(String id) {
        BizPrjPlan bizPrjPlan = this.getById(id);
        if(ObjectUtil.isEmpty(bizPrjPlan)) {
            throw new CommonException("业务表-项目计划付款单不存在，id值为：{}", id);
        }
        return bizPrjPlan;
    }

    @Override
    public List<PlanInvoiceVo> invoiceList(InvoiceParam invoiceParam) {
        QueryWrapper<BizPrjPlan> queryWrapper = new QueryWrapper<>();
        //审核通过
        queryWrapper.eq("a.STATUS","1");
        //有效数据
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(invoiceParam.getContractNo())){
            queryWrapper.eq("a.CONTRACT_NO",invoiceParam.getContractNo());
        }
        queryWrapper.orderByAsc("b.CREATE_TIME");
        return bizPrjPlanMapper.getInvoiceList(queryWrapper);
    }

    @Override
    public List<BizPrjPlanVo> planList(InvoiceParam invoiceParam) {
        QueryWrapper<BizPrjPlan> queryWrapper = new QueryWrapper<>();
        //审核通过
        queryWrapper.lambda().eq(BizPrjPlan::getStatus,"1");
        //有效数据
        queryWrapper.lambda().eq(BizPrjPlan::getDeleteFlag,"NOT_DELETE");
        //合同编号
        if(ObjectUtil.isNotEmpty(invoiceParam.getContractNo())){
            queryWrapper.lambda().eq(BizPrjPlan::getContractNo,invoiceParam.getContractNo());
        }
        queryWrapper.lambda().orderByAsc(BizPrjPlan::getPlanNo);
        List<BizPrjPlan> tmp = this.list(queryWrapper);
        List<BizPrjPlanVo> result = BeanUtil.copyToList(tmp,BizPrjPlanVo.class);
        result.forEach(x->{
            x.setPlanCollectionList(bizPrjPlanCollectionService.list(new QueryWrapper<BizPrjPlanCollection>().lambda().eq(BizPrjPlanCollection::getPlanId,x.getId())));
        });
        return result;
    }
}
