package vip.xiaonuo.bp.modular.platform.param.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 招采平台-用户注册
 */
@Getter
@Setter
public class RegisterParam {

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true, position = 2)
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 账号 - 手机号 */
    @ApiModelProperty(value = "账号 - 手机号", required = true, position = 3)
    @NotBlank(message = "手机号不能为空")
    private String account;

    /** 密码（加密后） */
    @ApiModelProperty(value = "密码（加密后）",required = true, position = 4)
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 确认密码（加密后） */
    @ApiModelProperty(value = "确认密码（加密后）", required = true,position = 5)
    @NotBlank(message = "确认密码不能为空")
    private String passwordSure;

    /** 短信验证码 */
    @ApiModelProperty(value = "短信验证码",required = true, position = 6)
    @NotBlank(message = "短信验证码不能为空")
    private String validCode;

    /** 验证码请求号 */
    @ApiModelProperty(value = "验证码请求号", required = true, position = 7)
    @NotBlank(message = "验证码请求号不能为空")
    private String validCodeReqNo;
}
