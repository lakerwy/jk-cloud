package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class CashSelectParam {

    /** 报销人|借支人id */
    @ApiModelProperty(value = "报销人|借支人id",required = true, position = 1)
    @NotBlank(message = "报销人|借支人id不能为空")
    private String advanceId;

    /** 借支单 - SN 模糊搜索 */
    @ApiModelProperty(value = "借支单 - SN 模糊搜索", position = 2)
    private String sn;

}
