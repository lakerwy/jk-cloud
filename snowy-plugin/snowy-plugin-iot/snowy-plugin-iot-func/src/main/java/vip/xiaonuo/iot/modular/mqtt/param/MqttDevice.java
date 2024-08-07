package vip.xiaonuo.iot.modular.mqtt.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/2 16:51
 */
@Data
public class MqttDevice {
    @ApiModelProperty(value = "子设备ID")
    private String deviceCode;

    @ApiModelProperty(value = "子设备类型")
    private int deviceType;

    private int socketRestart;

    private int socketAllowStart;

    @ApiModelProperty(value = "配置开关第1路的通断电状态")
    private int switchPower1;

    @ApiModelProperty(value = "配置开关第2路的通断电状态")
    private int switchPower2;

    @ApiModelProperty(value = "配置开关第3路的通断电状态")
    private int switchPower3;

    private int switchRestate1;

    private int switchRestate2;

    private int switchRestate3;

    private int switchAllowStart;

    private int hornVoice;

    private int hornVolume;

    private int lockOpenControl;

    private int lockWorkMode;

    private int lockCloseTime;

    private Integer isOnline;

}
