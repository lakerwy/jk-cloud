package vip.xiaonuo.biz.modular.ledger.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 导入 拨付台账 集合
 */
@Getter
@Setter
public class ImportLedgerAllocation {

    @ApiModelProperty(value = "拨付台账 集合", position = 1)
    private List<BizLedgerAllocationAddParam> importData;
}
