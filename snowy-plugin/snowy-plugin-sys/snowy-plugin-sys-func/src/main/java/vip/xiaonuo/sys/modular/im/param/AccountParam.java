package vip.xiaonuo.sys.modular.im.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/14 11:03
 */
@Setter
@Getter
public class AccountParam {

    /**用户名，长度不超过32字节**/
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "userId不能为空")
    private String UserID;

    /**用户昵称**/
    @ApiModelProperty(value = "用户昵称", required = false)
    private String Nick;

    /**用户头像 URL**/
    @ApiModelProperty(value = "用户头像 URL", required = false)
    private String FaceUrl;
}
