package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/9 9:20
 */

@Getter
@Setter
public class VehicleApplyDetailDto extends VehicleApply {

    /** 可用车辆 */
    @ApiModelProperty(value = "可用车辆", position = 6)
    private List<VehicleInfoDto> availableVehicles;

    /** 可用司机 */
    @ApiModelProperty(value = "可用司机", position = 6)
    private List<DriverInfoDto> availableDrivers;

}
