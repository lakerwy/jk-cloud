package vip.xiaonuo.biz.modular.car.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;
import vip.xiaonuo.biz.modular.car.entity.EnterFees;
import vip.xiaonuo.biz.modular.car.entity.VehicleFees;

import java.util.List;

/**
 * @author yong.ye
 * @date 2023/11/9 19:14
 */

@Getter
@Setter
public class FeeChartViewModel {

    /** 列表结果集【费用统计表】 */
    @ApiModelProperty(value = "列表结果集【费用统计表】", position = 1)
    private Page<VehicleFees> feeResultDto ;

    /** 列表结果集【补登统计表】 */
    @ApiModelProperty(value = "列表结果集【补登统计表】", position = 1)
    private Page<EnterFees> enterResultDto ;

    /** 饼图数据集(按照车辆统计) */
    @ApiModelProperty(value = "饼图数据集(按照车辆统计)", position = 1)
    private List<PieChartData<Decimal>> pieChartCarDatas ;

    /** 饼图数据集（按照费用类别） */
    @ApiModelProperty(value = "饼图数据集（按照费用类别）", position = 1)
    private List<PieChartData<Decimal>> pieChartFeeDatas ;

    /** 统计值合计结果集 */
    @ApiModelProperty(value = "统计值合计结果集", position = 1)
    private TabTotalData<Decimal> tabTotalData ;
}
