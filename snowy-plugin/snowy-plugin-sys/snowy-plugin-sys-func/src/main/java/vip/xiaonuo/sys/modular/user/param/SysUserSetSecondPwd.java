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
    @ApiModelProperty(value = "用户Id - userId", position = 1)
    private String userId;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true, position = 1)
    @NotBlank(message = "phone不能为空")
    private String phone;

    /** 验证码 */
    @ApiModelProperty(value = "验证码", required = true, position = 2)
    @NotBlank(message = "validCode不能为空")
    private String validCode;

    /** 验证码请求号 */
    @ApiModelProperty(value = "验证码请求号", required = true, position = 3)
    @NotBlank(message = "validCodeReqNo不能为空")
    private String validCodeReqNo;

    /** 新的 - 用户二级密码 */
    @ApiModelProperty(value = "新的 - 用户二级密码", position = 4)
    private String secondPassword;
}
