package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 用户二级密码维护
 */

@Getter
@Setter
public class SysUserSetSecondPwd {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id - userId", required = true, position = 1)
    @NotBlank(message = "用户Id不能为空")
    private String userId;

    /** 旧的 - 用户二级密码 */
    @ApiModelProperty(value = "旧的 - 用户二级密码", position = 2)
    private String oldSecondPassword;

    /** 新的 - 用户二级密码 */
    @ApiModelProperty(value = "新的 - 用户二级密码", position = 3)
    private String secondPassword;
}
