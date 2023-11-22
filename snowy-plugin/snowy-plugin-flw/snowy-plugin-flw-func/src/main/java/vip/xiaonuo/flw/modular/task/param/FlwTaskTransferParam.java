package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/11/20 9:16
 * 任务移交
 */
@Getter
@Setter
public class FlwTaskTransferParam {

    /** 被交接人*/
    @ApiModelProperty(value = "被交接人id",required = true)
    @NotNull(message = "被交接人id不能未空")
    private String oldAssignee;

    @ApiModelProperty(value = "被交接人名称",required = true)
    @NotNull(message = "被交接人名称不能未空")
    private String oldAssigneeName;

    /** 交接人*/
    @ApiModelProperty(value = "交接人id",required = true)
    @NotNull(message = "交接人id不能未空")
    private String newAssignee;

    @ApiModelProperty(value = "交接人名称",required = true)
    @NotNull(message = "交接人名称不能未空")
    private String newAssigneeName;

    /**流程模板id*/
    @ApiModelProperty(value = "流程模板id",required = true)
    @NotNull(message = "流程模板不能未空")
    private String actExtModelIds;
}
