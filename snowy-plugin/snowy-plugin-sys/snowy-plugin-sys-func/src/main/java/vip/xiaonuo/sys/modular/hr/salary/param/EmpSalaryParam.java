package vip.xiaonuo.sys.modular.hr.salary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 员工工资条 查询参数
 */

@Getter
@Setter
public class EmpSalaryParam {

    /** 所属年 */
    @ApiModelProperty(value = "所属年")
    private String year;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月")
    private String yearMonth;

}
