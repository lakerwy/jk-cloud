package vip.xiaonuo.sys.modular.hr.financialSheet.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务状况表编辑参数
 *
 * @author pzh
 * @date  2024/10/18 15:53
 **/
@Getter
@Setter
public class FinancialSheetEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 2)
    private String year;

    /** 注册资金 */
    @ApiModelProperty(value = "注册资金", position = 3)
    private BigDecimal registeredCapital;

    /** 净资产 */
    @ApiModelProperty(value = "净资产", position = 4)
    private BigDecimal netAssets;

    /** 总资产 */
    @ApiModelProperty(value = "总资产", position = 5)
    private BigDecimal totalAssets;

    /** 固定资产 */
    @ApiModelProperty(value = "固定资产", position = 6)
    private BigDecimal fixedAssets;

    /** 流动资产 */
    @ApiModelProperty(value = "流动资产", position = 7)
    private BigDecimal currentAssets;

    /** 流动负债 */
    @ApiModelProperty(value = "流动负债", position = 8)
    private BigDecimal currentLiabilities;

    /** 负债合计 */
    @ApiModelProperty(value = "负债合计", position = 9)
    private BigDecimal totalLiabilities;

    /** 营业收入 */
    @ApiModelProperty(value = "营业收入", position = 10)
    private BigDecimal operatingRevenue;

    /** 净利润 */
    @ApiModelProperty(value = "净利润", position = 11)
    private BigDecimal netProfit;

    /** 现金流量净额 */
    @ApiModelProperty(value = "现金流量净额", position = 12)
    private BigDecimal cashFlowsNet;

    /** 主要财务指标 */
    @ApiModelProperty(value = "主要财务指标", position = 13)
    private String mainFinancialMetrics;

    /** 净资产收益率 */
    @ApiModelProperty(value = "净资产收益率", position = 14)
    private Double roe;

    /** 总资产报酬率 */
    @ApiModelProperty(value = "总资产报酬率", position = 15)
    private Double roa;

    /** 主营业务利润率 */
    @ApiModelProperty(value = "主营业务利润率", position = 16)
    private Double grossMargin;

    /** 资产负债率 */
    @ApiModelProperty(value = "资产负债率", position = 17)
    private Double debtRatio;

    /** 流动比率 */
    @ApiModelProperty(value = "流动比率", position = 18)
    private Double currentRatio;

    /** 速动比率 */
    @ApiModelProperty(value = "速动比率", position = 19)
    private Double quickRatio;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

}
