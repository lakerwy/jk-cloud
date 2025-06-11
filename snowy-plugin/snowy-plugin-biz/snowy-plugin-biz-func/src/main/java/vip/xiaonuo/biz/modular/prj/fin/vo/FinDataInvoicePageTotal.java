package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinDataInvoicePageTotal {

    /** 开票金额 */
    @ApiModelProperty(value = "开票金额", position = 8)
    private BigDecimal kpAmount;
}
