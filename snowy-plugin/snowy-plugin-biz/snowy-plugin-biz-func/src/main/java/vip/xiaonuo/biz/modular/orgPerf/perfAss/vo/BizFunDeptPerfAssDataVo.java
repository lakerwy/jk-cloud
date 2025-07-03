package vip.xiaonuo.biz.modular.orgPerf.perfAss.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryDetailVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 职能部门-绩效评估-指标数据参数
 * @author pzh
 * @date  2025/06/25 15:01
 **/
@Getter
@Setter
public class BizFunDeptPerfAssDataVo {

    /** 基本项指标 */
    @ApiModelProperty(value = "kpi指标-集合", position = 1)
    private List<FunDeptSummaryDetailVo> kpiList;

    /** 预算执行-实际完成值 */
    @ApiModelProperty(value = "预算执行-实际完成值", position = 3)
    private BigDecimal budgetExecRate;

    /** 预算执行-得分 */
    @ApiModelProperty(value = "预算执行-得分", position = 5)
    private BigDecimal budgetExecScore;

    /** 集团经营业绩-完成值 */
    @ApiModelProperty(value = "集团经营业绩-完成值", position = 7)
    private BigDecimal companyComp;

    /** 集团经营业绩-得分 */
    @ApiModelProperty(value = "集团经营业绩-得分", position = 9)
    private BigDecimal companyScore;

    /** 部门履职情况-完成值 */
    @ApiModelProperty(value = "部门履职情况-完成值", position = 11)
    private BigDecimal orgDutyFinishValue;

    /** 部门履职情况-完成值 */
    @ApiModelProperty(value = "部门履职情况-得分", position = 13)
    private BigDecimal orgDutyScore;

    /** 基本项指标 */
    @ApiModelProperty(value = "加减分项-集合", position = 15)
    private List<BizOrgAssRecordVo> bizOrgAssRecordVoList;

}
