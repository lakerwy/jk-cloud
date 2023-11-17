package vip.xiaonuo.pan.modular.fileRight.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 删除某个文件，某个对象的权限配置
 */
@Getter
@Setter
public class RemoveRightParam {

    /** 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "用户文件Id（UserFile.Id）", position = 1)
    private String userFileId;

    /** 用户Id | 部门Id */
    @ApiModelProperty(value = " 用户Id | 部门Id", position = 4)
    private String objectId;

}
