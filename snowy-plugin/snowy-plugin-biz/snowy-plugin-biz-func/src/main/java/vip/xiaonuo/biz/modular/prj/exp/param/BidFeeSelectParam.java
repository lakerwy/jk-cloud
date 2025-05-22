package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class BidFeeSelectParam {

    /** 投标费用单号 - SN 模糊搜索 */
    @ApiModelProperty(value = "投标费用单号 - SN 模糊搜索", position = 2)
    private String sn;

}
