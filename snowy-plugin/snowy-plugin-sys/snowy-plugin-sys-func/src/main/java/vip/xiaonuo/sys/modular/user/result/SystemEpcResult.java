package vip.xiaonuo.sys.modular.user.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class SystemEpcResult {

    /** 用户ID */
    @ApiModelProperty(value = "用户ID，当存在外部人员时，不传用户ID")
    private String userId;

    /** 主键 */
    @ApiModelProperty(value = "用户号码", required = true)
    @NotBlank(message = "用户号码不能为空")
    private String account;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", required = true)
    @NotBlank(message = "用户姓名不能为空")
    private String userName;

    /** 角色ID */
    @ApiModelProperty(value = "角色ID",required = true)
    @NotBlank(message = "角色id不能为空")
    private String roleId;
}
