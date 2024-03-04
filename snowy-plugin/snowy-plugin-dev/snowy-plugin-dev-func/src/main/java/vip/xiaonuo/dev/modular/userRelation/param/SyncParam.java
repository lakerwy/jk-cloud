package vip.xiaonuo.dev.modular.userRelation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/2/29 11:35
 */
@Getter
@Setter
public class SyncParam {

    /** 主键 */
    @ApiModelProperty(value = "系统主键id", required = true)
    @NotBlank(message = "系统主键id不能为空")
    private String id;

    @ApiModelProperty(value = "同步用户信息", required = true)
    private List<SyncUser> syncUsers;

    @Data
    public static class SyncUser{
        /** 用户ID */
        @ApiModelProperty(value = "用户ID，当存在外部人员时，不传用户ID")
        @NotBlank(message = "userId 不能为空")
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
}
