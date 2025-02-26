package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class LjNoBackVo {

    /** 借支人id */
    @ApiModelProperty(value = "借支人id", position = 1)
    private String advanceId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 2)
    private String advanceName;

    /** 累计未还金额 */
    @ApiModelProperty(value = "累计未还金额", position = 3)
    private BigDecimal ljWhAmount;

}
