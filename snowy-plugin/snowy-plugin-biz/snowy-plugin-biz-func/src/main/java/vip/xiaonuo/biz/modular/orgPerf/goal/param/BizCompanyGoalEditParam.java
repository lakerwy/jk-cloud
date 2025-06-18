package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 公司绩效考核-目标登记编辑参数
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Getter
@Setter
public class BizCompanyGoalEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 2)
    private String assType;

    /** 考核周期（例如：2025） */
    @ApiModelProperty(value = "考核周期（例如：2025）", position = 3)
    private String assYear;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** KPI指标 */
    @ApiModelProperty(value = "KPI指标-集合", position = 10)
    private List<BizCompanyGoalKpiEditParam> kpiList;

}
