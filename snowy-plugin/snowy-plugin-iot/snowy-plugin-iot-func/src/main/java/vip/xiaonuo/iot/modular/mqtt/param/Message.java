package vip.xiaonuo.iot.modular.mqtt.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 16:44
 */
@Data
public class Message {

    @ApiModelProperty(value = "命令代码", position = 1,required = true)
    private Integer cmd;

    private Long time = System.currentTimeMillis();

    @ApiModelProperty(value = "设备ID", position = 2,required = true)
    private String clientId;

    private Integer state;

    @ApiModelProperty(value = "消息", position = 3,required = true)
    private MqttData data;
}
