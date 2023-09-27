package vip.xiaonuo.pan.modular.commonFile.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/9/11 16:18
 */

@Getter
@Setter
public class CommonFileVo {

    /** CommonFile.Id 共享主键id */
    @ApiModelProperty(value = "CommonFile.Id 共享主键id", position = 1)
    private String id;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 2)
    private String fileName;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 3)
    private String userId;

    /** 用户文件Id */
    @ApiModelProperty(value = "用户文件Id", position = 4)
    private String userFileId;


}
