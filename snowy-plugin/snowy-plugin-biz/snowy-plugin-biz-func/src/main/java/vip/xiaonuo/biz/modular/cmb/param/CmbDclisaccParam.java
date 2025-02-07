package vip.xiaonuo.biz.modular.cmb.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CmbDclisaccParam {
    @ApiModelProperty(value = "业务类别", position = 1)
    private String buscod;

    @ApiModelProperty(value = "业务模式", position = 2)
    private String busmod;
}
