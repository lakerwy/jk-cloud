package vip.xiaonuo.dev.modular.server.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JiankeUserVo {
    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 1)
    private String userName;

    /** 电话号码 */
    @ApiModelProperty(value = "电话号码", position = 2)
    private String account;

    /** 角色 */
    @ApiModelProperty(value = "角色ID", position = 3)
    private String roleId;

    @Override
    public String toString() {
        return "userName:"+userName+",account:"+account+",roleId:"+roleId;
    }
}
