package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 根据 所属年月 获取 工资人员名单
 */

@Getter
@Setter
public class StandardParam {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月")
    private String yearMonth;

    /** 类型：工资|年终奖 */
    @ApiModelProperty(value = "类型：工资|年终奖")
    private String type;
}
