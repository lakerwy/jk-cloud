package vip.xiaonuo.flw.modular.process.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.flw.modular.task.param.FlwTaskAttachmentParam;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2023/10/25 15:58
 */
@Setter
@Getter
public class FlwCommentParam {
    /** 任务Id */
    @ApiModelProperty(value = "任务Id",  position = 1)
    private String id;

    @ApiModelProperty(value = "流程实例Id",  position = 2)
    private String processInstanceId;

    /** 意见 */
    @ApiModelProperty(value = "意见", position = 3)
    private String comment;

    /** 填写数据 */
    @ApiModelProperty(value = "填写数据", position = 4)
    private String dataJson;

    /** 附件信息 */
    @ApiModelProperty(value = "附件信息", position = 5)
    private List<FlwTaskAttachmentParam> attachmentList;

    @ApiModelProperty(value = "特殊表示（流程完成后评论添加附件）",position = 6)
    private Boolean flag = true;

    /** 指定通知人员 多个用英文逗号分割，通知全部传入 ALL 字符串 */
    @ApiModelProperty(value = "指定通知人员", position = 100)
    private String auditorIds;
}
