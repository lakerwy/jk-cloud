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
package vip.xiaonuo.biz.modular.invoice.service.impl;

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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCostBill;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceCostAdd;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceCostEdit;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceCostBillService;
import vip.xiaonuo.biz.modular.invoice.vo.InvoiceCostVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceCost;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceCostMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceCostPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceCostService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务系统-成本发票申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:22
 **/
@Service
@DS("slave_1")
public class InvoiceCostServiceImpl extends ServiceImpl<InvoiceCostMapper, InvoiceCost> implements InvoiceCostService {

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private InvoiceCostBillService invoiceCostBillService;
    @Override
    public Page<InvoiceCostVo> page(InvoiceCostPageParam invoiceCostPageParam) {
        QueryWrapper<InvoiceCost> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceCost::getApplyUnit, invoiceCostPageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoiceCost::getApplyId, invoiceCostPageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceCost::getApplyName, invoiceCostPageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getKpSn())) {
            queryWrapper.lambda().like(InvoiceCost::getKpSn, invoiceCostPageParam.getKpSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceCost::getHtProjectName, invoiceCostPageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getHtContractNo())) {
            queryWrapper.lambda().like(InvoiceCost::getHtContractNo, invoiceCostPageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceCost::getSn, invoiceCostPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceCostPageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceCost::getStatus, invoiceCostPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceCostPageParam.getSortField(), invoiceCostPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceCostPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceCostPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceCostPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceCost::getId);
        }
        Page<InvoiceCost> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<InvoiceCostVo> invoiceCostVos = BeanUtil.copyToList(result.getRecords(),InvoiceCostVo.class);
        invoiceCostVos.forEach(x->{
            List<InvoiceCostBill> invoiceCostBills = invoiceCostBillService.list(new QueryWrapper<InvoiceCostBill>().lambda().eq(InvoiceCostBill::getInvoiceCostId,x.getId()));
            x.setBillList(invoiceCostBills);
        });
        Page<InvoiceCostVo> newResult = new Page<>();
        newResult.setRecords(invoiceCostVos);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceCostAdd invoiceCostAdd) {
        InvoiceCost invoiceCost = BeanUtil.toBean(invoiceCostAdd, InvoiceCost.class);
        //走审批流程
        String processInstanceId = invoiceCostAdd.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            invoiceCost.setId(id);
            invoiceCost.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            invoiceCost.setModelId(modelId);
            invoiceCost.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_INVOICE_COST.getBizTable(),id,processInstanceId,dbName);
        }else{
            invoiceCost.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(invoiceCost);
        //开票信息|成本票信息|发票确认
        if (CollUtil.isNotEmpty(invoiceCostAdd.getBillList())) {
            List<InvoiceCostBill> tmpList = BeanUtil.copyToList(invoiceCostAdd.getBillList(), InvoiceCostBill.class);
            tmpList.forEach(v -> {
                v.setInvoiceCostId(invoiceCost.getId());
            });
            invoiceCostBillService.saveBatch(tmpList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceCostEdit invoiceCostEdit) {
        String id = invoiceCostEdit.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(invoiceCostEdit.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(invoiceCostEdit.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                invoiceCostEdit.setId(id);
            }
        }
        InvoiceCost invoiceCost = this.queryEntity(invoiceCostEdit.getId());
        BeanUtil.copyProperties(invoiceCostEdit, invoiceCost);
        this.updateById(invoiceCost);

        //【开票信息】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(invoiceCostEdit.getBillList())) {
            //新增|修改|删除
            List<InvoiceCostBill> oldList = invoiceCostBillService.list(new QueryWrapper<InvoiceCostBill>().lambda().eq(InvoiceCostBill::getInvoiceCostId, invoiceCost.getId()));
            List<String> oldIds = oldList.stream().map(InvoiceCostBill::getId).collect(Collectors.toList());
            //待更新的
            List<InvoiceCostBill> updateList = invoiceCostEdit.getBillList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<InvoiceCostBill> addList = invoiceCostEdit.getBillList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(InvoiceCostBill::getId).collect(Collectors.toList());
            List<InvoiceCostBill> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                invoiceCostBillService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                invoiceCostBillService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                invoiceCostBillService.removeByIds(removeList);
            }
        }else{
            invoiceCostBillService.remove(new QueryWrapper<InvoiceCostBill>().lambda().eq(InvoiceCostBill::getInvoiceCostId, invoiceCost.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceCostIdParam> invoiceCostIdParamList) {
        // 执行删除
        List<String> delIds = CollStreamUtil.toList(invoiceCostIdParamList, InvoiceCostIdParam::getId);
        this.removeByIds(delIds);
        if(CollUtil.isNotEmpty(delIds)){
            invoiceCostBillService.remove(new QueryWrapper<InvoiceCostBill>().lambda().in(InvoiceCostBill::getInvoiceCostId,delIds));
        }
    }

    @Override
    public InvoiceCostVo detail(InvoiceCostIdParam invoiceCostIdParam) {
        InvoiceCost invoiceCost = this.queryEntity(invoiceCostIdParam.getId());
        InvoiceCostVo invoiceCostVo = BeanUtil.copyProperties(invoiceCost,InvoiceCostVo.class);
        List<InvoiceCostBill> invoiceCostBills = invoiceCostBillService.list(new QueryWrapper<InvoiceCostBill>().lambda().eq(InvoiceCostBill::getInvoiceCostId,invoiceCost.getId()));
        invoiceCostVo.setBillList(invoiceCostBills);
        return invoiceCostVo;
    }

    @Override
    public InvoiceCost queryEntity(String id) {
        InvoiceCost invoiceCost = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceCost)) {
            throw new CommonException("财务系统-成本发票申请不存在，id值为：{}", id);
        }
        return invoiceCost;
    }
}
