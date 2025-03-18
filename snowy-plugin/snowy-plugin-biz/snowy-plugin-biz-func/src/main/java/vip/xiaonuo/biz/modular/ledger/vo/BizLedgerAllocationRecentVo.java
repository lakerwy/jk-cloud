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
public class BizLedgerAllocationRecentVo {

    /** 累计回款金额 */
    @ApiModelProperty(value = "累计回款金额", position = 12)
    private BigDecimal receiveAmountAcc;

}
