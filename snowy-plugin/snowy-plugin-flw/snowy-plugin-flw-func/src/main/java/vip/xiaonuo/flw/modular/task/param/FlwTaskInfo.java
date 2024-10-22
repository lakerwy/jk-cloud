package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取流程审批人信息
 */

@Getter
@Setter
public class FlwTaskInfo {

    @ApiModelProperty(value = "流程实例Id",  position = 2)
    private String processInstanceId;
}
