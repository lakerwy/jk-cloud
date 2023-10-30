package vip.xiaonuo.pan.modular.fileRight.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/10/30 11:13
 */

@Getter
@Setter
public class ManagerParam {
    /** 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "用户文件Id（UserFile.Id）", position = 1)
    private String userFileId;

    /** 文件负者人（管理者） 多个用英文逗号分割 */
    @ApiModelProperty(value = "文件负者人（管理者）多个用英文逗号分割", position = 2)
    private String fileManager;
}
