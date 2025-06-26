package vip.xiaonuo.biz.modular.orgPerf.summary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子公司及生产部门绩效汇总列表参数
 *
 * @author pzh
 * @date  2025/06/25 14:48
 **/
@Getter
@Setter
public class OrgSummaryListParam {

    /** 考核部门（子公司|生产部门） */
    @ApiModelProperty(value = "考核部门（子公司|生产部门）", required = true, position = 1)
    @NotBlank(message = "考核部门assOrg必填")
    private String assOrg;

    /** 考核维度（季度考核|年度考核） */
    @ApiModelProperty(value = "考核维度（季度考核|年度考核）", required = true, position = 3)
    @NotBlank(message = "考核维度assPeriod必填")
    private String assPeriod;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 5)
    private String orgId;

    /** 部门ID */
    @ApiModelProperty(value = "部门名称", position = 7)
    private String orgName;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）", position = 9)
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）", position = 11)
    private Integer assSeason;

    /** 是否评分 */
    @ApiModelProperty(value = "是否评分（是|否）", position = 13)
    private String isAss;

}
