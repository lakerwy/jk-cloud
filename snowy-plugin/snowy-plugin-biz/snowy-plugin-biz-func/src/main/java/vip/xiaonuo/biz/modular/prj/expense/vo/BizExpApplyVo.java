package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;

import java.util.List;

/**
 * 报销实体Vo
 */

@Getter
@Setter
public class BizExpApplyVo extends BizExpApply {
    /** 费用明细 - 集合 */
    @ApiModelProperty(value = "费用明细 - 集合", position = 34)
    private List<BizExpItem> itemList;

    /** 成本一览表 - 集合 */
    @ApiModelProperty(value = "成本一览表 - 集合", position = 35)
    private List<BizExpCost> costList;
}
