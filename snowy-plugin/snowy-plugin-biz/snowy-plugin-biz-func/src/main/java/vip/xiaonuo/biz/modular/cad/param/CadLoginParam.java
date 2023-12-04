package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadLoginParam {

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 4)
    private String account;

    /** 密码 */
    @ApiModelProperty(value = "密码", position = 5)
    private String password;

}
