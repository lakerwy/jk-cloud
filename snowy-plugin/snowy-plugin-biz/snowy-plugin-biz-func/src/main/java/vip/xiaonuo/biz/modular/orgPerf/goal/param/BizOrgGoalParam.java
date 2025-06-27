package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子公司及生产部门绩效考核-目标登记条件查询参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizOrgGoalParam {

    /** 考核部门（子公司|生产部门） */
    @ApiModelProperty(value = "考核部门（子公司|生产部门）", required = true, position = 2)
    @NotBlank(message = "考核部门assOrg必填")
    private String assOrg;

    /** 考核类型（季度考核|年度考核） */
    @ApiModelProperty(value = "考核类型（季度考核|年度考核）", required = true, position = 3)
    @NotBlank(message = "考核类型assType必填")
    private String assType;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", required = true, position = 5)
    @NotBlank(message = "部门ID-orgId必填")
    private String orgId;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）", position = 9)
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）", position = 10)
    private Integer assSeason;

}
