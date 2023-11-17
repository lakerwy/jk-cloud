package vip.xiaonuo.pan.modular.fileRight.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 清空某个文件及其子文件的权限配置（实现继承上级文件夹权限）
 */
@Getter
@Setter
public class ClearRightParam {
    /** 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "用户文件Id（UserFile.Id）", position = 1)
    private String userFileId;

    /**【不传就清除当前文件及其子文件，所有自定义权限设置记录】 需要指定清除 文件及其子文件 自定义权限的 对象集合 用户Id | 部门Id */
    @ApiModelProperty(value = " 需要指定清楚 文件及其子文件 自定义权限的 对象集合 用户Id | 部门Id", position = 4)
    private List<String> objectIds;
}
