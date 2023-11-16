package vip.xiaonuo.biz.modular.school.vo;


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
public class LineChart<T> {

    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<String> xAxis ;

    /** 数据 */
    @ApiModelProperty(value = "数据")
    private List<T> data ;
}
