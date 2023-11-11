package vip.xiaonuo.biz.modular.car.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class H5CarRegisterFromDto {

    /** (派车单)用车申请单id（主键Id） */
    @ApiModelProperty(value = "(派车单)用车申请单id（主键Id）", position = 1)
    private String id ;

    /** 实际出发时间 */
    @ApiModelProperty(value = "实际出发时间", position = 1)
    private Date startTime ;

    /** 出发里程 */
    @ApiModelProperty(value = "出发里程", position = 1)
    private BigDecimal startMileage ;
}
