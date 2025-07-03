package vip.xiaonuo.biz.modular.orgPerf.summary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 公司绩效汇总列表参数
 *
 * @author pzh
 * @date  2025/06/17 14:48
 **/
@Getter
@Setter
public class CompanySummaryDetailParam {

    @ApiModelProperty(value = "目标登记主键ID", required = true, position = 1)
    @NotBlank(message = "目标登记主键ID不能为空")
    private String id;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 2)
    private String assName;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", required = true,  position = 3)
    @NotNull(message = "考核年度assYear不能为空")
    private Integer assYear;

    /** 月份（例如：1） */
    @ApiModelProperty(value = "月份（例如：1）", position = 4)
    private Integer month;

}
