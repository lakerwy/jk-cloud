package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/***
 * 设置用户二级密码 【md5加密】
 */

@Getter
@Setter
public class SysUserSecondPassword {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id - userId", required = true, position = 1)
    @NotBlank(message = "用户Id不能为空")
    private String userId;

    /** 用户二级密码 */
    @ApiModelProperty(value = "用户二级密码", position = 2)
    private String secondPassword;

}
