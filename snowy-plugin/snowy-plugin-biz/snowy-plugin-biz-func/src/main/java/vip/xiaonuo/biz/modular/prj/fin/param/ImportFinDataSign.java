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
public class ImportFinDataSign {
    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 转款单位 */
    @ApiModelProperty(value = "转款单位", position = 5)
    private String unitName;

    /** 转款日期 */
    @ApiModelProperty(value = "转款日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zkDate;

    /** 转款金额 */
    @ApiModelProperty(value = "转款金额", position = 7)
    private BigDecimal zkAmount;

    /** 描述 */
    @ApiModelProperty(value = "描述", position = 8)
    private String content;
}
