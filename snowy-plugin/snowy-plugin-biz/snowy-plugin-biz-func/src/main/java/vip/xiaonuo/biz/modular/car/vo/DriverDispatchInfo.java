package vip.xiaonuo.biz.modular.car.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDispatchInfo {

    /** 照片 */
    @ApiModelProperty(value = "已外出司机数量", position = 1)
    private Long dispatchDriverNum;

    /** 照片 */
    @ApiModelProperty(value = "可外出司机数量", position = 2)
    private Long availableDriverNum;

}
