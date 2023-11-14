package vip.xiaonuo.pan.modular.commonFile.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.userFile.entity.UserFile;

@Getter
@Setter
public class CommonToMeVo extends UserFile {

    /** 共享权限设置表主键id PanFilePermission.ID */
    @ApiModelProperty(value = "共享权限设置表主键id PanFilePermission.ID", position = 1)
    private String commonId;

    /** 共享权限设置表共享文件id */
    @ApiModelProperty(value = "共享权限设置表共享文件id", position = 3)
    private String commonFileId;

    /** 共享权限设置表 用户对文件的权限码 */
    @ApiModelProperty(value = "共享权限设置表 用户对文件的权限码", position = 4)
    private Long filePermissionCode;

    /** 文件所有者 - 共享者*/
    @ApiModelProperty(value = "文件所有者 - 共享者", position = 2)
    private String objName;

    /** 文件所有者 - 共享者 - 所属组织*/
    @ApiModelProperty(value = "文件所有者 - 共享者 - 所属组织", position = 2)
    private String objMemo;

}
