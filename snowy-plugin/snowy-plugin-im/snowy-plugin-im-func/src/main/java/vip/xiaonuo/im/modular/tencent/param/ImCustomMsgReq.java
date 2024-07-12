package vip.xiaonuo.im.modular.tencent.param;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 10:39
 */
public class ImCustomMsgReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 1：把消息同步到 From_Account 在线终端和漫游上；
     * 2：消息不同步至 From_Account；
     * 若不填写默认情况下会将消息存 From_Account 漫游
     * 选填
     */
    @ApiModelProperty(value = "消息同步标识")
    private Integer syncOtherMachine;

    /**
     * 消息发送方 UserID
     * 必填
     */
    @ApiModelProperty(value = "消息发送方UserId", required = true)
    private String fromAccount;

    /**
     * 消息接收方 UserID
     * 选填
     */
    @ApiModelProperty(value = "消息接收方UserId", required = true)
    private String toAccount;

    /**
     * 默认为 0，表示消息存历史聊天记录
     * 1表示消息不存历史聊天记录，即发送消息时，若接收方在线，则能收到此消息，
     * 若接收方不在线，则收不到该消息。适用于实现一些实时状态类的功能，如实现“对方正在输入”功能
     */
    @ApiModelProperty(value = "消息的处理方式")
    private Integer onlineOnlyFlag;

    /**
     * 消息序列号，后台会根据该字段去重及进行同秒内消息的排序，详细规则请看本接口的功能说明。若不填该字段，则由后台填入随机数。
     * 选填
     */
    @ApiModelProperty(value = "消息序列号")
    private Integer msgSeq;

    /**
     * 消息随机数，后台用于同一秒内的消息去重。请确保该字段填的是随机数
     * 必填
     */
    @ApiModelProperty(value = "消息随机数" ,required = true)
    private Integer msgRandom;

    /**
     * 消息时间戳，UNIX 时间戳（单位：秒）
     * 选填
     */
    @ApiModelProperty("MsgTimeStamp")
    private Integer msgTimeStamp;

    /**
     * 消息回调禁止开关，只对本条消息有效，ForbidBeforeSendMsgCallback 表示禁止发消息前回调，
     * ForbidAfterSendMsgCallback 表示禁止发消息后回调
     * 选填
     */
    @ApiModelProperty(value = "消息回调",required = false)
    private Object[] forbidCallbackControl;

    /**
     * 消息发送控制选项，是一个 String 数组，只对本条消息有效。"NoUnread"表示该条消息不计入未读数。
     * "NoLastMsg"表示该条消息不更新会话列表。"WithMuteNotifications"表示该条消息的接收方对发送方设置的免打扰选项生效（默认不生效）。
     * 示例："SendMsgControl": ["NoUnread","NoLastMsg","WithMuteNotifications"]
     * 选填
     */
    @ApiModelProperty(value = "消息发送控制",required = false)
    private Object[] sendMsgControl;

    /**
     * 消息内容，具体格式请参考 消息格式描述（注意，一条消息可包括多种消息元素，MsgBody 为 Array 类型）
     * 必填
     */
    @ApiModelProperty("MsgBody")
    private List<MsgBody> msgBody;

    /**
     * TIM 消息对象类型，目前支持的消息对象包括：
     * TIMTextElem（文本消息）
     * TIMLocationElem（位置消息）
     * TIMFaceElem（表情消息）
     * TIMCustomElem（自定义消息）
     * TIMSoundElem（语音消息）
     * TIMImageElem（图像消息）
     * TIMFileElem（文件消息）
     * TIMVideoFileElem（视频消息）
     * 必填
     */
    @ApiModelProperty("MsgType")
    private String msgType;

    /**
     * 对于每种 MsgType 用不同的 MsgContent 格式，具体可参考
     * 必填
     */
    @ApiModelProperty("MsgContent")
    private Object msgContent;

    /**
     * 消息自定义数据（云端保存，会发送到对端，程序卸载重装后还能拉取到）
     * 选填
     */
    @ApiModelProperty("CloudCustomData")
    private String cloudCustomData;

    /**
     * 离线推送信息配置，具体可参考
     * 选填
     */
    @ApiModelProperty("OfflinePushInfo")
    private Object offlinePushInfo;

    /**
     * 自定义消息数据。 不作为 APNs 的 payload 字段下发，故从 payload 中无法获取 Data 字段
     */
    @ApiModelProperty("Data")
    private String data;
    /**
     * 说明
     * 当消息中只有一个 TIMCustomElem 自定义消息元素时，如果 Desc 字段和 OfflinePushInfo.Desc 字段都不填写，
     * 将收不到该条消息的离线推送，需要填写 OfflinePushInfo.Desc 字段才能收到该消息的离线推送。
     */
    @ApiModelProperty("Desc")
    private String desc;
    /**
     * 扩展字段。当接收方为 iOS 系统且应用处在后台时，此字段作为 APNs 请求包 Payloads 中的 Ext 键值下发，Ext 的协议格式由业务方确定，APNs 只做透传。
     */
    @ApiModelProperty("Ext")
    private String ext;
    /**
     * 自定义 APNs 推送铃音。
     */
    @ApiModelProperty("Sound")
    private String sound;

}
