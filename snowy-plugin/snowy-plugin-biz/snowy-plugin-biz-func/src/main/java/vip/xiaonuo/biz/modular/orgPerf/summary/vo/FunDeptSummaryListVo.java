package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 职能部门考核-汇总列表参数
 *
 * @author pzh
 * @date  2025/06/30 14:24
 **/
@Getter
@Setter
public class FunDeptSummaryListVo {

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", position = 1)
    private String id;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String orgName;

    /** 考核类型（季度考核|年度考核） */
    @ApiModelProperty(value = "考核类型（季度考核|年度考核）", position = 5)
    private String assType;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 7)
    private String owner;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 9)
    private Integer assYear;

    /** 考核季度（例如：1） */
    @ApiModelProperty(value = "考核季度（例如：1）", position = 11)
    private Integer assSeason;

    /** KPI指标得分（累计） */
    @ApiModelProperty(value = "KPI指标得分", position = 13)
    private BigDecimal kpiScoreTotal;

    /** 预算执行得分 */
    @ApiModelProperty(value = "预算执行得分", position = 15)
    private BigDecimal budgetExecScore;

    /** 集团经营业绩得分 */
    @ApiModelProperty(value = "集团经营业绩得分", position = 17)
    private BigDecimal groupScore;

    /** 部门履职情况得分（累计） */
    @ApiModelProperty(value = "部门履职情况得分", position = 19)
    private BigDecimal orgDutyScore;

    /** 加分（累计） */
    @ApiModelProperty(value = "加分（累计）", position = 21)
    private BigDecimal addScoreTotal;

    /** 减分（累计） */
    @ApiModelProperty(value = "减分（累计）", position = 23)
    private BigDecimal minusScoreTotal;

    /** 绩效评估状态 */
    @ApiModelProperty(value = "绩效评估状态（null代表未开始）", position = 25)
    private String assStatus;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 27)
    private BigDecimal totalScore;

    /** 评定等级 */
    @ApiModelProperty(value = "评定等级", position = 29)
    private String assLevel;

    /** 评定系数 */
    @ApiModelProperty(value = "评定系数", position = 31)
    private String assFactor;

    /** 绩效评估-流程实例id */
    @ApiModelProperty(value = "绩效评估-流程实例id", position = 33)
    private String assProcessInstanceId;

}
