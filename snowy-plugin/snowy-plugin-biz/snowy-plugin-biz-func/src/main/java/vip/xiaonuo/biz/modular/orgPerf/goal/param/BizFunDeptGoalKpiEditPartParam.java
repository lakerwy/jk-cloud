package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 职能部门绩效考核-目标登记编辑参数
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
@Getter
@Setter
public class BizFunDeptGoalKpiEditPartParam {

    /** kpi主键ID */
    @ApiModelProperty(value = "kpi主键ID", required = true, position = 1)
    @NotBlank(message = "kpi主键ID不能为空")
    private String id;

    /** 实际完成情况 */
    @ApiModelProperty(value = "实际完成情况", position = 11)
    private String actualCompDesc;

    /** 成果附件 */
    @ApiModelProperty(value = "成果附件", position = 12)
    private String attach;

    /** 完成进度 */
    @ApiModelProperty(value = "完成进度", position = 13)
    private BigDecimal progress;

}
