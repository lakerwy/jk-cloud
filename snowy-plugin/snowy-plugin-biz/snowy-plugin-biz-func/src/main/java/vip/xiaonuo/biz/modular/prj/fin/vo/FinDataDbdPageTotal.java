package vip.xiaonuo.biz.modular.prj.fin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FinDataDbdPageTotal {

    /** 拨付金额 */
    @ApiModelProperty(value = "拨付金额", position = 5)
    private BigDecimal bfAmount;

    /** 扣款小计 */
    @ApiModelProperty(value = "扣款小计", position = 6)
    private BigDecimal kkSum;

    /** 扣管理费 */
    @ApiModelProperty(value = "扣管理费", position = 7)
    private BigDecimal kkManage;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 8)
    private BigDecimal kkTax;

    /** 扣签字费 */
    @ApiModelProperty(value = "扣签字费", position = 9)
    private BigDecimal kkSign;

    /** 扣签字费税差 */
    @ApiModelProperty(value = "扣签字费税差", position = 10)
    private BigDecimal kkSignTax;

    /** 扣职工薪酬 */
    @ApiModelProperty(value = "扣职工薪酬", position = 11)
    private BigDecimal kkSalary;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 12)
    private BigDecimal kkOther;
}
