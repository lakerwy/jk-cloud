package vip.xiaonuo.sys.modular.hr.magic.magicattendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MagicCallback {

    @ApiModelProperty(value = "回调tag", position = 1)
    private String callbackTag;

    @ApiModelProperty(value = "回调tag", position = 1)
    private MagicData data;
}
