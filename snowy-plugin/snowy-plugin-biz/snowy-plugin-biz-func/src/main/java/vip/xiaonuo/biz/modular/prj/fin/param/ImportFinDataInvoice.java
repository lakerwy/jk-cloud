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
public class ImportFinDataInvoice {

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 开票单位 */
    @ApiModelProperty(value = "开票单位", position = 5)
    private String unitName;

    /** 开票日期 */
    @ApiModelProperty(value = "开票日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date kpDate;

    /** 开票内容 */
    @ApiModelProperty(value = "开票内容", position = 7)
    private String content;

    /** 开票金额 */
    @ApiModelProperty(value = "开票金额", position = 8)
    private BigDecimal kpAmount;

}
