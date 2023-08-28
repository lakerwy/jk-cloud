package vip.xiaonuo.pan.modular.fold.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/28 14:13
 */
@Getter
@Setter
public class DevFoldOperateParam {

    /** 原文件夹ID */
    @ApiModelProperty(value = "原文件夹ID", required = true, position = 1)
    @NotBlank(message = "原文件夹id不能为空")
    private String sourceFoldId;

    /** 原文件夹名称 */
    @ApiModelProperty(value = "原文件夹名称", position = 1)
    private String sourceFoldName;

    /** 主键ID */
    @ApiModelProperty(value = "目标文件夹ID", required = true, position = 2)
    @NotBlank(message = "目标文件夹ID不能为空")
    private String targetFoldId;

}
