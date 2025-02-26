package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class LjNoBackParam {

    /** 借支人id */
    @ApiModelProperty(value = "借支人id",required = true, position = 1)
    @NotBlank(message = "借支人id不能为空")
    private String advanceId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 2)
    private String advanceName;

}
