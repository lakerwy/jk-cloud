package vip.xiaonuo.biz.modular.car.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;
import vip.xiaonuo.biz.modular.car.entity.EnterMileage;
import vip.xiaonuo.biz.modular.car.entity.VehicleApply;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/10 9:07
 */

@Getter
@Setter
public class VehicleChartViewModel {

    /** 列表结果集【用车统计表】 */
    @ApiModelProperty(value = "列表结果集【用车统计表】", position = 1)
    private Page<VehicleApply> applyResultDto ;

    /** 列表结果集【补登统计表】 */
    @ApiModelProperty(value = "列表结果集【补登统计表】", position = 1)
    private Page<EnterMileage> enterResultDto ;

    /** 饼图数据集(按照用车次数) */
    @ApiModelProperty(value = "饼图数据集(按照用车次数)", position = 1)
    private List<PieChartData<Long>> pieChartTimesDatas ;

    /** 饼图数据集（按照用车里程） */
    @ApiModelProperty(value = "饼图数据集（按照用车里程）", position = 1)
    public List<PieChartData<Decimal>> pieChartMileagesDatas ;

    /** 统计值合计结果集 */
    @ApiModelProperty(value = "统计值合计结果集", position = 1)
    public TabTotalData<Decimal> tabTotalData ;

}
