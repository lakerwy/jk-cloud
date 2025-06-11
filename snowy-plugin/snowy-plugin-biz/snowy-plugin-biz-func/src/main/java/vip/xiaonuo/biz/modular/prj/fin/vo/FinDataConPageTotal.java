package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinDataConPageTotal {

    /** 拨付金额 */
    @ApiModelProperty(value = "拨付金额", position = 5)
    private BigDecimal bfAmount;

    /** 扣管理费 */
    @ApiModelProperty(value = "扣管理费", position = 6)
    private BigDecimal kkManage;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 7)
    private BigDecimal kkTax;

    /** 扣签字费 */
    @ApiModelProperty(value = "扣签字费", position = 8)
    private BigDecimal kkSign;

    /** 扣派驻人员工资 */
    @ApiModelProperty(value = "扣派驻人员工资", position = 9)
    private BigDecimal kkGz;

    /** 扣职工薪酬 */
    @ApiModelProperty(value = "扣职工薪酬", position = 10)
    private BigDecimal kkSalary;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;

}
