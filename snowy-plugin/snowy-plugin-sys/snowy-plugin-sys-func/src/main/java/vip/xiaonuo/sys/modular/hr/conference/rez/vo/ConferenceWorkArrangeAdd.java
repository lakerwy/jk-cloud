package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
public class ConferenceWorkArrangeAdd {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    @NotNull(message = "部署任务修改时不能为空")
    private String id;

    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 3)
    @NotNull(message = "部署事项不能为空")
    private String workArrange;

    /** 任务时间 */
    @ApiModelProperty(value = "任务开始时间", position = 4)
    @NotNull(message = "任务开始时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 5)
    @NotNull(message = "完成时间不能为空")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishTime;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    @NotNull(message = "责任人不能为空")
    private String dutyUserId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    private String extJson;

   /* *//** 任务参与人 *//*
    @ApiModelProperty(value = "任务参与人", position = 16)
    private List<SysUser> joinUsers;*/

    /** 责任部门ID */
    @ApiModelProperty(value = "责任部门ID", position = 10)
    @NotNull(message = "责任人不能为空")
    private String dutyOrgId;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 未确认，1 已确认，2 已取消，3 进行中，4 已完成", position = 10)
    private String taskStatus;

    /** 钉钉日程ID */
    @ApiModelProperty(value = "钉钉日程ID", position = 12)
    private String scheduleId;
}
