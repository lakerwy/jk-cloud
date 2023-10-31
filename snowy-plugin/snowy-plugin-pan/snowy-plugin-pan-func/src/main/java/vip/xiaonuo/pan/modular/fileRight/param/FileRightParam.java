package vip.xiaonuo.pan.modular.fileRight.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/10/31 9:08
 */

@Getter
@Setter
public class FileRightParam {

    /** 选中的 用户文件Id（UserFile.Id） */
    @ApiModelProperty(value = "选中的 用户文件Id（UserFile.Id）", position = 1)
    private List<String> userFileIds;
}
