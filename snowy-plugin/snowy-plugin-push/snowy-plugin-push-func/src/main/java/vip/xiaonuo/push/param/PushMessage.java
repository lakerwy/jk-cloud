package vip.xiaonuo.push.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class PushMessage {

    @ApiModelProperty(value = "发送者ID", required = true)
    @NotBlank(message = "发送者ID")
    public String sender;

    @ApiModelProperty(value = "发送者姓名", required = true)
    @NotBlank(message = "发送者姓名")
    public String senderName;

    @ApiModelProperty(value = "会话类型", required = true)
    @NotBlank(message = "会话类型")
    public int convType;

    @ApiModelProperty(value = "接收用户ID", required = true)
    @NotBlank(message = "接收用户ID")
    public String target;

    @ApiModelProperty(value = "接收用户名称", required = true)
    @NotBlank(message = "接收用户名称")
    public String targetName;

    public String userId;

    @ApiModelProperty(value = "会话线路，缺省为0")
    @NotBlank(message = "会话线路，缺省为0")
    public int line;
    public int cntType;

    @ApiModelProperty(value = "消息时间", required = true)
    @NotBlank(message = "消息时间")
    public long serverTime;

    //消息的类型，普通消息通知栏；voip要透传。 0 普通消息；1 voip消息。在支持透传的系统上，voip消息用透传
    @ApiModelProperty(value = "消息的类型", required = true)
    @NotBlank(message = "消息的类型")
    public int pushMessageType;
    /**
     * 推送类型
     * Android 推送分为小米/华为/魅族等
     * iOS 分别为开发和发布， 推送类型，android推送分为小米/华为/魅族等。ios分别为开发和发布。
     */
    @ApiModelProperty(value = "推送类型", required = true)
    @NotBlank(message = "推送类型")
    public int pushType;

    @ApiModelProperty(value = "消息推送内容", required = true)
    @NotBlank(message = "消息推送内容")
    public String pushContent;

    @ApiModelProperty(value = "消息推送数据", required = false)
    @NotBlank(message = "消息推送数据")
    public String pushData;

    @ApiModelProperty(value = "服务器端没有接收下来的消息数（只计算计数消息）", required = true)
    @NotBlank(message = "服务器端没有接收下来的消息数")
    public int unReceivedMsg;

    //消息提醒类型，0，没提醒；1，提醒了当前用户；2，提醒了所有人
    @ApiModelProperty(value = "消息提醒类型", required = false)
    @NotBlank(message = "消息提醒类型")
    public int mentionedType;

    @ApiModelProperty(value = "应用包名", required = false)
    @NotBlank(message = "应用包名")
    public String packageName;

    @ApiModelProperty(value = "设备token",required = true)
    @NotBlank(message = "设备token")
    public String deviceToken;

    @ApiModelProperty(value = "voipDeviceToken")
    @NotBlank(message = "voipDeviceToken")
    public String voipDeviceToken;

    @ApiModelProperty(value = "是否要隐藏推送详情")
    @NotBlank(message = "是否要隐藏推送详情")
    public boolean isHiddenDetail;

    @ApiModelProperty(value = "接收者的手机语言")
    @NotBlank(message = "接收者的手机语言")
    public String language;

    public long messageId;
}
