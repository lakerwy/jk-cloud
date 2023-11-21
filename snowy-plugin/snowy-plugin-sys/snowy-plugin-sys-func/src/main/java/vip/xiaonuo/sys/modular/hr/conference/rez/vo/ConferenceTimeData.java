package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ConferenceTimeData {

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID")
    private String conferenceId;

    /** 会议室名称 */
    @ApiModelProperty(value = "会议室名称")
    private String conferencename;

    /** 会议室时间列表对应状态数据 */
    @ApiModelProperty(value = "会议室时间列表对应状态数据")
    private List<ConferenceTime> conferenceTimes;

    /** 容纳人数 */
    @ApiModelProperty(value = "容纳人数")
    private Integer containMax;

    /** 会议室位置 */
    @ApiModelProperty(value = "会议室位置")
    private String location;

    @ApiModelProperty(value = "设备列表")
    private String device;

    /** 附件 */
    @ApiModelProperty(value = "附件")
    private String fileId;
}
