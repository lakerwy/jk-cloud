package vip.xiaonuo.sys.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SystemEpcResult {
    /** 主键 */
    @ApiModelProperty(value = "用户主键id", required = true)
    @NotBlank(message = "userId不能为空")
    private String userId;

    /** 角色ID */
    @ApiModelProperty(value = "角色ID",required = true)
    @NotBlank(message = "角色id不能为空")
    private String roleId;
}
