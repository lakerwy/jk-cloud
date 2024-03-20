package vip.xiaonuo.biz.modular.invoice.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 销售发票 - 累计开票金额|累计回款金额
 *
 * 同一销售合同编号（已审核通过）
 */

@Getter
@Setter
public class InvoiceLjInfoVo {

    /** 销售发票申请Id */
    @ApiModelProperty(value = "销售发票申请Id", position = 1)
    private String id;

    /** 销售合同编号 */
    @ApiModelProperty(value = "销售合同编号", position = 2)
    private String htContractNo;

    /** 累计开票金额(2.25) */
    @ApiModelProperty(value = "累计开票金额(2.25)", position = 3)
    private BigDecimal ljKpAmount;

    /** 累计收款金额(2.26)  */
    @ApiModelProperty(value = "累计收款金额(2.26) ", position = 4)
    private BigDecimal ljHkAmount;

    /** 累计预缴税款金额(2.39)  */
    @ApiModelProperty(value = "累计预缴税款金额(2.39) ", position = 5)
    private BigDecimal ljPreTax;

    /** 累计实际收取税差(2.42)  */
    @ApiModelProperty(value = "累计实际收取税差(2.42) ", position = 6)
    private BigDecimal ljPerTaxDiff;

}
