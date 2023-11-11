package vip.xiaonuo.biz.modular.car.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.car.entity.VehicleInformation;

import java.math.BigDecimal;

@Getter
@Setter
public class H5VehicleDto extends VehicleInformation {

    /** 距下次保养里程（KM） = 参考保养里程 + 上次保养里程 - 总行驶里程 */
    @ApiModelProperty(value = "距下次保养里程（KM） = 参考保养里程 + 上次保养里程 - 总行驶里程", position = 7)
    private BigDecimal toReferenceMileage ;

    /** 平均里程 = 总里程/出车次数 */
    @ApiModelProperty(value = "平均里程 = 总里程/出车次数", position = 7)
    private BigDecimal avgMileage ;

    /** 平均油耗 = 总耗油/总里程 * 100 */
    @ApiModelProperty(value = "平均油耗 = 总耗油/总里程 * 100", position = 7)
    private BigDecimal avgOil ;

}
