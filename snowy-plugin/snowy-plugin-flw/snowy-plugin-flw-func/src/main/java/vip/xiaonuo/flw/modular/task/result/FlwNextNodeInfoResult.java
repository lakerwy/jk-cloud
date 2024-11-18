package vip.xiaonuo.flw.modular.task.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 后续节点信息
 *
 * @author pzh
 * @date 2024/11/13 14:30
 */
@Getter
@Setter
public class FlwNextNodeInfoResult {


    /** 节点id */
    @ApiModelProperty(value = "节点id", position = 1)
    private String nodeId;

    /** 节点名称 */
    @ApiModelProperty(value = "节点名称", position = 2)
    private String nodeName;

}
