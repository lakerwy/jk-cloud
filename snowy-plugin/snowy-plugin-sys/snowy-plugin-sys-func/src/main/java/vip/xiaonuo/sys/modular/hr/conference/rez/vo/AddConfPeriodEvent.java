package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.ConferenceRezAddParam;

@Getter
@Setter
public class AddConfPeriodEvent {

    /** 周期会议参数  */
    @ApiModelProperty(value = "周期会议参数", position = 1)
    private ConferenceRezAddParam conferenceRezAddParam;

    public AddConfPeriodEvent(){}

    public AddConfPeriodEvent(ConferenceRezAddParam conferenceRezAddParam){
        this.conferenceRezAddParam = conferenceRezAddParam;
    }

}
