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
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity.HrUserAttendanceStat;
import vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelUtil {

    public static void workbookExcelMonth(List<MonthStatData> monthStatDataList, HttpServletResponse response) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        StringBuffer headBuff = new StringBuffer("姓名,考勤组,部门,工号,职位,出勤天数,迟到次数,迟到时长,上班缺卡数,下班缺卡数,"+
                "旷工天数,事假(天),病假(天),婚假(天),产假(天),丧假(天),年假(天),调休(小时),加班总时长");
        if(CollectionUtil.isEmpty(monthStatDataList)){
            throw new CommonException("没有数据，下载失败！");
        }
        List<MonthStatDataStatus> monthStatDataStatusList = monthStatDataList.get(0).getMonthStatDataStatusList();
        monthStatDataStatusList.forEach(month -> headBuff.append(",").append(month.getWeekTime()));

        setHerd(sheet,workbook,headBuff.toString());
        setContent(workbook,sheet,monthStatDataList);

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

    private static void setContent(Workbook workbook,Sheet sheet,List<MonthStatData> monthStatDataList){
        Integer rowSize = monthStatDataList.size();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); //字体
        style.setFont(font);//增加字体样式
        for (int i = 1; i <= rowSize; i++) {//i=1,表头占一行
            Row row = sheet.createRow(i); // 创建行
            Short height = (short) 360;
            row.setHeight(height);
            int j=0;
            MonthStatExcel monthStatExcel = BeanUtil.toBean(monthStatDataList.get(i-1),MonthStatExcel.class);
            for (Field field : MonthStatExcel.class.getDeclaredFields()) {
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
            //还需要增加日期数据列
            j = j-1;//去除错误列
            List<MonthStatDataStatus> monthStatDataStatusList = monthStatDataList.get(i-1).getMonthStatDataStatusList();
            for(MonthStatDataStatus monthStatDataStatus : monthStatDataStatusList){
                Cell cell = row.createCell(j); // 创建单元格
                String cellValue = ObjectUtil.isNotEmpty(monthStatDataStatus.getStatus()) ? monthStatDataStatus.getStatus() : "";
                cell.setCellValue(cellValue); // 设置单元格的值
                setColorStyle(workbook,cell,cellValue);
                j++;
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
        if(status.contains("缺卡")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("迟到")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("早退".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("请假")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("补卡")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("旷工")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

    }





    /**
     * 每日统计excel导出
     * */

    /**
     * 每日统计数据下载
     * */
    public static void workbookExcelDay(List<DayStatExcelParam> dayStatExcelList,HttpServletResponse response) throws Exception{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        setDayHerd(sheet,workbook,"");
        setDayContent(workbook,sheet,dayStatExcelList);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            workbook.write(outputStream);
            byte[] byteArr = outputStream.toByteArray();
            String fileName = "月统计"+DateUtil.format(new Date(),DatePattern.NORM_DATE_PATTERN);
            CommonDownloadUtil.download(fileName+".xlsx",byteArr,response);
        }catch (Exception e){
            throw new CommonException("下载excel失败,e:"+e.toString());
        }finally {
            workbook.close();
            outputStream.close();
        }
    }

    /** 设置内容 */
    private static void setDayContent(Workbook workbook,Sheet sheet,List<DayStatExcelParam> dayStatExcelList){
        Integer rowSize = dayStatExcelList.size();
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);// 设置水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 12); //字体
        style.setFont(font);//增加字体样式
        for (int i = 1; i <= rowSize; i++) {//i=1,表头占一行
            Row row = sheet.createRow(i); // 创建行
            Short height = (short) 360;
            row.setHeight(height);

            //打卡是否异常：-1 缺卡，1 正常,3 迟到，4 早退，5 请假，6 管理员修改
            DayStatExcelParam dayStatExcelParam = dayStatExcelList.get(i-1);
            DayStatExcel dayStatExcel = BeanUtil.toBean(dayStatExcelParam,DayStatExcel.class);
            isNeedStyle(dayStatExcelParam,dayStatExcel);
            int j=0;
            for (Field field : DayStatExcel.class.getDeclaredFields()) {
                field.setAccessible(true); // 确保可以访问私有属性
                try {
                    Object value = field.get(dayStatExcel);
                    Cell cell = row.createCell(j); // 创建单元格
                    String cellValue = ObjectUtil.isNotEmpty(value) ? value.toString() : "";
                    cell.setCellValue(cellValue); // 设置单元格的值
                    setColorStyle(workbook,cell,dayStatExcelParam,dayStatExcel,cellValue);
                    j++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void isNeedStyle(DayStatExcelParam dayStatExcelParam,DayStatExcel dayStatExcel){
        List<HrUserAttendanceStat> hrUserAttendanceStatList =  dayStatExcelParam.getUserAttendanceStatList();
        if(dayStatExcelParam.getAttendanceTypeDetails().equals("休息") && CollectionUtil.isEmpty(hrUserAttendanceStatList)){
            return;
        }
        /*if(dayStatExcelParam.getAttendanceTypeDetails().equals("休息")){
            return;
        }*/
        Map<String,HrUserAttendanceStat> map = new HashMap<>();
        hrUserAttendanceStatList.forEach(stat -> map.put(stat.getCheckType(),stat));
        //OnDuty：上班；OffDuty：下班；休息 REST
        //打卡是否异常：-1 缺卡，0 管理员修改,1 正常,3 迟到，4 早退，5 请假，6 外出，7 出差
        HrUserAttendanceStat onDutyStat = map.get("OnDuty");
        if(onDutyStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setOnDutyStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setOnDutyStatus(admin);
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 1){//正常
            dayStatExcel.setOnDutyStatus("正常");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 3){//迟到
            dayStatExcel.setOnDutyStatus("迟到");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(onDutyStat.getAbnormalFlag() == 4){//早退
            dayStatExcel.setOnDutyStatus("早退");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(onDutyStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setOnDutyStatus("补卡审批通过");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }

        HrUserAttendanceStat restStat = map.get("REST");
        if(restStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setRestStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setRestStatus(admin);
            dayStatExcel.setRestTime(DateUtil.formatDateTime(restStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(restStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setRestStatus("补卡审批通过");
            dayStatExcel.setRestTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }

        HrUserAttendanceStat offDutyStat = map.get("OffDuty");
        if(offDutyStat.getAbnormalFlag() == -1){//缺卡
            dayStatExcel.setOffDutyStatus("缺卡");
        }else if(onDutyStat.getAbnormalFlag() == 0){//管理员修改
            String admin = "管理员"+onDutyStat.getUpdateUser()+"改为正常";
            dayStatExcel.setOffDutyStatus(admin);
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 1){//正常
            dayStatExcel.setOffDutyStatus("正常");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 3){//迟到
            dayStatExcel.setOffDutyStatus("迟到");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(offDutyStat.getAbnormalFlag() == 4){//早退
            dayStatExcel.setOffDutyStatus("早退");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(offDutyStat.getCheckTime()));
        }else if(ObjectUtil.isNotEmpty(offDutyStat.getApplyResubmitAttendanceId())){
            dayStatExcel.setOffDutyStatus("补卡审批通过");
            dayStatExcel.setOffDutyTime(DateUtil.formatDateTime(onDutyStat.getCheckTime()));
        }
    }

    private static void setColorStyle(Workbook workbook,Cell cell,DayStatExcelParam dayStatExcelParam,DayStatExcel dayStatExcel,String status){
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
        if("缺卡".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if(status.contains("管理员")){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("迟到".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("早退".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("请假".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

        if("补卡审批通过".equals(status)){
            style.setFillForegroundColor(IndexedColors.RED.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            return;
        }

    }

    /** 设置表头,暂时先固定 */
    private static void setDayHerd(Sheet sheet,Workbook workbook,String headStr){
        headStr = "姓名,考勤组,部门,工号,职位,日期,班次,上班打卡时间,上班打卡结果,休息打卡时间,休息打卡结果,下班打卡时间,下班打卡结果,"+
                "关联的审批单,出勤天数,休息天数,工作时长,迟到次数,迟到时长,严重迟到次数,严重迟到时长,早退次数,早退时长,缺卡次数,"+
                "旷工天数,出差时长,外出时长,事假(天),病假(天),婚假(天),产假(天),丧假(天),年假(天),调休(小时),加班总时长";
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

}
