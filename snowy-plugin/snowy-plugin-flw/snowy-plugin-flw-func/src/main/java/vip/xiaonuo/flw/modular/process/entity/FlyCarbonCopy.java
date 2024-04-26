package vip.xiaonuo.flw.modular.process.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 流程抄送前端参数
 * @Author hxw
 * @Date 2024/4/24 17:30
 */
@Getter
@Setter
public class FlyCarbonCopy {
    /**
     *流程ID
     */
    @ApiModelProperty(value = "流程ID", position = 1)
    @NotBlank(message = "流程ID不能为空")
    private String ProcessInstanceId;
    /**
     * 人员ID
     */
    @ApiModelProperty(value = "人员ID", position = 2)
    @NotEmpty(message = "人员ID集合不能为空")
    private List<String> userList;
}
