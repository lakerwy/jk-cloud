package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class FinDataPpcPageTotal {

    /** 收款金额 */
    @ApiModelProperty(value = "收款金额", position = 7)
    private BigDecimal hkAmount;
}
