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
package vip.xiaonuo.biz.modular.crm.pursalecontract.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-购销合同编辑参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:03
 **/
@Getter
@Setter
public class PursalecontractEditParam {

    /** 主键ID（历史表PurSaleContract.Id） */
    @ApiModelProperty(value = "主键ID（历史表PurSaleContract.Id）", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 登记人（历史表PurSaleContract.Opt） */
//    @ApiModelProperty(value = "登记人（历史表PurSaleContract.Opt）", position = 2)
//    private String opt;

    /** 登记日期（历史表PurSaleContract.RegisterDate） */
//    @ApiModelProperty(value = "登记日期（历史表PurSaleContract.RegisterDate）", position = 3)
//    private Date registerDate;

    /** 合同编号（历史表PurSaleContract.ContractNo） */
//    @ApiModelProperty(value = "合同编号（历史表PurSaleContract.ContractNo）", position = 4)
//    private String contractNo;

    /** 合同名称（历史表PurSaleContract.ContractName） */
    @ApiModelProperty(value = "合同名称（历史表PurSaleContract.ContractName）", position = 5)
    private String contractName;

    /** 合同甲方（历史表PurSaleContract.PartyA） */
    @ApiModelProperty(value = "合同甲方（历史表PurSaleContract.PartyA）", position = 6)
    private String partyA;

    /** 合同乙方（历史表PurSaleContract.PartyB） */
    @ApiModelProperty(value = "合同乙方（历史表PurSaleContract.PartyB）", position = 7)
    private String partyB;

    /** 甲方代表（历史表PurSaleContract.BehalfA） */
    @ApiModelProperty(value = "甲方代表（历史表PurSaleContract.BehalfA）", position = 8)
    private String behalfA;

    /** 甲方电话（历史表PurSaleContract.PhoneA） */
    @ApiModelProperty(value = "甲方电话（历史表PurSaleContract.PhoneA）", position = 9)
    private String phoneA;

    /** 甲方地址（历史表PurSaleContract.AddressA） */
    @ApiModelProperty(value = "甲方地址（历史表PurSaleContract.AddressA）", position = 10)
    private String addressA;

    /** 乙方代表（历史表PurSaleContract.BehalfB） */
    @ApiModelProperty(value = "乙方代表（历史表PurSaleContract.BehalfB）", position = 11)
    private String behalfB;

    /** 乙方电话（历史表PurSaleContract.PhoneB） */
    @ApiModelProperty(value = "乙方电话（历史表PurSaleContract.PhoneB）", position = 12)
    private String phoneB;

    /** 乙方地址（历史表PurSaleContract.AddressB） */
    @ApiModelProperty(value = "乙方地址（历史表PurSaleContract.AddressB）", position = 13)
    private String addressB;

    /** 合同摘要（历史表PurSaleContract.Brief） */
    @ApiModelProperty(value = "合同摘要（历史表PurSaleContract.Brief）", position = 14)
    private String brief;

    /** 经办部门（历史表PurSaleContract.Dept） */
    @ApiModelProperty(value = "经办部门（历史表PurSaleContract.Dept）", position = 15)
    private String dept;

    /** 经办人（历史表PurSaleContract.Operator） */
    @ApiModelProperty(value = "经办人（历史表PurSaleContract.Operator）", position = 16)
    private String operator;

    /** 合同盖章日期（历史表PurSaleContract.SealDate） */
    @ApiModelProperty(value = "合同盖章日期（历史表PurSaleContract.SealDate）", position = 17)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date sealDate;

    /** 原件归档（历史表PurSaleContract.IsFill） */
    @ApiModelProperty(value = "原件归档（历史表PurSaleContract.IsFill）", position = 18)
    private String isFill;

    /** 归档日期（历史表PurSaleContract.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表PurSaleContract.FilingDate）", position = 19)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date filingDate;

    /** 合同金额（历史表PurSaleContract.Amount） */
    @ApiModelProperty(value = "合同金额（历史表PurSaleContract.Amount）", position = 20)
    private BigDecimal amount;

    /** 合同有效期（历史表PurSaleContract.Indate） */
    @ApiModelProperty(value = "合同有效期（历史表PurSaleContract.Indate）", position = 21)
    private String indate;

    /** 合同扫码件（历史表PurSaleContract.Attachments） */
    @ApiModelProperty(value = "合同扫码件（历史表PurSaleContract.Attachments）", position = 22)
    private String attachments;

    /** 备注（历史表PurSaleContract.Note） */
    @ApiModelProperty(value = "备注（历史表PurSaleContract.Note）", position = 23)
    private String note;

    /** 总页数（历史表PurSaleContract.Page） */
    @ApiModelProperty(value = "总页数（历史表PurSaleContract.Page）", position = 24)
    private String page;

    /** 原件份数（历史表PurSaleContract.Copys） */
    @ApiModelProperty(value = "原件份数（历史表PurSaleContract.Copys）", position = 25)
    private String copys;

    /** 存档位置（历史表PurSaleContract.Location） */
    @ApiModelProperty(value = "存档位置（历史表PurSaleContract.Location）", position = 26)
    private String location;

}
