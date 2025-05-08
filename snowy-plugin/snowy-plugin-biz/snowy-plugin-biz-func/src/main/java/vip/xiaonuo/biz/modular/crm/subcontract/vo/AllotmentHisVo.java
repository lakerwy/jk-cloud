package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 项目前期拨付情况Vo
 *
 * @author pzh
 * @date  2025/05/06 09:00
 **/
@Getter
@Setter
public class AllotmentHisVo {

    /** 分包合同主键ID */
    @ApiModelProperty(value = "分包合同主键ID", position = 3)
    private String subcontractId;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 4)
    private String subcontractNo;

//    /** 分包合同名称 */
//    @ApiModelProperty(value = "分包合同名称", position = 5)
//    private String subcontractName;
//
//    /** 分包合同乙方 */
//    @ApiModelProperty(value = "分包合同乙方", position = 6)
//    private String counterparty;

    /** 分包合同金额 */
    @ApiModelProperty(value = "分包合同金额", position = 7)
    private BigDecimal subcontractAmount;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 8)
    private String paymentDate;

    /** 拨付金额 */
    @ApiModelProperty(value = "拨付金额", position = 9)
    private BigDecimal allotmentAmount;

    /** 拨付-流水号 */
    @ApiModelProperty(value = "拨付-流水号（生产院及子公司项目拨付-流水号）", position = 20)
    private String allotmentSn;

    /** 拨付-流程实例id */
    @ApiModelProperty(value = "分包合同审批-流程实例id", position = 21)
    private String subcontractProcessInstanceId;

    /** 拨付-流程实例id */
    @ApiModelProperty(value = "拨付-流程实例id（生产院及子公司项目拨付-流程实例id）", position = 22)
    private String allotmentProcessInstanceId;

}
