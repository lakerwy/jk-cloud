package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FlwTaskUrgeParam {
    /** 任务Id */
    @ApiModelProperty(value = "任务Id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 意见 */
    @ApiModelProperty(value = "意见", position = 2)
    private String comment;
}
