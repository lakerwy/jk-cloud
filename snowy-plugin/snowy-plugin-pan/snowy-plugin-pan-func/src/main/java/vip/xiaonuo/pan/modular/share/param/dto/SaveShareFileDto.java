package vip.xiaonuo.pan.modular.share.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/8 10:45
 */

@Getter
@Setter
public class SaveShareFileDto {

    /** 分享的文件集Id */
    @ApiModelProperty(value = "分享的文件集Id", position = 3)
    private List<String> userFileIds;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 2)
    private String filePath;

    /** 分享批次号 */
    @ApiModelProperty(value = "分享批次号", position = 1)
    private String shareBatchNum;

}
