package vip.xiaonuo.pan.modular.fileRight.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/1 10:49
 */


@Getter
@Setter
public class UserInfoVo {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 1)
    private String userId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 2)
    private String userName;

    /** 组织id */
    @ApiModelProperty(value = "组织id", position = 3)
    private String orgId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 4)
    private String orgName;

    /** 角色id */
    @ApiModelProperty(value = "角色id", position = 1)
    private String roleId;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称", position = 1)
    private String roleName;

    /** 角色编码 */
    @ApiModelProperty(value = "角色编码", position = 5)
    private String roleCode;
}
