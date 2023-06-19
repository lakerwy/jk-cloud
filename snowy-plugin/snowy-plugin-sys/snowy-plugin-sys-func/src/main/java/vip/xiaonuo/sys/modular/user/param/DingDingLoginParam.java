package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 钉钉应用免登跳转参数
 *
 * @author yong.ye
 * @date 2023/6/14 15:40
 */

@Getter
@Setter
public class DingDingLoginParam {
    /** 钉钉内部应用AppKey */
    @ApiModelProperty(value = "钉钉内部应用AppKey", required = true, position = 1)
    @NotBlank(message = "appKey不能为空")
    private String appKey;

    /** 钉钉内部应用AppSecret */
    @ApiModelProperty(value = "钉钉内部应用AppSecret", required = true, position = 2)
    @NotBlank(message = "appSecret不能为空")
    private String appSecret;

    /** 免登码code */
    @ApiModelProperty(value = "免登码code", required = true, position = 3)
    @NotBlank(message = "code不能为空")
    private String code;
}
