package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.util.DateUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExportInfo;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExportResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

/**
 * 导出工具类 excel
 */
public class ExportUtil {

    /**
     *
     * @param data 数据源
     * @param size 每页限制行数
     * @return
     */
    public static void genGzExcel(List<GzExportResult> data, int size,GzExportInfo gzInfo){
        if(CollUtil.isEmpty(data) || ObjectUtil.isEmpty(size) || size <=0) return;
        //计算分多少页
        int total = data.size();
        int pageCount = (int) Math.ceil((double) total/size);
        //分页填充模板
        for (int pageIndex = 1;pageIndex<=pageCount;pageIndex++){
            int fromIndex = (pageIndex-1) * size;
            int toIndex = pageIndex * size > total ? total : pageIndex * size;
            List<GzExportResult> gzData = data.subList(fromIndex,toIndex);
            FillTemplateExcel(gzData,gzInfo,pageIndex);
        }
    }


    public static String FillTemplateExcel(List<GzExportResult> gzData, GzExportInfo gzInfo,int pageIndex){
        try{
            String exportFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + gzInfo.getYear()+ "_"+ gzInfo.getMonth() + "_" + pageIndex+"_模板填充导出测试.xlsx";
            ExcelWriter excelWriter = EasyExcel.write(exportFileName).withTemplate(gzInfo.getTemplatePath()).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data", gzData), writeSheet);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("company", gzInfo.getCompany());
            map.put("year", gzInfo.getYear());
            map.put("month", gzInfo.getMonth());
            excelWriter.fill(map, writeSheet);
            excelWriter.finish();
            return null;
        }catch (Exception e){
            return null;
        }
    }






    public static void genGzExcel3(List<GzExportResult> data, int size, GzExportInfo gzInfo) throws IOException {
        List<ByteArrayOutputStream> outputStreams = new ArrayList<>();

        try {
            if (CollUtil.isEmpty(data) || ObjectUtil.isEmpty(size) || size <= 0) return;
            //计算分多少页
            int total = data.size();
            int pageCount = (int) Math.ceil((double) total / size);
            String exportFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + gzInfo.getYear() + "_" + gzInfo.getMonth() + "_模板填充导出测试.xlsx";

            //分页填充模板
//            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
//                int fromIndex = (pageIndex - 1) * size;
//                int toIndex = pageIndex * size > total ? total : pageIndex * size;
//                List<GzExportResult> gzData = data.subList(fromIndex, toIndex);
//                String sheetName = StrUtil.join("_",gzInfo.getCompany(),gzInfo.getYear(),gzInfo.getMonth(),pageIndex) ;
//                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
//                // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
//                excelWriter.fill(new FillWrapper("data", gzData), writeSheet);
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("company", gzInfo.getCompany());
//                map.put("year", gzInfo.getYear());
//                map.put("month", gzInfo.getMonth());
//                excelWriter.fill(map, writeSheet);
//                excelWriter.finish();
//            }

//            try(ExcelWriter excelWriter = EasyExcel.write(exportFileName).withTemplate(gzInfo.getTemplatePath()).build()){
//                //分页填充模板
//                for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
//                    int fromIndex = (pageIndex - 1) * size;
//                    int toIndex = pageIndex * size > total ? total : pageIndex * size;
//                    List<GzExportResult> gzData = data.subList(fromIndex, toIndex);
//                    String sheetName = StrUtil.join("_",gzInfo.getCompany(),gzInfo.getYear(),gzInfo.getMonth(),pageIndex) ;
//                    WriteSheet writeSheet = EasyExcel..writerSheet(sheetName).build();
//                    // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
//                    excelWriter.fill(new FillWrapper("data", gzData), writeSheet);
//                    Map<String, Object> map = new HashMap<String, Object>();
//                    map.put("company", gzInfo.getCompany());
//                    map.put("year", gzInfo.getYear());
//                    map.put("month", gzInfo.getMonth());
//                    excelWriter.fill(map, writeSheet);
//                }
//                excelWriter.finish();
//            }

            /**
            try(ExcelWriter excelWriter = EasyExcel.write(exportFileName).withTemplate(gzInfo.getTemplatePath()).build()){
                //分页填充模板
                for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
                    int fromIndex = (pageIndex - 1) * size;
                    int toIndex = pageIndex * size > total ? total : pageIndex * size;
                    List<GzExportResult> gzData = data.subList(fromIndex, toIndex);
                    String sheetName = StrUtil.join("_",gzInfo.getCompany(),gzInfo.getYear(),gzInfo.getMonth(),pageIndex) ;
                    WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
                    // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
                    excelWriter.fill(new FillWrapper("data", gzData), writeSheet);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("company", gzInfo.getCompany());
                    map.put("year", gzInfo.getYear());
                    map.put("month", gzInfo.getMonth());
                    excelWriter.fill(map, writeSheet);
                    excelWriter.finish();
                }
            }**/
        } catch (Exception e) {
            e.printStackTrace(); // 在实际情况下，你可能会想要处理异常，而不仅仅是打印堆栈跟踪信息
        }
    }




    public static void mergeExcelStreamsToFile(List<ByteArrayOutputStream> excelStreams, String outputFileName) throws IOException {
        ByteArrayOutputStream mergedOutputStream = mergeExcelFiles(excelStreams);
        writeOutputStreamToFile(mergedOutputStream, outputFileName);
    }

    public static void writeOutputStreamToFile(ByteArrayOutputStream outputStream, String outputFileName) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFileName)) {
            outputStream.writeTo(fileOutputStream);
        }
    }

    public static List<ByteArrayOutputStream> genGzExcel2(List<GzExportResult> data, int size, GzExportInfo gzInfo) throws IOException {
        List<ByteArrayOutputStream> outputStreams = new ArrayList<>();

        try {
            if (CollUtil.isEmpty(data) || ObjectUtil.isEmpty(size) || size <= 0) return outputStreams;
            //计算分多少页
            int total = data.size();
            int pageCount = (int) Math.ceil((double) total / size);

            //分页填充模板
            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
                int fromIndex = (pageIndex - 1) * size;
                int toIndex = pageIndex * size > total ? total : pageIndex * size;
                List<GzExportResult> gzData = data.subList(fromIndex, toIndex);

                ByteArrayOutputStream outputStream = fillTemplateWithData(gzData, gzInfo);
                outputStreams.add(outputStream);
            }
            String exportFileName = FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + gzInfo.getYear() + "_" + gzInfo.getMonth() + "_模板填充导出测试1.xlsx";
            mergeExcelStreamsToFile(outputStreams,exportFileName);

        } catch (Exception e) {
            e.printStackTrace(); // 在实际情况下，你可能会想要处理异常，而不仅仅是打印堆栈跟踪信息
        }

        return outputStreams;
    }

    private static ByteArrayOutputStream fillTemplateWithData(List<GzExportResult> data, GzExportInfo gzInfo) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (InputStream templateStream = new FileInputStream(gzInfo.getTemplatePath())) {
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templateStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            // 如果有多个list，模板上必须有{前缀.}，这里的前缀就是 data，然后多个list必须用 FillWrapper 包裹
            excelWriter.fill(new FillWrapper("data", data), writeSheet);

            Map<String, Object> map = new HashMap<>();
            map.put("company", gzInfo.getCompany());
            map.put("year", gzInfo.getYear());
            map.put("month", gzInfo.getMonth());
            excelWriter.fill(map, writeSheet);

            excelWriter.finish();
        }

        return outputStream;
    }


    public static ByteArrayOutputStream mergeExcelSheetsWithStyle(List<ByteArrayOutputStream> sheetStreams) throws IOException {
        // 创建一个新的Excel工作簿
        // 创建新的工作簿和工作表
        Workbook mergedWorkbook = new XSSFWorkbook();

        for (ByteArrayOutputStream sheetStream : sheetStreams) {
            // 将字节数组流转为输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(sheetStream.toByteArray());
            // 使用Apache POI读取输入流中的Excel文件
            Workbook sourceWorkbook = new XSSFWorkbook(inputStream);
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0); // 假设每个流只有一个工作表

            // 在新工作簿中创建新的工作表
            XSSFSheet targetSheet = (XSSFSheet) mergedWorkbook.createSheet(sourceSheet.getSheetName());

            // 遍历源工作表的所有行
            for (Row sourceRow : sourceSheet) {
                Row targetRow = targetSheet.createRow(sourceRow.getRowNum());

                // 复制行样式
                if (sourceRow.getRowStyle() != null) {
                    targetRow.setRowStyle(sourceRow.getRowStyle());
                }

                // 遍历每一行中的单元格
                for (Cell sourceCell : sourceRow) {
                    Cell targetCell = targetRow.createCell(sourceCell.getColumnIndex());

                    // 复制单元格样式
                    if (sourceCell.getCellStyle() != null) {
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }

                    // 根据单元格类型复制数据
                    switch (sourceCell.getCellType()) {
                        case STRING:
                            targetCell.setCellValue(sourceCell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (isCellDateFormatted(sourceCell,sourceWorkbook)) {
                                targetCell.setCellValue(sourceCell.getDateCellValue());
                            } else {
                                targetCell.setCellValue(sourceCell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            targetCell.setCellValue(sourceCell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            targetCell.setCellFormula(sourceCell.getCellFormula());
                            break;
                        case BLANK:
                            break;
                        default:
                            break;
                    }
                }
            }

            // 关闭源工作簿，释放资源
            sourceWorkbook.close();
            inputStream.close();
        }

        // 创建输出流用于保存合并后的工作簿
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 写入合并后的工作簿到输出流
        mergedWorkbook.write(outputStream);
        // 关闭合并后的工作簿，释放资源
        mergedWorkbook.close();

        return outputStream;
    }
    public static ByteArrayOutputStream mergeExcelFiles(List<ByteArrayOutputStream> streams) throws IOException {
            // 创建新的工作簿和工作表
            Workbook mergedWorkbook = new XSSFWorkbook();
            Sheet mergedSheet = mergedWorkbook.createSheet("Merged Sheet");

            int rowCount = 0;

            for (ByteArrayOutputStream stream : streams) {
                // 将每个流转换为工作簿
                try (InputStream inputStream = new ByteArrayInputStream(stream.toByteArray())) {
                    Workbook workbook = WorkbookFactory.create(inputStream);

                    // 假设只合并第一个工作表的内容
                    Sheet sheet = workbook.getSheetAt(0);

                    for (Row row : sheet) {
                        // 创建新行
                        Row newRow = mergedSheet.createRow(rowCount++);
                        copyRow(row, newRow, workbook, mergedWorkbook);
                    }

                    // 复制合并单元格
                    for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                        CellRangeAddress region = sheet.getMergedRegion(i);
                        mergedSheet.addMergedRegion(region);
                    }
                }
            }

            // 将合并的内容写入新的 ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            mergedWorkbook.write(outputStream);


        // 关闭工作簿
            mergedWorkbook.close();

        return outputStream;
    }

    private static void copyRow(Row sourceRow, Row targetRow, Workbook sourceWorkbook, Workbook targetWorkbook) {
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell sourceCell = sourceRow.getCell(i);
            Cell targetCell = targetRow.createCell(i);

            // 复制单元格样式
            if (sourceCell != null && sourceCell.getCellStyle() != null) {
                targetCell.setCellStyle(cloneStyle(targetWorkbook, sourceCell.getCellStyle()));
            }

            // 复制单元格内容
            if (sourceCell != null) {
                switch (sourceCell.getCellType()) {
                    case STRING:
                        targetCell.setCellValue(sourceCell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (isCellDateFormatted(sourceCell, sourceWorkbook)) {
                            targetCell.setCellValue(sourceCell.getDateCellValue());
                        } else {
                            targetCell.setCellValue(sourceCell.getNumericCellValue());
                        }
                        break;
                    case BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case FORMULA:
                        targetCell.setCellFormula(sourceCell.getCellFormula());
                        break;
                    case BLANK:
                        targetCell.setBlank();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private static CellStyle cloneStyle(Workbook targetWorkbook, CellStyle sourceCellStyle) {
        CellStyle newCellStyle = targetWorkbook.createCellStyle();
        copyCellStyle(newCellStyle, sourceCellStyle, targetWorkbook);
        return newCellStyle;
    }

    private static void copyCellStyle(CellStyle newCellStyle, CellStyle oldCellStyle, Workbook workbook) {
        newCellStyle.cloneStyleFrom(oldCellStyle); // 这行代码会复制大部分样式属性
        if (oldCellStyle.getFontIndex() != Font.U_NONE) {
            Font newFont = workbook.createFont();
            newCellStyle.setFont(newFont);
        }
    }


    public static boolean isCellDateFormatted(Cell cell, Workbook workbook) {
        if (cell == null) {
            return false;
        }

        CellStyle style = cell.getCellStyle();
        if (style == null) {
            return false;
        }

        DataFormat dataFormat = workbook.createDataFormat();
        String formatString = style.getDataFormatString();

        // 检查格式字符串是否为日期格式
        // 这里列出了一些常见的日期格式字符串，实际应用可能需要更全面的检查
        if (formatString != null) {
            return formatString.equals(dataFormat.getFormat("m/d/yy")) ||
                    formatString.equals(dataFormat.getFormat("yyyy-mm-dd")) ||
                    formatString.equals(dataFormat.getFormat("dd/mm/yyyy")) ||
                    // 添加更多可能的日期格式
                    // 或者使用formatID进行判断，例如：
                    style.getDataFormat() == (short)14 || // Excel内置的日期格式ID
                    style.getDataFormat() == (short)22;  // 时间格式ID
        }

        return false;
    }

}
