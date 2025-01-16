package vip.xiaonuo.biz.modular.prjFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 获取前期款项数据参数
 *
 * @author pzh
 * @date  2025/01/15 15:45
 **/
@Getter
@Setter
public class PrjFeeHisVo {

    /** 本项目已拨付次数 */
    @ApiModelProperty(value = "本项目已拨付次数", position = 1)
    private int disbursedTimes;

    /** 前期银行收款累计 */
    @ApiModelProperty(value = "前期银行收款累计", position = 33)
    private BigDecimal bankReceiptAmountAcc;

    /** 前期保理费累计 */
    @ApiModelProperty(value = "前期保理费累计", position = 34)
    private BigDecimal factoringFeeAmountAcc;

    /** 前期回款总额累计 */
    @ApiModelProperty(value = "前期回款总额累计", position = 35)
    private BigDecimal totalReceiptAcc;

    /** 前期拨付款 */
    @ApiModelProperty(value = "前期拨付款", position = 36)
    private BigDecimal disburseAmountAcc;

    /** 前期税差扣款 */
    @ApiModelProperty(value = "前期税差扣款", position = 37)
    private BigDecimal taxDiffDedAcc;

    /** 前期管理费（2%） */
    @ApiModelProperty(value = "前期管理费（2%）", position = 38)
    private BigDecimal manageFeeAcc;

    /** 前期其他扣款 */
    @ApiModelProperty(value = "前期其他扣款", position = 39)
    private BigDecimal otherDedAcc;

    /** 前期挂账金额 */
    @ApiModelProperty(value = "前期挂账金额", position = 40)
    private BigDecimal suspendedAmountAcc;

}
