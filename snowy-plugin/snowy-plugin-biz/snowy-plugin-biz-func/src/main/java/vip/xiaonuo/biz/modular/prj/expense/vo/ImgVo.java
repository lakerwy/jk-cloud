package vip.xiaonuo.biz.modular.prj.expense.vo;

import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.Getter;
import lombok.Setter;

/**
 * 图片 数据
 */

@Getter
@Setter
public class ImgVo {
    /**
     * 图片设置信息
     *
     * @since 3.0.0-beta1
     */
    private WriteCellData<Void> image1;

    /**
     * 图片设置信息
     *
     * @since 3.0.0-beta1
     */
    private WriteCellData<Void> image2;

    public ImgVo(WriteCellData<Void> image1, WriteCellData<Void> image2) {
        this.image1 = image1;
        this.image2 = image2;
    }
}
