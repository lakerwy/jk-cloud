package vip.xiaonuo.flw.modular.task.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 待办任务结果
 *
 * @author mayike
 * @date 2024/2/18 10:19
 */
@Getter
@Setter
public class FlwTaskInfoResult extends FlwTodoTaskResult {

    /** 状态值 */
    @ApiModelProperty(value = "状态值", position = 19)
    private String stateText;

    /** 状态码 */
    @ApiModelProperty(value = "状态码", position = 20)
    private String stateCode;

    /** 办理节点id */
    @ApiModelProperty(value = "办理节点id", position = 21)
    private String doneActivityId;

    /** 办理节点名称 */
    @ApiModelProperty(value = "办理节点名称", position = 22)
    private String doneActivityName;

}
