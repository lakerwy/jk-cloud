package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SysUserUpdatePasswordParam {

    /** id */
    @ApiModelProperty(value = "id", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 新密码 */
    @ApiModelProperty(value = "新密码", required = true, position = 3)
    @NotBlank(message = "newPassword不能为空")
    private String newPassword;

}
