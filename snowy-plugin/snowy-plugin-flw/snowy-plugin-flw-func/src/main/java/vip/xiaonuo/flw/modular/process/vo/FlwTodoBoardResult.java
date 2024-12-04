package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.flw.modular.task.result.FlwTodoTaskResult;

import java.util.Date;

/**
 * 看板 - 待办模块
 */

@Getter
@Setter
public class FlwTodoBoardResult extends FlwTodoTaskResult {

    /** 任务生成时间 */
    @ApiModelProperty(value = "任务生成时间", position = 66)
    private Date taskCreateTime;

    /** 流程耗时(h) */
    @ApiModelProperty(value = "流程耗时(h)", position = 66)
    private Double boardTime;

    /** 办理人（Id） */
    @ApiModelProperty(value = "办理人（Id）", position = 66)
    private String assigneeId;
}
