package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 电子名片微信小程序，绑定系统用户参数
 *
 * @author yong.ye
 * @date 2023/6/15 9:48
 */
@Getter
@Setter
public class WeChatBindEcardParam {
    /** 用户账号 */
    @ApiModelProperty(value = "用户账号account", required = true, position = 1)
    @NotBlank(message = "用户账号account不能为空")
    private String account;

    /** 用户密码 */
    @ApiModelProperty(value = "用户密码pwd", required = true, position = 2)
    @NotBlank(message = "用户密码pwd不能为空")
    private String pwd;

    /** 电子名片小程序openid */
    @ApiModelProperty(value = "电子名片小程序openid", position = 3)
    private String openid;
}
