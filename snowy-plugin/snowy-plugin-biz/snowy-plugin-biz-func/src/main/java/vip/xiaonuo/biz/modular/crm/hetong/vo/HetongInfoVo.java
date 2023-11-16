package vip.xiaonuo.biz.modular.crm.hetong.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/5 15:23
 */

@Getter
@Setter
public class HetongInfoVo {
    /** 主键ID（历史表Hetong.Id） */
    @ApiModelProperty(value = "主键ID（历史表Hetong.Id）", position = 1)
    private String id;

    /** 工程类别（历史表Hetong.SecondLevel） */
    @ApiModelProperty(value = "工程类别（历史表Hetong.SecondLevel）", position = 5)
    private String secondLevel;

    /** 合同编号（历史表Hetong.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表Hetong.ContractNo）", position = 6)
    private String contractNo;

    /** 合同名称（历史表Hetong.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表Hetong.ProjectName）", position = 7)
    private String projectName;

    /** 甲方（历史表Hetong.Counterparty） */
    @ApiModelProperty(value = "甲方（历史表Hetong.Counterparty）", position = 8)
    private String counterparty;

    /** 乙方（历史表Hetong.Secondparty） */
    @ApiModelProperty(value = "乙方（历史表Hetong.Secondparty）", position = 9)
    private String secondparty;

    /** 合作伙伴责任单位（历史表Hetong.CompanyOrDepartment） */
    @ApiModelProperty(value = "合作伙伴责任单位（历史表Hetong.CompanyOrDepartment）", position = 12)
    private String companyOrDepartment;

    /** 合作伙伴责任人（历史表Hetong.Owner） */
    @ApiModelProperty(value = "合作伙伴责任人（历史表Hetong.Owner）", position = 13)
    private String owner;

    /** 中标日期（历史表Hetong.BidDate） */
    @ApiModelProperty(value = "中标日期（历史表Hetong.BidDate）", position = 16)
    private Date bidDate;

    /** 合同盖章日期（历史表Hetong.SealDate） */
    @ApiModelProperty(value = "合同盖章日期（历史表Hetong.SealDate）", position = 17)
    private Date sealDate;

    /** 归档日期（历史表Hetong.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表Hetong.FilingDate）", position = 18)
    private Date filingDate;

    /** 合同金额 - 建科所占金额 */
    @ApiModelProperty(value = "合同金额 - 建科所占金额 ", position = 19)
    private BigDecimal contractAmount;

    /** 丙方 */
    @ApiModelProperty(value = "丙方", position = 20)
    private String partyc;

    /** 零星单位名称 */
    @ApiModelProperty(value = "零星单位名称", position = 21)
    private String unitsName;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 52)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 53)
    private String processInstanceId;

    /** 合同总金额 */
    @ApiModelProperty(value = "合同总金额", position = 63)
    private BigDecimal sumAmount;

}
