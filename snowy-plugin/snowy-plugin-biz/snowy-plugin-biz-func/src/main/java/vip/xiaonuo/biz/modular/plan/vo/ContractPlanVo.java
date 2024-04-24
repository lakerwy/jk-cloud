package vip.xiaonuo.biz.modular.plan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.plan.entity.ContractBidDept;
import vip.xiaonuo.biz.modular.plan.entity.ContractPlan;

import java.util.List;

/**
 * 合约规划-信息实体Vo
 */


@Getter
@Setter
public class ContractPlanVo extends ContractPlan {

    /** 投标组织（项目牵头部门|项目协同部门） */
    @ApiModelProperty(value = "投标组织（项目牵头部门|项目协同部门）", position = 100)
    private List<ContractBidDept> bidDeptList;

    /** 合约规划-申报 - 项目承包方 */
    @ApiModelProperty(value = "合约规划-申报 - 项目承包方", position = 100)
    private List<ContractUnitVo> unitList;
}
