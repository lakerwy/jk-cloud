package vip.xiaonuo.sys.modular.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpcRoleVo {

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    private String roleId;
}
