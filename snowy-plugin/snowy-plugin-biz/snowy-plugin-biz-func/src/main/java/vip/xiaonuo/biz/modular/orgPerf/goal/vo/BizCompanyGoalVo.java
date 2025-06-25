package vip.xiaonuo.biz.modular.orgPerf.goal.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

/**
 * 公司绩效考核-详情vo
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Getter
@Setter
public class BizCompanyGoalVo extends BizCompanyGoal {

    /** KPI指标 */
    @ApiModelProperty(value = "KPI指标-集合", position = 10)
    private List<BizCompanyGoalKpi> kpiList;

}
