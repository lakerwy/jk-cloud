package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分公司日常费用拨付 - 付款信息添加参数
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
public class BranchPaymentInfoAddParam {

    /** 合同主键ID */
    @ApiModelProperty(value = "合同主键ID", position = 3)
    private String contractId;

    /** 合同编号 */
    @ApiModelProperty(value = "合同编号", position = 4)
    private String contractNo;

    /** 合同甲方 */
    @ApiModelProperty(value = "合同甲方", position = 5)
    private String counterparty;

    /** 合同盖章日期 */
    @ApiModelProperty(value = "合同盖章日期", position = 6)
    private Date sealDate;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 7)
    private String paymentType;

    /** 款项具体内容 */
    @ApiModelProperty(value = "款项具体内容", position = 8)
    private String paymentDetail;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 9)
    private String unitName;

    /** 付款金额 */
    @ApiModelProperty(value = "付款金额", position = 10)
    private BigDecimal paymentAmount;

    /** 审定付款金额 */
    @ApiModelProperty(value = "审定付款金额", position = 11)
    private BigDecimal approvedAmount;

    /** 原始附件 */
    @ApiModelProperty(value = "原始附件", position = 12)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

}
