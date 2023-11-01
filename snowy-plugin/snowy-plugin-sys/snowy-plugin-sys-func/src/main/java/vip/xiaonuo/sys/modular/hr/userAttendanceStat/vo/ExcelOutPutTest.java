package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExcelOutPutTest {

    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");


        // 设置列宽和行高（可选）
        sheet.setColumnWidth(0, 256 * 10); // 设置第一列的列宽为10个字符宽度
        sheet.setColumnWidth(1, 256 * 10); // 设置第二列的列宽为10个字符宽度
        for (int i = 1; i < 10; i++) {
            Row row = sheet.createRow(i); // 创建行
            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j); // 创建单元格
                cell.setCellValue("Cell " + i + "," + j); // 设置单元格的值
                CellStyle style = workbook.createCellStyle();
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(style);
            }
        }

        OutputStream outputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\File\\output.xlsx");
        try{
            workbook.write(outputStream);
        }catch (Exception e){
            System.out.println("");
        }finally {
            workbook.close();
            outputStream.close();
        }
        System.out.println("Excel文件已成功导出！");
    }
}