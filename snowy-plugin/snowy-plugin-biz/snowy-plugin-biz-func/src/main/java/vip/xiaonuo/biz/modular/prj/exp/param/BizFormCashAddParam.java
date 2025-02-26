package vip.xiaonuo.biz.modular.prj.exp.param;

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
 * 费用报销（二期）- 借支单添加参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:12
 **/
@Getter
@Setter
public class BizFormCashAddParam {

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 3)
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 4)
    private String orgName;

    /** 借支日期 */
    @ApiModelProperty(value = "借支日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date advanceDate;

    /** 借支人id */
    @ApiModelProperty(value = "借支人id", position = 6)
    private String advanceId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 7)
    private String advanceName;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 8)
    private String duty;

    /** 借支类型 */
    @ApiModelProperty(value = "借支类型", position = 9)
    private String type;

    /** 归还时限 */
    @ApiModelProperty(value = "归还时限", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date backDate;

    /** 借支金额（元） */
    @ApiModelProperty(value = "借支金额（元）", position = 11)
    private BigDecimal advanceAmount;

    /** 借支事由 */
    @ApiModelProperty(value = "借支事由", position = 12)
    private String advanceReason;

    /** 累计未还金额 */
    @ApiModelProperty(value = "累计未还金额", position = 13)
    private BigDecimal ljWhAmount;

    /** 收款账户名 */
    @ApiModelProperty(value = "收款账户名", position = 14)
    private String bankUserName;

    /** 开户行 */
    @ApiModelProperty(value = "开户行", position = 15)
    private String bankName;

    /** 账户号 */
    @ApiModelProperty(value = "账户号", position = 16)
    private String bankNo;

    /** 出纳支付金额 */
    @ApiModelProperty(value = "出纳支付金额", position = 17)
    private BigDecimal cnPayAmount;

    /** 付款方式 */
    @ApiModelProperty(value = "付款方式", position = 18)
    private String payType;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 19)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 20)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 21)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 29)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 30)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 31)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 32)
    private String status;

}
