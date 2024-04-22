package vip.xiaonuo.biz.modular.plan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.plan.entity.ContractUnit;
import vip.xiaonuo.biz.modular.plan.entity.ContractUnitCost;

import java.util.List;

/**
 * 合约规划-申报-项目承包方
 */


@Getter
@Setter
public class ContractUnitVo extends ContractUnit {

    /** 合约规划 - 申报 - 项目承包方 - 成本 */
    @ApiModelProperty(value = "合约规划 - 申报 - 项目承包方 - 成本", position = 100)
    private List<ContractUnitCost> unitCostList;

}
