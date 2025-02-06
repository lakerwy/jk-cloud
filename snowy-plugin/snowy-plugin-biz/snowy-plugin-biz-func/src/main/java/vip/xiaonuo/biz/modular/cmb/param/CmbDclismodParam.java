package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmbDclismodParam {
    @ApiModelProperty(value = "业务类别", position = 1)
    private String buscod;
}
