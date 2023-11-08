package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceMsgEvent {

    /** 人员ID */
    @ApiModelProperty(value = "人员ID", position = 1)
    private String userId;

    /** 推送的消息 */
    @ApiModelProperty(value = "推送的消息", position = 2)
    private String msg;

    public ConferenceMsgEvent(){}

    public ConferenceMsgEvent(String userId,String msg){
        this.userId = userId;
        this.msg = msg;
    }
}
