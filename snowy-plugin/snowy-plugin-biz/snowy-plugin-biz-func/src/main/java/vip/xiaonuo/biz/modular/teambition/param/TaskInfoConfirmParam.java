package vip.xiaonuo.biz.modular.teambition.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TaskInfoConfirmParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 未确认，1 已确认，2 已取消，3 进行中，4 已完成", required = true)
    @NotBlank(message = "任务状态不能为空")
    private String taskStatus;
}
