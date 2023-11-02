package vip.xiaonuo.pan.modular.fileRight.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.fileRight.entity.PanFileRight;

/**
 * @author yong.ye
 * @date 2023/10/25 15:56
 */

@Getter
@Setter
public class PanFileRightVo extends PanFileRight {

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 3)
    private String objName;

    /** 用户类型：1 用户  2 部门 */
    @ApiModelProperty(value = "用户类型：1 用户  2 部门 ", position = 3)
    private String objType;
}
