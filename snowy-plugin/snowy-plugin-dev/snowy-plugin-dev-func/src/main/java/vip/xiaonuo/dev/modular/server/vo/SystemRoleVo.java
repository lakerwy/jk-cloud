package vip.xiaonuo.dev.modular.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/16 19:34
 * 子系统 角色信息
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SystemRoleVo {
    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    /** 角色id */
    @ApiModelProperty(value = "角色id")
    private String roleId;
}
