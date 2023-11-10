package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/9 15:09
 */
@Getter
@Setter
public class DispatchCarParam {

    /** 派车单（用车申请单）id */
    @ApiModelProperty(value = "派车单（用车申请单）id", position = 1)
    private String vehicleApplyId;

    /** 车辆Id -- VehicleInformation.Id (外键) */
    @ApiModelProperty(value = "车辆Id -- VehicleInformation.Id (外键)", position = 1)
    private String vehicleId;

    /** 指派车辆 */
    @ApiModelProperty(value = "指派车辆", position = 1)
    private String vehicle;

    /** 指派驾驶员 */
    @ApiModelProperty(value = "指派驾驶员", position = 1)
    private String driver;

    /** 司机在系统中的userid */
    @ApiModelProperty(value = "司机在系统中的userid", position = 1)
    private String driverUserId;

    /** 派车后，通知的接收人集合 - 钉钉用户Id */
    @ApiModelProperty(value = "派车后，通知的接收人集合 - 钉钉用户Id", position = 1)
    private List<String> dingDingUserIds;
}
