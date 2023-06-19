package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 根据系统用户Id，获取钉钉用户Id
 *
 * @author yong.ye
 * @date 2023/6/15 15:35
 */
@Getter
@Setter
public class DingDingUserParam {
    /** 系统用户Id */
    @ApiModelProperty(value = "userId", required = true)
    @NotBlank(message = "userId不能为空")
    private String userId;
}
