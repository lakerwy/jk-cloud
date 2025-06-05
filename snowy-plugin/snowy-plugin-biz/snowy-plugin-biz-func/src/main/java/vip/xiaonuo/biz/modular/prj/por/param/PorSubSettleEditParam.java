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
 * 项目产值台账 - 项目管理 - 项目分包结算记录编辑参数
 *
 * @author yy
 * @date  2025/06/03 09:53
 **/
@Getter
@Setter
public class PorSubSettleEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 分包合同- 主键Id */
    @ApiModelProperty(value = "分包合同- 主键Id", position = 2)
    private String subHetongId;

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

    /** 申报结算金额 */
    @ApiModelProperty(value = "申报结算金额", position = 6)
    private BigDecimal applyAmount;

    /** 审核结算金额 */
    @ApiModelProperty(value = "审核结算金额", position = 7)
    private BigDecimal reviewAmount;

    /** 结算内容描述 */
    @ApiModelProperty(value = "结算内容描述", position = 8)
    private String settleContent;

    /** 工程量清单附件 */
    @ApiModelProperty(value = "工程量清单附件", position = 9)
    private String billAttach;

    /** 扣管理费 */
    @ApiModelProperty(value = "扣管理费", position = 10)
    private BigDecimal deductManageAmount;

    /** 扣签字费 */
    @ApiModelProperty(value = "扣签字费", position = 11)
    private BigDecimal deductSignAmount;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 12)
    private BigDecimal deductTaxAmount;

    /** 其他扣款 */
    @ApiModelProperty(value = "其他扣款", position = 13)
    private BigDecimal deductOtherAmount;

    /** 结算净额 */
    @ApiModelProperty(value = "结算净额", position = 14)
    private BigDecimal settleAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String settleMemo;

    /** 结算状态 */
    @ApiModelProperty(value = "结算状态", position = 16)
    private String settleStatus;

    /** 总包审核人 */
    @ApiModelProperty(value = "总包审核人", position = 17)
    private String subSure;

    /** 审核日期 */
    @ApiModelProperty(value = "审核日期", position = 18)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date subSureDate;

    /** 实际支付金额 */
    @ApiModelProperty(value = "实际支付金额", position = 19)
    private BigDecimal realPayAmount;

    /** 支付日期 */
    @ApiModelProperty(value = "支付日期", position = 20)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    /** 质保金预留 */
    @ApiModelProperty(value = "质保金预留", position = 21)
    private BigDecimal qualityAmount;

    /** 滞纳金 */
    @ApiModelProperty(value = "滞纳金", position = 22)
    private BigDecimal lateAmount;

    /** 结算争议记录 */
    @ApiModelProperty(value = "结算争议记录", position = 23)
    private String disputeMemo;

    /** 预留字段-备注 */
    @ApiModelProperty(value = "预留字段-备注", position = 28)
    private String memo;

    /** 预留字段-附件 */
    @ApiModelProperty(value = "预留字段-附件", position = 29)
    private String attach;

}
