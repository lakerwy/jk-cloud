package vip.xiaonuo.pan.modular.userFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/28 14:13
 */
@Getter
@Setter
public class UserFileOperateParam {

    /** 原文件夹ID */
    @ApiModelProperty(value = "原文件夹ID集合", required = true, position = 1)
    @NotEmpty(message = "原文件夹ID集合不能为空")
    private String[] sourceFoldId;

    /** 原文件夹名称 */
    @ApiModelProperty(value = "原文件夹名称", position = 2)
    private String sourceFoldName;

    /** 主键ID */
    @ApiModelProperty(value = "目标文件夹ID--目标文件夹路径  两个值必传一个",  position = 3)
    //@NotBlank(message = "目标文件夹ID不能为空")
    private String targetFoldId;

    @ApiModelProperty(value = "目标文件夹路径", position = 4,required = true)
    @NotBlank(message = "目标文件夹路径不能为空")
    private String targetFoldPath;

    @ApiModelProperty(value = "权限校验类型：GROUP 群组  COMMON 共享  注：（不传或不在指定范围） 不做权限校验")
    private String rightType;

}
