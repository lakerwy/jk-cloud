package vip.xiaonuo.biz.modular.orgPerf.summary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;

import java.math.BigDecimal;
import java.util.List;

/**
 * 公司绩效考核-考核指标添加参数
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
@Getter
@Setter
public class CompanySummaryListVo {

    @ApiModelProperty(value = "目标登记主键ID", position = 1)
    private String id;

    /** 考核年度（例如：2025） */
    @ApiModelProperty(value = "考核年度（例如：2025）", position = 2)
    private Integer assYear;

    /** 新签合同总额完成值 */
    @ApiModelProperty(value = "新签合同总额-完成值", position = 3)
    private BigDecimal newContractTotalComp;

    /** 新签合同总额完成值 */
    @ApiModelProperty(value = "新签合同总额-目标值", position = 5)
    private BigDecimal newContractTotalGoal;

    /** 新签合同总额完成比例 */
    @ApiModelProperty(value = "新签合同总额-完成进度", position = 7)
    private BigDecimal newContractTotalCompProgress;

    /** 收入总额完成值 */
    @ApiModelProperty(value = "收入总额-完成值", position = 9)
    private BigDecimal incomeTotalComp;

    /** 收入总额目标值 */
    @ApiModelProperty(value = "收入总额-目标值", position = 11)
    private BigDecimal incomeTotalGoal;

    /** 收入总额完成比例 */
    @ApiModelProperty(value = "收入总额-完成进度", position = 13)
    private BigDecimal incomeTotalCompProgress;

    /** 产值完成值 */
    @ApiModelProperty(value = "产值-完成值", position = 15)
    private BigDecimal proValueTotalComp;

    /** 产值目标值 */
    @ApiModelProperty(value = "产值-目标值", position = 17)
    private BigDecimal proValueTotalGoal;

    /** 产值目标值 */
    @ApiModelProperty(value = "产值目标值-完成进度", position = 19)
    private BigDecimal proValueTotalCompProgress;

    /** 利润总额完成值 */
    @ApiModelProperty(value = "利润总额-完成值", position = 21)
    private BigDecimal profitTotalComp;

    /** 利润总额完成值 */
    @ApiModelProperty(value = "利润总额-目标值", position = 23)
    private BigDecimal profitTotalGoal;

    /** 利润总额完成进度 */
    @ApiModelProperty(value = "利润总额-完成进度", position = 25)
    private BigDecimal profitTotalCompProgress;

    /** 总分 */
    @ApiModelProperty(value = "总分", position = 20)
    private BigDecimal totalScore;

    /** 目标调整审核状态 */
    @ApiModelProperty(value = "目标调整审核状态（注：null代表未发起）", position = 22)
    private String goalModifyStatus;

}
