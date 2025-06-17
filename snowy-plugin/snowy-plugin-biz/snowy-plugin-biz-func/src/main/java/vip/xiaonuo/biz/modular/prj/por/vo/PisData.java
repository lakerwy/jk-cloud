package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PisData {

    /** 关联审批单 - SN流水号 */
    @ApiModelProperty(value = "关联审批单 - SN流水号", position = 1)
    private String sn;

    /** 关联审批单 - 流程实例Id */
    @ApiModelProperty(value = "关联审批单 - 流程实例Id", position = 2)
    private String processInstanceId;

    /** 关联审批单 - 流程状态 */
    @ApiModelProperty(value = "关联审批单 - 流程状态", position = 3)
    private String status;

    /** 总包合同 - 合同Id */
    @ApiModelProperty(value = "总包合同 - 合同Id", position = 4)
    private String htId;

    /** 总包合同 - 合同名称 */
    @ApiModelProperty(value = "总包合同 - 合同名称", position = 5)
    private String htProjectName;

    /** 总包合同 - 合同编号 */
    @ApiModelProperty(value = "总包合同 - 合同编号", position = 6)
    private String htContractNo;


    /** 分包合同 - 合同Id */
    @ApiModelProperty(value = "分包合同 - 合同Id", position = 7)
    private String subId;

    /** 分包合同 - 合同名称 */
    @ApiModelProperty(value = "分包合同 - 合同名称", position = 8)
    private String subProjectName;

    /** 分包合同 - 合同编号 */
    @ApiModelProperty(value = "分包合同 - 合同编号", position = 9)
    private String subContractNo;

    /** 分包合同 - 合同金额 */
    @ApiModelProperty(value = "分包合同 - 合同金额", position = 10)
    private BigDecimal subContractAmount;


    /** 来源于 - 拨付 - H  - json 体 */

    /** 分包合同 - 合同编号 */
    @ApiModelProperty(value = "分包合同 - 合同编号", position = 20)
    private String subcontractNumber;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 21)
    private String moneyNature;

    /** 本次应付金额(元) */
    @ApiModelProperty(value = "本次应付金额(元)", position = 22)
    private BigDecimal payable;

    /** 出纳拨付金额(元) */
    @ApiModelProperty(value = "出纳拨付金额(元)", position = 23)
    private BigDecimal payCashier;

    /** 扣管理费 */
    @ApiModelProperty(value = "扣管理费", position = 24)
    private BigDecimal deductFee;

    /** 扣税差 */
    @ApiModelProperty(value = "扣税差", position = 25)
    private BigDecimal deductTax;

    /** 扣签字费 */
    @ApiModelProperty(value = "扣签字费", position = 26)
    private BigDecimal deductSignature;

    /** 其他扣款项 */
    @ApiModelProperty(value = "其他扣款项", position = 27)
    private BigDecimal otherDeduct;

    /** 付款日期 */
    @ApiModelProperty(value = "付款日期", position = 28)
    private String paymentDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 29)
    private String memo;
}
