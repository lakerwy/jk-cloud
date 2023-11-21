package vip.xiaonuo.sys.modular.hr.conference.config.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConferenceTimeDataParam {
    /** 时间-年月日 */
    @ApiModelProperty(value = "时间-格式：年月日")
    private String day;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID")
    private String conferenceId;

    /** 会议室名称 */
    @ApiModelProperty(value = "会议室名称")
    private String conferencename;
}
