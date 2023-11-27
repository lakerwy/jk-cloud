package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRez;
import vip.xiaonuo.sys.modular.hr.conference.rezPeriod.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.user.entity.SysUser;

import java.util.List;

@Getter
@Setter
public class ConferenceMsgEvent {

    /** 人员List */
    @ApiModelProperty(value = "人员List", position = 1)
    private List<SysUser> userList;

    /** 推送的消息 */
    @ApiModelProperty(value = "推送的消息", position = 2)
    private String msg;

    /** 标准会议ID */
    @ApiModelProperty(value = "会议ID", position = 3)
    private ConferenceRez conferenceRez;

    /** 周期会议ID */
    @ApiModelProperty(value = "周期会议ID", position = 4)
    private ConferenceRezPeriod conferenceRezPeriod;

    public ConferenceMsgEvent(){}

    public ConferenceMsgEvent(List<SysUser> userList,String msg,ConferenceRez conferenceRez,ConferenceRezPeriod conferenceRezPeriod){
        this.userList = userList;
        this.msg = msg;
        this.conferenceRez = conferenceRez;
        this.conferenceRezPeriod = conferenceRezPeriod;
    }
}
