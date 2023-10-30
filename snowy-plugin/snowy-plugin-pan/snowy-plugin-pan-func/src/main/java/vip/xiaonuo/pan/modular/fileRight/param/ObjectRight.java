package vip.xiaonuo.pan.modular.fileRight.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/10/25 14:38
 */

@Getter
@Setter
public class ObjectRight {

    /** 用户Id | 部门Id | 角色Id */
    @ApiModelProperty(value = " 用户Id | 部门Id | 角色Id ", position = 4)
    private String objectId;

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 5)
    private Long rightValue;
}
