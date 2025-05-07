package vip.xiaonuo.biz.modular.crm.subcontract.param;

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
 * 分包合同结算明细编辑参数
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
@Getter
@Setter
public class SubcontractSettlementItemEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 关联-分包合同结算审批ID */
    @ApiModelProperty(value = "关联-分包合同结算审批ID", position = 2)
    private String subcontractSettlementId;

    /** 分包合同主键ID */
    @ApiModelProperty(value = "分包合同主键ID", position = 3)
    private String subcontractId;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 4)
    private String subcontractNo;

    /** 分包合同名称 */
    @ApiModelProperty(value = "分包合同名称", position = 5)
    private String subcontractName;

    /** 分包合同乙方 */
    @ApiModelProperty(value = "分包合同乙方", position = 6)
    private String counterparty;

    /** 分包合同金额 */
    @ApiModelProperty(value = "分包合同金额", position = 7)
    private BigDecimal subcontractAmount;

    /** 结算金额（累计已拨付） */
    @ApiModelProperty(value = "结算金额（累计已拨付）", position = 8)
    private BigDecimal settlementAmountTotal;

    /** 结算时间 */
    @ApiModelProperty(value = "结算时间（格式：yyyy-MM-dd）", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settlementTime;

    /** 结算说明 */
    @ApiModelProperty(value = "结算说明", position = 10)
    private String settlementDesc;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
