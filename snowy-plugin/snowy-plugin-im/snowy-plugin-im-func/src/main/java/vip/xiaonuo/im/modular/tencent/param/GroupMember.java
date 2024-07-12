package vip.xiaonuo.im.modular.tencent.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 14:55
 * 群成员资料
 */
@Setter
@Getter
public class GroupMember {

    @ApiModelProperty(value = "群成员 ID", position = 4)
    private String memberAccount;

    /**
     * 群内身份，包括 Owner 群主、Admin 群管理员以及 Member 群成员
     */
    @ApiModelProperty(value = "群内身份", position = 4)
    private String role;

    @ApiModelProperty(value = "入群时间", position = 4)
    private Integer joinTime;

    @ApiModelProperty(value = "该成员当前已读消息 ", position = 4)
    private Integer msgSeq;

    /**
     * 消息接收选项，包括如下几种：
     * AcceptAndNotify 表示接收并提示
     * AcceptNotNotify 表示接收不提示（不会触发离线推送）
     * Discard 表示屏蔽群消息（不会向客户端推送消息）
     * AcceptNotNotifyExceptAt 表示接收并提示 at 消息（仅 at 消息触发离线推送，其他消息不触发）
     */
    @ApiModelProperty(value = "消息接收选项", position = 4)
    private String msgFlag;

    @ApiModelProperty(value = "最后发送消息的时间", position = 4)
    private Integer lastSendMsgTime;

    @ApiModelProperty(value = "群名片", position = 4)
    private String nameCard;

    @ApiModelProperty(value = "禁言状态", position = 4)
    private String muteUntil;

    @ApiModelProperty(value = "在线状态", position = 4)
    private String onlineStatus;
}
