package vip.xiaonuo.iot.modular.device.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 物联网-设备表实体
 *
 * @author mayike
 * @date  2024/08/05 09:51
 **/
@Getter
@Setter
@TableName("IOT_DEVICE")
public class IotDevice {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

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
    @ApiModelProperty(value = "网关TopicId", position = 5)
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

    /**
     * 1=欢迎语，2=90分钟提醒，3=20分钟提醒，4=电源即将关闭
     */
    @ApiModelProperty(value = "喇叭播报内容", position = 16)
    private Integer hornVoice;

    @ApiModelProperty(value = "喇叭音量", position = 17)
    private Integer hornVolume;

    @ApiModelProperty(value = "控制智能门锁或门禁一体机进行开关门动作", position = 18)
    private Integer lockOpenControl;

    @ApiModelProperty(value = "配置智能门锁或门禁一体机的常开常闭模式", position = 19)
    private Integer lockWorkMode;

    @ApiModelProperty(value = "配置智能门锁或智能门禁开门后的延迟关门时间", position = 20)
    private Integer lockCloseTime;


    @ApiModelProperty(value = "是否在线(0离线，1在线)",position = 21)
    private Integer isOnline;

    @ApiModelProperty(value = "分组",position = 22)
    private String groupId;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 30)
    private String tenantId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 31)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 32)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 33)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 34)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 35)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 36)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
//
//    @ApiModelProperty(value = "开关状态", position = 37)
//    @TableField(exist = false)
//    private Integer switchType;

    @ApiModelProperty(value = "群组名称",position = 37)
    @TableField(exist = false)
    private String groupName;

    @ApiModelProperty(value = "网关名称",position = 38)
    @TableField(exist = false)
    private String topicName;


    public Integer getSwitchPower1() {
        return switchPower1;
    }

    public Integer getSwitchPower2() {
        if (deviceType == 8){
            return null;
        }
        return switchPower2;
    }

    public Integer getSwitchPower3() {
        if (deviceType == 8 || deviceType == 9){
            return null;
        }
        return switchPower3;
    }
}
