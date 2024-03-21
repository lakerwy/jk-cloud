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
import cn.hutool.core.collection.CollectionUtil;
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
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.invoice.param.Customer.DoImport;
import vip.xiaonuo.biz.modular.invoice.param.Customer.ImportRefund;
import vip.xiaonuo.biz.modular.invoice.util.InvoiceUtil;
import vip.xiaonuo.biz.modular.invoice.vo.ImportResult;
import vip.xiaonuo.biz.modular.invoice.vo.InvoicePrjRefundVo;
import vip.xiaonuo.common.cache.CommonCacheOperator;
import vip.xiaonuo.common.consts.DbTypeCacheConstant;
import vip.xiaonuo.common.consts.FlwCacheConstant;
import vip.xiaonuo.common.enums.BizCommonEnum;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjRefund;
import vip.xiaonuo.biz.modular.invoice.mapper.InvoicePrjRefundMapper;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundAddParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundEditParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundIdParam;
import vip.xiaonuo.biz.modular.invoice.param.InvoicePrjRefundPageParam;
import vip.xiaonuo.biz.modular.invoice.service.InvoicePrjRefundService;
import vip.xiaonuo.dbs.core.consts.DbsConstant;
import vip.xiaonuo.flw.api.FlwCommonApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务系统-项目回款录入申请Service接口实现类
 *
 * @author yong.ye
 * @date  2024/03/19 10:24
 **/
@Service
@DS("slave_1")
public class InvoicePrjRefundServiceImpl extends ServiceImpl<InvoicePrjRefundMapper, InvoicePrjRefund> implements InvoicePrjRefundService {

    /**
     * 员工类别 范围
     */
    private static final String[] APPLY_UNIT_LIST = {"总部","分公司","零星"};

    @Resource
    private CommonCacheOperator commonCacheOperator;
    @Resource
    private HetongService hetongService;

    @Override
    public Page<InvoicePrjRefund> page(InvoicePrjRefundPageParam invoicePrjRefundPageParam) {
        QueryWrapper<InvoicePrjRefund> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().in(InvoicePrjRefund::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getApplyUnit())) {
            queryWrapper.lambda().eq(InvoicePrjRefund::getApplyUnit, invoicePrjRefundPageParam.getApplyUnit());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getApplyId())) {
            queryWrapper.lambda().eq(InvoicePrjRefund::getApplyId, invoicePrjRefundPageParam.getApplyId());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getApplyName())) {
            queryWrapper.lambda().like(InvoicePrjRefund::getApplyName, invoicePrjRefundPageParam.getApplyName());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getStartSkDate()) && ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getEndSkDate())) {
            queryWrapper.lambda().between(InvoicePrjRefund::getSkDate, invoicePrjRefundPageParam.getStartSkDate(), invoicePrjRefundPageParam.getEndSkDate());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getHtContractNo())) {
            queryWrapper.lambda().like(InvoicePrjRefund::getHtContractNo, invoicePrjRefundPageParam.getHtContractNo());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getHtProjectName())) {
            queryWrapper.lambda().like(InvoicePrjRefund::getHtProjectName, invoicePrjRefundPageParam.getHtProjectName());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getUnitName())) {
            queryWrapper.lambda().like(InvoicePrjRefund::getUnitName, invoicePrjRefundPageParam.getUnitName());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getSn())) {
            queryWrapper.lambda().like(InvoicePrjRefund::getSn, invoicePrjRefundPageParam.getSn());
        }
        if(ObjectUtil.isNotEmpty(invoicePrjRefundPageParam.getStatus())) {
            queryWrapper.lambda().eq(InvoicePrjRefund::getStatus, invoicePrjRefundPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(invoicePrjRefundPageParam.getSortField(), invoicePrjRefundPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(invoicePrjRefundPageParam.getSortOrder());
            queryWrapper.orderBy(true, invoicePrjRefundPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(invoicePrjRefundPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(InvoicePrjRefund::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(InvoicePrjRefundAddParam invoicePrjRefundAddParam) {
        InvoicePrjRefund invoicePrjRefund = BeanUtil.toBean(invoicePrjRefundAddParam, InvoicePrjRefund.class);
        //走审批流程
        String processInstanceId = invoicePrjRefundAddParam.getProcessInstanceId();
        if(ObjectUtil.isNotEmpty(processInstanceId)){
            String id = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_ID_KEY + processInstanceId));
            String modelId = String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_MODEL_ID_KEY + processInstanceId));
            invoicePrjRefund.setId(id);
            invoicePrjRefund.setSn(String.valueOf(commonCacheOperator.get(FlwCacheConstant.CACHE_FLW_SN_KEY+processInstanceId)));
            invoicePrjRefund.setModelId(modelId);
            invoicePrjRefund.setStatus(FlwStatusEnum.ACTIVE_STATUS.getValue());
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            //将流程实例和业务表关联起来
            //MASTER 数据库可能存到缓存中的名称是小写 修正关系库中存的PERSONNEL_FILE_MOVES 关联的库名称为null 的问题
            String dbName = String.valueOf(commonCacheOperator.get(DbTypeCacheConstant.CACHE_DB_KEY + DbsConstant.SLAVE_1_DATASOURCE_NAME));
            flwCommonApi.save(modelId, BizCommonEnum.BIZ_INVOICE_PRJ_REFUND.getBizTable(),id,processInstanceId,dbName);
        }else{
            invoicePrjRefund.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue());
        }
        this.save(invoicePrjRefund);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(InvoicePrjRefundEditParam invoicePrjRefundEditParam) {
        String id = invoicePrjRefundEditParam.getId();
        //判断是否走审批流程内的修改
        if(ObjectUtil.isEmpty(id) && ObjectUtil.isNotEmpty(invoicePrjRefundEditParam.getProcessInstanceId())){
            FlwCommonApi flwCommonApi = SpringUtil.getBean(FlwCommonApi.class);
            JSONObject formRelationTable = flwCommonApi.getInfoByProcessInstanceId(invoicePrjRefundEditParam.getProcessInstanceId());
            if(ObjectUtil.isNotEmpty(formRelationTable)){
                id = formRelationTable.getStr("id");
                //流程中修改 可能表单中没有id 未保证可以正常修改，需重新设置
                invoicePrjRefundEditParam.setId(id);
            }
        }
        InvoicePrjRefund invoicePrjRefund = this.queryEntity(invoicePrjRefundEditParam.getId());
        BeanUtil.copyProperties(invoicePrjRefundEditParam, invoicePrjRefund);
        this.updateById(invoicePrjRefund);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<InvoicePrjRefundIdParam> invoicePrjRefundIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(invoicePrjRefundIdParamList, InvoicePrjRefundIdParam::getId));
    }

    @Override
    public InvoicePrjRefund detail(InvoicePrjRefundIdParam invoicePrjRefundIdParam) {
        return this.queryEntity(invoicePrjRefundIdParam.getId());
    }

    @Override
    public InvoicePrjRefund queryEntity(String id) {
        InvoicePrjRefund invoicePrjRefund = this.getById(id);
        if(ObjectUtil.isEmpty(invoicePrjRefund)) {
            throw new CommonException("财务系统-项目回款录入申请不存在，id值为：{}", id);
        }
        return invoicePrjRefund;
    }

    @Override
    public ImportResult importRefund(ImportRefund importRefund) {
        List<InvoicePrjRefundVo> importData = BeanUtil.copyToList(importRefund.getImportData(), InvoicePrjRefundVo.class);
        checkData(importData);
        ImportResult result = new ImportResult();
        result.setTotal(importData.size());
        List<InvoicePrjRefundVo> errList = importData.stream().filter(x->ObjectUtil.isNotEmpty(x.getErrorMsg())).collect(Collectors.toList());
        result.setErrCount(errList.size());
        result.setResultList(importData);
        result.setSucCount(result.getTotal() - result.getErrCount());
        return result;
    }

    @Override
    public Boolean doImport(DoImport input) {
        List<InvoicePrjRefundVo> importData = input.getImportData().stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).collect(Collectors.toList());
        if(CollUtil.isEmpty(importData)){
            throw new CommonException("无有效导入数据");
        }
        List<InvoicePrjRefund> invoicePrjRefunds = BeanUtil.copyToList(importData, InvoicePrjRefund.class);
        invoicePrjRefunds.forEach(x->x.setStatus(FlwStatusEnum.COMPLETED_STATUS.getValue()));
        this.saveBatch(invoicePrjRefunds);
        return true;
    }


    /**
     * 通用校验（必填|数据格式校验|匹配合同）
     * @param importData
     * @return
     */
    public void checkData(List<InvoicePrjRefundVo> importData){
        //校验格式与必填
        importData.forEach(item->{
            String errMsg = "";
            if(ObjectUtil.isEmpty(item.getApplyUnit())){
                errMsg += "申请人单位必填；";
            }
            if(ObjectUtil.isEmpty(item.getHtContractNo())){
                errMsg += "销售合同编号必填；";
            }
            if(ObjectUtil.isNotEmpty(item.getApplyUnit()) && !CollectionUtil.newArrayList(APPLY_UNIT_LIST).contains(item.getApplyUnit())){
                errMsg += "申请人单位必须为：" + StrUtil.join("、",APPLY_UNIT_LIST) +"；";
            }
            if(ObjectUtil.isNotEmpty(item.getSkDate()) && !InvoiceUtil.isValidDate(item.getSkDate())){
                errMsg += "收款日期格式不合法；";
            }
            item.setErrorMsg(errMsg);
            if(ObjectUtil.isNotEmpty(item.getHtContractNo())){
                item.setHtContractNo(item.getHtContractNo().trim());
            }
        });
        //校验合同，并匹配合同数据
        List<String> htContractNos = importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).map(x->x.getHtContractNo()).collect(Collectors.toList());
        if(CollUtil.isNotEmpty(htContractNos)){
            QueryWrapper<Hetong> hetongQueryWrapper = new QueryWrapper<>();
            hetongQueryWrapper.lambda().select(Hetong::getId,Hetong::getProjectName,Hetong::getContractAmount,Hetong::getCounterparty,Hetong::getContractNo);
            hetongQueryWrapper.lambda().in(Hetong::getContractNo,htContractNos);
            hetongQueryWrapper.lambda().in(Hetong::getStatus,FlwStatusEnum.COMPLETED_STATUS.getValue(),FlwStatusEnum.ACTIVE_STATUS.getValue());
            List<Hetong> hetongList = hetongService.list(hetongQueryWrapper);
            importData.stream().filter(x->ObjectUtil.isEmpty(x.getErrorMsg())).forEach(item->{
                List<Hetong> tmpHtList = hetongList.stream().filter(obj->item.getHtContractNo().equals(obj.getContractNo())).collect(Collectors.toList());
                if(tmpHtList.size() == 0){
                    item.setErrorMsg("合同号不存在，请检查");
                }else if(tmpHtList.size() == 1){
                    Hetong tmpHt = tmpHtList.get(0);
                    item.setHtId(tmpHt.getId());
                    item.setHtProjectName(tmpHt.getProjectName());
                    item.setHtContractAmount(tmpHt.getContractAmount());
                    item.setHtCounterparty(tmpHt.getCounterparty());
                }else{
                    item.setErrorMsg("合同号未唯一匹配，请检查");
                }
            });
        }
    }
}
