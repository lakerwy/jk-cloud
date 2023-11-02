package vip.xiaonuo.pan.modular.commonFile.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 15:27
 */

@Getter
@Setter
public class CommonFileUserVo {

    /** 用户ID | 组织ID */
    @ApiModelProperty(value = "用户ID", position = 1)
    private String objId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 2)
    private String objName;

    /** 用户类型：1 用户  2 部门 */
    @ApiModelProperty(value = "用户类型：1 用户  2 部门 ", position = 3)
    private String objType;

}
