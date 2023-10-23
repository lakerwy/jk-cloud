package vip.xiaonuo.pan.modular.file.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/10/19 16:59
 */
@Getter
@Setter
public class PanRootFoldParam {
    @ApiModelProperty(value = "1 个人根目录  2 群组根目录")
    private int type;
}
