package vip.xiaonuo.iot.modular.device.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 物联网-设备表Id参数
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
@Getter
@Setter
public class IotDeviceSwitchParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;

//    @ApiModelProperty(value = "开关操作（0关 1 开）", required = true)
//    private int switchType;

    /**
     * deviceType=5，6，7，8，9，10时可传
     */
    @ApiModelProperty(value = "配置开关第1路的通断电状态(0=断电，1=通电)", position = 9)
    private Integer switchPower1;

    /**
     * deviceType=6，7，9，10时可传
     */
    @ApiModelProperty(value = "配置开关第1路的通断电状态(0=断电，1=通电)", position = 10)
    private Integer switchPower2;

    /**
     * deviceType=7，10时可传
     */
    @ApiModelProperty(value = "配置开关第1路的通断电状态(0=断电，1=通电)", position = 11)
    private Integer switchPower3;
}
