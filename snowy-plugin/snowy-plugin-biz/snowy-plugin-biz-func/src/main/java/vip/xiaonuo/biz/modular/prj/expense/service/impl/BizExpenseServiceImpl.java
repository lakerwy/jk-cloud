package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpenseService;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.vo.DebitExcelVo;
import vip.xiaonuo.biz.modular.prj.expense.vo.FeeExcelVo;
import vip.xiaonuo.biz.modular.prj.expense.vo.MergeCellParam;
import vip.xiaonuo.biz.modular.prj.expense.vo.TripExcelVo;

import java.util.ArrayList;
import java.util.List;


@Service
public class BizExpenseServiceImpl implements BizExpenseService {

    @Override
    public void genExcel() {  //ExcelParam excelParam, HttpServletResponse response

        ClassPathResource classPathResource = new ClassPathResource("FeeReportTemplate.xlsx");
        String templateFileName  = classPathResource.getAbsolutePath();
        String outputFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + "报销测试.xlsx";

        FeeExcelVo feeExcel = new FeeExcelVo();
        TripExcelVo tripExcel = new TripExcelVo();
        DebitExcelVo debitExcel = new DebitExcelVo();

        // 使用 EasyExcel 填充数据
        try {
            ExcelWriter excelWriter = EasyExcel.write(outputFileName).withTemplate(templateFileName).build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            //费用报销单
            List<MergeCellParam> feeMergeList = new ArrayList<>();
            //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
            int firstRow = 3;
            feeMergeList.add(new MergeCellParam(firstRow,feeExcel.getFeeItems().size()+firstRow-1,1,3));
            WriteSheet sheet1 = EasyExcel.writerSheet(1).registerWriteHandler(new MergeCellHandler(feeMergeList)).build();
            excelWriter.fill(feeExcel,sheet1);
            excelWriter.fill(new FillWrapper("data1", feeExcel.getFeeItems()),fillConfig, sheet1);
            excelWriter.fill(new FillWrapper("data2", feeExcel.getCostItems()),fillConfig, sheet1);

            //差旅费报销单
            List<MergeCellParam> tripMergeList = new ArrayList<>();
            //firstRow:模版行（因为模版行已合并）  lastRow:合并结束行
            firstRow = 13 + tripExcel.getTripItems().size()-1;
            tripMergeList.add(new MergeCellParam(firstRow,(tripExcel.getCostItems().size()+firstRow-1),0,1));
            tripMergeList.add(new MergeCellParam(firstRow,(tripExcel.getCostItems().size()+firstRow-1),2,3));
            tripMergeList.add(new MergeCellParam(firstRow,(tripExcel.getCostItems().size()+firstRow-1),4,8));
            WriteSheet sheet2 = EasyExcel.writerSheet(2).registerWriteHandler(new MergeCellHandler(tripMergeList)).build();
            excelWriter.fill(tripExcel,sheet2);
            excelWriter.fill(new FillWrapper("data1", tripExcel.getTripItems()),fillConfig, sheet2);
            excelWriter.fill(new FillWrapper("data2", tripExcel.getCostItems()),fillConfig, sheet2);

            //借支单
            WriteSheet sheet3 = EasyExcel.writerSheet(3).build();
            excelWriter.fill(debitExcel,sheet3);
            excelWriter.finish();

        }catch (Exception e){
            System.console().printf(e.getMessage());

        }

    }


}
