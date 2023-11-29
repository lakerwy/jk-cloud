package vip.xiaonuo.sys.modular.hr.conference.record.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceRecord;
import vip.xiaonuo.sys.modular.hr.conference.record.entity.ConferenceWorkArrange;

import java.util.List;

@Setter
@Getter
public class RecordDetailVo {

    @ApiModelProperty(value = "会议记录信息", position = 1)
    private ConferenceRecord conferenceRecord;

    //会议信息
    @ApiModelProperty(value = "会议信息", position = 2)
    private RezRecordVo rezRecordVo;

    //工作部署
    @ApiModelProperty(value = "工作部署", position = 3)
    private List<ConferenceWorkArrange> conferenceWorkArrangeList;
}
