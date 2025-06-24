package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.por.entity.PorOwnerSettle;

import java.math.BigDecimal;


@Getter
@Setter
public class PorOwnerSettleVo extends PorOwnerSettle {

    /*********************************** 来源-收入合同 ******************************************/

    /** 合同名称（Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（Hetong.ProjectName） ", position = 101)
    private String contractName;

    /** 合同编号（Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号（Hetong.ContractNo）", position = 102)
    private String contractNo;

    /** 合同类型（Hetong.ContractType） */
    @ApiModelProperty(value = "合同类型（Hetong.ContractType）", position = 103)
    private String contractType;

    /** 合同 - 建科所占金额（Hetong.contractAmount） */
    @ApiModelProperty(value = "合同 - 建科所占金额（Hetong.contractAmount） ", position = 104)
    private BigDecimal contractAmount;

    /** 合同总金额（Hetong.sumAmount） */
    @ApiModelProperty(value = "合同总金额（Hetong.sumAmount）", position = 105)
    private BigDecimal contractSumAmount;


    /** 工程类别（Hetong.SecondLevel） */
    @ApiModelProperty(value = "工程类别（Hetong.SecondLevel）", position = 106)
    private String contractSecondLevel;


    /** 甲方（Hetong.Counterparty） */
    @ApiModelProperty(value = "甲方（Hetong.Counterparty）", position = 107)
    private String contractCounterparty;



    /** 项目产值台账 - 项目管理 - 主键Id */
    @ApiModelProperty(value = "项目产值台账 - 项目管理 - 主键Id", position = 200)
    private String porProjectId;

    /** 立项Id */
    @ApiModelProperty(value = "立项Id", position = 200)
    private String bizProjectId;
}
