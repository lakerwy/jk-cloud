package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/11/10 9:33
 *
 * 车辆平均油耗视图模型
 */

@Getter
@Setter
public class AvgVehicleOilViewModel {

    /** 区间 */
    @ApiModelProperty(value = "区间", position = 1)
    private String quJian;

    /** 车辆id */
    @ApiModelProperty(value = "车辆id", position = 1)
    private String vehicleId ;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 1)
    private String licenseNo ;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 1)
    private String oilCardNumber ;

    /** 油卡充值合计金额 */
    @ApiModelProperty(value = "油卡充值合计金额", position = 1)
    private BigDecimal subAmount ;

    /** 用车里程 */
    @ApiModelProperty(value = "用车里程", position = 1)
    private BigDecimal subApplyMileages ;

    /** 补登里程 */
    @ApiModelProperty(value = "补登里程", position = 1)
    private BigDecimal subEnterMileages;

    /** 总里程 = 补登里程+用车里程 */
    @ApiModelProperty(value = "总里程 = 补登里程+用车里程", position = 1)
    private BigDecimal totalMileages ;

    /** 平均油耗 */
    @ApiModelProperty(value = "平均油耗", position = 1)
    private BigDecimal avgOil ;
}
