package vip.xiaonuo.pan.modular.commonFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 取消共享（参数结构）
 */

@Getter
@Setter
public class RemoveCommonParam {
    /** 共享文件id */
    @ApiModelProperty(value = "共享文件id", position = 1)
    private String userFileId;

    /** 用户Id | 部门Id */
    @ApiModelProperty(value = " 用户Id | 部门Id", position = 2)
    private List<String> objectIds;
}
