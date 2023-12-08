package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/12/8 8:56
 */
@Setter
@Getter
public class FlwTaskCommentParam {
    /** 意见 */
    @ApiModelProperty(value = "输入值", position = 1)
    private String inputValue;
}
