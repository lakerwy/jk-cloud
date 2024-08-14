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
    private Integer deviceType;

    private Integer socketRestart;

    private Integer socketAllowStart;

    @ApiModelProperty(value = "配置开关第1路的通断电状态")
    private Integer switchPower1;

    @ApiModelProperty(value = "配置开关第2路的通断电状态")
    private Integer switchPower2;

    @ApiModelProperty(value = "配置开关第3路的通断电状态")
    private Integer switchPower3;

    private Integer switchRestate1;

    private Integer switchRestate2;

    private Integer switchRestate3;

    private Integer switchAllowStart;

//    private int hornVoice;
//
//    private int hornVolume;
//
//    private int lockOpenControl;
//
//    private int lockWorkMode;
//
//    private int lockCloseTime;

    private Integer isOnline;

}
