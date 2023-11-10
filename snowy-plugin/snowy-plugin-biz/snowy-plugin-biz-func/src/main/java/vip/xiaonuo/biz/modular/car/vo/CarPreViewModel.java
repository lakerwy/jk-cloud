package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;

import java.util.Date;
import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/10 9:53
 *
 * 用车预定
 */

@Getter
@Setter
public class CarPreViewModel {

    /** 车辆id */
    @ApiModelProperty(value = "车辆id", position = 1)
    private String id;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 6)
    private String licenseNo;

    /** 车辆品牌 */
    @ApiModelProperty(value = "车辆品牌", position = 7)
    private String vehicleBrand;

    /** 车辆类型 */
    @ApiModelProperty(value = "车辆类型", position = 8)
    private String vehicleType;

    /** 乘坐人数 */
    @ApiModelProperty(value = "乘坐人数", position = 9)
    private String seats;

    /** 车辆颜色 */
    @ApiModelProperty(value = "车辆颜色", position = 11)
    private String color;

    /** 适用燃油标号 92 95 98 */
    @ApiModelProperty(value = "适用燃油标号 92 95 98", position = 14)
    private String fuel;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 15)
    private String oilCardNumber;

    /** 照片 */
    @ApiModelProperty(value = "照片", position = 22)
    private String photo;

    /** 行驶证有效期 */
    @ApiModelProperty(value = "行驶证有效期", position = 23)
    private Date licenseValid;

    /** 当前预定日期下，已存在的派车单 */
    @ApiModelProperty(value = "当前预定日期下，已存在的派车单", position = 23)
    public List<VehicleApply> preVehicleApplyData ;


}
