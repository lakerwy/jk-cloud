package vip.xiaonuo.biz.modular.report.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.crm.hetong.entity.Hetong;
import vip.xiaonuo.biz.modular.crm.hetong.service.HetongService;
import vip.xiaonuo.biz.modular.report.mapper.ReportMapper;
import vip.xiaonuo.biz.modular.report.param.HetongReportParam;
import vip.xiaonuo.biz.modular.report.result.HetongReportChildrenItem;
import vip.xiaonuo.biz.modular.report.result.HetongReportColumnsItem;
import vip.xiaonuo.biz.modular.report.result.HetongReportData;
import vip.xiaonuo.biz.modular.report.result.HetongReportResult;
import vip.xiaonuo.biz.modular.report.service.HetongReportService;

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
    private HetongService hetongService;
    @Override
    public HetongReportResult listReport(HetongReportParam hetongReportParam) {
        QueryWrapper<Hetong> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Hetong::getFlowStatus,1);
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

}
