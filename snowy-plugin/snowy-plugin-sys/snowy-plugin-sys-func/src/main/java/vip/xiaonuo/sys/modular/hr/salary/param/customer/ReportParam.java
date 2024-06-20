package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 成本归属 - 分析报表
 */

@Getter
@Setter
public class ReportParam {
    /** 所属年月 */
    @ApiModelProperty(value = "所属年月",required = true)
    @NotBlank(message = "所属年月不能为空")
    private String yearMonth;

    /** 报表统计维度 社保|公积金|汇总 */
    @ApiModelProperty(value = "报表统计维度 社保|公积金|汇总",required = true)
    @NotBlank(message = "报表统计维度不能为空")
    private String reportType;
}
