package vip.xiaonuo.biz.modular.prjFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpApply;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpCost;
import vip.xiaonuo.biz.modular.prj.expense.entity.BizExpItem;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;

import java.util.List;

/**
 * 报销实体Vo
 */

@Getter
@Setter
public class PrjFeeAllotmentItemVo extends PrjFeeAllotmentItem {
    /** 款项拨付 - 集合 */
    @ApiModelProperty(value = "款项拨付 - 集合", position = 80)
    private List<PrjFeeAllotmentItem> feeItemList;
}
