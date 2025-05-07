package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包合同结算明细Vo
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
@Getter
@Setter
public class SubcontractSettlementItemVo {

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
    @ApiModelProperty(value = "结算时间", position = 9)
    private Date settlementTime;

}
