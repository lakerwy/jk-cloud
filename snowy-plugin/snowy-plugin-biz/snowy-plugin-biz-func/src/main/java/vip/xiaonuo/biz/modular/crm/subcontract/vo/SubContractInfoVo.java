package vip.xiaonuo.biz.modular.crm.subcontract.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yong.ye
 * @date 2023/7/3 15:56
 */
public class SubContractInfoVo {

    /** 主键ID（历史表SubContract.Id） */
    @ApiModelProperty(value = "主键ID（历史表SubContract.Id）", position = 1)
    private String id;

    /** 登记人（历史表SubContract.Opt） */
    @ApiModelProperty(value = "登记人（历史表SubContract.Opt）", position = 2)
    private String opt;

    /** 登记日期（历史表SubContract.RegisterDate） */
    @ApiModelProperty(value = "登记日期（历史表SubContract.RegisterDate）", position = 3)
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
    private Date sealDate;

    /** 归档日期（历史表SubContract.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表SubContract.FilingDate）", position = 13)
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

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 21)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 25)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 源合同信息-合同名称（历史表SubContract.MainContractName） */
    @ApiModelProperty(value = "备注（历史表SubContract.MainContractName）", position = 18)
    private String mainContractName;

    /** 源合同信息-合同金额（历史表SubContract.MainContractAmount） */
    @ApiModelProperty(value = "备注（历史表SubContract.MainContractAmount）", position = 18)
    private BigDecimal mainContractAmount;

    /** 源合同信息-工程类别（历史表SubContract.MainContractSecondLevel） */
    @ApiModelProperty(value = "备注（历史表SubContract.MainContractSecondLevel）", position = 18)
    private String mainContractSecondLevel;

}
