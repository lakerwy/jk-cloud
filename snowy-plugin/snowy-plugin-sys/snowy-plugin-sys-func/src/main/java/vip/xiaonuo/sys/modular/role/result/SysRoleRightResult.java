package vip.xiaonuo.sys.modular.role.result;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.resource.entity.SysMenu;
import vip.xiaonuo.sys.modular.role.entity.SysRole;
import vip.xiaonuo.sys.modular.user.vo.SysSimpleUser;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/10 14:32
 */
@Getter
@Setter
public class SysRoleRightResult extends SysRole {

    @ApiModelProperty(value = "管理员列表",position = 20)
    List<SysSimpleUser> sysSimpleUsers;

    @ApiModelProperty(value = "菜单列表", position = 21)
    List<SysMenu> sysMenus;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 1002)
    private Date updateTime;

//    /** 更新人 */
//    @ApiModelProperty(value = "更新人", position = 1003)
//    private String updateUser;

    @ApiModelProperty(value = "更新人", position = 1003)
    private String updateUserName;
}
