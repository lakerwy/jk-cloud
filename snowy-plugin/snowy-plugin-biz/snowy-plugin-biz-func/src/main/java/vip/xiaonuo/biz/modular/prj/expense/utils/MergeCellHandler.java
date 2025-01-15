package vip.xiaonuo.biz.modular.prj.expense.utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import vip.xiaonuo.biz.modular.prj.expense.vo.MergeCell;

import java.util.List;

public class MergeCellHandler extends AbstractMergeStrategy {

    /** list 表格所有的合并列集合 */
    private List<MergeCell> mergeCellList = null;

    public MergeCellHandler(List<MergeCell> mergeCellList){
        this.mergeCellList = mergeCellList;
    }


    /**
     * 合并单元格
     * @param sheet sheet页
     * @param cell  单元格
     * @param head
     * @param relativeRowIndex
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        //设置样式
        CellStyle cellStyle = cell.getCellStyle();
        //水平居中
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //自动换行
        cellStyle.setWrapText(Boolean.TRUE);
        //在这里判断从哪一行开始调用合并方法
        if(relativeRowIndex == null || relativeRowIndex == 0){
            return;
        }
        mergeColumn(cell,this.mergeCellList);

    }

    /**
     * 合并单元格 从 firstRow 到 lastRow ，按列合并 firstCol 到 lastCol
     * @param cell 当前单元格
     * @param mergeCellList 合并设置
     */
    private void mergeColumn(Cell cell, List<MergeCell> mergeCellList){
        //当前单元格所在行和列
        int rowIndex = cell.getRowIndex();
        //获取单元格所在sheet
        Sheet sheet = cell.getSheet();
        //循环处理合并项
        mergeCellList.forEach(item->{
            //参数合法校验|是否满足合并设置|firstRow模版行（已合并）
            if(item.getLastRow() < item.getFirstRow() || item.getLastCol() < item.getFirstCol()
               || rowIndex <= item.getFirstRow() || rowIndex > item.getLastRow()){
                return;
            }
            //是否已合并
            if(isMergedRegion(sheet,rowIndex,item.getFirstCol(),item.getLastCol())){
                return;
            }
            CellRangeAddress cra = new CellRangeAddress(rowIndex,rowIndex,item.getFirstCol(),item.getLastCol());
            sheet.addMergedRegion(cra);
            //加边框
            RegionUtil.setBorderBottom(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN,cra,sheet);
            RegionUtil.setBorderTop(BorderStyle.THIN,cra,sheet);

        });
    }


    /**
     * 判断单元格是否已合并
     * @param sheet
     * @param rowIndex
     * @param startColumnIndex
     * @param endColumnIndex
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int rowIndex, int startColumnIndex, int endColumnIndex) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            if (region.getFirstRow() == rowIndex && region.getLastRow() == rowIndex
                    && region.getFirstColumn() == startColumnIndex && region.getLastColumn() == endColumnIndex) {
                return true;
            }
        }
        return false;
    }
}
