package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ConferenceWorkArrangeAdd {
    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 3)
    private String workArrange;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 4)
    private Date finishTime;

    /** 责任部门ID */
    @ApiModelProperty(value = "责任部门ID", position = 5)
    private String dutyOrgId;

    /** 责任部门名称 */
    @ApiModelProperty(value = "责任部门名称", position = 6)
    private String dutyOrgName;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 7)
    private String dutyUserId;

    /** 责任人名称 */
    @ApiModelProperty(value = "责任人名称", position = 8)
    private String dutyUserName;

    /** 工作进展 */
    @ApiModelProperty(value = "工作进展", position = 9)
    private String workEvolve;

    /** 是否完成,1 完成 */
    @ApiModelProperty(value = "是否完成,1 完成", position = 10)
    private Integer isWorkEnd;

    /** 通知类别：1 记录通知，2 纪要通知 */
    @ApiModelProperty(value = "通知类别：1 记录通知，2 纪要通知", position = 11)
    private Integer noticeType;

    /** 反馈方式 */
    @ApiModelProperty(value = "反馈方式", position = 12)
    private String feedbackType;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 13)
    private String extJson;
}
