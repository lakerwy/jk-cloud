/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.crm.subcontract.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-分包合同添加参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:22
 **/
@Getter
@Setter
public class SubcontractAddParam {

    /** 登记人（历史表SubContract.Opt） */
    @ApiModelProperty(value = "登记人（历史表SubContract.Opt）", position = 2)
    private String opt;

    /** 登记日期（历史表SubContract.RegisterDate） */
    @ApiModelProperty(value = "登记日期（历史表SubContract.RegisterDate）", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date registerDate;

    /** 是否归档（历史表SubContract.IsFiling） */
    @ApiModelProperty(value = "是否归档（历史表SubContract.IsFiling）", position = 4)
    private String isFiling;

    /** 分包类型（历史表SubContract.SecondLevel） */
    @ApiModelProperty(value = "分包类型（历史表SubContract.SecondLevel）", position = 5)
    private String secondLevel;

    /** 合同编号（历史表SubContract.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表SubContract.ContractNo）", position = 6)
    private String contractNo;

    /** 合同名称（历史表SubContract.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表SubContract.ProjectName）", position = 7)
    private String projectName;

    /** 乙方（历史表SubContract.Counterparty） */
    @ApiModelProperty(value = "乙方（历史表SubContract.Counterparty）", position = 8)
    private String counterparty;

    /** 甲方（历史表SubContract.Firstparty） */
    @ApiModelProperty(value = "甲方（历史表SubContract.Firstparty）", position = 9)
    private String firstparty;

    /** 责任部门（历史表SubContract.CompanyOrDepartment） */
    @ApiModelProperty(value = "责任部门（历史表SubContract.CompanyOrDepartment）", position = 10)
    private String companyOrDepartment;

    /** 责任人（历史表SubContract.Owner） */
    @ApiModelProperty(value = "责任人（历史表SubContract.Owner）", position = 11)
    private String owner;

    /** 合同盖章日期（历史表SubContract.SealDate） */
    @ApiModelProperty(value = "合同盖章日期（历史表SubContract.SealDate）", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date sealDate;

    /** 归档日期（历史表SubContract.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表SubContract.FilingDate）", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date filingDate;

    /** 合同金额（历史表SubContract.ContractAmount） */
    @ApiModelProperty(value = "合同金额（历史表SubContract.ContractAmount）", position = 14)
    private BigDecimal contractAmount;

    /** 源合同信息-合同ID主键（历史表SubContract.HetongId） */
    @ApiModelProperty(value = "源合同信息-合同ID主键（历史表SubContract.HetongId）", position = 15)
    private String hetongId;

    /** 源合同信息-合同编号（历史表SubContract.MainContractNo） */
    @ApiModelProperty(value = "源合同信息-合同编号（历史表SubContract.MainContractNo）", position = 16)
    private String mainContractNo;

    /** 合同扫码件（历史表SubContract.Attachments） */
    @ApiModelProperty(value = "合同扫码件（历史表SubContract.Attachments）", position = 17)
    private String attachments;

    /** 备注（历史表SubContract.Note） */
    @ApiModelProperty(value = "备注（历史表SubContract.Note）", position = 18)
    private String note;

    /** 总页数（历史表SubContract.Page） */
    @ApiModelProperty(value = "总页数（历史表SubContract.Page）", position = 19)
    private String page;

    /** 原件份数（历史表SubContract.Copys） */
    @ApiModelProperty(value = "原件份数（历史表SubContract.Copys）", position = 20)
    private String copys;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 52)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 53)
    private String processInstanceId;

    /** 流程状态（0 - 已提交  1- 已审批） */
    @ApiModelProperty(value = "流程状态（0 - 已提交  1- 已审批）", position = 54)
    private String status;

    /** 分包类型（新的） */
    @ApiModelProperty(value = "分包类型（新的）", position = 66)
    private String subType;
}
