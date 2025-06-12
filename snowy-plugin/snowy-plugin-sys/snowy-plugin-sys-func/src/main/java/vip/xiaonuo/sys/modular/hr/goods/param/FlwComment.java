package vip.xiaonuo.sys.modular.hr.goods.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FlwComment {

    /** 任务id */
    @ApiModelProperty(value = "任务id", position = 1)
    private String taskId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 2)
    private String taskName;

    /** 活动节点id */
    @ApiModelProperty(value = "活动节点id", position = 3)
    private String activityId;

    /** 用户id */
    @ApiModelProperty(value = "用户id", position = 4)
    private String userId;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 5)
    private String userName;

    /** 审批操作类型 */
    @ApiModelProperty(value = "审批操作类型", position = 6)
    private String operateType;

    /** 审批操作文字 */
    @ApiModelProperty(value = "审批操作文字", position = 7)
    private String operateText;

    /** 审批意见 */
    @ApiModelProperty(value = "审批意见", position = 8)
    private String comment;

    /** 审批时间 */
    @ApiModelProperty(value = "审批时间", position = 9)
    private String approveTime;

    /** 审批耗时，最小单位：分钟 */
    @ApiModelProperty(value = "审批耗时，最小单位：分钟", position = 11)
    private String approveExpendHours;

    /** 评论-主键id */
    @ApiModelProperty(value = "评论-主键id", position = 12)
    private String commentId;

}
