package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 报销统计表单 - 查询
 */

@Getter
@Setter
public class BizExpTotalSearch {

    /** 月份 */
    @ApiModelProperty(value = "月份", position = 1)
    private String yearMonth;

    /** 类型 */
    @ApiModelProperty(value = "类型", position = 2)
    private String type;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 3)
    private String unit;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

}
