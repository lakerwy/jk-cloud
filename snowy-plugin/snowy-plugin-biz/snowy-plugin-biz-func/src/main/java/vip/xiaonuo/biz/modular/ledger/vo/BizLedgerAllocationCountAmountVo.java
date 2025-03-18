package vip.xiaonuo.biz.modular.ledger.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 拨付台账历史数据Vo
 *
 * @author pzh
 * @date  2025/03/18 10:43
 **/
@Getter
@Setter
public class BizLedgerAllocationCountAmountVo {

    /** 回款累计 */
    @ApiModelProperty(value = "回款累计（本次回款求和）", position = 1)
    private BigDecimal receiveAmountTotal;

    /** 管理费累计 */
    @ApiModelProperty(value = "管理费累计（本次管理费求和）", position = 2)
    private BigDecimal manageAmountTotal;

    /** 回款累计+管理费累计 */
    @ApiModelProperty(value = "回款累计+管理费累计", position = 3)
    private BigDecimal receiveAndManageAmountTotal;

}
