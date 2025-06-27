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
 * 职能部门绩效考核-目标登记添加参数
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
@Getter
@Setter
public class BizFunDeptGoalAddParam {

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String orgName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 3)
    private String orgId;

    /** 考核类型（季度考核|年度考核） */
    @ApiModelProperty(value = "考核类型（季度考核|年度考核）", required = true, position = 4)
    @NotBlank(message = "考核类型assType必填")
    private String assType;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 5)
    private String owner;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    private String ownerId;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）", position = 7)
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）", position = 8)
    private Integer assSeason;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 9)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** KPI指标 */
    @ApiModelProperty(value = "KPI指标-集合", position = 15)
    private List<BizFunDeptGoalKpiAddParam> kpiList;

}
