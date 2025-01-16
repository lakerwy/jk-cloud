package vip.xiaonuo.biz.modular.prjFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 工程款拨付 - 款项拨付Id参数
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
@Getter
@Setter
public class PrjFeeAllotmentItemIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
