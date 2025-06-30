package vip.xiaonuo.biz.modular.orgPerf.goal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoalKpi;

import java.util.List;

/**
 * 职能部门绩效考核-详情vo
 *
 * @author pzh
 * @date  2025/06/27 08:53
 **/
@Getter
@Setter
public class BizFunDeptGoalVo extends BizFunDeptGoal {

    /** KPI指标 */
    @ApiModelProperty(value = "KPI指标-集合", position = 10)
    private List<BizFunDeptGoalKpi> kpiList;

}
