package vip.xiaonuo.iot.modular.device.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物联网-设备表添加参数
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
@Getter
@Setter
public class IotDeviceAddParam {

    /** 设备ID */
    @ApiModelProperty(value = "设备ID", position = 2)
    private String deviceCode;

    /** 设备描述 */
    @ApiModelProperty(value = "设备描述", position = 3)
    private String deviceName;

    /** 子设备类型 */
    @ApiModelProperty(value = "子设备类型", position = 4)
    private Integer deviceType;

    /** 设备描述 */
    @ApiModelProperty(value = "设备主题Topic", position = 5)
    private String deviceTopicId;

    @ApiModelProperty(value = "插座通断电状态(0=断电，1=通电)", position = 6)
    private int socketPower;

    /**
     * 0=插座重启后保持断电
     * 1=插座重启后保持通电
     * 2=保持插座重启前的通断电状态
     * 出厂默认值为2
     */
    @ApiModelProperty(value = "配置插座停电重启后是否保持供电输出", position = 7)
    private Integer socketRestart;

    /***
     * 0=关闭物理按键控制通断电
     * 1=允许物理按键控制通断电
     * 出厂默认为1
     */
    @ApiModelProperty(value = "是否允许插座面板上的物理按键控制通断电", position = 8)
    private int socketAllowStart;

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

    /** 配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电
     2=保持开关重启前第1路的通断电状态
     出厂默认值为2)
     deviceType=6，7，9，10时可传
     */
    @ApiModelProperty(value = "配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电 2=保持开关重启前第1路的通断电状态 出厂默认值为2)", position = 12)
    private Integer switchRestate1;

    /** 配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电
     2=保持开关重启前第1路的通断电状态
     出厂默认值为2)
     deviceType=6，7，9，10时可传
     */
    @ApiModelProperty(value = "配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电 2=保持开关重启前第1路的通断电状态 出厂默认值为2)", position = 13)
    private Integer switchRestate2;

    /** 配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电
     2=保持开关重启前第1路的通断电状态
     出厂默认值为2)
     deviceType=7，10时可传
     */
    @ApiModelProperty(value = "配置开关断电重启后第1路是否保持供电输出(0=开关第1路重启后保持断电 1=开关第1路重启后保持通电 2=保持开关重启前第1路的通断电状态 出厂默认值为2)", position = 14)
    private Integer switchRestate3;

    /** 插座终端自由通断电功能 */
    @ApiModelProperty(value = "是否允许开关面板上的物理按键控制通断电", position = 15)
    private Integer switchAllowStart;

    @ApiModelProperty(value = "分组",position = 22)
    private String groupId;

}
