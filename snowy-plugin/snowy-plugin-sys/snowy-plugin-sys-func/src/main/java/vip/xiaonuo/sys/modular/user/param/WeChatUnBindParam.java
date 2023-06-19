package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 电子名片，解绑，参数
 *
 * @author yong.ye
 * @date 2023/6/15 11:12
 */

@Getter
@Setter
public class WeChatUnBindParam {
    /** 电子名片，系统用户Id */
    @ApiModelProperty(value = "userId", required = true)
    @NotBlank(message = "userId不能为空")
    private String userId;
}
