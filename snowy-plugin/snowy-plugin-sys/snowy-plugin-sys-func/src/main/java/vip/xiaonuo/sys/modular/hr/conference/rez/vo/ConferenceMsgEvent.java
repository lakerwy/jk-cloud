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

    /** 通知人员List */
    @ApiModelProperty(value = "通知人员List", position = 1)
    private String userIds;

    /** 周期会议ID */
    @ApiModelProperty(value = "周期会议ID", position = 3)
    private ConferenceRezPeriod conferenceRezPeriod;

    @ApiModelProperty(value = "会议主题", position = 4)
    private String theme ;

    @ApiModelProperty(value = "开会时间", position = 5)
    private String confTime;

    @ApiModelProperty(value = "开会地点", position = 6)
    private String location;

    @ApiModelProperty(value = "预约人", position = 7)
    private String rezUser;

    @ApiModelProperty(value = "主持人", position = 8)
    private String presideUser;

    @ApiModelProperty(value = "联系电话", position = 9)
    private String phone;

    /** 不同的消息 */
    @ApiModelProperty(value = "不同的消息", position = 10)
    private Integer type;

    public ConferenceMsgEvent(){}

    public ConferenceMsgEvent(String userIds){
        this.userIds = userIds;
    }
}
