package vip.xiaonuo.biz.modular.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 柱状图(包括折线、柱混合)
 * xAxis: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
 * data1: [120, 132, 101, 134, 90, 230, 210]
 * data2: [120, 132, 101, 134, 90, 230, 210]
 */
@Getter
@Setter
public class BarChart {

    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<String> xAxis ;

    /** 柱图数据 多条柱形 */
    @ApiModelProperty(value = "柱图数据 多条柱形")
    private List<Integer> firstData ;

    /** 柱图数据 多条柱形 */
    @ApiModelProperty(value = "柱图数据 多条柱形")
    private List<Integer> secondData ;

    /** 柱图数据 多条柱形 */
    @ApiModelProperty(value = "柱图数据 多条柱形")
    private List<Integer> thirdData ;

}
