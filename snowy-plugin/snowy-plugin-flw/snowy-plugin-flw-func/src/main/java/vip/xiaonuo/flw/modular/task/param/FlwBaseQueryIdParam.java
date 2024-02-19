package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/2/19 16:23
 */
@Getter
@Setter
public class FlwBaseQueryIdParam {
    /** id */
    @ApiModelProperty(value = "id", required = true)
    @NotBlank(message = "id不能为空")
    //不同接口中，id所表达的意义不一样
    private String id;
}
