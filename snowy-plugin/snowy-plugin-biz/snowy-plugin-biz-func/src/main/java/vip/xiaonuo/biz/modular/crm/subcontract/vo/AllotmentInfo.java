package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 *
 * @author pzh
 * @date  2025/05/06 09:00
 **/
@Getter
@Setter
public class AllotmentInfo {

    @ApiModelProperty(value = "分包合同编号")
    private String subcontractNumber;

    @ApiModelProperty(value = "付款日期")
    private String paymentDate;

    @ApiModelProperty(value = "出纳拨付金额")
    private BigDecimal payCashier;
}
