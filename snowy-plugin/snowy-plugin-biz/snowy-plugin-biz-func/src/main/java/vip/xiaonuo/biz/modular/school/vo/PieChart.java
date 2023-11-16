package vip.xiaonuo.biz.modular.school.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  饼图
 *     ///   data: [
 *     ///    { value: 1048, name: 'Search Engine' },
 *     ///    { value: 735, name: 'Direct' },
 *     ///    { value: 580, name: 'Email' },
 *     ///    { value: 484, name: 'Union Ads' },
 *     ///    { value: 300, name: 'Video Ads' } ]
 */
@Getter
@Setter
public class PieChart {

    /** X轴信息 */
    @ApiModelProperty(value = "X轴信息")
    private List<Pie> data;
}
