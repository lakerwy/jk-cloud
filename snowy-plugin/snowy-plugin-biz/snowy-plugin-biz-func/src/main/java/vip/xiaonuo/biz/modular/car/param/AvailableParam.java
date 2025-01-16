package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/9 17:14
 */

@Getter
@Setter
public class AvailableParam {

    @ApiModelProperty(value = "预计用车时间（格式：yyyy-MM-dd HH:mm:ss）", position = 1)
    private String predictTransportTime;

    @ApiModelProperty(value = "预计还车时间（格式：yyyy-MM-dd HH:mm:ss）", position = 2)
    private String predictCarTime;

    @ApiModelProperty(value = "车辆用车类型(公用|私用)", position = 3)
    private String useType;

    @ApiModelProperty(value = "司机派遣类型(公派|私派)", position = 4)
    private String dispatchType;

    @ApiModelProperty(value = "是否排除状态异常车辆（true-是，false-否）", position = 5)
    private boolean excludeUnavailableVehicle;

    @ApiModelProperty(value = "是否排除状态异常司机（true-是，false-否）", position = 6)
    private boolean excludeUnavailableDriver;
}
