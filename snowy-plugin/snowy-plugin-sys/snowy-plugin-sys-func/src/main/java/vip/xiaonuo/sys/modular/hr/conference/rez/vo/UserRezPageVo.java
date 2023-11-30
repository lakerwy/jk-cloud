package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceConfirmUser;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceRecord;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;

import java.util.List;

@Setter
@Getter
public class UserRezPageVo {
    /** 会议信息 ：根据传入参数确定预约和参与的会议*/
    @ApiModelProperty(value = "会议信息：根据传入参数确定预约和参与的会议", position = 1)
    private ConferenceRez conferenceRez;

    @ApiModelProperty(value = "会议记录确认", position = 2)
    private List<ConfirmStat> recordList;

    @ApiModelProperty(value = "会议纪要确认", position = 3)
    private List<ConfirmStat> summaryList;

    @ApiModelProperty(value = "工作部署",position = 4)
    private List<ConferenceWorkArrange> conferenceWorkArrangeList;
}
