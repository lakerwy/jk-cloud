package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 子公司及生产部门绩效考核-目标登记查询参数
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
@Setter
public class BizOrgGoalPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 考核部门（子公司|生产部门） */
    @ApiModelProperty(value = "考核部门（子公司|生产部门）", required = true)
    @NotBlank(message = "考核部门assOrg必填")
    private String assOrg;

    /** 考核维度（季度考核|年度考核） */
    @ApiModelProperty(value = "考核维度（季度考核|年度考核）", required = true)
    @NotBlank(message = "考核维度assPeriod必填")
    private String assPeriod;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID")
    private String orgId;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）")
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）")
    private Integer assSeason;

}
