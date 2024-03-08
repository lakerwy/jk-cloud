package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 员工年月，嘉奖金额|扣减金额合计值
 */

@Getter
@Setter
public class SalaryAddTotal {
    /** 外键（HR_SALARY_ADD.ID) */
    @ApiModelProperty(value = "外键（HR_SALARY_ADD.ID)", position = 2)
    private String addId;

    /** 税前补发小计 */
    @ApiModelProperty(value = "税前补发小计", position = 10)
    private BigDecimal addAmount;

    /** 税前补扣小计 */
    @ApiModelProperty(value = "税前补扣小计", position = 11)
    private BigDecimal minusAmount;

    /** 税后补发小计 */
    @ApiModelProperty(value = "税后补发小计", position = 10)
    private BigDecimal lastAddAmount;

    /** 税后补扣小计 */
    @ApiModelProperty(value = "税后补扣小计", position = 11)
    private BigDecimal lastMinusAmount;
}
