package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 子公司及生产部门绩效考核-汇总详情参数
 *
 * @author pzh
 * @date  2025/06/25 15:42
 **/
@Getter
@Setter
public class OrgSummaryDetailVo {

//    /** 目标登记主键ID */
//    @ApiModelProperty(value = "目标登记主键ID", position = 1)
//    private String id;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 3)
    private Integer assYear;

    /** 考月份（例如：1） */
    @ApiModelProperty(value = "月份（例如：1）", position = 5)
    private Integer month;

    /** 完成值预警 */
    @ApiModelProperty(value = "完成值预警", position = 7)
    private String compWarning;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 9)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 11)
    private String assRule;

    /** 评分标准 */
    @ApiModelProperty(value = "评分标准", position = 13)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 17)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 19)
    private BigDecimal goalValue;

    /** 数据提供部门 */
    @ApiModelProperty(value = "数据提供部门", position = 21)
    private String fromDept;

    /** 数据提供部门ID */
    @ApiModelProperty(value = "数据提供部门ID", position = 23)
    private String fromDeptId;

    /** 实际完成值（累计） */
    @ApiModelProperty(value = "实际完成值（累计）", position = 25)
    private BigDecimal compValueTotal;

    /** 累计得分 */
    @ApiModelProperty(value = "累计得分", position = 27)
    private BigDecimal score;

    /** 进度 */
    @ApiModelProperty(value = "进度", position = 29)
    private BigDecimal progress;

}
