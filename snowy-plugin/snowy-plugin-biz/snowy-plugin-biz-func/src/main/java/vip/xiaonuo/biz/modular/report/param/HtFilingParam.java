package vip.xiaonuo.biz.modular.report.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/7/25 17:22
 */

@Getter
@Setter
public class HtFilingParam {

    /** 统计开始时间 */
    @ApiModelProperty(value = "开始时间", position = 1)
    private String startDate;

    /** 统计结束时间 */
    @ApiModelProperty(value = "结束时间", position = 2)
    private String endDate;

    /** 伙伴类型：分公司、零星...  */
    @ApiModelProperty(value = "伙伴类型：分公司、零星...", position = 6)
    private String partnerType;

    /** 报表类型：
     * 1、近一年收入合同清单（盖章日期2022.3.31-2023.3.31）
     * 2、超过3个月未归档合同数统计表（盖章日期2017.1.1-2023.3.31）
     * 3、统计合并
     */
    @ApiModelProperty(value = "报表类型：1、分公司近一年收入合同清单（盖章日期2022.3.31-2023.3.31）  2、分公司超过3个月未归档合同数统计表（盖章日期2017.1.1-2023.3.31）", required = true, position = 7)
    @NotBlank(message = "reportType不能为空")
    private String reportType;

}
