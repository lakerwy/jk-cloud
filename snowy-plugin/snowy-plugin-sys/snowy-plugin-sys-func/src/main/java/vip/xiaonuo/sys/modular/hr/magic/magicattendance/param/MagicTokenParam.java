package vip.xiaonuo.sys.modular.hr.magic.magicattendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MagicTokenParam {

    @ApiModelProperty(value = "申请ID", position = 1)
    private String appId;

    @ApiModelProperty(value = "申请KEY", position = 2)
    private String appKey;

    @ApiModelProperty(value = "token", position = 3)
    private String token;
}
