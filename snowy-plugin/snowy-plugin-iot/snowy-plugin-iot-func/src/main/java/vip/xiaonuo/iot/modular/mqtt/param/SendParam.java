package vip.xiaonuo.iot.modular.mqtt.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 15:30
 */
@Data
public class SendParam {

    /**
     * 客户端发消息的主题主题
     */
    @ApiModelProperty(value = "设备主题", position = 1,required = true)
    private String topic;

    /***
     * 消息内容
     */
    @ApiModelProperty(value = "主题消息", position = 2,required = true)
    private Message message;
}
