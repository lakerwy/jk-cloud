package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.car.entity.ServiceRatings;

/**
 * @author yong.ye
 * @date 2023/11/9 8:35
 */

@Getter
@Setter
public class ServiceRatingsViewModel extends ServiceRatings {

    /** 流水号VehicleApply.SN */
    @ApiModelProperty(value = "流水号VehicleApply.SN", position = 8)
    private String vehicleApplySn;

    /** 用车人VehicleApply.Users */
    @ApiModelProperty(value = "用车人VehicleApply.Users", position = 8)
    private String vehicleApplyUsers;

    /** 指派车辆VehicleApply.Vehicle */
    @ApiModelProperty(value = "指派车辆VehicleApply.Vehicle", position = 8)
    private String vehicleApplyVehicle;

    /** 指派驾驶员VehicleApply.Driver */
    @ApiModelProperty(value = "指派驾驶员VehicleApply.Driver", position = 8)
    private String vehicleApplyDriver;

}
