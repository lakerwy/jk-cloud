package vip.xiaonuo.biz.modular.prj.fin.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务数据汇总台账 - 销售发票添加参数
 *
 * @author yy
 * @date  2025/06/05 17:24
 **/
@Getter
@Setter
public class FinDataInvoiceAddParam {

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 2)
    private String contractId;

    /** 合同编码 */
    @ApiModelProperty(value = "合同编码", position = 3)
    private String contractNo;

    /** 开票单位id */
    @ApiModelProperty(value = "开票单位id", position = 4)
    private String unitId;

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

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 14)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 15)
    private String attach;

}
