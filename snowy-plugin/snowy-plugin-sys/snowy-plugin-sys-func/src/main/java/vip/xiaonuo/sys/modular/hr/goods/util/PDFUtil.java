package vip.xiaonuo.sys.modular.hr.goods.util;


import cn.hutool.core.collection.CollUtil;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PDFUtil {
    /**
     * findKeywordPostions
     * @param pdfData     通过IO流 PDF文件转化的byte数组
     * @param keyword     关键字
     * @return List<float [ ]> : float[0]:pageNum float[1]:x float[2]:y
     * @throws IOException
     */
    public static List<float[]> findKeywordPostions(byte[] pdfData, String keyword) {
        List<float[]> result = new ArrayList<>();
        try {
            List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);
            for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
                List<float[]> charPositions = findPositions(keyword, pdfPageContentPosition);
                if (charPositions == null || charPositions.size() < 1) {
                    continue;
                }
                result.addAll(charPositions);
            }
            return result;
        }catch (IOException e){

        }
        return result;
    }


    private static List<PdfPageContentPositions> getPdfContentPostionsList(byte[] pdfData) throws IOException {
        PdfReader reader = new PdfReader(pdfData);
        List<PdfPageContentPositions> result = new ArrayList<>();
        int pages = reader.getNumberOfPages();
        for (int pageNum = 1; pageNum <= pages; pageNum++) {
            float width = reader.getPageSize(pageNum).getWidth();
            float height = reader.getPageSize(pageNum).getHeight();
            PdfRenderListener pdfRenderListener = new PdfRenderListener(pageNum, width, height);
            //解析pdf，定位位置
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(pdfRenderListener);
            PdfDictionary pageDic = reader.getPageN(pageNum);
            PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
            try {
                processor.processContent(ContentByteUtils.getContentBytesForPage(reader, pageNum), resourcesDic);
            } catch (IOException e) {
                reader.close();
                throw e;
            }
            String content = pdfRenderListener.getContent();
            List<CharPosition> charPositions = pdfRenderListener.getcharPositions();
            List<float[]> positionsList = new ArrayList<>();
            for (CharPosition charPosition : charPositions) {
                float[] positions = new float[]{charPosition.getPageNum(), charPosition.getX(), charPosition.getY()};
                positionsList.add(positions);
            }
            PdfPageContentPositions pdfPageContentPositions = new PdfPageContentPositions();
            pdfPageContentPositions.setContent(content);
            pdfPageContentPositions.setPostions(positionsList);
            result.add(pdfPageContentPositions);
        }
        reader.close();
        return result;
    }


    private static List<float[]> findPositions(String keyword, PdfPageContentPositions pdfPageContentPositions) {
        List<float[]> result = new ArrayList<>();
        String content = pdfPageContentPositions.getContent();
        List<float[]> charPositions = pdfPageContentPositions.getPositions();
        for (int pos = 0; pos < content.length(); ) {
            int positionIndex = content.indexOf(keyword, pos);
            if (positionIndex == -1) {
                break;
            }
            float[] postions = charPositions.get(positionIndex);
            result.add(postions);
            pos = positionIndex + 1;
        }
        return result;
    }


    private static class PdfPageContentPositions {
        private String content;
        private List<float[]> positions;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<float[]> getPositions() {
            return positions;
        }

        public void setPostions(List<float[]> positions) {
            this.positions = positions;
        }
    }



    private static class PdfRenderListener implements RenderListener {
        private int pageNum;
        private float pageWidth;
        private float pageHeight;
        private StringBuilder contentBuilder = new StringBuilder();
        private List<CharPosition> charPositions = new ArrayList<>();

        public PdfRenderListener(int pageNum, float pageWidth, float pageHeight) {
            this.pageNum = pageNum;
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
        }

        public void beginTextBlock() {
        }

        public void renderText(TextRenderInfo renderInfo) {
            List<TextRenderInfo> characterRenderInfos = renderInfo.getCharacterRenderInfos();
            for (TextRenderInfo textRenderInfo : characterRenderInfos) {
                String word = textRenderInfo.getText();
                if (word.length() > 1) {
                    word = word.substring(word.length() - 1, word.length());
                }
                Rectangle2D.Float rectangle = textRenderInfo.getAscentLine().getBoundingRectange();

                float x = (float)rectangle.getX();
                float y = (float)rectangle.getY();

                //这两个是关键字在所在页面的XY轴的百分比
                float xPercent = Math.round(x / pageWidth * 10000) / 10000f;
                float yPercent = Math.round((1 - y / pageHeight) * 10000) / 10000f;

                CharPosition charPosition = new CharPosition(pageNum, (float)x, (float)y);
                charPositions.add(charPosition);
                contentBuilder.append(word);
            }
        }

        public void endTextBlock() {
        }

        public void renderImage(ImageRenderInfo renderInfo) {
        }

        public String getContent() {
            return contentBuilder.toString();
        }

        public List<CharPosition> getcharPositions() {
            return charPositions;
        }
    }


    private static class CharPosition {
        private int pageNum = 0;
        private float x = 0;
        private float y = 0;

        public CharPosition(int pageNum, float x, float y) {
            this.pageNum = pageNum;
            this.x = x;
            this.y = y;
        }

        public int getPageNum() {
            return pageNum;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[pageNum=" + this.pageNum + ",x=" + this.x + ",y=" + this.y + "]";
        }
    }


    /**
     * 查找目标文本的位置
     * @param pageText 当前页面的文本内容
     * @param targetText 目标文本（如日期）
     * @return 返回目标文本的坐标（x, y）
     */
    private static float[] getTextPosition(String pageText, String targetText,byte[] pdfBytes) {
        //最后一个地方
        int index = pageText.lastIndexOf(targetText);
        if (index == -1) return null;
        // 计算目标文本的位置，这里假设每个字符宽度是10，这只是一个简化的示例
        List<float[]> keywordPostions = PDFUtil.findKeywordPostions(pdfBytes, targetText);
        if(CollUtil.isEmpty(keywordPostions)) return null;
        //取最后一个
        return keywordPostions.get(keywordPostions.size()-1);
    }



    /**
     * 在PDF指定文字上方盖章（浮动在文字上方）
     * @param pdfBytes pdf文件字节
     * @param imgBytes 印章
     * @param sealContent 盖章位置的文本
     * @return 返回盖章后的字节
     */
    public static byte[] addStampToText(byte[] pdfBytes, byte[] imgBytes, String sealContent) {
        com.lowagie.text.pdf.PdfReader reader = null;
        PdfStamper stamper = null;
        try (ByteArrayOutputStream modifiedPdfOutputStream = new ByteArrayOutputStream()) {
            // 读取转换后的 PDF 并添加电子印章
            reader = new com.lowagie.text.pdf.PdfReader(new ByteArrayInputStream(pdfBytes));
            // 创建 PdfStamper 对象以修改现有的 PDF
            stamper = new PdfStamper(reader, modifiedPdfOutputStream);
            int totalPages = reader.getNumberOfPages();
            // 盖章在最后一页 - 日期上
            com.lowagie.text.pdf.parser.PdfTextExtractor pdfTextExtractor = new com.lowagie.text.pdf.parser.PdfTextExtractor(reader);
            String pageText = pdfTextExtractor.getTextFromPage(totalPages);
            PdfContentByte contentByte = stamper.getOverContent(totalPages);
            // 假设目标日期是固定的，如 "2025-01-07"
            if (pageText.contains(sealContent)) {
                // 找到日期文本的坐标
                float[] position = getTextPosition(pageText,sealContent,pdfBytes);
                if (position != null) {
                    // 插入电子印章
                    insertStamp(contentByte, position, imgBytes);
                }
            }
            stamper.close();
            reader.close();
            log.info("pdf在日期上盖章完");
            return modifiedPdfOutputStream.toByteArray();
        }catch (Exception e){
            log.error("pdf在日期上盖章异常："+e.getMessage());
            return pdfBytes;
        }finally {
            if(stamper != null){
                try {
                    stamper.close();
                } catch (Exception e) {
                    log.error("pdf在日期上盖章，关闭stamper异常："+e.getMessage());
                }
            }
            if(reader != null){
                reader.close();
            }
        }
    }



    /**
     * 在指定位置插入浮动印章
     * @param contentByte 页面内容
     * @param position 目标位置（x, y）
     * @param imgBytes 印章图片
     * @throws Exception 异常
     */
    private static void insertStamp(PdfContentByte contentByte, float[] position, byte[] imgBytes) throws Exception {
        // 读取印章图片
        Image stampImage = Image.getInstance(imgBytes);
        stampImage.scaleToFit(100, 100); // 设置印章图片的大小

        // 计算印章的坐标（在目标文本的上方插入）
        float x = position[1];
        float y = position[2]; // 在目标文本上方插入

        // 设置印章位置
        stampImage.setAbsolutePosition(x-10, y-50);

        // 将印章插入PDF
        contentByte.addImage(stampImage);
    }
}
