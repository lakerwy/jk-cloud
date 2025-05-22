package vip.xiaonuo.biz.modular.bidFee.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class BizBidFeeExpInfo {

    /** 主键ID  - 报销单主键 */
    @ApiModelProperty(value = "主键ID - - 报销单主键 ", position = 1)
    private String id;

    /** 报销日期  = 冲账时间 */
    @ApiModelProperty(value = "日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;

    /** 核定冲借支金额  = 冲账金额 */
    @ApiModelProperty(value = "核定冲借支金额  = 冲账金额 ", position = 4)
    private BigDecimal verifyOffsetAmount;

    /** SN = 冲账流水号 */
    @ApiModelProperty(value = "SN = 冲账流水号 ", position = 2)
    private String sn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 10)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 5)
    private String status;


    /** 冲账类型 */
    @ApiModelProperty(value = "冲账类型", position = 3)
    private String offsetType;

}
