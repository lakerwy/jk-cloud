package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 组织责任人变更参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizFunDeptGoalEditOwnerParam {

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

}
