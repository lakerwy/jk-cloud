package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 电子名片，根据openId，获取用户信息参数
 *
 * @author yong.ye
 * @date 2023/6/15 11:06
 */
@Getter
@Setter
public class WeChatUserParam {
    /** 电子名片，微信小程序openId */
    @ApiModelProperty(value = "openId", required = true)
    @NotBlank(message = "openId不能为空")
    private String openId;
}
