package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/9/15 12:53
 */
@Getter
@Setter
public class PreviewOfficeFileParam {

    @ApiModelProperty(value = "文件ID")
    private String userFileId;
    
}
