package vip.xiaonuo.sys.modular.hr.conference.record.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.record.vo.WorkArrangeVo;

import java.util.List;

@Setter
@Getter
public class ConfSummaryAddParam {

    /** 会议记录ID */
    @ApiModelProperty(value = "会议记录ID", position = 1)
    private String recordId;

    /** 会议纪要*/
    @ApiModelProperty(value = "会议纪要", position = 2)
    private String confSummary;

    /** 会议纪要确认人(逗号隔开)  CONFIRM_USER_IDS*/
    @ApiModelProperty(value = "会议纪要确认人(逗号隔开)", position = 3)
    private String confirmUserIds;

    /** 工作部署,部署事项，完成时间，责任部门，责任人，工作进展，是否完成*/
    @ApiModelProperty(value = "工作部署", position = 4)
    private List<WorkArrangeVo> workArrangeVoList;

    @ApiModelProperty(value = "会议主题", position = 5)
    private String theme;

    /** 会议记录确认人(逗号隔开) */
    @ApiModelProperty(value = "会议记录确认人(逗号隔开)", position = 6)
    private String recordConfirmIds;
}
