package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/10 9:32
 *
 * 油耗分摊视图模型
 */

@Getter
@Setter
public class AvgOilChartViewModel {

    /** 各车平均油耗表 */
    @ApiModelProperty(value = "各车平均油耗表", position = 1)
    private List<AvgVehicleOilViewModel> avgOilCarResultDto ;

    /** 各部门各车油耗分摊表 */
    @ApiModelProperty(value = "各部门各车油耗分摊表", position = 1)
    private List<AvgDeptOilViewModel> avgOilDeptResultDto ;
}
