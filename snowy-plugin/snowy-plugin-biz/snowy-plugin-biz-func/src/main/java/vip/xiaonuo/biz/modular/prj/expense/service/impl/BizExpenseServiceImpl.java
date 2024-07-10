package vip.xiaonuo.biz.modular.prj.expense.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.ImageData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.prj.expense.service.BizExpenseService;
import vip.xiaonuo.biz.modular.prj.expense.utils.MergeCellHandler;
import vip.xiaonuo.biz.modular.prj.expense.vo.*;

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

            //手机截图
            WriteCellStyle writeCellStyle = new WriteCellStyle();
            writeCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            writeCellStyle.setBorderLeft(BorderStyle.THIN);
            writeCellStyle.setBorderTop(BorderStyle.THIN);
            writeCellStyle.setBorderRight(BorderStyle.THIN);
            writeCellStyle.setBorderBottom(BorderStyle.THIN);
            //设置 自动换行
            writeCellStyle.setWrapped(true);
            // 字体策略
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 11);
            contentWriteFont.setFontName("宋体");
            contentWriteFont.setBold(true);
            writeCellStyle.setWriteFont(contentWriteFont);

            /** 图1 **/
            List<ImageData> imageDataList1 = new ArrayList<>();
            ImageData imageData1 = new ImageData();
            imageData1.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\1.png"));
            imageData1.setTop(40);
            imageData1.setBottom(20);
            imageData1.setLeft(10);
            imageData1.setRight(10);
            imageDataList1.add(imageData1);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData1 = new WriteCellData<>();
            writeCellData1.setWriteCellStyle(writeCellStyle);
            writeCellData1.setImageDataList(imageDataList1);
            writeCellData1.setType(CellDataTypeEnum.STRING);
            writeCellData1.setStringValue("说明：张工和昌盛集团小王吃饭");

            /** 图2 **/
            List<ImageData> imageDataList2 = new ArrayList<>();
            ImageData imageData2 = new ImageData();
            imageData2.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\2.jpeg"));
            imageData2.setTop(40);
            imageData2.setBottom(20);
            imageData2.setLeft(10);
            imageData2.setRight(10);
            imageDataList2.add(imageData2);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData2 = new WriteCellData<>();
            writeCellData2.setWriteCellStyle(writeCellStyle);
            writeCellData2.setImageDataList(imageDataList2);
            writeCellData2.setType(CellDataTypeEnum.STRING);
            writeCellData2.setStringValue("说明：张工和昌盛集团小王吃饭张工和昌盛集团小王吃饭");

            /** list 单元格中填充文字和图片 **/
            List<ImgVo> imgList1 = new ArrayList<>();
            imgList1.add(new ImgVo(writeCellData1,writeCellData1));
            imgList1.add(new ImgVo(writeCellData2,writeCellData2));
            imgList1.add(new ImgVo(writeCellData1,writeCellData2));
            WriteSheet sheet4 = EasyExcel.writerSheet(4).build();
            excelWriter.fill(new FillWrapper("data", imgList1),fillConfig, sheet4);

            //发票
            /** 图1 **/
            List<ImageData> imageDataList11 = new ArrayList<>();
            ImageData imageData11 = new ImageData();
            imageData11.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\2.jpeg"));
            imageData11.setTop(40);
            imageData11.setBottom(20);
            imageData11.setLeft(10);
            imageData11.setRight(10);
            imageDataList11.add(imageData11);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData11 = new WriteCellData<>();
            writeCellData11.setWriteCellStyle(writeCellStyle);
            writeCellData11.setImageDataList(imageDataList11);
            writeCellData11.setType(CellDataTypeEnum.STRING);
            writeCellData11.setStringValue("说明：张工和昌盛集团小王吃饭张工和昌盛集团小王吃饭");
            /** 图2 **/
            List<ImageData> imageDataList12 = new ArrayList<>();
            ImageData imageData12 = new ImageData();
            imageData12.setImage(FileUtil.readBytes("C:\\Users\\yezi\\Desktop\\img\\3.png"));
            imageData12.setTop(40);
            imageData12.setBottom(20);
            imageData12.setLeft(10);
            imageData12.setRight(10);
            imageDataList12.add(imageData12);
            /** 单元格中填充文字和图片 **/
            WriteCellData<Void> writeCellData12 = new WriteCellData<>();
            writeCellData12.setWriteCellStyle(writeCellStyle);
            writeCellData12.setImageDataList(imageDataList12);
            writeCellData12.setType(CellDataTypeEnum.STRING);
            writeCellData12.setStringValue("说明：张工和昌盛集团小王吃饭");
            /** list 单元格中填充文字和图片 **/
            List<ImgVo> imgList2 = new ArrayList<>();
            imgList2.add(new ImgVo(writeCellData11,null));
            imgList2.add(new ImgVo(writeCellData12,null));
            imgList2.add(new ImgVo(writeCellData11,null));
            WriteSheet sheet5 = EasyExcel.writerSheet(5).build();
            excelWriter.fill(new FillWrapper("data", imgList2),fillConfig, sheet5);

            excelWriter.finish();

        }catch (Exception e){
            System.console().printf(e.getMessage());

        }

    }


}
