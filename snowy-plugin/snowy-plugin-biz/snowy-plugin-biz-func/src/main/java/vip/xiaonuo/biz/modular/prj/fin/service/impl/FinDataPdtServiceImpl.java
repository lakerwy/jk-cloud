package vip.xiaonuo.biz.modular.prj.fin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.prj.fin.entity.*;
import vip.xiaonuo.biz.modular.prj.fin.param.*;
import vip.xiaonuo.biz.modular.prj.fin.service.*;
import vip.xiaonuo.biz.modular.prj.fin.vo.ErrorResult;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataPdtPageTotal;
import vip.xiaonuo.biz.modular.prj.fin.vo.FinDataResult;
import vip.xiaonuo.biz.modular.prj.fin.vo.ImportResult;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.enums.FlwStatusEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.prj.fin.mapper.FinDataPdtMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 财务数据汇总台账 - 生产院Service接口实现类
 *
 * @author yy
 * @date  2025/06/05 17:26
 **/
@Service
@DS("slave_1")
public class FinDataPdtServiceImpl extends ServiceImpl<FinDataPdtMapper, FinDataPdt> implements FinDataPdtService {

    @Resource
    private HetongService hetongService;
    
    @Resource
    private FinDataPdtMapper finDataPdtMapper;

    @Resource
    private FinDataInvoiceService finDataInvoiceService;

    @Resource
    private FinDataConService finDataConService;

    @Resource
    private FinDataPpcService finDataPpcService;

    @Resource
    private FinDataDbdService finDataDbdService;

    @Resource
    private FinDataManageService finDataManageService;

    @Resource
    private FinDataSignService finDataSignService;

    @Override
    public Page<FinDataPdt> page(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = getFinDataPdtQueryWrapper(finDataPdtPageParam);
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    private static QueryWrapper<FinDataPdt> getFinDataPdtQueryWrapper(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getContractId())){
            queryWrapper.lambda().eq(FinDataPdt::getContractId, finDataPdtPageParam.getContractId());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getContractNo())) {
            queryWrapper.lambda().like(FinDataPdt::getContractNo, finDataPdtPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getStartBfDate()) && ObjectUtil.isNotEmpty(finDataPdtPageParam.getEndBfDate())) {
            queryWrapper.lambda().between(FinDataPdt::getBfDate, finDataPdtPageParam.getStartBfDate(), finDataPdtPageParam.getEndBfDate());
        }
        if(ObjectUtil.isNotEmpty(finDataPdtPageParam.getSearchKey())){
            queryWrapper.lambda().and(q->q.like(FinDataPdt::getContractNo, finDataPdtPageParam.getSearchKey())
                    .or().like(FinDataPdt::getKkOtherContent, finDataPdtPageParam.getSearchKey()));
        }
        if(ObjectUtil.isAllNotEmpty(finDataPdtPageParam.getSortField(), finDataPdtPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(finDataPdtPageParam.getSortOrder());
            queryWrapper.orderBy(true, finDataPdtPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(finDataPdtPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(FinDataPdt::getCreateTime);
        }
        return queryWrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(FinDataPdtAddParam finDataPdtAddParam) {
        FinDataPdt finDataPdt = BeanUtil.toBean(finDataPdtAddParam, FinDataPdt.class);
        finDataPdt.setExtJson("页面-新增");
        this.save(finDataPdt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(FinDataPdtEditParam finDataPdtEditParam) {
        FinDataPdt finDataPdt = this.queryEntity(finDataPdtEditParam.getId());
        BeanUtil.copyProperties(finDataPdtEditParam, finDataPdt);
        this.updateById(finDataPdt);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<FinDataPdtIdParam> finDataPdtIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(finDataPdtIdParamList, FinDataPdtIdParam::getId));
    }

    @Override
    public FinDataPdt detail(FinDataPdtIdParam finDataPdtIdParam) {
        return this.queryEntity(finDataPdtIdParam.getId());
    }

    @Override
    public FinDataPdt queryEntity(String id) {
        FinDataPdt finDataPdt = this.getById(id);
        if(ObjectUtil.isEmpty(finDataPdt)) {
            throw new CommonException("财务数据汇总台账 - 生产院不存在，id值为：{}", id);
        }
        return finDataPdt;
    }

    @Override
    public ImportResult doImport(ImportParam<ImportFinDataPdt> importParam) {
        if(CollUtil.isEmpty(importParam.getImportList())){
            throw new CommonException("导入数据为空");
        }
        List<FinDataPdt> addList = new ArrayList<>();
        List<ErrorResult> errList = new ArrayList<>();
        List<Hetong> hetongList = hetongService.list(new LambdaQueryWrapper<Hetong>().eq(Hetong::getStatus, FlwStatusEnum.COMPLETED_STATUS.getValue()));
        importParam.getImportList().forEach(item->{
            String errorMsg = "";
            ErrorResult tmpErrorResult = BeanUtil.copyProperties(item, ErrorResult.class);
            FinDataPdt tmpFinData = BeanUtil.copyProperties(item,FinDataPdt.class);
            tmpFinData.setExtJson(JSONUtil.toJsonStr(item));

            //校验合同号
            if(ObjectUtil.isNotEmpty(item.getContractNo())) {
                Hetong tmpHetong = hetongList.stream().filter(x ->item.getContractNo().equals(x.getContractNo())).findFirst().orElse(null);
                if(ObjectUtil.isNotEmpty(tmpHetong)){
                    tmpFinData.setContractId(tmpHetong.getId());
                }else{
                    tmpFinData.setContractNo(null);
                    errorMsg += "合同编码不匹配；";
                }
            }

            if(ObjectUtil.isNotEmpty(errorMsg)){
                tmpErrorResult.setErrorMsg(errorMsg);
                errList.add(tmpErrorResult);
            }
            addList.add(tmpFinData);
        });
        this.saveBatch(addList);

        ImportResult result = new ImportResult();
        result.setTotal((int) importParam.getImportList().stream().count());
        result.setErrCount((int) errList.stream().count());
        result.setSucCount((int) addList.stream().count());
        result.setResultList(errList);
        return result;
    }

    @Override
    public FinDataPdtPageTotal pageTotal(FinDataPdtPageParam finDataPdtPageParam) {
        QueryWrapper<FinDataPdt> queryWrapper = getFinDataPdtQueryWrapper(finDataPdtPageParam);
        List<FinDataPdt> dataList = this.list(queryWrapper);
        FinDataPdtPageTotal result = new FinDataPdtPageTotal();
        result.setBfOutput(dataList.stream().map(x->x.getBfOutput()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfExpense(dataList.stream().map(x->x.getBfExpense()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfSub(dataList.stream().map(x->x.getBfSub()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfLabor(dataList.stream().map(x->x.getBfLabor()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setKkOther(dataList.stream().map(x->x.getKkOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setBfTotal(dataList.stream().map(x->x.getBfTotal()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setJtManage(dataList.stream().map(x->x.getJtManage()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setOutputKManage(dataList.stream().map(x->x.getOutputKManage()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setOutputKTax(dataList.stream().map(x->x.getOutputKTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setOutputKSign(dataList.stream().map(x->x.getOutputKSign()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setOutputRealPay(dataList.stream().map(x->x.getOutputRealPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setOutputNoPay(dataList.stream().map(x->x.getOutputNoPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setExpenseKTax(dataList.stream().map(x->x.getExpenseKTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setExpenseRealPay(dataList.stream().map(x->x.getExpenseRealPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setExpenseNoPay(dataList.stream().map(x->x.getExpenseNoPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setSubKTax(dataList.stream().map(x->x.getSubKTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setSubRealPay(dataList.stream().map(x->x.getSubRealPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setSubNoPay(dataList.stream().map(x->x.getSubNoPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setLaborKTax(dataList.stream().map(x->x.getLaborKTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setLaborRealPay(dataList.stream().map(x->x.getLaborRealPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        result.setLaborNoPay(dataList.stream().map(x->x.getLaborNoPay()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add));
        return result;
    }

    @Override
    public Page<FinDataResult> pageList(FinDataPageParam param) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("a.DELETE_FLAG","NOT_DELETE");
        queryWrapper.ge("a.CREATE_TIME","2024-01-01");
        queryWrapper.eq("a.STATUS",FlwStatusEnum.COMPLETED_STATUS.getValue());
        if(ObjectUtil.isNotEmpty(param.getContractId())){
            queryWrapper.eq("a.ID",param.getContractId());
        }
        if(ObjectUtil.isNotEmpty(param.getContractNo())){
            queryWrapper.like("a.CONTRACT_NO",param.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(param.getBizProjectType())){
            queryWrapper.like("b.TYPE",param.getBizProjectType());
        }
        if(ObjectUtil.isNotEmpty(param.getCompanyOrDepartment())){
            queryWrapper.like("a.COMPANY_OR_DEPARTMENT",param.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(param.getManageDept())){
            queryWrapper.like("b.MANAGE_DEPT",param.getManageDept());
        }
        if(ObjectUtil.isNotEmpty(param.getSearchKey())){
            queryWrapper.and(q->q.like("a.CONTRACT_NO",param.getSearchKey())
                    .or().like("a.PROJECT_NAME",param.getSearchKey())
                    .or().like("a.COUNTERPARTY",param.getSearchKey())
                    .or().like("a.SECONDPARTY",param.getSearchKey())
                    .or().like("a.`OWNER`",param.getSearchKey()));
        }
        queryWrapper.orderByDesc("a.CONTRACT_NO");
        Page<FinDataResult> dataList = finDataPdtMapper.getPageList(CommonPageRequest.defaultPage(), queryWrapper);
        if(CollUtil.isNotEmpty(dataList.getRecords())){
            List<String> htIds = dataList.getRecords().stream().map(x->x.getId()).collect(Collectors.toList());
            //销售发票
            List<FinDataInvoice> invoiceData = finDataInvoiceService.list(new LambdaQueryWrapper<FinDataInvoice>().in(FinDataInvoice::getContractId,htIds));
            //项目回款
            List<FinDataPpc> ppcData = finDataPpcService.list(new LambdaQueryWrapper<FinDataPpc>().in(FinDataPpc::getContractId,htIds));
            //拨付明细
            List<FinDataDbd> dbdData = finDataDbdService.list(new LambdaQueryWrapper<FinDataDbd>().in(FinDataDbd::getContractId,htIds));
            //生产院
            List<FinDataPdt> pdtData = this.list(new LambdaQueryWrapper<FinDataPdt>().in(FinDataPdt::getContractId,htIds));
            //施工
            List<FinDataCon> conData = finDataConService.list(new LambdaQueryWrapper<FinDataCon>().in(FinDataCon::getContractId,htIds));
            //转入管理费
            List<FinDataManage> manageData = finDataManageService.list(new LambdaQueryWrapper<FinDataManage>().in(FinDataManage::getContractId,htIds));
            //转入签字费
            List<FinDataSign> signData = finDataSignService.list(new LambdaQueryWrapper<FinDataSign>().in(FinDataSign::getContractId,htIds));
            for (FinDataResult record : dataList.getRecords()) {
                //销售发票
                List<FinDataInvoice> invoiceDataList = invoiceData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //项目回款
                List<FinDataPpc> ppcDataList = ppcData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //拨付明细
                List<FinDataDbd> dbdDataList = dbdData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //生产院
                List<FinDataPdt> pdtDataList = pdtData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //施工
                List<FinDataCon> conDataList = conData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //转入管理费
                List<FinDataManage> manageDataList = manageData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());
                //转入签字费
                List<FinDataSign> signDataList = signData.stream().filter(x->record.getId().equals(x.getContractId())).collect(Collectors.toList());

                //统计值
                BigDecimal invoiceTotal = invoiceDataList.stream().map(x->x.getKpAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal ppcTotal = ppcDataList.stream().map(x->x.getHkAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal dbdTotal_dbd = dbdDataList.stream().map(x->x.getBfAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal dbdTotal_pdt = pdtDataList.stream().map(x->x.getBfTotal()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal dbdTotal_con = conDataList.stream().map(x->x.getBfAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal dbdTotal = dbdTotal_dbd.add(dbdTotal_pdt).add(dbdTotal_con);

                BigDecimal kManageTotal_dbd = dbdDataList.stream().map(x->x.getKkManage()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kManageTotal_pdt = pdtDataList.stream().map(item -> Stream.of(item.getJtManage(), item.getOutputKManage()).filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal kManageTotal_con = conDataList.stream().map(x->x.getKkManage()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kManageTotal = kManageTotal_dbd.add(kManageTotal_pdt).add(kManageTotal_con);

                BigDecimal kTaxTotal_dbd = dbdDataList.stream().map(x->x.getKkTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kTaxTotal_pdt = pdtDataList.stream().map(item -> Stream.of(item.getOutputKTax(), item.getExpenseKTax(),item.getSubKTax(),item.getLaborKTax()).filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal kTaxTotal_con = conDataList.stream().map(x->x.getKkTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kTaxTotal = kTaxTotal_dbd.add(kTaxTotal_pdt).add(kTaxTotal_con);

                BigDecimal kGzTotal = conDataList.stream().map(x->x.getKkGz()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal kSignTotal_dbd = dbdDataList.stream().map(x->x.getKkSign()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kSignTotal_pdt = pdtDataList.stream().map(x->x.getOutputKSign()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kSignTotal_con = conDataList.stream().map(x->x.getKkSign()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kSignTotal = kSignTotal_dbd.add(kSignTotal_pdt).add(kSignTotal_con);

                BigDecimal kSignTaxTotal = dbdDataList.stream().map(x->x.getKkSignTax()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal kSalaryTotal = conDataList.stream().map(x->x.getKkSalary()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal kOtherTotal_dbd = dbdDataList.stream().map(x->x.getKkOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kOtherTotal_pdt = pdtDataList.stream().map(x->x.getKkOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kOtherTotal_con = conDataList.stream().map(x->x.getKkOther()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
                BigDecimal kOtherTotal = kOtherTotal_dbd.add(kOtherTotal_pdt).add(kOtherTotal_con);

                BigDecimal balanceTotal = ppcTotal.subtract(dbdTotal).subtract(kManageTotal).subtract(kTaxTotal).subtract(kGzTotal).subtract(kSignTotal).subtract(kSignTaxTotal).subtract(kSalaryTotal).subtract(kOtherTotal);

                BigDecimal ysTotal = invoiceTotal.subtract(ppcTotal);

                BigDecimal inManageTotal = manageDataList.stream().map(x->x.getZkAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                BigDecimal inSignTotal = signDataList.stream().map(x->x.getZkAmount()).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);

                record.setInvoiceTotal(invoiceTotal);
                record.setPpcTotal(ppcTotal);
                record.setDbdTotal(dbdTotal);
                record.setKManageTotal(kManageTotal);
                record.setKTaxTotal(kTaxTotal);
                record.setKGzTotal(kGzTotal);
                record.setKSignTotal(kSignTotal);
                record.setKSignTaxTotal(kSignTaxTotal);
                record.setKSalaryTotal(kSalaryTotal);
                record.setKOtherTotal(kOtherTotal);
                record.setBalanceTotal(balanceTotal);
                record.setYsTotal(ysTotal);
                record.setInManageTotal(inManageTotal);
                record.setInSignTotal(inSignTotal);
            }
        }
        return dataList;
    }
}
