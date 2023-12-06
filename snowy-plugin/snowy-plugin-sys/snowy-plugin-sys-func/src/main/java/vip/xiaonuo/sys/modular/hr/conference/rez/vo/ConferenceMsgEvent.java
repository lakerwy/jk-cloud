package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.conference.rez.entity.ConferenceRezPeriod;
import vip.xiaonuo.sys.modular.hr.conference.rez.param.RuanLianParam;

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

    @ApiModelProperty(value = "预约软联会议参数", position = 11)
    private RuanLianParam ruanLianParam;

    /** 不同的消息 */
    @ApiModelProperty(value = "不同的消息", position = 10)
    private Integer type;

    @ApiModelProperty(value = "会议ID", position = 11)
    private String rezId;

    @ApiModelProperty(value = "会议室ID", position = 12)
    private String conferenceConfigId;

    /** 外部人员 */
    @ApiModelProperty(value = "外部人员", position = 13)
    private List<OutsideUser> outsideUsers;


    public ConferenceMsgEvent(){}

    public ConferenceMsgEvent(String userIds){
        this.userIds = userIds;
    }
}
