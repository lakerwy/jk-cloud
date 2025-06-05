package vip.xiaonuo.biz.modular.prj.por.param;

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
 * 项目产值台账 - 项目管理 - 项目业主结算记录编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:52
 **/
@Getter
@Setter
public class PorOwnerSettleEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 收入合同- 主键Id */
    @ApiModelProperty(value = "收入合同- 主键Id", position = 2)
    private String hetongId;

    /** 结算单号 */
    @ApiModelProperty(value = "结算单号", position = 3)
    private String sn;

    /** 结算类型 */
    @ApiModelProperty(value = "结算类型", position = 4)
    private String settleType;

    /** 结算日期 */
    @ApiModelProperty(value = "结算日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settleDate;

    /** 竣工日期 */
    @ApiModelProperty(value = "竣工日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completeDate;

    /** 结算金额 */
    @ApiModelProperty(value = "结算金额", position = 7)
    private BigDecimal settleAmount;

    /** 送审金额 */
    @ApiModelProperty(value = "送审金额", position = 8)
    private BigDecimal reviewAmount;

    /** 质量保证金 */
    @ApiModelProperty(value = "质量保证金", position = 9)
    private BigDecimal qualityAmount;

    /** 结算内容描述 */
    @ApiModelProperty(value = "结算内容描述", position = 10)
    private String settleContent;

    /** 工程量清单附件 */
    @ApiModelProperty(value = "工程量清单附件", position = 11)
    private String billAttach;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 12)
    private Double taxRate;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 13)
    private BigDecimal taxAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String settleMemo;

    /** 结算状态 */
    @ApiModelProperty(value = "结算状态", position = 15)
    private String settleStatus;

    /** 业主确认人 */
    @ApiModelProperty(value = "业主确认人", position = 16)
    private String ownerSure;

    /** 业主确认日期 */
    @ApiModelProperty(value = "业主确认日期", position = 17)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ownerSureDate;

    /** 我方经办人id */
    @ApiModelProperty(value = "我方经办人id", position = 18)
    private String optId;

    /** 我方经办人 */
    @ApiModelProperty(value = "我方经办人", position = 19)
    private String optName;

    /** 审批意见 */
    @ApiModelProperty(value = "审批意见", position = 20)
    private String auditMemo;

    /** 发票状态 */
    @ApiModelProperty(value = "发票状态", position = 21)
    private String invoiceStatus;

    /** 关联发票号json */
    @ApiModelProperty(value = "关联发票号json", position = 22)
    private String invoiceJson;

    /** 实际付款金额 */
    @ApiModelProperty(value = "实际付款金额", position = 23)
    private BigDecimal realPayAmount;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 24)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    /** 滞纳金 */
    @ApiModelProperty(value = "滞纳金", position = 25)
    private BigDecimal lateAmount;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 30)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 31)
    private String attach;

}
