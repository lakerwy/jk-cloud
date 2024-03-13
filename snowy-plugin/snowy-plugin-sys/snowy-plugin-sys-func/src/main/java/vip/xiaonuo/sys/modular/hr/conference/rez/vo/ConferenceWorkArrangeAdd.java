package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    private Date startTime;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 5)
    @NotNull(message = "完成时间不能为空")
    private Date finishTime;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    @NotNull(message = "责任人不能为空")
    private String dutyUserId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    private String extJson;

    /** 任务参与人 */
    @ApiModelProperty(value = "任务参与人", position = 16)
    private List<SysUser> joinUsers;
}
