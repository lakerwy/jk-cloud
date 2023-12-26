package vip.xiaonuo.dev.modular.mxDraw.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * DWG文件转换编辑格式mxweb
 */
@Getter
@Setter
public class ConvertData {

    /** dwg原始文件路径 */
    @ApiModelProperty(value = "dwg原始文件路径", position = 1)
    private String srcpath;

}
