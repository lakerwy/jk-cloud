package vip.xiaonuo.iot.modular.device.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/8/6 11:49
 */
@Data
public class IotDeviceOperateParam {

    @ApiModelProperty(value = "群组ID 集合")
    private List<String> groupIds;

    @ApiModelProperty(value = "开关操作（0关 1 开）", required = true)
    private int switchType;

    @ApiModelProperty(value = "设备ID集合 （groupId 和 id 必须传一个）")
    private List<String> ids;
}
