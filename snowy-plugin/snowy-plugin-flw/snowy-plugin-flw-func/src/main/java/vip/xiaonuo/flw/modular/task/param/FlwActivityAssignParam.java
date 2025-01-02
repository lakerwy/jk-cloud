package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
@Setter
public class FlwActivityAssignParam {

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", required = true, position = 1)
    @NotBlank(message = "processInstanceId不能为空")
    private String processInstanceId;

    /** 流程定义id */
    @ApiModelProperty(value = "流程定义id", required = true, position = 2)
    @NotBlank(message = "processDefinitionId不能为空")
    private String processDefinitionId;

    /** 目标节点id */
    @ApiModelProperty(value = "目标节点id", required = true, position = 3)
    @NotBlank(message = "activityId不能为空")
    private String activityId;

    /** 审批人集合 */
    @ApiModelProperty(value = "审批人集合", position = 4)
    private List<String> assignList;

}
