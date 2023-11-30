package vip.xiaonuo.sys.modular.hr.userAttendanceStat.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.MonthPreDayStatData;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.MonthPreviewExcel;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PreDayExcelUtil {
    public static void workbookExcelDay(List<MonthPreDayStatData> monthPreDayStatDataList, HttpServletResponse response) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        String headBuff = "姓名,考勤组,部门,工号,职位,日期,班次,上班1打卡时间,上班1打卡结果,下班1打卡时间,下班1打卡结果,上班2打卡时间,上班2打卡结果,下班2打卡时间," +
                "下班2打卡结果,上班3打卡时间,上班3打卡结果,下班3打卡时间,下班3打卡结果,关联的审批单,出勤天数,休息天数,工作时长,迟到次数,迟到时长,严重迟到次数,严重迟到时长,旷工迟到天数," +
                "早退次数,早退时长,上班缺卡次数,下班缺卡次数,旷工天数,出差时长,外出时长,事假(天),病假(天),婚假(天),产假(天),丧假(天),年假(天),调休(小时),加班总时长,工作日(转调休)," +
                "休息日(转调休),节假日(转调休)";
        if(CollectionUtil.isEmpty(monthPreDayStatDataList)){
            throw new CommonException("没有数据，下载失败！");
        }
        //List<MonthStatDataStatus> monthStatDataStatusList = monthStatDataList.get(0).getMonthStatDataStatusList();

        setHerd(sheet,workbook,headBuff);//设置头
        setContent(workbook,sheet,monthPreDayStatDataList,monthPreDayStatDataList.size());//设置内容

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            workbook.write(outputStream);
            byte[] byteArr = outputStream.toByteArray();
            String fileName = "月统计"+ DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
            CommonDownloadUtil.download(fileName+".xlsx",byteArr,response);
        }catch (Exception e){
            throw new CommonException("月度汇总报表下载失败！");
        }finally {
            workbook.close();
            outputStream.close();
        }
    }

    /** 设置表头,暂时先固定 */
    private static void setHerd(Sheet sheet,Workbook workbook,String headStr){
        List<String> list = Arrays.asList(headStr.split(","));

        Row row = sheet.createRow(0); // 创建第一行：当作标题
        Short height = (short) 500;
        row.setHeight(height);

        /** 设置背景颜色 */
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        /** 设置设置边框 */
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        /** 字体剧中 */
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中

        /** 字体大小和样式*/
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        font.setFontName("仿宋_GB2312");
        font.setBold(true);//设置字体加粗
        style.setFont(font);//增加字体样式

        for(int i=0;i<list.size();i++){
            Cell cell = row.createCell(i); // 创建单元格
            cell.setCellValue(list.get(i)); // 设置单元格的值
            /** 设置宽度*/
            sheet.setColumnWidth(i, 256 * 25); // 设置第一列的列宽为30个字符宽度
            /** 应用样式*/
            cell.setCellStyle(style);
        }
    }

    private static void setContent(Workbook workbook,Sheet sheet,List<MonthPreDayStatData> monthPreDayStatDataList,Integer rowSize){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); //字体
        style.setFont(font);//增加字体样式
        for (int i = 1; i <= rowSize+1; i++) {//i=1,表头占一行
            Row row = sheet.createRow(i); // 创建行
            Short height = (short) 360;
            row.setHeight(height);
            int j=0;
            MonthPreviewExcel monthStatExcel = BeanUtil.toBean(monthPreDayStatDataList.get(i-1),MonthPreviewExcel.class);
            for (Field field : MonthPreviewExcel.class.getDeclaredFields()) {
                field.setAccessible(true); // 确保可以访问私有属性
                try {
                    Object value = field.get(monthStatExcel);
                    Cell cell = row.createCell(j); // 创建单元格
                    String cellValue = ObjectUtil.isNotEmpty(value) ? value.toString() : "";
                    cell.setCellValue(cellValue); // 设置单元格的值
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void setColorStyle(Workbook workbook,Cell cell,String status){
        if (StringUtils.isEmpty(status)){
            return;
        }
        if("正常".equals(status)){
            return;
        }
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setBold(true);//设置字体加粗
        style.setFont(font);//增加字体样式
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        if(status.contains("缺卡")){//红色
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("迟到")){
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("早退".equals(status)){
            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("请假")){
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        /*if(status.contains("补卡")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }*/
        if(status.contains("旷工")){
            style.setFillForegroundColor(IndexedColors.PINK.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("出差")){
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("外出")){
            style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

    }
}
