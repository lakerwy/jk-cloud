package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSubSettle;

import java.math.BigDecimal;


@Getter
@Setter
public class PorSubSettleVo extends PorSubSettle {

    /*********************************** 来源-分包合同 ******************************************/

    /** 分包合同 - 分包合同编号（SubContract.ContractNo） */
    @ApiModelProperty(value = "分包合同编号（SubContract.ContractNo）", position = 301)
    private String subContractNo;

    /** 分包合同 - 合同名称（SubContract.ProjectName） */
    @ApiModelProperty(value = "合同名称（SubContract.ProjectName）", position = 302)
    private String subContractName;

    /** 分包合同 - 乙方（SubContract.Counterparty） */
    @ApiModelProperty(value = "乙方（SubContract.Counterparty）", position = 303)
    private String subContractCounterparty;

    /** 分包合同 - 分包类型（SubContract.SecondLevel） */
    @ApiModelProperty(value = "分包类型（SubContract.SecondLevel）", position = 304)
    private String subContractSecondLevel;

    /** 分包合同 - 分包类型（新的）(SubContract.subType) */
    @ApiModelProperty(value = "分包类型（新的）(SubContract.subType)", position = 305)
    private String subContractType;

    /** 分包合同 - 分包合同 - 费用类型(SubContract.expenseNature) */
    @ApiModelProperty(value = "费用类型(SubContract.expenseNature)", position = 306)
    private String subContractExpenseNature;

    /** 分包合同 - 费用类型 - 其他 备注(SubContract.expenseNatureMemo) */
    @ApiModelProperty(value = "费用类型 - 其他 备注(SubContract.expenseNatureMemo)", position = 307)
    private String subContractExpenseNatureMemo;

    /** 分包合同 - 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 308)
    private BigDecimal subContractAmount;

}
