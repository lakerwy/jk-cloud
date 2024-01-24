package vip.xiaonuo.flw.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class FlwTaskUrgeParam {
    /** 任务Id */
    @ApiModelProperty(value = "任务Id", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 意见 */
    @ApiModelProperty(value = "意见", position = 2)
    private String comment;

    /** 填写数据 */
    @ApiModelProperty(value = "填写数据", position = 3)
    private String dataJson;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 4)
    private List<FlwTaskAttachmentParam> attachmentList;
}
