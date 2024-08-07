package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.branch.entity.BranchPaymentInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分公司日常费用拨付 - 申请流程添加参数
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
public class BranchAllotmentApplyAddParam {
    /** 分公司名称 */
    @ApiModelProperty(value = "分公司银行账户信息ID", position = 2)
    private String branchAccountId;

    /** 分公司名称 */
    @ApiModelProperty(value = "分公司名称", position = 3)
    private String branchName;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 4)
    private String operator;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 5)
    private Date paymentDate;

    /** 付款银行名称 */
    @ApiModelProperty(value = "付款银行名称", position = 6)
    private String bankName;

    /** 银行账户 */
    @ApiModelProperty(value = "银行账户", position = 7)
    private String bankAccount;

    /** 银行账户余额 */
    @ApiModelProperty(value = "银行账户余额", position = 8)
    private BigDecimal accountBalance;

//    /** 出纳登记账户余额 */
//    @ApiModelProperty(value = "出纳登记账户余额", position = 9)
//    private BigDecimal realBalance;

    /** 付款信息 */
    @ApiModelProperty(value = "付款信息", position = 15)
    private List<BranchPaymentInfo> paymentInfoList;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 16)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 17)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 18)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 19)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 20)
    private String extJson;

}
