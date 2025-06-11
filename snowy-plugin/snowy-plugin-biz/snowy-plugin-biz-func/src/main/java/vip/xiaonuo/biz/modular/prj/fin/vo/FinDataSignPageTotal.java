package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class FinDataSignPageTotal {

    /** 转款金额 */
    @ApiModelProperty(value = "转款金额", position = 7)
    private BigDecimal zkAmount;
}
