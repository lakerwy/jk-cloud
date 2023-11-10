package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/10 10:03
 *
 * 当前申请时间区间可用车辆和司机信息
 */

@Getter
@Setter
public class AvailableInfo {

    /// <summary>
    /// 可用车辆
    /// </summary>
    /** 当前申请时间区间可用车辆 */
    @ApiModelProperty(value = "当前申请时间区间可用车辆")
    private List<VehicleInfoDto> availableVehicles ;

    /// <summary>
    /// 可用司机
    /// </summary>
    /** 当前申请时间区间可用司机 */
    @ApiModelProperty(value = "当前申请时间区间可用司机")
    private List<DriverInfoDto> availableDrivers ;
}
