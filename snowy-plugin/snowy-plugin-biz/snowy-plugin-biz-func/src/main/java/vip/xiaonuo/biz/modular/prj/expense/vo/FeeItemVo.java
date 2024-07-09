package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 费用明细详情实体
 */

@Getter
@Setter
public class FeeItemVo {
    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 1)
    private String item;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 2)
    private String abstractDesc;

    /** 金额（含税） */
    @ApiModelProperty(value = "金额（含税）", position = 3)
    private String amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 3)
    private String memo;
}
