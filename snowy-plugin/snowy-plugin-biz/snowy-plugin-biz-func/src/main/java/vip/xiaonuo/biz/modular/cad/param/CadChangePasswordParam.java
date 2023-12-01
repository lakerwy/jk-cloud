package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 插件端修改密码
 */
@Getter
@Setter
public class CadChangePasswordParam {

    /** CAD用户ID - 主键ID */
    @ApiModelProperty(value = "CAD用户ID - 主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 旧密码 */
    @ApiModelProperty(value = "旧密码", position = 2)
    private String oldPassword;

    /** 新密码 */
    @ApiModelProperty(value = "薪密码", position = 3)
    private String newPassword;
}
