package vip.xiaonuo.biz.modular.prj.por.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.por.entity.PorSign;

import java.math.BigDecimal;


@Getter
@Setter
public class PorSignVo extends PorSign {

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



    /*********************************** 来源-立项 ******************************************/

    /** 项目类型（BizProject.type） */
    @ApiModelProperty(value = "项目类型（BizProject.type）", position = 201)
    private String bizPrjType;


    /** 项目地点 - 建设地点（BizProject.location） */
    @ApiModelProperty(value = "项目地点 - 建设地点（BizProject.location）", position = 202)
    private String bizPrjLocation;

    /** 项目来源 （BizProject.head）*/
    @ApiModelProperty(value = "项目来源（BizProject.head）", position = 203)
    private String bizPrjHead;

}
