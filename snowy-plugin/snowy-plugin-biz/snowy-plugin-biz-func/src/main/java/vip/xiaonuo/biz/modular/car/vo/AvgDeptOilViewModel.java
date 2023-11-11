package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/11/10 9:39
 *
 * 各部门各车辆油耗分摊视图模型
 */

@Getter
@Setter
public class AvgDeptOilViewModel {

    /** 区间 */
    @ApiModelProperty(value = "区间")
    private String quJian;

    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String dept;

    /** 车辆id */
    @ApiModelProperty(value = "车辆id")
    public String vehicleId;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码")
    private String licenseNo;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码")
    private String oilCardNumber;

    /** 用车里程 */
    @ApiModelProperty(value = "用车里程")
    private BigDecimal subApplyMileages;

    /** 平均油耗 */
    @ApiModelProperty(value = "平均油耗")
    private BigDecimal avgOil;

    /** 部门分摊油耗费用 */
    @ApiModelProperty(value = "部门分摊油耗费用")
    private BigDecimal subAmount;
}
