package vip.xiaonuo.pan.modular.recoveryfile.param.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/9/11 10:37
 */

@Getter
@Setter
public class RestoreFileDto {
    /** 删除批次号 */
    @ApiModelProperty(value = "删除批次号", position = 5)
    private String deleteBatchNum;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径", position = 5)
    private String filePath;
}
