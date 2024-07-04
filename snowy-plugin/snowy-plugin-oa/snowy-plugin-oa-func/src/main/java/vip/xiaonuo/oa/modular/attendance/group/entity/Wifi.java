package vip.xiaonuo.oa.modular.attendance.group.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mayike
 * @version 1.0  wifi 打卡设置
 * @date 2024/7/3 8:47
 */
@Setter
@Getter
public class Wifi {

    @ApiModelProperty(value = "mac地址", position = 1)
    private String mac_addr;

    @ApiModelProperty(value = "wifi的ssid", position = 2)
    private String ssid;
}
