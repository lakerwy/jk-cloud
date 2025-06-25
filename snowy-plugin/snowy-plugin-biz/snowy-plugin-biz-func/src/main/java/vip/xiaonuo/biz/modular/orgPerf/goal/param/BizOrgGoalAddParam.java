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
 * 子公司及生产部门绩效考核-目标登记添加参数
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
@Setter
public class BizOrgGoalAddParam {

    /** 考核部门（子公司|生产部门） */
    @ApiModelProperty(value = "考核部门（子公司|生产部门）", required = true, position = 2)
    @NotBlank(message = "考核部门assOrg必填")
    private String assOrg;

    /** 考核维度（季度考核|年度考核） */
    @ApiModelProperty(value = "考核维度（季度考核|年度考核）", required = true, position = 3)
    @NotBlank(message = "考核维度assPeriod必填")
    private String assPeriod;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 5)
    private String orgId;

    /** 责任人 */
    @ApiModelProperty(value = "责任人", position = 6)
    private String owner;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 7)
    private String ownerId;

    /** 考核周期-年份（例如：2025） */
    @ApiModelProperty(value = "考核周期-年份（例如：2025）", position = 9)
    private Integer assYear;

    /** 考核周期-季度（例如：1） */
    @ApiModelProperty(value = "考核周期-季度（例如：1）", position = 10)
    private Integer assSeason;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 基本项指标 */
    @ApiModelProperty(value = "基本项指标-集合", position = 15)
    private List<BizOrgGoalItemAddParam> itemList;

}
