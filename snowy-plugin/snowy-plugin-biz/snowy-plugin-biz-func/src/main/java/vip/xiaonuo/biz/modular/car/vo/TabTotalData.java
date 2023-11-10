package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/10 9:17
 *
 * 统计值合计数据结构
 */
@Getter
@Setter
public class TabTotalData<T> {

    /** 用车总里程|用车总费用 */
    @ApiModelProperty(value = "用车总里程|用车总费用", position = 1)
    private T value1 ;

    /** 补登总里程|补登总费用 */
    @ApiModelProperty(value = "补登总里程|补登总费用", position = 1)
    private T value2 ;

    /** 合计总里程|合计总费用 */
    @ApiModelProperty(value = "合计总里程|合计总费用", position = 1)
    private T sumValue ;
}
