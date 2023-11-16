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
public class LineChart2<T> {

    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<String> xAxis ;

    /** 第一组数据（左柱） */
    @ApiModelProperty(value = "第一组数据（左柱）")
    private List<T> firstData ;

    /** 第二组数据（左柱） */
    @ApiModelProperty(value = "第二组数据（左柱）")
    private List<T> secondData ;
}
