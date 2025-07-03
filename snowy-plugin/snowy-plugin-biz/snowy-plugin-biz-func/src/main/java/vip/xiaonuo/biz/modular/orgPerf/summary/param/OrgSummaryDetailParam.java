package vip.xiaonuo.biz.modular.orgPerf.summary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 子公司及生产部门绩效汇总列表参数
 *
 * @author pzh
 * @date  2025/06/26 08:54
 **/
@Getter
@Setter
public class OrgSummaryDetailParam {

    @ApiModelProperty(value = "目标登记主键ID", required = true, position = 1)
    @NotBlank(message = "目标登记主键ID不能为空")
    private String id;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", required = true, position = 2)
    @NotBlank(message = "部门ID-orgId不能为空")
    private String orgId;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 3)
    private String assName;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", required = true, position = 5)
    @NotNull(message = "考核年度assYear不能为空")
    private Integer assYear;

    /** 月份（例如：1） */
    @ApiModelProperty(value = "月份（例如：1）", position = 7)
    private Integer month;

}
