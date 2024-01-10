package vip.xiaonuo.flw.modular.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FlwModelParam {
    @ApiModelProperty(value = "主键id" ,required = true)
    @NotNull(message = "主键id必传")
    private String id;
}
