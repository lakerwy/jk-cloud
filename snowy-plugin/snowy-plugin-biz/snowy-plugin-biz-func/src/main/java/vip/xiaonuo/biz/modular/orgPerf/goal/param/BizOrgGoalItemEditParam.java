package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 组织绩效考核-考核指标编辑参数
 *
 * @author pzh
 * @date  2025/06/24 16:45
 **/
@Getter
@Setter
public class BizOrgGoalItemEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    private String id;

    /** 关联-目标登记主键ID */
    @ApiModelProperty(value = "关联-目标登记主键ID", position = 2)
    private String goalId;

    /** 考核指标 */
    @ApiModelProperty(value = "考核指标", position = 3)
    private String assName;

    /** 考核细则 */
    @ApiModelProperty(value = "考核细则", position = 4)
    private String assRule;

    /** 评分标准 */
    @ApiModelProperty(value = "评分标准", position = 5)
    private String standard;

    /** 权重 */
    @ApiModelProperty(value = "权重", position = 6)
    private BigDecimal weight;

    /** 目标值 */
    @ApiModelProperty(value = "目标值", position = 7)
    private BigDecimal goalValue;

    /** 数据提供部门 */
    @ApiModelProperty(value = "数据提供部门", position = 8)
    private String fromDept;

    /** 数据提供部门ID */
    @ApiModelProperty(value = "数据提供部门ID", position = 9)
    private String fromDeptId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

}
