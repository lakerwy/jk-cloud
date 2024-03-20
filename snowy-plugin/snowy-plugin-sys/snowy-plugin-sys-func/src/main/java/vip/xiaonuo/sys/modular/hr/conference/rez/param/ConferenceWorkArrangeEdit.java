package vip.xiaonuo.sys.modular.hr.conference.rez.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConferenceWorkArrangeEdit {
    /** ID */
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 会议ID */
    @ApiModelProperty(value = "会议ID", position = 2)
    private String rezId;

    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 3)
    private String workArrange;

    /** 任务时间 */
    @ApiModelProperty(value = "任务开始时间", position = 4)
    private Date startTime;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 5)
    private Date finishTime;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    private String dutyUserId;

   /* *//** 工作进展：任务状态，0 已取消，1 进行中，2 已完成 *//*
    @ApiModelProperty(value = "工作进展：任务状态，0 已取消，1 进行中，2 已完成", position = 7)
    private Integer workEvolve;*/

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    private String extJson;


    /** 是否创建日程：0 未创建，1 为已创建 */
    @ApiModelProperty(value = "是否创建日程：0 未创建，1 为已创建", position = 15)
    private Integer isSchedule;


    /** 钉钉日程ID */
    @ApiModelProperty(value = "钉钉日程ID", position = 17)
    private String scheduleId;

    /** 责任部门ID */
    @ApiModelProperty(value = "责任部门ID", position = 18)
    private String dutyOrgId;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 已取消，1 进行中，2 已完成", position = 10)
    private String taskStatus;
}
