package vip.xiaonuo.flw.modular.task.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 节点审批人信息
 */
@Getter
@Setter
public class FlwTaskAuditorResult {

    /** 节点id */
    @ApiModelProperty(value = "节点id", position = 1)
    private String id;

    /** 节点名称 */
    @ApiModelProperty(value = "节点名称", position = 2)
    private String name;

    /** 节点审批人 */
    @ApiModelProperty(value = "节点名称", position = 3)
    private String assign;

}
