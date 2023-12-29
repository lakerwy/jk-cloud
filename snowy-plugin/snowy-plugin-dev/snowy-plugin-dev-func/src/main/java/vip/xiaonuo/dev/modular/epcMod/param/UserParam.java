package vip.xiaonuo.dev.modular.epcMod.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * 导入excel的用户数据
 */

@Getter
@Setter
public class UserParam {

    /** 组织部门 */
    @ApiModelProperty(value = "组织部门", required = true, position = 1)
    @NotBlank(message = "组织部门不能为空")
    private String orgName;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true, position = 2)
    @NotBlank(message = "姓名不能为空")
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 3)
    private String gender;

    /** 手机 */
    @ApiModelProperty(value = "手机",required = true, position = 4)
    @NotBlank(message = "手机不能为空")
    private String phone;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称",required = true, position = 5)
    @NotBlank(message = "角色不能为空")
    private String roleName;

    /** 职位名称 */
    @ApiModelProperty(value = "职位名称", position = 6)
    private String positionName;




    /** 后端用 - id */
    @ApiModelProperty(value = "后端用 - id", position = 10)
    private String id;

//    /** 后端用 - 账号 */
//    @ApiModelProperty(value = "后端用 - 账号", position = 10)
//    private String account;

    /** 后端用 - 密码 */
    @ApiModelProperty(value = "后端用 - 密码", position = 10)
    private String password;

    /** 后端用 - 组织id */
    @ApiModelProperty(value = "后端用 - 组织id", position = 10)
    private String orgId;

    /** 后端用 - 角色id */
    @ApiModelProperty(value = "后端用 - 角色id", position = 10)
    private String roleId;

    /** 后端用 - 职位id */
    @ApiModelProperty(value = "后端用 - 职位id", position = 10)
    private String positionId;

}
