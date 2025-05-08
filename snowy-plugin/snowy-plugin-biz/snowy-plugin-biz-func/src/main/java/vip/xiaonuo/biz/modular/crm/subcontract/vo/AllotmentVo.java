package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 分包拨付情况Vo
 *
 * @author pzh
 * @date  2025/05/06 09:00
 **/
@Getter
@Setter
public class AllotmentVo extends AllotmentHisVo{

    /** 分包合同状态 */
    @ApiModelProperty(value = "分包合同状态（未完结|审核中|已完结）", position = 30)
    private String subcontractStatus;

    /** 分包结算流水号 */
    @ApiModelProperty(value = "分包合同结算流水号", position = 31)
    private String subcontractSettlementSn;

    /** 分包合同结算-流程实例id */
    @ApiModelProperty(value = "分包合同结算-流程实例id", position = 32)
    private String allotmentProcessInstanceId;

    /** 结算金额 */
    @ApiModelProperty(value = "结算金额", position = 33)
    private BigDecimal settlementAmountTotal;

}
