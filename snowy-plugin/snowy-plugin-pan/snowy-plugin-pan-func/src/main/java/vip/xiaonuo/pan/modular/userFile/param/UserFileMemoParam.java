package vip.xiaonuo.pan.modular.userFile.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 添加文件备注信息
 */
@Getter
@Setter
public class UserFileMemoParam {
    /** 文件FileID */
    @ApiModelProperty(value = "文件FileID", position = 1)
    private String fileId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 2)
    private String memo;
}
