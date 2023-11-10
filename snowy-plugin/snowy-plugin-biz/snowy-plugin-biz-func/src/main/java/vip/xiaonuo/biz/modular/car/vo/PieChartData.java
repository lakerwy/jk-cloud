package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/10 9:12
 *
 * 饼图数据结构
 */

@Getter
@Setter
public class PieChartData<T> {

    /** 分组名称 */
    @ApiModelProperty(value = "分组名称", position = 1)
    private String name;

    /** 分组值 */
    @ApiModelProperty(value = "分组值", position = 1)
    private T value;

}
