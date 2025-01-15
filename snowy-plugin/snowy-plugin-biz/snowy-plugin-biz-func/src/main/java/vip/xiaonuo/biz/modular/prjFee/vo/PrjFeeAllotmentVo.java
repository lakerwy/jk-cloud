package vip.xiaonuo.biz.modular.prjFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotment;
import vip.xiaonuo.biz.modular.prjFee.entity.PrjFeeAllotmentItem;

import java.util.List;

/**
 * 报销实体Vo
 */

@Getter
@Setter
public class PrjFeeAllotmentVo extends PrjFeeAllotment {
    /** 款项拨付 - 集合 */
    @ApiModelProperty(value = "款项拨付 - 集合", position = 80)
    private List<PrjFeeAllotmentItem> feeItemList;
}
