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
public class FunDeptSummaryListParam {

    /** 考核类型（季度考核|年度考核） */
    @ApiModelProperty(value = "考核类型（季度考核|年度考核）", required = true, position = 3)
    @NotBlank(message = "考核类型assType必填")
    private String assType;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 5)
    private String orgId;

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
