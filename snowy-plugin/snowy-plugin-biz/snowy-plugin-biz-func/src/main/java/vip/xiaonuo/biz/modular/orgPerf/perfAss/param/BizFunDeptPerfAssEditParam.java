package vip.xiaonuo.biz.modular.orgPerf.perfAss.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 职能部门-绩效评估编辑参数
 *
 * @author pzh
 * @date  2025/06/30 10:41
 **/
@Getter
@Setter
public class BizFunDeptPerfAssEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 关联-目标登记主键ID */
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 2)
    private String goalId;

    /** KPI指标得分（合计） */
    @ApiModelProperty(value = "KPI指标得分（合计）", position = 3)
    private BigDecimal kpiScoreTotal;

    /** 预算执行得分 */
    @ApiModelProperty(value = "预算执行得分", position = 4)
    private BigDecimal budgetExecScore;

    /** 集团经营业绩得分 */
    @ApiModelProperty(value = "集团经营业绩得分", position = 5)
    private BigDecimal groupScore;

    /** 部门履职情况得分 */
    @ApiModelProperty(value = "部门履职情况得分", position = 6)
    private BigDecimal orgDutyScore;

    /** 实际加分分值（合计） */
    @ApiModelProperty(value = "实际加分分值（合计）", position = 7)
    private BigDecimal addScoreTotal;

    /** 实际减分分值（合计） */
    @ApiModelProperty(value = "实际减分分值（合计）", position = 8)
    private BigDecimal minusScoreTotal;

    /** 总结 */
    @ApiModelProperty(value = "总结", position = 9)
    private String summary;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 11)
    private BigDecimal totalScore;

    /** 评定等级 */
    @ApiModelProperty(value = "评定等级", position = 12)
    private String assLevel;

    /** 评定系数 */
    @ApiModelProperty(value = "评定系数", position = 13)
    private String assFactor;

    /** 上级评价意见 */
    @ApiModelProperty(value = "上级评价意见", position = 14)
    private String superiorOpinion;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 22)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 23)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 24)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 25)
    private String processInstanceId;

}
