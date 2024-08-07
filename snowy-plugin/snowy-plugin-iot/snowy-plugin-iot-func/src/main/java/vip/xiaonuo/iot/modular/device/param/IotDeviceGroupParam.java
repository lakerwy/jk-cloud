package vip.xiaonuo.iot.modular.device.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/6 11:01
 * 设置设置群组
 */
@Data
public class IotDeviceGroupParam {
    @ApiModelProperty(value = "群组ID",required = true)
    private String groupId;

    @ApiModelProperty(value = "设备id集合",required = true)
    private List<String> deviceIdList;
}
