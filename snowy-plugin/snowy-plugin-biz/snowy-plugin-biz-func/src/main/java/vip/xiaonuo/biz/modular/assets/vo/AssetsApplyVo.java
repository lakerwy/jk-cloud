package vip.xiaonuo.biz.modular.assets.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.assets.entity.AssetsApply;
import vip.xiaonuo.biz.modular.assets.entity.AssetsBill;

import java.util.List;

@Getter
@Setter
public class AssetsApplyVo extends AssetsApply {

    /** 申购物品清单集合 */
    @ApiModelProperty(value = "申购物品清单集合", position = 100)
    private List<AssetsBill> billList;

}
