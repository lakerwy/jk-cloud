package vip.xiaonuo.biz.modular.prj.fin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class ImportFinDataPdt {

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 拨付日期 */
    @ApiModelProperty(value = "拨付日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date bfDate;

    /** 拨付外部产值 */
    @ApiModelProperty(value = "拨付外部产值", position = 5)
    private BigDecimal bfOutput;

    /** 拨付经营费 */
    @ApiModelProperty(value = "拨付经营费", position = 6)
    private BigDecimal bfExpense;

    /** 拨付分包款 */
    @ApiModelProperty(value = "拨付分包款", position = 7)
    private BigDecimal bfSub;

    /** 拨付劳务费 */
    @ApiModelProperty(value = "拨付劳务费", position = 8)
    private BigDecimal bfLabor;

    /** 拨付其他费用 */
    @ApiModelProperty(value = "拨付其他费用", position = 9)
    private BigDecimal bfOther;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 10)
    private BigDecimal kkTax;

    /** 其他扣款金额 */
    @ApiModelProperty(value = "其他扣款金额", position = 11)
    private BigDecimal kkOther;

    /** 其他扣款事项 */
    @ApiModelProperty(value = "其他扣款事项", position = 12)
    private String kkOtherContent;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 13)
    private String content;
}
