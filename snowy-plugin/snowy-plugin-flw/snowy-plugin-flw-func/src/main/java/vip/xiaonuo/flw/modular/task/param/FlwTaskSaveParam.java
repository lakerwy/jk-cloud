package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 审批保存参数
 *
 * @author xuyuxiang
 * @date 2022/8/1 14:45
 */
@Getter
@Setter
public class FlwTaskSaveParam {

    /** 任务Id */
    @ApiModelProperty(value = "任务Id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 填写数据 */
    @ApiModelProperty(value = "填写数据", position = 2)
    private String dataJson;
}
