package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 成本明细详情实体
 */

@Getter
@Setter
public class CostItemVo {
    /** 项目/合同名称 */
    @ApiModelProperty(value = "项目/合同名称", position = 1)
    private String prjName;

    /** 明细科目 */
    @ApiModelProperty(value = "明细科目", position = 2)
    private String item;

    /** 摘要|具体内容 */
    @ApiModelProperty(value = "摘要|具体内容", position = 3)
    private String abstractDesc;

    /** 金额（含税）|小计金额 */
    @ApiModelProperty(value = "金额（含税）|小计金额", position = 4)
    private String amount;

    /** 进项税额 */
    @ApiModelProperty(value = "进项税额", position = 5)
    private String tax;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 6)
    private String memo;
}
