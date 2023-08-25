package vip.xiaonuo.pan.modular.fold.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.dev.modular.file.param.DevFileListParam;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/8/25 16:43
 */
@Getter
@Setter
public class DevFoldListParam extends DevFileListParam {

    @ApiModelProperty(value = "文件夹父目录id" ,example = "0")
    private String parentId;

}
