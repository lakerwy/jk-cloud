package vip.xiaonuo.sys.modular.hr.goods.util;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import com.deepoove.poi.XWPFTemplate;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.Map;

@Slf4j
public class MyFileUtil {

    /**
     * 生成通知书文件 word填充生成pdf 【pdf版本】
     * @param templateName  word模版
     * @param map   填充参数map
     * @return
     */
    public static byte[] genPdfFile(Map<String, Object> map,String templateName){
        try (InputStream templateStream = POICacheManager.getFile(templateName);
             ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream()) {
            // 1. 读取模板并填充数据
            XWPFTemplate template = XWPFTemplate.compile(templateStream);
            template.render(map);
            // 将填充后的 Word 写入内存
            XWPFDocument doc = template.getXWPFDocument();
            // 2. 将 Word 转换为 PDF
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(doc, pdfOutputStream, options);
            byte[] pdfBytes = pdfOutputStream.toByteArray();
            log.info(">>> PDF 生成成功");
            return pdfBytes;
        } catch (Exception e) {
            log.error(">>> PDF 生成失败：", e.getMessage());
            return null;
        }
    }
}
