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
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleCost;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;
import vip.xiaonuo.biz.modular.invoice.enums.SaleBillStatusEnum;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceCostBillMapper;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleBillMapper;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleCostMapper;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceSaleAdd;
import vip.xiaonuo.biz.modular.invoice.param.Customer.InvoiceSaleEdit;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleBillService;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleCostService;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleKpService;
import vip.xiaonuo.biz.modular.invoice.vo.*;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSale;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoiceSaleMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSaleIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoiceSalePageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoiceSaleService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务系统-销售发票开具申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:17
 **/
@Service
@DS("slave_1")
public class InvoiceSaleServiceImpl extends ServiceImpl<InvoiceSaleMapper, InvoiceSale> implements InvoiceSaleService {

    @Resource
    private InvoiceSaleKpService invoiceSaleKpService;
    @Resource
    private InvoiceSaleCostService invoiceSaleCostService;
    @Resource
    private InvoiceSaleBillService invoiceSaleBillService;
    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private InvoiceSaleBillMapper invoiceSaleBillMapper;
    @Resource
    private InvoiceSaleCostMapper invoiceSaleCostMapper;
    @Resource
    private InvoiceCostBillMapper invoiceCostBillMapper;

    @Override
    public Page<InvoiceSaleVo> page(InvoiceSalePageParam invoiceSalePageParam) {
        QueryWrapper<InvoiceSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InvoiceSale::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyUnit, invoiceSalePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyId, invoiceSalePageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceSale::getApplyName, invoiceSalePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillId())) {
            queryWrapper.lambda().eq(InvoiceSale::getBillId, invoiceSalePageParam.getBillId());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillUnit())) {
            queryWrapper.lambda().like(InvoiceSale::getBillUnit, invoiceSalePageParam.getBillUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceSale::getHtProjectName, invoiceSalePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtContractNo())) {
            queryWrapper.lambda().eq(InvoiceSale::getHtContractNo, invoiceSalePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getPurchaser())) {
            queryWrapper.lambda().like(InvoiceSale::getPurchaser, invoiceSalePageParam.getPurchaser());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerType())) {
            queryWrapper.lambda().eq(InvoiceSale::getTaxpayerType, invoiceSalePageParam.getTaxpayerType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpType())) {
            queryWrapper.lambda().eq(InvoiceSale::getFpType, invoiceSalePageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerNo())) {
            queryWrapper.lambda().like(InvoiceSale::getTaxpayerNo, invoiceSalePageParam.getTaxpayerNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBankInfo())) {
            queryWrapper.lambda().like(InvoiceSale::getBankInfo, invoiceSalePageParam.getBankInfo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getGatherType())) {
            queryWrapper.lambda().eq(InvoiceSale::getGatherType, invoiceSalePageParam.getGatherType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceSale::getSn, invoiceSalePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceSale::getStatus, invoiceSalePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSalePageParam.getSortField(), invoiceSalePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSalePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSalePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSalePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoiceSale::getId);
        }
        Page<InvoiceSale> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);

        List<InvoiceSaleVo> invoiceSaleVos = BeanUtil.copyToList(result.getRecords(), InvoiceSaleVo.class);
        invoiceSaleVos.forEach(x->{
            List<InvoiceSaleKp> invoiceSaleKps = invoiceSaleKpService.list(new QueryWrapper<InvoiceSaleKp>().lambda().eq(InvoiceSaleKp::getInvoiceSaleId,x.getId()));
            x.setKpList(invoiceSaleKps);
            List<InvoiceSaleCost> invoiceSaleCosts = invoiceSaleCostService.list(new QueryWrapper<InvoiceSaleCost>().lambda().eq(InvoiceSaleCost::getInvoiceSaleId,x.getId()));
            x.setCostList(invoiceSaleCosts);
            List<InvoiceSaleBill> invoiceSaleBills = invoiceSaleBillService.list(new QueryWrapper<InvoiceSaleBill>().lambda().eq(InvoiceSaleBill::getInvoiceSaleId,x.getId()));
            x.setBillList(invoiceSaleBills);
        });
        Page<InvoiceSaleVo> newResult = new Page<>();
        newResult.setRecords(invoiceSaleVos);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoiceSaleAdd invoiceSaleAdd) {
        InvoiceSale invoiceSale = BeanUtil.toBean(invoiceSaleAdd, InvoiceSale.class);
        //走审批流程
        String processInstanceId = invoiceSaleAdd.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            invoiceSale.setId(id);
            invoiceSale.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            invoiceSale.setModelId(modelId);
            invoiceSale.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_INVOICE_SALE.getBizTable(),id,processInstanceId,dbName);
        }else{
            invoiceSale.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(invoiceSale);
        //开票信息|成本票信息|发票确认
        if (CollUtil.isNotEmpty(invoiceSaleAdd.getKpList())) {
            List<InvoiceSaleKp> tmpList = BeanUtil.copyToList(invoiceSaleAdd.getKpList(), InvoiceSaleKp.class);
            tmpList.forEach(v -> {
                v.setInvoiceSaleId(invoiceSale.getId());
            });
            invoiceSaleKpService.saveBatch(tmpList);
        }
        if (CollUtil.isNotEmpty(invoiceSaleAdd.getCostList())) {
            List<InvoiceSaleCost> tmpList = BeanUtil.copyToList(invoiceSaleAdd.getCostList(), InvoiceSaleCost.class);
            tmpList.forEach(v -> {
                v.setInvoiceSaleId(invoiceSale.getId());
            });
            invoiceSaleCostService.saveBatch(tmpList);
        }
        if (CollUtil.isNotEmpty(invoiceSaleAdd.getBillList())) {
            List<InvoiceSaleBill> tmpList = BeanUtil.copyToList(invoiceSaleAdd.getBillList(), InvoiceSaleBill.class);
            tmpList.forEach(v -> {
                v.setInvoiceSaleId(invoiceSale.getId());
                v.setFpStatus(SaleBillStatusEnum.NORMAL.getValue());
            });
            invoiceSaleBillService.saveBatch(tmpList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoiceSaleEdit invoiceSaleEdit) {
        String id = invoiceSaleEdit.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(invoiceSaleEdit.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(invoiceSaleEdit.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                invoiceSaleEdit.setId(id);
            }
        }
        InvoiceSale invoiceSale = this.queryEntity(invoiceSaleEdit.getId());
        BeanUtil.copyProperties(invoiceSaleEdit, invoiceSale);
        this.updateById(invoiceSale);

        //【开票信息】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(invoiceSaleEdit.getKpList())) {
            invoiceSaleEdit.getKpList().forEach(x->x.setInvoiceSaleId(invoiceSale.getId()));
            //新增|修改|删除
            List<InvoiceSaleKp> oldList = invoiceSaleKpService.list(new QueryWrapper<InvoiceSaleKp>().lambda().eq(InvoiceSaleKp::getInvoiceSaleId, invoiceSale.getId()));
            List<String> oldIds = oldList.stream().map(InvoiceSaleKp::getId).collect(Collectors.toList());
            //待更新的
            List<InvoiceSaleKp> updateList = invoiceSaleEdit.getKpList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<InvoiceSaleKp> addList = invoiceSaleEdit.getKpList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(InvoiceSaleKp::getId).collect(Collectors.toList());
            List<InvoiceSaleKp> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                invoiceSaleKpService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                invoiceSaleKpService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                invoiceSaleKpService.removeByIds(removeList);
            }
        }else{
            invoiceSaleKpService.remove(new QueryWrapper<InvoiceSaleKp>().lambda().eq(InvoiceSaleKp::getInvoiceSaleId, invoiceSale.getId()));
        }
        //【成本发票信息】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(invoiceSaleEdit.getCostList())) {
            invoiceSaleEdit.getCostList().forEach(x->x.setInvoiceSaleId(invoiceSale.getId()));
            //新增|修改|删除
            List<InvoiceSaleCost> oldList = invoiceSaleCostService.list(new QueryWrapper<InvoiceSaleCost>().lambda().eq(InvoiceSaleCost::getInvoiceSaleId, invoiceSale.getId()));
            List<String> oldIds = oldList.stream().map(InvoiceSaleCost::getId).collect(Collectors.toList());
            //待更新的
            List<InvoiceSaleCost> updateList = invoiceSaleEdit.getCostList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<InvoiceSaleCost> addList = invoiceSaleEdit.getCostList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(InvoiceSaleCost::getId).collect(Collectors.toList());
            List<InvoiceSaleCost> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                invoiceSaleCostService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                invoiceSaleCostService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                invoiceSaleCostService.removeByIds(removeList);
            }
        }else{
            invoiceSaleCostService.remove(new QueryWrapper<InvoiceSaleCost>().lambda().eq(InvoiceSaleCost::getInvoiceSaleId, invoiceSale.getId()));
        }
        //【发票确认信息】删除历史数据【全部删光的情况】，新增（避免流程重新发起）
        if (CollUtil.isNotEmpty(invoiceSaleEdit.getBillList())) {
            invoiceSaleEdit.getBillList().forEach(x->{
                x.setInvoiceSaleId(invoiceSale.getId());
                x.setFpStatus(SaleBillStatusEnum.NORMAL.getValue());
            });
            //新增|修改|删除
            List<InvoiceSaleBill> oldList = invoiceSaleBillService.list(new QueryWrapper<InvoiceSaleBill>().lambda().eq(InvoiceSaleBill::getInvoiceSaleId, invoiceSale.getId()));
            List<String> oldIds = oldList.stream().map(InvoiceSaleBill::getId).collect(Collectors.toList());
            //待更新的
            List<InvoiceSaleBill> updateList = invoiceSaleEdit.getBillList().stream().filter(x -> oldIds.contains(x.getId())).collect(Collectors.toList());
            //待新增的
            List<InvoiceSaleBill> addList = invoiceSaleEdit.getBillList().stream().filter(x -> !oldIds.contains(x.getId())).collect(Collectors.toList());
            //待删除的
            List<String> updateIds = updateList.stream().map(InvoiceSaleBill::getId).collect(Collectors.toList());
            List<InvoiceSaleBill> removeList = oldList.stream().filter(x -> !updateIds.contains(x.getId())).collect(Collectors.toList());
            //更新数据库
            if (CollUtil.isNotEmpty(addList)) {
                invoiceSaleBillService.saveBatch(addList);
            }
            if (CollUtil.isNotEmpty(updateList)) {
                invoiceSaleBillService.updateBatchById(updateList);
            }
            if (CollUtil.isNotEmpty(removeList)) {
                invoiceSaleBillService.removeByIds(removeList);
            }
        }else{
            invoiceSaleBillService.remove(new QueryWrapper<InvoiceSaleBill>().lambda().eq(InvoiceSaleBill::getInvoiceSaleId, invoiceSale.getId()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoiceSaleIdParam> invoiceSaleIdParamList) {
        List<String> delIds = CollStreamUtil.toList(invoiceSaleIdParamList, InvoiceSaleIdParam::getId);
        // 执行删除
        this.removeByIds(delIds);
        // 删除关联数据
        if(CollUtil.isNotEmpty(delIds)) {
            //【开票信息】删除历史数据【全部删光的情况】
            QueryWrapper<InvoiceSaleKp> kpQueryWrapper = new QueryWrapper<>();
            kpQueryWrapper.lambda().in(InvoiceSaleKp::getInvoiceSaleId, delIds);
            invoiceSaleKpService.remove(kpQueryWrapper);
            //【成本发票信息】删除历史数据【全部删光的情况】
            QueryWrapper<InvoiceSaleCost> costQueryWrapper = new QueryWrapper<>();
            costQueryWrapper.lambda().in(InvoiceSaleCost::getInvoiceSaleId, delIds);
            invoiceSaleCostService.remove(costQueryWrapper);
            //【发票确认信息】删除历史数据【全部删光的情况】
            QueryWrapper<InvoiceSaleBill> billQueryWrapper = new QueryWrapper<>();
            billQueryWrapper.lambda().in(InvoiceSaleBill::getInvoiceSaleId, delIds);
            invoiceSaleBillService.remove(billQueryWrapper);
        }
    }

    @Override
    public InvoiceSaleVo detail(InvoiceSaleIdParam invoiceSaleIdParam) {
        InvoiceSale invoiceSale = this.queryEntity(invoiceSaleIdParam.getId());
        InvoiceSaleVo invoiceSaleVo = BeanUtil.copyProperties(invoiceSale,InvoiceSaleVo.class);
        List<InvoiceSaleKp> invoiceSaleKps = invoiceSaleKpService.list(new QueryWrapper<InvoiceSaleKp>().lambda().eq(InvoiceSaleKp::getInvoiceSaleId,invoiceSale.getId()));
        invoiceSaleVo.setKpList(invoiceSaleKps);
        List<InvoiceSaleCost> invoiceSaleCosts = invoiceSaleCostService.list(new QueryWrapper<InvoiceSaleCost>().lambda().eq(InvoiceSaleCost::getInvoiceSaleId,invoiceSale.getId()));
        invoiceSaleVo.setCostList(invoiceSaleCosts);
        List<InvoiceSaleBill> invoiceSaleBills = invoiceSaleBillService.list(new QueryWrapper<InvoiceSaleBill>().lambda().eq(InvoiceSaleBill::getInvoiceSaleId,invoiceSale.getId()));
        invoiceSaleVo.setBillList(invoiceSaleBills);
        return invoiceSaleVo;
    }

    @Override
    public InvoiceSale queryEntity(String id) {
        InvoiceSale invoiceSale = this.getById(id);
        if(ObjectUtil.isEmpty(invoiceSale)) {
            throw new CommonException("财务系统-销售发票开具申请不存在，id值为：{}", id);
        }
        return invoiceSale;
    }

    @Override
    public Page<InvoiceSaleInfo> billPage(InvoiceSalePageParam invoiceSalePageParam) {
        QueryWrapper<InvoiceSaleBill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.eq("b.DELETE_FLAG","NOT_DELETE");
        queryWrapper.in("b.STATUS", FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpNo())){
            queryWrapper.like("a.FP_NO",invoiceSalePageParam.getFpNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpStatus())){
            queryWrapper.eq("a.FP_STATUS",invoiceSalePageParam.getFpStatus());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyUnit())) {
            queryWrapper.eq("b.APPLY_UNIT", invoiceSalePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyName())) {
            queryWrapper.like("b.APPLY_NAME", invoiceSalePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillUnit())) {
            queryWrapper.like("b.BILL_UNIT", invoiceSalePageParam.getBillUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtProjectName())) {
            queryWrapper.like("b.HT_PROJECT_NAME", invoiceSalePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtContractNo())) {
            queryWrapper.eq("b.HT_CONTRACT_NO", invoiceSalePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerType())) {
            queryWrapper.eq("b.TAXPAYER_TYPE", invoiceSalePageParam.getTaxpayerType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpType())) {
            queryWrapper.eq("b.FP_TYPE", invoiceSalePageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerNo())) {
            queryWrapper.like("b.TAXPAYER_NO", invoiceSalePageParam.getTaxpayerNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getGatherType())) {
            queryWrapper.eq("b.GATHER_TYPE", invoiceSalePageParam.getGatherType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getSn())) {
            queryWrapper.like("b.SN", invoiceSalePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getStatus())) {
            queryWrapper.eq("b.STATUS", invoiceSalePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSalePageParam.getSortField(), invoiceSalePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSalePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSalePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSalePageParam.getSortField()));
        } else {
            queryWrapper.orderByDesc("b.SN").orderByDesc("a.FP_NO");
        }
        return invoiceSaleBillMapper.getSalePageList(CommonPageRequest.defaultPage(),queryWrapper);
    }

    @Override
    public InvoiceLjInfoVo getTopLjInfo(String htContractNo) {
        if(ObjectUtil.isEmpty(htContractNo)){
            throw new CommonException("请传入销售合同编号");
        }
        QueryWrapper<InvoiceSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(InvoiceSale::getHtContractNo,htContractNo);
        queryWrapper.lambda().in(InvoiceSale::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue());
        queryWrapper.lambda().orderByDesc(InvoiceSale::getCreateTime);
        queryWrapper.lambda().last(" limit 1 ");
        InvoiceSale invoiceSale = this.getOne(queryWrapper);
        return BeanUtil.copyProperties(invoiceSale, InvoiceLjInfoVo.class);
    }

    @Override
    public Page<InvoiceInfo> getTotal(InvoiceSalePageParam invoiceSalePageParam) {
        QueryWrapper<InvoiceSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InvoiceSale::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyUnit, invoiceSalePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceSale::getApplyName, invoiceSalePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillUnit())) {
            queryWrapper.lambda().like(InvoiceSale::getBillUnit, invoiceSalePageParam.getBillUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceSale::getHtProjectName, invoiceSalePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtContractNo())) {
            queryWrapper.lambda().like(InvoiceSale::getHtContractNo, invoiceSalePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getPurchaser())) {
            queryWrapper.lambda().like(InvoiceSale::getPurchaser, invoiceSalePageParam.getPurchaser());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerType())) {
            queryWrapper.lambda().eq(InvoiceSale::getTaxpayerType, invoiceSalePageParam.getTaxpayerType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpType())) {
            queryWrapper.lambda().eq(InvoiceSale::getFpType, invoiceSalePageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerNo())) {
            queryWrapper.lambda().like(InvoiceSale::getTaxpayerNo, invoiceSalePageParam.getTaxpayerNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBankInfo())) {
            queryWrapper.lambda().like(InvoiceSale::getBankInfo, invoiceSalePageParam.getBankInfo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getGatherType())) {
            queryWrapper.lambda().eq(InvoiceSale::getGatherType, invoiceSalePageParam.getGatherType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceSale::getSn, invoiceSalePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceSale::getStatus, invoiceSalePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSalePageParam.getSortField(), invoiceSalePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSalePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSalePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSalePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(InvoiceSale::getSn);
        }
        Page<InvoiceSale> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        //转换数据，拼接参数
        List<InvoiceInfo> invoiceInfos = BeanUtil.copyToList(result.getRecords(), InvoiceInfo.class);
        if(CollUtil.isNotEmpty(invoiceInfos)){
            //1. 获取涉及销售发票申请单号,id
            List<String> invoiceSaleIdList = invoiceInfos.stream().map(x->x.getId()).collect(Collectors.toList());
            List<String> kpSnList = invoiceInfos.stream().filter(x->ObjectUtil.isNotEmpty(x.getSn())).map(x->x.getSn()).collect(Collectors.toList());
            //2. 获取销售发票 - 开票确认 - 统计值（按invoiceSaleIdList统计）
            List<InvoiceSaleBillTotal> invoiceSaleBillTotals = invoiceSaleBillMapper.getBillTotal(invoiceSaleIdList);
            //3. 获取销售发票 - 成本票应收 - 统计值（按invoiceSaleIdList统计）
            List<InvoiceSaleBillTotal> invoiceSaleCostTotals = invoiceSaleCostMapper.getCostTotal(invoiceSaleIdList);
            //4. 获取成本发票 - 成本确认 - 统计值（按kpSnList统计）
            List<InvoiceCostBillTotal> invoiceCostBillTotals = invoiceCostBillMapper.getBillTotal(kpSnList);
            //5. 获取成本发票 - 供应商维度 - 统计值（按kpSnList统计）
            List<InvoiceCostPrvBillTotal> invoiceCostPrvBillTotals = invoiceCostBillMapper.getPrvBillTotal(kpSnList);
            invoiceInfos.forEach(item->{
                if(ObjectUtil.isEmpty(item.getSn())){
                    item.setSn("");
                }
                InvoiceSaleBillTotal saleBillTotal = invoiceSaleBillTotals.stream().filter(x->item.getId().equals(x.getInvoiceSaleId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(saleBillTotal)){
                    item.setSaleBillTotal(saleBillTotal.getAmountTotal());
                    item.setSaleBillTaxTotal(saleBillTotal.getTaxAmountTotal());
                }
                InvoiceSaleBillTotal saleCostTotal = invoiceSaleCostTotals.stream().filter(x->item.getId().equals(x.getInvoiceSaleId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(saleCostTotal)){
                    item.setSaleCostTotal(saleCostTotal.getAmountTotal());
                }
                InvoiceCostBillTotal costBillTotal = invoiceCostBillTotals.stream().filter(x->item.getSn().equals(x.getKpSn())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(costBillTotal)){
                    item.setCostBillTotal(costBillTotal.getAmountTotal());
                }
                List<InvoiceCostPrvBillTotal> costPrvBillList = invoiceCostPrvBillTotals.stream().filter(x->item.getSn().equals(x.getKpSn())).collect(Collectors.toList());
                item.setPrvCostList(costPrvBillList);
            });
        }
        Page<InvoiceInfo> newResult = new Page<>();
        newResult.setRecords(invoiceInfos);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }

    @Override
    public Page<InvoiceInfoNew> getTotalNew(InvoiceSalePageParam invoiceSalePageParam) {
        QueryWrapper<InvoiceSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InvoiceSale::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoiceSale::getApplyUnit, invoiceSalePageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoiceSale::getApplyName, invoiceSalePageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBillUnit())) {
            queryWrapper.lambda().like(InvoiceSale::getBillUnit, invoiceSalePageParam.getBillUnit());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoiceSale::getHtProjectName, invoiceSalePageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getHtContractNo())) {
            queryWrapper.lambda().like(InvoiceSale::getHtContractNo, invoiceSalePageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getPurchaser())) {
            queryWrapper.lambda().like(InvoiceSale::getPurchaser, invoiceSalePageParam.getPurchaser());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerType())) {
            queryWrapper.lambda().eq(InvoiceSale::getTaxpayerType, invoiceSalePageParam.getTaxpayerType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getFpType())) {
            queryWrapper.lambda().eq(InvoiceSale::getFpType, invoiceSalePageParam.getFpType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getTaxpayerNo())) {
            queryWrapper.lambda().like(InvoiceSale::getTaxpayerNo, invoiceSalePageParam.getTaxpayerNo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getBankInfo())) {
            queryWrapper.lambda().like(InvoiceSale::getBankInfo, invoiceSalePageParam.getBankInfo());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getGatherType())) {
            queryWrapper.lambda().eq(InvoiceSale::getGatherType, invoiceSalePageParam.getGatherType());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getSn())) {
            queryWrapper.lambda().like(InvoiceSale::getSn, invoiceSalePageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoiceSalePageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoiceSale::getStatus, invoiceSalePageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoiceSalePageParam.getSortField(), invoiceSalePageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoiceSalePageParam.getSortOrder());
            queryWrapper.orderBy(true, invoiceSalePageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoiceSalePageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(InvoiceSale::getSn);
        }
        Page<InvoiceSale> result = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        //转换数据，拼接参数
        List<InvoiceInfoNew> invoiceInfos = BeanUtil.copyToList(result.getRecords(), InvoiceInfoNew.class);
        //处理后的结果集
        List<InvoiceInfoNew> totalRecords = new ArrayList<>();
        if(CollUtil.isNotEmpty(invoiceInfos)){
            //1. 获取涉及销售发票申请单号,id
            List<String> invoiceSaleIdList = invoiceInfos.stream().map(x->x.getId()).collect(Collectors.toList());
            List<String> kpSnList = invoiceInfos.stream().filter(x->ObjectUtil.isNotEmpty(x.getSn())).map(x->x.getSn()).collect(Collectors.toList());
            //2. 获取销售发票 - 开票确认 - 统计值（按invoiceSaleIdList统计）
            List<InvoiceSaleBillTotal> invoiceSaleBillTotals = invoiceSaleBillMapper.getBillTotal(invoiceSaleIdList);
            //3. 获取销售发票 - 成本票应收 - 统计值（按invoiceSaleIdList统计）
            List<InvoiceSaleBillTotal> invoiceSaleCostTotals = invoiceSaleCostMapper.getCostTotal(invoiceSaleIdList);
            //4. 获取成本发票 - 成本确认 - 统计值（按kpSnList统计）
            List<InvoiceCostBillTotal> invoiceCostBillTotals = invoiceCostBillMapper.getBillTotal(kpSnList);
            //5. 获取成本发票 - 供应商维度 - 统计值（按kpSnList统计）
            List<InvoiceCostPrvBillTotal> invoiceCostPrvBillTotals = invoiceCostBillMapper.getPrvBillTotal(kpSnList);
            invoiceInfos.forEach(item->{
                if(ObjectUtil.isEmpty(item.getSn())){
                    item.setSn("");
                }
                InvoiceSaleBillTotal saleBillTotal = invoiceSaleBillTotals.stream().filter(x->item.getId().equals(x.getInvoiceSaleId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(saleBillTotal)){
                    item.setSaleBillTotal(saleBillTotal.getAmountTotal());
                    item.setSaleBillTaxTotal(saleBillTotal.getTaxAmountTotal());
                }
                InvoiceSaleBillTotal saleCostTotal = invoiceSaleCostTotals.stream().filter(x->item.getId().equals(x.getInvoiceSaleId())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(saleCostTotal)){
                    item.setSaleCostTotal(saleCostTotal.getAmountTotal());
                }
                InvoiceCostBillTotal costBillTotal = invoiceCostBillTotals.stream().filter(x->item.getSn().equals(x.getKpSn())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(costBillTotal)){
                    item.setCostBillTotal(costBillTotal.getAmountTotal());
                }
                //供应商+税率统计 - 数据
                List<InvoiceCostPrvBillTotal> costPrvBillList = invoiceCostPrvBillTotals.stream().filter(x->item.getSn().equals(x.getKpSn())).collect(Collectors.toList());
                if(CollUtil.isNotEmpty(costPrvBillList)) {
                    for (int i = 0; i < costPrvBillList.size(); i++) {
                        InvoiceCostPrvBillTotal tmpPrv = costPrvBillList.get(i);
                        if (i == 0){
                            item.setPrvName(tmpPrv.getPrvName());
                            item.setTaxRate(tmpPrv.getTaxRate());
                            item.setAmountTotal(tmpPrv.getAmountTotal());
                            item.setTaxAmountTotal(tmpPrv.getTaxAmountTotal());
                            totalRecords.add(item);
                        }else{
                            InvoiceInfoNew tmpItem = new InvoiceInfoNew();
                            tmpItem.setId(item.getId());
                            tmpItem.setPrvName(tmpPrv.getPrvName());
                            tmpItem.setTaxRate(tmpPrv.getTaxRate());
                            tmpItem.setAmountTotal(tmpPrv.getAmountTotal());
                            tmpItem.setTaxAmountTotal(tmpPrv.getTaxAmountTotal());
                            totalRecords.add(tmpItem);
                        }
                    }
                }
            });
        }
        Page<InvoiceInfoNew> newResult = new Page<>();
        newResult.setRecords(totalRecords);
        newResult.setPages(result.getPages());
        newResult.setSize(result.getSize());
        newResult.setTotal(result.getTotal());
        newResult.setCurrent(result.getCurrent());
        return newResult;
    }
}
