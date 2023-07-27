package vip.xiaonuo.biz.modular.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fhs.common.utils.StringUtil;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.report.mapper.ReportMapper;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.param.HtBillParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingPageParam;
import vip.xiaonuo.biz.modular.report.param.HtFilingParam;
import vip.xiaonuo.biz.modular.report.result.*;
import vip.xiaonuo.biz.modular.report.service.HetongReportService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yong.ye
 * @date 2023/7/10 9:11
 */
@DS("slave_1")
@Service
public class HetongReportServiceImpl extends ServiceImpl<ReportMapper, Hetong> implements HetongReportService {

    @Resource
    private ReportMapper reportMapper;
    @Resource
    private HetongService hetongService;
    @Override
    public HetongReportResult listReport(HetongReportParam hetongReportParam) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Hetong::getStatus,"1");
        if(ObjectUtil.isNotEmpty(hetongReportParam.getYear())) {
            queryWrapper.lambda().apply("YEAR(CREATE_TIME) = {0}", hetongReportParam.getYear());
        }
        if(ObjectUtil.isNotEmpty(hetongReportParam.getStartDate()) && ObjectUtil.isNotEmpty(hetongReportParam.getEndDate())) {
            queryWrapper.lambda().between(Hetong::getCreateTime, hetongReportParam.getStartDate(), hetongReportParam.getEndDate());
        }
        if(ObjectUtil.isNotEmpty(hetongReportParam.getType())) {
            queryWrapper.lambda().eq(Hetong::getPartnerType, hetongReportParam.getType());
        }
        List<Hetong> list = hetongService.list(queryWrapper);
        return getHetongReportResult(list);
    }

    /**
     * 构造前端多表头数据结构
     * @param hetongs
     * @return
     */
    private HetongReportResult getHetongReportResult(List<Hetong> hetongs){
        //获取所有工程类别
        List<String> secondLevels = hetongs.stream().map(Hetong::getSecondLevel).distinct().collect(Collectors.toList());
        //获取所有责任部门
        List<String> companyOrDepartments = hetongs.stream().map(Hetong::getCompanyOrDepartment).distinct().collect(Collectors.toList());
        //按照责任部门+工程类别，分组统计
        Map<List<String>,List<Hetong>> mapGroup = hetongs.stream().collect(Collectors.groupingBy(obj-> Arrays.asList(obj.getSecondLevel(), obj.getCompanyOrDepartment())));
        //接收统计数据
        List<HetongReportData> reportData = new ArrayList<>();
        //循环处理
        for (Map.Entry<List<String>,List<Hetong>> item : mapGroup.entrySet()) {
            HetongReportData hr = new HetongReportData();
            hr.setSecondLevel(item.getKey().get(0));
            hr.setCompanyOrDepartment(item.getKey().get(1));
            hr.setProjsNum(item.getValue().size());
            hr.setHetongNum((int)item.getValue().stream().filter(obj->obj.getIsFiling() != null && obj.getIsFiling().equals("是")).count());
            hr.setHetongAmount(item.getValue().stream().filter(obj->obj.getContractAmount() != null).map(Hetong::getContractAmount).reduce(BigDecimal.ZERO,BigDecimal::add));
            reportData.add(hr);
        }
        //构造返回数据结构
        HetongReportResult htResult = new HetongReportResult();
        //表头
        List<HetongReportColumnsItem> columns = new ArrayList<>();
        //表数据
        List<Map<String,String>> tableDataList = new ArrayList<>();
        //构造表头
        //1. 部门
        if(secondLevels.size()>0){
            HetongReportColumnsItem columnsItem = new HetongReportColumnsItem();
            columnsItem.setLabel("部门名称");
            columnsItem.setProp("CompanyOrDepartment");
            columns.add(columnsItem);
        }
        //2. 工程类型数 n 从1开始
        for(int i=1;i<=secondLevels.size();i++){
            //正表头
            HetongReportColumnsItem colItem = new HetongReportColumnsItem();
            colItem.setLabel(secondLevels.get(i-1));
            colItem.setProp("SecondLevel"+i);
            //副表头
            List<HetongReportChildrenItem> children = new ArrayList<>();
            HetongReportChildrenItem c1 = new HetongReportChildrenItem();
            c1.setLabel("项目数量");
            c1.setProp(colItem.getProp()+"1");
            children.add(c1);
            HetongReportChildrenItem c2 = new HetongReportChildrenItem();
            c2.setLabel("合同归档");
            c2.setProp(colItem.getProp()+"2");
            children.add(c2);
            HetongReportChildrenItem c3 = new HetongReportChildrenItem();
            c3.setLabel("合同金额");
            c3.setProp(colItem.getProp()+"3");
            children.add(c3);
            colItem.setChildren(children);
            columns.add(colItem);
        }
        htResult.setColumns(columns);
        //构造报表数据（每行数据）
        for (String dept:companyOrDepartments) {
            Map<String,String> tableData = new HashMap<>();
            tableData.put("CompanyOrDepartment",dept);
            for (int j=1;j<=secondLevels.size();j++){
                String tempProp = "SecondLevel"+j;
                int fianlJ = j-1;
                List<HetongReportData> dbDataList =reportData.stream()
                        .filter(obj->obj.getCompanyOrDepartment() != null && obj.getCompanyOrDepartment().equals(dept)
                                && obj.getSecondLevel() != null && obj.getSecondLevel().equals(secondLevels.get(fianlJ)))
                        .collect(Collectors.toList());
                if(CollUtil.isNotEmpty(dbDataList)){
                    HetongReportData dbData = dbDataList.get(0);
                    tableData.put(tempProp+"1",dbData.getProjsNum().toString());
                    tableData.put(tempProp+"2",dbData.getHetongNum().toString());
                    tableData.put(tempProp+"3",dbData.getHetongAmount().toString());
                }else {
                    tableData.put(tempProp+"1","0");
                    tableData.put(tempProp+"2","0");
                    tableData.put(tempProp+"3","0");
                }
            }
            tableDataList.add(tableData);
        }
        htResult.setTableDataList(tableDataList);
        return htResult;
    }


    /**
     *  统计日期是：先向前推3个月，再近一年 比如：当前日期 2023-7-26 先推 2023-4-26 再推一年 2022-4-26
     * @param htFilingParam
     * @return
     */
    @Override
    public List<HTYearFilingRate> filingReport(HtFilingParam htFilingParam) {
        //当前日期，向前推3个月
        DateTime preThreeMonth = new DateTime(LocalDate.now().minusMonths(3).toString());
        //推3个月，再推1年
        DateTime preYear = new DateTime(preThreeMonth.toLocalDateTime().minusYears(1).toString());

        //归档率,收入合同归档率统计（若未传入 统计开始时间、统计结束时间 ，默认统计当前日期向前推3个月，再近一年数据）
        DateTime startDate = ObjectUtil.isNotEmpty(htFilingParam.getStartDate())? new DateTime(htFilingParam.getStartDate()):preYear;
        DateTime endDate = ObjectUtil.isNotEmpty(htFilingParam.getEndDate())? new DateTime(htFilingParam.getEndDate()):preThreeMonth;
        //数据统计
        List<HTYearFilingRate> htYearFilingRateList = reportMapper.getHtYearFilingRate(startDate,endDate,htFilingParam.getPartnerType());

        //超过三个月未归档(若未传入 统计开始时间、统计结束时间，默认2017-1-1 至 当前日期向前3个月)
        DateTime threeInit = new DateTime("2017-1-1");
        DateTime threeStartDate = ObjectUtil.isNotEmpty(htFilingParam.getThreeStartDate())? new DateTime(htFilingParam.getThreeStartDate()):threeInit;
        DateTime threeEndDate = ObjectUtil.isNotEmpty(htFilingParam.getThreeEndDate())? new DateTime(htFilingParam.getThreeEndDate()):preThreeMonth;
        List<HTThreeMonthUnFiling> htThreeMonthUnFilingList = reportMapper.getHtThreeMonthUnFiling(threeStartDate,threeEndDate,htFilingParam.getPartnerType());

        //数据处理与合并
        for (HTYearFilingRate htYearFilingRate : htYearFilingRateList) {
            htYearFilingRate.setUnFilingNum(htYearFilingRate.getTotalNum()-htYearFilingRate.getFilingNum());
            htYearFilingRate.setFilingRate(htYearFilingRate.getTotalNum() >0 ? (double) htYearFilingRate.getFilingNum()/htYearFilingRate.getTotalNum() : 0);
            Optional<HTThreeMonthUnFiling> threeMonthUnFiling = htThreeMonthUnFilingList.stream().filter(x->x.getPartnerId().equals(htYearFilingRate.getPartnerId())).findFirst();
            htYearFilingRate.setThreeUnFilingNum(threeMonthUnFiling.isPresent() ? threeMonthUnFiling.get().getThreeUnFilingNum() : 0 );
        }

        //过滤数据
        if(ObjectUtil.isNotEmpty(htFilingParam.getPartnerId())){
            return htYearFilingRateList.stream().filter(x->x.getPartnerId().equals(htFilingParam.getPartnerId())).collect(Collectors.toList());
        }
        return htYearFilingRateList;
    }

    @Override
    public IPage<HTBillResult> billReport(HtBillParam htBillParam) {
        //当前日期，向前推3个月
        DateTime preThreeMonth = new DateTime(LocalDate.now().minusMonths(3).toString());
        //推3个月，再推1年
        DateTime preYear = new DateTime(preThreeMonth.toLocalDateTime().minusYears(1).toString());

        // 超过三个月未归档(若未传入 统计开始时间、统计结束时间，默认2017-1-1 至 今)
        DateTime threeInit = new DateTime("2017-1-1");
        // 统计起始时间
        DateTime startDate = null;
        DateTime endDate = StringUtil.isEmpty(htBillParam.getEndDate())?preThreeMonth:new DateTime(htBillParam.getEndDate());

        // 筛选条件
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Hetong::getDeleteFlag,"NOT_DELETE");
        queryWrapper.lambda().eq(Hetong::getStatus,"1");
        queryWrapper.lambda().isNotNull(Hetong::getCompanyOrDepartment);
        queryWrapper.lambda().ne(Hetong::getCompanyOrDepartment,"");
        if(ObjectUtil.isNotEmpty(htBillParam.getType())) {
            queryWrapper.lambda().eq(Hetong::getPartnerType, htBillParam.getType());
        }

        // 近一年收入合同清单（若未传入 统计开始时间、统计结束时间 ，默认统计近一年数据）
        if("1".equals(htBillParam.getReportType())){
            startDate = StringUtil.isEmpty(htBillParam.getStartDate())?preYear:new DateTime(htBillParam.getStartDate());
            queryWrapper.lambda().between(Hetong::getSealDate,startDate,endDate);
        }
        // 超过3个月未归档合同数统计表
        else if("2".equals(htBillParam.getReportType())){
            startDate = StringUtil.isEmpty(htBillParam.getStartDate())?threeInit:new DateTime(htBillParam.getStartDate());
            queryWrapper.lambda().between(Hetong::getSealDate,startDate,endDate);
            // 当前日期向前推3个月
            DateTime previousThreeMonth = new DateTime(LocalDate.now().minusMonths(3).toString());
            queryWrapper.lambda().lt(Hetong::getSealDate,previousThreeMonth);
            // 未归档
            queryWrapper.lambda().and(q->q.isNull(Hetong::getIsFiling).or().eq(Hetong::getIsFiling,"").or().ne(Hetong::getIsFiling,"是"));
        }

        // 关键字搜索
        if (ObjectUtil.isNotEmpty(htBillParam.getSearchKey())) {
            queryWrapper.lambda().like(Hetong::getCompanyOrDepartment, htBillParam.getSearchKey()).or()
                    .like(Hetong::getOwner, htBillParam.getSearchKey());
        }

        //排序
        if(ObjectUtil.isAllNotEmpty(htBillParam.getSortField(), htBillParam.getSortOrder())) {
            CommonSortOrderEnum.validate(htBillParam.getSortOrder());
            queryWrapper.orderBy(true, htBillParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(htBillParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Hetong::getContractNo);
        }

        return reportMapper.getBillPageList(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Override
    public IPage<HTFilingPageResult> pageHtFiling(HtFilingPageParam htFilingPageParam) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        //审核通过的合同
        queryWrapper.lambda().eq(Hetong::getDeleteFlag,"NOT_DELETE");
        queryWrapper.lambda().eq(Hetong::getStatus,"1");
        if (ObjectUtil.isNotEmpty(htFilingPageParam.getSearchKey())) {
            queryWrapper.lambda().like(Hetong::getSecondLevel, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getContractNo, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getProjectName, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getCounterparty, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getSecondparty, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getCompanyOrDepartment, htFilingPageParam.getSearchKey()).or()
                    .like(Hetong::getPartyc, htFilingPageParam.getSearchKey());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getSecondLevel())) {
            queryWrapper.lambda().eq(Hetong::getSecondLevel, htFilingPageParam.getSecondLevel());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getContractNo())) {
            queryWrapper.lambda().eq(Hetong::getContractNo, htFilingPageParam.getContractNo());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getProjectName())) {
            queryWrapper.lambda().like(Hetong::getProjectName, htFilingPageParam.getProjectName());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getCounterparty())) {
            queryWrapper.lambda().like(Hetong::getCounterparty, htFilingPageParam.getCounterparty());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getSecondparty())) {
            queryWrapper.lambda().like(Hetong::getSecondparty, htFilingPageParam.getSecondparty());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getPartnerType())) {
            queryWrapper.lambda().eq(Hetong::getPartnerType, htFilingPageParam.getPartnerType());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getCompanyOrDepartment())) {
            queryWrapper.lambda().like(Hetong::getCompanyOrDepartment, htFilingPageParam.getCompanyOrDepartment());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getStartSealDate()) && ObjectUtil.isNotEmpty(htFilingPageParam.getEndSealDate())) {
            queryWrapper.lambda().between(Hetong::getSealDate, htFilingPageParam.getStartSealDate(), htFilingPageParam.getEndSealDate());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getPartyc())) {
            queryWrapper.lambda().like(Hetong::getPartyc, htFilingPageParam.getPartyc());
        }
        if(ObjectUtil.isNotEmpty(htFilingPageParam.getPartnerId())) {
            queryWrapper.lambda().eq(Hetong::getPartnerId, htFilingPageParam.getPartnerId());
        }
        if(ObjectUtil.isAllNotEmpty(htFilingPageParam.getSortField(), htFilingPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(htFilingPageParam.getSortOrder());
            queryWrapper.orderBy(true, htFilingPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(htFilingPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByDesc(Hetong::getContractNo);
        }
        //获取分页信息
        IPage<HTFilingPageResult> resultIPage = reportMapper.getHtFilingPageList(CommonPageRequest.defaultPage(), queryWrapper);
        //获取所属责任单位的 近一年合同归档率 超过3个月未归档 统计数据
        List<HTYearFilingRate> htYearFilingRateList = filingReport(new HtFilingParam());
        for (HTFilingPageResult item:resultIPage.getRecords()) {
            Optional<HTYearFilingRate> filingRate = htYearFilingRateList.stream().filter(x->x.getPartnerId().equals(item.getPartnerId())).findFirst();
            item.setTotalNum(filingRate.isPresent()?filingRate.get().getTotalNum() : 0);
            item.setFilingNum(filingRate.isPresent()?filingRate.get().getFilingNum() : 0);
            item.setUnFilingNum(filingRate.isPresent()?filingRate.get().getUnFilingNum() : 0);
            item.setFilingRate(filingRate.isPresent()?filingRate.get().getFilingRate() : 0);
            item.setThreeUnFilingNum(filingRate.isPresent()?filingRate.get().getThreeUnFilingNum() : 0);
        }
        return resultIPage;
    }
}
