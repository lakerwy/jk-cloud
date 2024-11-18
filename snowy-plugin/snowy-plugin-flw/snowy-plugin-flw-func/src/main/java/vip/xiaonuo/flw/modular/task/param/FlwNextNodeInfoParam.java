package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 获取后续节点信息参数
 *
 * @author pzh
 * @date 2024/11/13 14:30
 */
@Getter
@Setter
public class FlwNextNodeInfoParam {

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 1, required = true)
    @NotBlank(message = "流程实例id不能为空")
    private String processInstanceId;

//    /** 填写数据 */
//    @ApiModelProperty(value = "填写数据", position = 2)
//    private String dataJson;
}
