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

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-分包合同查询参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:22
 **/
@Getter
@Setter
public class SubcontractPageParam {

    /** 当前页 */
    @ApiModelProperty(value = "当前页码")
    private Integer current;

    /** 每页条数 */
    @ApiModelProperty(value = "每页条数")
    private Integer size;

    /** 排序字段 */
    @ApiModelProperty(value = "排序字段，字段驼峰名称，如：userName")
    private String sortField;

    /** 排序方式 */
    @ApiModelProperty(value = "排序方式，升序：ASCEND；降序：DESCEND")
    private String sortOrder;

    /** 关键词 */
    @ApiModelProperty(value = "关键词")
    private String searchKey;

    /** 登记人（历史表SubContract.Opt） */
    @ApiModelProperty(value = "登记人（历史表SubContract.Opt）")
    private String opt;

    /** 登记日期（历史表SubContract.RegisterDate）开始 */
    @ApiModelProperty(value = "登记日期（历史表SubContract.RegisterDate）开始")
    private String startRegisterDate;

    /** 登记日期（历史表SubContract.RegisterDate）结束 */
    @ApiModelProperty(value = "登记日期（历史表SubContract.RegisterDate）结束")
    private String endRegisterDate;

    /** 是否归档（历史表SubContract.IsFiling） */
    @ApiModelProperty(value = "是否归档（历史表SubContract.IsFiling）")
    private String isFiling;

    /** 分包类型（历史表SubContract.SecondLevel） */
    @ApiModelProperty(value = "分包类型（历史表SubContract.SecondLevel）")
    private String secondLevel;

    /** 合同编号（历史表SubContract.ContractNo） */
    @ApiModelProperty(value = "合同编号（历史表SubContract.ContractNo）")
    private String contractNo;

    /** 合同名称（历史表SubContract.ProjectName） */
    @ApiModelProperty(value = "合同名称（历史表SubContract.ProjectName）")
    private String projectName;

    /** 乙方（历史表SubContract.Counterparty） */
    @ApiModelProperty(value = "乙方（历史表SubContract.Counterparty）")
    private String counterparty;

    /** 甲方（历史表SubContract.Firstparty） */
    @ApiModelProperty(value = "甲方（历史表SubContract.Firstparty）")
    private String firstparty;

    /** 责任部门（历史表SubContract.CompanyOrDepartment） */
    @ApiModelProperty(value = "责任部门（历史表SubContract.CompanyOrDepartment）")
    private String companyOrDepartment;

    /** 责任人（历史表SubContract.Owner） */
    @ApiModelProperty(value = "责任人（历史表SubContract.Owner）")
    private String owner;

    /** 合同盖章日期（历史表SubContract.SealDate）开始 */
    @ApiModelProperty(value = "合同盖章日期（历史表SubContract.SealDate）开始")
    private String startSealDate;

    /** 合同盖章日期（历史表SubContract.SealDate）结束 */
    @ApiModelProperty(value = "合同盖章日期（历史表SubContract.SealDate）结束")
    private String endSealDate;

    /** 源合同信息-合同ID主键（历史表SubContract.HetongId） */
    @ApiModelProperty(value = "源合同信息-合同ID主键（历史表SubContract.HetongId）")
    private String hetongId;

    /** 源合同信息-合同编号（历史表SubContract.MainContractNo） */
    @ApiModelProperty(value = "源合同信息-合同编号（历史表SubContract.MainContractNo）")
    private String mainContractNo;

    /** 分包类型（新的） */
    @ApiModelProperty(value = "分包类型（新的）")
    private String subType;

    /** 费用性质 */
    @ApiModelProperty(value = "费用性质")
    private String expenseNature;

}
