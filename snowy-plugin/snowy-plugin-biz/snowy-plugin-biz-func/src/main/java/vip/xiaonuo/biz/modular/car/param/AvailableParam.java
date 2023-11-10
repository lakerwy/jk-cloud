package vip.xiaonuo.biz.modular.car.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yong.ye
 * @date 2023/11/9 17:14
 */

@Getter
@Setter
public class AvailableParam {

    /** 预计用车 */
    @ApiModelProperty(value = "预计用车", position = 2)
    private String predictTransportTime;

    /** 预计还车 */
    @ApiModelProperty(value = "预计还车", position = 2)
    private String predictCarTime;

}
