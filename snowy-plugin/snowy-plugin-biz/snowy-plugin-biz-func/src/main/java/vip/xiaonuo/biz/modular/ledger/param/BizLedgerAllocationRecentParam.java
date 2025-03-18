package vip.xiaonuo.biz.modular.ledger.param;

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
public class BizLedgerAllocationRecentParam {

    /** 合同ID */
    @ApiModelProperty(value = "合同ID", position = 3)
    private String hetongId;

}
