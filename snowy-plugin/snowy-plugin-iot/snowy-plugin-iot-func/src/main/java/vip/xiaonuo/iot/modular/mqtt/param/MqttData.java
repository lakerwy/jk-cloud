package vip.xiaonuo.iot.modular.mqtt.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 16:45
 */
@Data
public class MqttData {
    @ApiModelProperty(value = "消息ID", position = 1)
    private String  id;

    private int  ispush;

    private int pollTime;

    @ApiModelProperty(value = "设备列表", position = 1)
    private List<MqttDevice> devices;
}
