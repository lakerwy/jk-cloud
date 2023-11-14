package vip.xiaonuo.pan.modular.commonFile.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.pan.modular.fileCenter.entity.PanFilePermission;

/**
 * 文件个人权限设置详情实体
 */
@Getter
@Setter
public class CommonFilePermissionVo extends PanFilePermission {

    /** 共享对象 名称 */
    @ApiModelProperty(value = "共享对象 名称", position = 20)
    private String objName;

    /** 共享对象 备注（所属组织等） */
    @ApiModelProperty(value = "共享对象 备注（所属组织等） ", position = 20)
    private String objMemo;

}
