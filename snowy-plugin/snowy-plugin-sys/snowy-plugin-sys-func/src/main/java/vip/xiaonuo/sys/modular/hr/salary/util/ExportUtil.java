package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.DownloadCompressedVo;
import vip.xiaonuo.sys.modular.hr.salary.enums.ExcelEnum;
import vip.xiaonuo.sys.modular.hr.salary.enums.YesOrNoEnum;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExcelInfo;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExportInfo;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExportResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vip.xiaonuo.sys.modular.hr.salary.vo.GzExportTotalResult;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 导出工具类 excel
 */
public class ExportUtil {

    /**
     * 【模式一】
     * 针对模板 GZTemplateTwo.xlsx 对2个sheet填充数据，不分割页和统计【数据模式】
     * @param data 数据源 - 全部数据
     * @param yearMonth 工资所属年月
     * @return
     */
    public static void genGzExcelTwo(List<GzExportResult> data,String yearMonth,HttpServletResponse response){
        ClassPathResource classPathResource = new ClassPathResource("GZTemplateTwo.xlsx");
        String template = classPathResource.getAbsolutePath();
        //无数据|分页大小不合法
        if(CollUtil.isEmpty(data) || ObjectUtil.isEmpty(yearMonth) || ObjectUtil.isEmpty(template)) return;
        //结果集
        List<GzExcelInfo> gzExcelInfoList = new ArrayList<>();
        //按照公司分组
        Map<String,List<GzExportResult>> companyGroup = data.stream().collect(Collectors.groupingBy(x->x.getCompany()));
        //获取年月信息
        String year = yearMonth.substring(0,4);
        String month = yearMonth.substring(4);
        String tmpTitle = year + "年" + month + "月工资表";
        //循环生成excel
        for (Map.Entry<String, List<GzExportResult>> entry : companyGroup.entrySet()) {
            //获取工资（缓发）数据
            List<GzExportResult> gzSlowData = entry.getValue().stream().filter(x-> YesOrNoEnum.YES.getValue().equals(x.getIsSlow())).collect(Collectors.toList());
            //生成序号
            for (int i = 0; i < gzSlowData.size(); i++) {
                gzSlowData.get(i).setNo(i+1);
            }
            //工资数据（非缓发）
            List<GzExportResult> gzData = entry.getValue().stream().filter(x-> ObjectUtil.isEmpty(x.getIsSlow()) || !YesOrNoEnum.YES.getValue().equals(x.getIsSlow())).collect(Collectors.toList());
            //生成序号
            for (int i = 0; i < gzData.size(); i++) {
                gzData.get(i).setNo(i+1);
            }
            //生成title公共部分
            String title = entry.getKey() + tmpTitle;
            GzExportInfo gzInfo = new GzExportInfo(entry.getKey(),yearMonth,year,month,template,title,null);
            // file 模式
            gzExcelInfoList.add(fillTemplateTwo(gzData,gzSlowData,gzInfo));
            // stream 模式
//            gzExcelInfoList.add(fillTemplateTwoStream(gzData,gzSlowData,gzInfo));
        }
        //zip打包
        if(CollUtil.isNotEmpty(gzExcelInfoList)){
            String zipFileName = yearMonth+"_"+DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN)+ExcelEnum.ZIP.getValue();
            downloadZip(gzExcelInfoList,zipFileName,response);
        }
    }

    /**
     * 【模式一】
     *
     * 【Excel模板填充】 - 文件模式
     *
     * @param gzData 某公司某年月工资数据
     * @param gzSlowData 某公司某年月工资（缓发）数据
     * @param gzInfo 某公司某年月基础数据
     * @return 返回文件路径
     */
    public static GzExcelInfo fillTemplateTwo(List<GzExportResult> gzData, List<GzExportResult> gzSlowData, GzExportInfo gzInfo){
        //判空
        if(ObjectUtil.isEmpty(gzInfo) || (CollUtil.isEmpty(gzData) && CollUtil.isEmpty(gzSlowData))) return null;
        String fileName = gzInfo.getTitle() + ExcelEnum.XLSX.getValue();
        try{
            //定义临时文件路径
            String exportFilePath = genTmpFilePath(fileName);
            //模板填充
            ExcelWriter excelWriter = EasyExcel.write(exportFilePath).withTemplate(gzInfo.getTemplatePath()).build();
            //填充模板sheet1
            WriteSheet writeSheet1 = EasyExcel.writerSheet(0).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data", gzData), writeSheet1);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("title", gzInfo.getTitle());
            excelWriter.fill(map1, writeSheet1);
            //填充模板sheet2
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data", gzSlowData), writeSheet2);
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("title", gzInfo.getTitle()+"（缓发）");
            excelWriter.fill(map2, writeSheet2);
            excelWriter.finish();
            return new GzExcelInfo(ExcelEnum.TYPE_File.getValue(), fileName,exportFilePath);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 【模式一】
     *
     * 【Excel模板填充】 - 文件流模式
     *
     * @param gzData 某公司某年月工资数据
     * @param gzSlowData 某公司某年月工资（缓发）数据
     * @param gzInfo 某公司某年月基础数据
     * @return 返回文件路径
     */
    private static GzExcelInfo fillTemplateTwoStream(List<GzExportResult> gzData,List<GzExportResult> gzSlowData,GzExportInfo gzInfo){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //判空
        if(ObjectUtil.isEmpty(gzInfo) || (CollUtil.isEmpty(gzData) && CollUtil.isEmpty(gzSlowData))) return null;
        String fileName = gzInfo.getTitle() + ExcelEnum.XLSX.getValue();
        try (InputStream templateStream = new FileInputStream(gzInfo.getTemplatePath())) {
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templateStream).build();
            //填充模板sheet1
            WriteSheet writeSheet1 = EasyExcel.writerSheet(0).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data", gzData), writeSheet1);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("title", gzInfo.getTitle());
            excelWriter.fill(map1, writeSheet1);
            //填充模板sheet2
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1).build();
            // 如果有多个list 模板上必须有{前缀.} 这里的前缀就是 data1，然后多个list必须用 FillWrapper包裹
            excelWriter.fill(new FillWrapper("data", gzSlowData), writeSheet2);
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("title", gzInfo.getTitle()+"（缓发）");
            excelWriter.fill(map2, writeSheet2);
            //结束填充
            excelWriter.finish();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new GzExcelInfo(ExcelEnum.TYPE_STREAM.getValue(),fileName,outputStream);
    }

    /**
     * 指定路径，将ByteArrayOutputStreamToFile
     * @param outputStream ByteArrayOutputStream 流
     * @param filePath 指定文件路径
     * @return
     */
    public static File ByteArrayOutputStreamToFile(ByteArrayOutputStream outputStream,String filePath){
        // 将ByteArrayOutputStream转换为字节数组
        byte[] byteArray = outputStream.toByteArray();

        // 指定输出文件的路径
        File file = new File(filePath);

        // 将字节数组写入到文件
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteArray);
        } catch (IOException e) {
            System.err.println("Error occurred while writing bytes to file: " + e.getMessage());
        }
        return file;
    }

    /**
     * 生成临时文件路径
     * @param fileName 文件名含后缀
     * @return
     */
    public static String genTmpFilePath(String fileName){
        return FileUtil.getTmpDir() + FileUtil.FILE_SEPARATOR + fileName;
    }

    /**
     * zip打包下载
     * @param gzExcelInfos 待打包的excel信息集合
     * @param response
     */
    private static void downloadZip(List<GzExcelInfo> gzExcelInfos,String zipFileName, HttpServletResponse response){
        List<DownloadCompressedVo> downloadCompressedVoList = new ArrayList<>();
        if(CollUtil.isNotEmpty(gzExcelInfos)){
            gzExcelInfos.forEach(item->{
                if(ExcelEnum.TYPE_File.getValue().equals(item.getType())){
                    downloadCompressedVoList.add(new DownloadCompressedVo(item.getFileName(),new File(item.getFileObj().toString())));
                }else if(ExcelEnum.TYPE_STREAM.getValue().equals(item.getType())){
                    //定义临时文件路径
                    String filePath = genTmpFilePath(item.getFileName());
                    File file = ByteArrayOutputStreamToFile((ByteArrayOutputStream) item.getFileObj(),filePath);
                    downloadCompressedVoList.add(new DownloadCompressedVo(item.getFileName(),file));
                }
            });
        }
        try{
            if(CollUtil.isNotEmpty(downloadCompressedVoList)){
                CommonDownloadUtil.zipFilesAndDownload(response,zipFileName,downloadCompressedVoList);
            }
        }catch (Exception e){
            throw new CommonException("打包下载失败！请联系管理员");
        }
    }


    /**
     * 【模式二】
     * 针对模板GZTemplateOne.xlsx，动态拷贝模板，一个excel中复用模板动态生成多个sheet【打印模式】
     * @param data 数据源 - 全部数据
     * @param yearMonth 工资所属年月
     */
    public static void genGzExcelOne(List<GzExportResult> data,String yearMonth,HttpServletResponse response) {
        //无数据|分页大小不合法
        if(CollUtil.isEmpty(data) || ObjectUtil.isEmpty(yearMonth)) return;
        //结果集
        List<GzExcelInfo> gzExcelInfoList = new ArrayList<>();
        //按照公司分组
        Map<String,List<GzExportResult>> companyGroup = data.stream().collect(Collectors.groupingBy(x->x.getCompany()));
        //获取年月信息
        String year = yearMonth.substring(0,4);
        String month = yearMonth.substring(4);
        String tmpTitle = year + "年" + month + "月工资表";

        //循环生成excel
        for (Map.Entry<String, List<GzExportResult>> entry : companyGroup.entrySet()) {
            //获取工资（缓发）数据
            List<GzExportResult> gzSlowData = entry.getValue().stream().filter(x-> YesOrNoEnum.YES.getValue().equals(x.getIsSlow())).collect(Collectors.toList());
            //生成序号
            for (int i = 0; i < gzSlowData.size(); i++) {
                gzSlowData.get(i).setNo(i+1);
            }
            //工资数据（非缓发）
            List<GzExportResult> gzData = entry.getValue().stream().filter(x-> ObjectUtil.isEmpty(x.getIsSlow()) || !YesOrNoEnum.YES.getValue().equals(x.getIsSlow())).collect(Collectors.toList());
            //生成序号
            for (int i = 0; i < gzData.size(); i++) {
                gzData.get(i).setNo(i+1);
            }
            //生成title公共部分
            String title = entry.getKey() + tmpTitle;

            //循环拷贝模板，填充数据，生成临时文件
            GzExportInfo gzInfo = new GzExportInfo(entry.getKey(),yearMonth,year,month,null,title,30);
            // file 模式
            gzExcelInfoList.add(fillTemplateOne(gzData,gzSlowData,gzInfo));
        }
        //zip打包
        if(CollUtil.isNotEmpty(gzExcelInfoList)){
            String zipFileName = yearMonth+"_"+DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN)+ExcelEnum.ZIP.getValue();
            downloadZip(gzExcelInfoList,zipFileName,response);
        }
    }

    /**
     * 【模式二】
     *
     *  同一个公司下，同一个模板，复用，分页填充
     *
     * @param gzData
     * @param gzSlowData
     * @param gzInfo
     * @return
     */
    public static GzExcelInfo fillTemplateOne(List<GzExportResult> gzData, List<GzExportResult> gzSlowData, GzExportInfo gzInfo){
        if((CollUtil.isEmpty(gzData) && CollUtil.isEmpty(gzSlowData)) || ObjectUtil.isEmpty(gzInfo) ) return null;
        //文件名
        String fileName = gzInfo.getTitle() + ExcelEnum.XLSX.getValue();
        //定义临时文件路径
        String exportFilePath = genTmpFilePath(fileName);

        //计算【非缓发】分多少页
        int pageSize = gzInfo.getPageSize();
        int gzPageCount = (int) Math.ceil((double) gzData.size() / pageSize);
        //计算【缓发】分多少页
        int gzSlowPageCount =  (int) Math.ceil((double) gzSlowData.size() / pageSize);

        /* 拷贝模板 */
        //获取模板流
        ClassPathResource classPathResource = new ClassPathResource("GZTemplateOne.xlsx");
        InputStream inputStream = classPathResource.getStream();
        ExcelWriter excelWriter = null;
        //clone模板
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            //原模板只有一个sheet，通过poi复制出需要的sheet个数的模板
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            //设置模板的第一个sheet的名称
            workbook.setSheetName(0, "工资1");
            //循环分页次数，即clone sheet次数
            for (int i = 1; i < gzPageCount+gzSlowPageCount; i++) {
                workbook.cloneSheet(0, "工资"+(i+1));

                // 获取原始工作表的打印设置，并复制到克隆的工作表
                PrintSetup originalPrintSetup = workbook.getSheetAt(0).getPrintSetup();
                // 复制打印设置
                PrintSetup clonedPrintSetup = workbook.getSheetAt(i).getPrintSetup();
                clonedPrintSetup.setFitHeight((short) originalPrintSetup.getFitHeight());
                clonedPrintSetup.setFitWidth((short) originalPrintSetup.getFitWidth());
                clonedPrintSetup.setPaperSize(originalPrintSetup.getPaperSize());
                clonedPrintSetup.setScale((short) originalPrintSetup.getScale());
                clonedPrintSetup.setPageStart((short) originalPrintSetup.getPageStart());
                clonedPrintSetup.setLandscape(originalPrintSetup.getLandscape());
            }

            //写到流里
            workbook.write(bos);
            byte[] bArray = bos.toByteArray();
            InputStream is = new ByteArrayInputStream(bArray);
            excelWriter = EasyExcel.write(exportFilePath).withTemplate(is).build();

            //数据填充-循环sheet
            /* 非缓发部分 页码从1开始 */
            for(int pageIndex = 1; pageIndex <= gzPageCount; pageIndex++){
                //获取当页数据
                int fromIndex = (pageIndex - 1) * pageSize;
                int toIndex = Math.min(pageIndex * pageSize,gzData.size());
                List<GzExportResult> tmpData = gzData.subList(fromIndex, toIndex);
                //填充当前sheet sheet页面从0开始
                WriteSheet fieldSheet = EasyExcel.writerSheet(pageIndex-1).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(new FillWrapper("data", tmpData),fillConfig, fieldSheet);
                //统计行 - 最后一页合计值-全部
                List<GzExportTotalResult> totalResultList = new ArrayList<>();
                if(pageIndex == gzPageCount){
                    totalResultList.add(GzSheetTotal("","本页小计",tmpData));
                    totalResultList.add(GzSheetTotal("","合计",gzData));
                }else{
                    totalResultList.add(GzSheetTotal("","本页小计",tmpData));
                }
                excelWriter.fill(new FillWrapper("total", totalResultList),fillConfig, fieldSheet);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("title", gzInfo.getTitle());
                excelWriter.fill(map1, fieldSheet);
            }

            /* 缓发部分 */
            for(int pageIndex = 1; pageIndex <= gzSlowPageCount; pageIndex++){
                //获取当页数据
                int fromIndex = (pageIndex - 1) * pageSize;
                int toIndex = Math.min(pageIndex * pageSize,gzSlowData.size());
                List<GzExportResult> tmpData = gzSlowData.subList(fromIndex, toIndex);
                //填充当前sheet sheet页面从gzPageCount开始
                WriteSheet fieldSheet = EasyExcel.writerSheet(gzPageCount+pageIndex-1).build();
                FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
                excelWriter.fill(new FillWrapper("data", tmpData), fillConfig,fieldSheet);
                //统计行 - 最后一页合计值-全部
                List<GzExportTotalResult> totalResultList = new ArrayList<>();
                if(pageIndex == gzSlowPageCount){
                    totalResultList.add(GzSheetTotal("","本页小计",tmpData));
                    totalResultList.add(GzSheetTotal("","合计",gzSlowData));
                }else{
                    totalResultList.add(GzSheetTotal("","本页小计",tmpData));
                }
                excelWriter.fill(new FillWrapper("total", totalResultList),fillConfig, fieldSheet);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("title", gzInfo.getTitle()+"（缓发）");
                excelWriter.fill(map1, fieldSheet);
            }
            return new GzExcelInfo(ExcelEnum.TYPE_File.getValue(), fileName,exportFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            // 关闭流
            if(excelWriter!=null){
                excelWriter.close();
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 【模式二】合计行
     * @param typeName
     * @param data
     * @return
     */
    public static GzExportTotalResult GzSheetTotal(String no,String typeName,List<GzExportResult> data){
        GzExportTotalResult totalResult = new GzExportTotalResult(no,typeName);
        if(CollUtil.isNotEmpty(data)){
            data.forEach(item->{
                totalResult.setBasicAmountTotal(totalResult.getBasicAmountTotal().add(item.getBasicAmount()));
                totalResult.setPostAmountTotal(totalResult.getPostAmountTotal().add(item.getPostAmount()));
                totalResult.setMeritAmountTotal(totalResult.getRealMeritAmountTotal().add(item.getMeritAmount()));
                totalResult.setMeritRateTotal(totalResult.getMeritRateTotal()+item.getMeritRate());
                totalResult.setRealMeritAmountTotal(totalResult.getRealMeritAmountTotal().add(item.getRealMeritAmount()));
                totalResult.setRegisterAmountTotal(totalResult.getRegisterAmountTotal().add(item.getRegisterAmount()));
                totalResult.setWorkAgeAmountTotal(totalResult.getWorkAgeAmountTotal().add(item.getWorkAgeAmount()));
                totalResult.setFullAmountTotal(totalResult.getFullAmountTotal().add(item.getFullAmount()));
                totalResult.setTeacherAmountTotal(totalResult.getTeacherAmountTotal().add(item.getTeacherAmount()));
                totalResult.setDutyAmountTotal(totalResult.getDutyAmountTotal().add(item.getDutyAmount()));
                totalResult.setSickAmountTotal(totalResult.getSickAmountTotal().add(item.getSickAmount()));
                totalResult.setWorkAmountTotal(totalResult.getWorkAmountTotal().add(item.getWorkAmount()));
                totalResult.setLateAmountTotal(totalResult.getLateAmountTotal().add(item.getLateAmount()));
                totalResult.setMinerAmountTotal(totalResult.getMinerAmountTotal().add(item.getMinerAmount()));
                totalResult.setMaterAmountTotal(totalResult.getMaterAmountTotal().add(item.getMaterAmount()));
                totalResult.setInOutAmountTotal(totalResult.getInOutAmountTotal().add(item.getInOutAmount()));
                totalResult.setAddAmountTotal(totalResult.getAddAmountTotal().add(item.getAddAmount()));
                totalResult.setMinusAmountTotal(totalResult.getMinusAmountTotal().add(item.getMinusAmount()));
                totalResult.setGrossPayAmountTotal(totalResult.getGrossPayAmountTotal().add(item.getGrossPayAmount()));
                totalResult.setPerTotal(totalResult.getPerTotal().add(item.getPer()));
                totalResult.setSysPerTotal(totalResult.getSysPerTotal().add(item.getSysPer()));
                totalResult.setEmpWelfareAmountTotal(totalResult.getEmpWelfareAmountTotal().add(item.getEmpWelfareAmount()));
                totalResult.setOuterAmountTotal(totalResult.getOuterAmountTotal().add(item.getOuterAmount()));
                totalResult.setLastAddAmountTotal(totalResult.getLastAddAmountTotal().add(item.getLastAddAmount()));
                totalResult.setLastMinusAmountTotal(totalResult.getLastMinusAmountTotal().add(item.getLastMinusAmount()));
                totalResult.setMonthTaxAmountTotal(totalResult.getMonthTaxAmountTotal().add(item.getMonthTaxAmount()));
                totalResult.setRealPayAmountTotal(totalResult.getRealPayAmountTotal().add(item.getRealPayAmount()));
            });
        }
        return totalResult;
    }







    /******************************************* 暂未用 *******************************************************/
    /*
     * 合并excel sheet
     * @param data
     * @param size
     * @param gzInfo
     * @throws IOException
     */
    public static void genGzExcelMerge(List<GzExportResult> data, int size, GzExportInfo gzInfo) throws IOException {
        List<ByteArrayOutputStream> outputStreams = new ArrayList<>();
        try {
            if (CollUtil.isEmpty(data) || ObjectUtil.isEmpty(size) || size <= 0) return;
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

    private static ByteArrayOutputStream fillTemplateWithData(List<GzExportResult> data, GzExportInfo gzInfo) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream templateStream = new FileInputStream(gzInfo.getTemplatePath())) {
            ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templateStream).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            // 如果有多个list，模板上必须有{前缀.}，这里的前缀就是 data，然后多个list必须用 FillWrapper 包裹
            excelWriter.fill(new FillWrapper("data", data), writeSheet);
            Map<String, Object> map = new HashMap<>();
            map.put("title", gzInfo.getCompany());
            excelWriter.fill(map, writeSheet);
            excelWriter.finish();
        }
        return outputStream;
    }

    public static ByteArrayOutputStream mergeExcelSheetsWithStyle(List<ByteArrayOutputStream> sheetStreams) throws IOException {
        // 创建一个新的Excel工作簿
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
