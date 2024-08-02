package vip.xiaonuo.biz.modular.prj.expense.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * Excel 单元格合并参数 几种模式
 *
 * 从 firstRow 到 lastRow ，按列合并 firstCol 到 lastCol
 */

@Getter
@Setter
public class MergeCell {

    /**
     * excel中需要合并模式：
     *
     *   1：从 firstRow 到 lastRow ，按列合并 firstCol 到 lastCol
     *   2：从 firstCol 到 lastCol ，按行合并 firstRow 到 lastRow
     *   3：从 firstRow 到 lastRow ，从 firstCol 到 lastCol 整个合并
     */

    /**
     * excel中需要合并区域范围：开始行 模版行（已合并，需要特殊处理）
     */
    private int firstRow;
    /**
     *  excel中需要合并区域范围：结束行
     */
    private int lastRow;

    /**
     * excel中需要合并区域范围：开始列
     */
    private int firstCol;
    /**
     * excel中需要合并区域范围：结束列
     */
    private int lastCol;

    public MergeCell(int firstRow, int lastRow, int firstCol, int lastCol) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

}
