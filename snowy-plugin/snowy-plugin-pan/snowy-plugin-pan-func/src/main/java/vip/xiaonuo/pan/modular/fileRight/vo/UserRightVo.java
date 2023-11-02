package vip.xiaonuo.pan.modular.fileRight.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/1 11:07
 */

@Getter
@Setter
public class UserRightVo {

    /** 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "用户文件Id（UserFile.Id）", position = 1)
    private String userFileId;

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

    /** 权限值 */
    @ApiModelProperty(value = "权限值", position = 5)
    private Long rightValue;

}
