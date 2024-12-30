package vip.xiaonuo.biz.modular.bidFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 投标费用审批Id参数
 *
 * @author pzh
 * @date  2024/12/26 16:37
 **/
@Getter
@Setter
public class BidFeeIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
