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

    /** 任务优先级：0 最低，1较低，2普通，3较高，4 最高*/
    @ApiModelProperty(value = "任务优先级(默认为最低 0)：0 最低，1 较低，2 普通，3 较高，4 最高", position = 11)
    private String taskPriority;
}
