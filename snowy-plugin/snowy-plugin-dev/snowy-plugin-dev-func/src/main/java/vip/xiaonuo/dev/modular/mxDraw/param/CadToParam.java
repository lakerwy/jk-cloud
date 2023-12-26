package vip.xiaonuo.dev.modular.mxDraw.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DWG文件转成PDF
 */

@Getter
@Setter
public class CadToParam {

    /** dwg文件路径 */
    @ApiModelProperty(value = "dwg文件路径", position = 1)
    private String filePath;
}
