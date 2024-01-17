package vip.xiaonuo.biz.modular.prj.settle.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.settle.entity.BizPrjSettleInvoice;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/10/19 14:50
 */

@Getter
@Setter
public class SettleInvoiceVo extends BizPrjSettleInvoice {

    /** 结算单编号 */
    @ApiModelProperty(value = "结算单编号", position = 5)
    private String settleNo;

    /** 总包-合同编号 */
    @ApiModelProperty(value = "总包-合同编号", position = 12)
    private String contractNo;

    /** 总包-合同名称 */
    @ApiModelProperty(value = "总包-合同名称", position = 13)
    private String contractName;

    /** 总包-合同金额 */
    @ApiModelProperty(value = "总包-合同金额", position = 14)
    private BigDecimal contractAmount;

    /** 结算单-状态 */
    @ApiModelProperty(value = "结算单-状态", position = 14)
    private String status;

}
