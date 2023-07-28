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

//    /** 分公司合同超3个月未归档  ： 统计开始时间 */
//    @ApiModelProperty(value = "分公司合同超3个月未归档  ：开始时间", position = 3)
//    private String threeStartDate;
//
//    /** 分公司合同超3个月未归档  */
//    @ApiModelProperty(value = "分公司合同超3个月未归档  ：结束时间", position = 4)
//    private String threeEndDate;

    /** 责任单位Id：用于过滤 ，若未传，则默认返回全部分公司合同归档率统计列表 */
    @ApiModelProperty(value = "责任单位Id：用于过滤，若未传，则默认返回全部分公司合同归档率统计列表", position = 5)
    private String partnerId;

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
