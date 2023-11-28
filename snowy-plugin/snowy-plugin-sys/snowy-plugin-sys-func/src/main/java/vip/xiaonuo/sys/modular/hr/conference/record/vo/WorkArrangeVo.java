package vip.xiaonuo.sys.modular.hr.conference.record.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkArrangeVo {
    /** 工作部署,部署事项，完成时间，责任部门，责任人，工作进展，是否完成*/
    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 1)
    private String workArrange;

    @ApiModelProperty(value = "完成时间", position = 2)
    private String  finishTime;

    @ApiModelProperty(value = "责任部门", position = 3)
    private String  dutyOrgId;

    @ApiModelProperty(value = "责任部门名称", position = 4)
    private String  dutyOrgName;

    @ApiModelProperty(value = "责任人", position = 5)
    private String  dutyUserId;

    @ApiModelProperty(value = "责任人名称", position = 6)
    private String  dutyUserName;

    @ApiModelProperty(value = "工作进展", position = 6)
    private String  workEvolve;

    @ApiModelProperty(value = "是否完成,1 完成", position = 6)
    private Integer  isWorkEnd;

}
