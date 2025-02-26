package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BizFormCashTotal {

    /** 借支金额合计（元） */
    @ApiModelProperty(value = "借支金额合计（元）", position = 1)
    private BigDecimal advanceAmountTotal;

    /** 借支余额合计 (借支金额 - （审批中+审批通过)费用报销、差旅报销、归还登记 ）*/
    @ApiModelProperty(value = "借支余额合计", position = 2)
    private BigDecimal cashBalanceTotal;

    /** 归还金额合计 （审核通过 费用报销、差旅报销、归还登记） */
    @ApiModelProperty(value = "归还金额合计", position = 3)
    private BigDecimal backAmountTotal;

    /** 未还金额合计 （借支金额-归还金额合计）*/
    @ApiModelProperty(value = "未还金额合计", position = 4)
    private BigDecimal noBackAmountTotal;

}
