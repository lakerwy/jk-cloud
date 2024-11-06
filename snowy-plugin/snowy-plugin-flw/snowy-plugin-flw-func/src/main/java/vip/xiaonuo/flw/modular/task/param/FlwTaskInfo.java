package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 获取流程审批人信息
 */

@Getter
@Setter
public class FlwTaskInfo {

    @ApiModelProperty(value = "流程实例Id", required = true)
    @NotBlank(message = "processInstanceId不能为空")
    private String processInstanceId;
}
