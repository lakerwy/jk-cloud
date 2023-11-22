package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/11/20 9:16
 * 任务移交
 */
@Getter
@Setter
public class FlwTaskTransferPageParam {

    /** 被交接人*/
    @ApiModelProperty(value = "被交接人id",required = true)
    @NotNull(message = "被交接人id不能未空")
    private String oldAssignee;
}
