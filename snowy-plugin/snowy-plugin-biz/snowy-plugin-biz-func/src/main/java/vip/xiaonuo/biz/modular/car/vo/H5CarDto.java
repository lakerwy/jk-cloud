package vip.xiaonuo.biz.modular.car.vo;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class H5CarDto {

    /** 车辆ID */
    @ApiModelProperty(value = "车辆ID", position = 1)
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

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 15)
    private String oilCardNumber;

    /** 车辆状态 可用 出车 维保 其他 */
    @ApiModelProperty(value = "车辆状态 可用 出车 维保 其他", position = 17)
    private String status;

}
