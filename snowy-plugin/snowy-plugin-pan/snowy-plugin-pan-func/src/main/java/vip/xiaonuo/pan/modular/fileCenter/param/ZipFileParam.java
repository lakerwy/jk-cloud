package vip.xiaonuo.pan.modular.fileCenter.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ZipFileParam {

    @ApiModelProperty(value = "需要压缩的文件夹ID")
    private List<String> userFileId;

    @ApiModelProperty(value = "保存的路径-文件id", required = true)
    private String saveFileId;

    @ApiModelProperty(value = "压缩包名称", required = true)
    private String zipName;

    /** md5唯一标识 */
    @ApiModelProperty(value = "md5唯一标识", required = true)
    private String identifier;
}
