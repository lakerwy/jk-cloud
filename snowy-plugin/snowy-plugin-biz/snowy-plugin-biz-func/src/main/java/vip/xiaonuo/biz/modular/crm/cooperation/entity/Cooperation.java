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
package vip.xiaonuo.biz.modular.crm.cooperation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合作协议实体
 *
 * @author yong.ye
 * @date  2023/06/30 16:53
 **/
@Getter
@Setter
@TableName("BIZ_CRM_COOPERATION")
public class Cooperation {

    /** 主键ID（历史表Cooperation.Id） */
    @TableId
    @ApiModelProperty(value = "主键ID（历史表Cooperation.Id）", position = 1)
    private String id;

    /** 主键ID（历史表Cooperation.RegisterYear） */
    @ApiModelProperty(value = "主键ID（历史表Cooperation.RegisterYear）", position = 2)
    private Integer registerYear;

    /** 协议编号（历史表Cooperation.ContractNo） */
    @ApiModelProperty(value = "协议编号（历史表Cooperation.ContractNo）", position = 3)
    private String contractNo;

    /** 协议名称（历史表Cooperation.ProtocolName） */
    @ApiModelProperty(value = "协议名称（历史表Cooperation.ProtocolName）", position = 4)
    private String protocolName;

    /** 乙方（历史表Cooperation.Counterparty） */
    @ApiModelProperty(value = "乙方（历史表Cooperation.Counterparty）", position = 5)
    private String counterparty;

    /** 甲方（历史表Cooperation.FirstParty） */
    @ApiModelProperty(value = "甲方（历史表Cooperation.FirstParty）", position = 6)
    private String firstParty;

    /** 责任单位（历史表Cooperation.CompanyOrDepartment） */
    @ApiModelProperty(value = "责任单位（历史表Cooperation.CompanyOrDepartment）", position = 7)
    private String companyOrDepartment;

    /** 责任人（历史表Cooperation.Owner） */
    @ApiModelProperty(value = "责任人（历史表Cooperation.Owner）", position = 8)
    private String owner;

    /** 协议类型（历史表Cooperation.ContractType） */
    @ApiModelProperty(value = "协议类型（历史表Cooperation.ContractType）", position = 9)
    private String contractType;

    /** 经办人（历史表Cooperation.Agent） */
    @ApiModelProperty(value = "经办人（历史表Cooperation.Agent）", position = 10)
    private String agent;

    /** 合同盖章日期（历史表Cooperation.SealDate） */
    @ApiModelProperty(value = "合同盖章日期（历史表Cooperation.SealDate）", position = 11)
    private Date sealDate;

    /** 归档日期（历史表Cooperation.FilingDate） */
    @ApiModelProperty(value = "归档日期（历史表Cooperation.FilingDate）", position = 12)
    private Date filingDate;

    /** 有效期至（历史表Cooperation.Indate） */
    @ApiModelProperty(value = "有效期至（历史表Cooperation.Indate）", position = 13)
    private String indate;

    /** 备注（历史表Cooperation.Note） */
    @ApiModelProperty(value = "备注（历史表Cooperation.Note）", position = 14)
    private String note;

    /** 登记人（历史表Cooperation.Opt） */
    @ApiModelProperty(value = "登记人（历史表Cooperation.Opt）", position = 15)
    private String opt;

    /** 登记日期（历史表Cooperation.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表Cooperation.OptDate）", position = 16)
    private Date optDate;

    /** 协议扫描件（历史表Cooperation.Attachments） */
    @ApiModelProperty(value = "协议扫描件（历史表Cooperation.Attachments）", position = 17)
    private String attachments;

    /** 是否归档（历史表Cooperation.IsFiling） */
    @ApiModelProperty(value = "是否归档（历史表Cooperation.IsFiling）", position = 18)
    private String isFiling;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 19)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 22)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
