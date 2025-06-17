package vip.xiaonuo.biz.modular.orgPerf.finance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务情况编辑参数
 *
 * @author pzh
 * @date  2025/06/17 11:47
 **/
@Getter
@Setter
public class BizFinanceEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 3)
    private String orgId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private String year;

    /** 月份（例如：01） */
    @ApiModelProperty(value = "月份（例如：01）", position = 5)
    private String month;

    /** 累计回款额 */
    @ApiModelProperty(value = "累计回款额", position = 6)
    private BigDecimal recAmountAcc;

    /** 累计净利润 */
    @ApiModelProperty(value = "累计净利润", position = 7)
    private BigDecimal profitAmountAcc;

    /** 预算执行率（成本） */
    @ApiModelProperty(value = "预算执行率（成本）", position = 8)
    private BigDecimal budgetExecRate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 10)
    private String attach;

}
