package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.exp.entity.BizFormCash;

import java.math.BigDecimal;


@Getter
@Setter
public class BizFormCashVo extends BizFormCash {

    /** 借支余额 (借支金额 - （审批中+审批通过)费用报销、差旅报销、归还登记 ）*/
    @ApiModelProperty(value = "借支余额(借支金额 - （审批中+审批通过)费用报销、差旅报销、归还登记 ）", position = 88)
    private BigDecimal cashBalance;

    /** 归还金额（审批通过合计） */
    @ApiModelProperty(value = "归还金额（审批通过合计）", position = 89)
    private BigDecimal backAmount;

    /** 未还金额 （借支金额-归还金额合计）*/
    @ApiModelProperty(value = "未还金额（借支金额-归还金额合计）", position = 90)
    private BigDecimal noBackAmount;

    /** 归还状态 */
    @ApiModelProperty(value = "归还状态", position = 91)
    private String backStatus;

}
