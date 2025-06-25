package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 组织绩效考核-汇总列表参数
 *
 * @author pzh
 * @date  2025/06/25 15:42
 **/
@Getter
@Setter
public class OrgSummaryListVo {

    /** 目标登记主键ID */
    @ApiModelProperty(value = "目标登记主键ID", position = 1)
    private String id;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 3)
    private String orgName;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 5)
    private Integer assYear;

    /** 考核季度（例如：1） */
    @ApiModelProperty(value = "考核季度（例如：1）", position = 7)
    private Integer assSeason;

    /** 基本项得分（累计） */
    @ApiModelProperty(value = "基本项得分（累计）", position = 9)
    private BigDecimal itemScoreTotal;

    /** 部门履职情况得分（累计） */
    @ApiModelProperty(value = "部门履职情况得分（累计）", position = 11)
    private BigDecimal orgDutyScore;

    /** 加分（累计） */
    @ApiModelProperty(value = "加分（累计）", position = 13)
    private BigDecimal addScoreTotal;

    /** 减分（累计） */
    @ApiModelProperty(value = "减分（累计）", position = 17)
    private BigDecimal minusScoreTotal;

    /** 绩效评估状态 */
    @ApiModelProperty(value = "绩效评估状态（null代表未开始）", position = 19)
    private String assStatus;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 21)
    private BigDecimal totalScore;

    /** 评定等级 */
    @ApiModelProperty(value = "评定等级", position = 23)
    private String assLevel;

    /** 评定系数 */
    @ApiModelProperty(value = "评定系数", position = 25)
    private String assFactor;

}
