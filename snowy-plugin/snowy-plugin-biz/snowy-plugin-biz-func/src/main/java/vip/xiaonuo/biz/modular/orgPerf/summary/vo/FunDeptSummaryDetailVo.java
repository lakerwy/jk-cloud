package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 职能部门考核-汇总详情参数
 *
 * @author pzh
 * @date  2025/06/30 14:24
 **/
@Getter
@Setter
public class FunDeptSummaryDetailVo {

    @ApiModelProperty(value = "kpi主键ID", position = 1)
    private String id;

    @ApiModelProperty(value = "绩效维度", position = 2)
    private String perfType;

    /** 指标名称 */
    @ApiModelProperty(value = "指标名称", position = 3)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 4)
    private String assRule;

    /** 计算公式 */
    @ApiModelProperty(value = "计算公式", position = 5)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 6)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 7)
    private BigDecimal goalValue;

    /** 计划完成时间 */
    @ApiModelProperty(value = "计划完成时间", position = 8)
    private Date planCompTime;

    /** 数据来源 */
    @ApiModelProperty(value = "数据来源", position = 9)
    private String fromDept;

    /** 数据来源-部门ID */
    @ApiModelProperty(value = "数据来源-部门ID", position = 10)
    private String fromDeptId;

    /** 实际完成情况 */
    @ApiModelProperty(value = "实际完成情况", position = 11)
    private String actualCompDesc;

    /** 成果附件 */
    @ApiModelProperty(value = "成果附件", position = 12)
    private String attach;

    /** 完成进度 */
    @ApiModelProperty(value = "完成进度", position = 13)
    private BigDecimal progress;

    /** 总分 */
    @ApiModelProperty(value = "考评得分", position = 21)
    private BigDecimal score;

    /** 评定等级 */
    @ApiModelProperty(value = "评分流程-状态（null代表进行中）", position = 23)
    private String ratingStatus;

    /** 绩效评估-流程实例id */
    @ApiModelProperty(value = "评分流程-实例id", position = 27)
    private String ratingProcessInstanceId;

}
