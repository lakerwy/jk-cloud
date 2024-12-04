package vip.xiaonuo.flw.modular.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 折线图
 *     /// xAxis: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
 *     /// data: [120, 132, 101, 134, 90, 230, 210]
 */
@Getter
@Setter
public class LineChart {

    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<String> xAxis ;

    /** 审批单量 */
    @ApiModelProperty(value = "审批单量")
    private List<Long> firstData ;

    /** 流程平均耗时（h） */
    @ApiModelProperty(value = "流程平均耗时（h）")
    private List<Double> secondData ;
}
