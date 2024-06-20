package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryCostConfig {
    /** 成本类型：工资|生日福利等 */
    @ApiModelProperty(value = "成本类型：工资|生日福利等", position = 1)
    private String type;

    /** 成本归属部门Id */
    @ApiModelProperty(value = "成本归属部门Id", position = 2)
    private String deptId;

    /** 成本归属部门 */
    @ApiModelProperty(value = "成本归属部门", position = 3)
    private String dept;

    /** 成本归属 - 比例 0.1 0.3 */
    @ApiModelProperty(value = "成本归属 - 比例 0.1 0.3", position = 4)
    private float rate;
}
