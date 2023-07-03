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
package vip.xiaonuo.biz.modular.crm.fpinformations.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-合作伙伴人员信息实体
 *
 * @author yong.ye
 * @date  2023/06/30 16:55
 **/
@Getter
@Setter
@TableName("BIZ_CRM_FPINFORMATIONS")
public class Fpinformations {

    /** 主键ID（历史表FPInformations.Id） */
    @TableId
    @ApiModelProperty(value = "主键ID（历史表FPInformations.Id）", position = 1)
    private String id;

    /** 流水号SN（历史表FPInformations.SN） */
    @ApiModelProperty(value = "流水号SN（历史表FPInformations.SN）", position = 2)
    private String sn;

    /** 登记人（历史表FPInformations.Opt） */
    @ApiModelProperty(value = "登记人（历史表FPInformations.Opt）", position = 3)
    private String opt;

    /** 登记日期（历史表FPInformations.OptDate） */
    @ApiModelProperty(value = "登记日期（历史表FPInformations.OptDate）", position = 4)
    private Date optDate;

    /** 姓名（历史表FPInformations.Name） */
    @ApiModelProperty(value = "姓名（历史表FPInformations.Name）", position = 5)
    private String name;

    /** 合作伙伴主键ID（历史表FPInformations.PartnerId） */
    @ApiModelProperty(value = "合作伙伴主键ID（历史表FPInformations.PartnerId）", position = 6)
    private String partnerId;

    /** 部门（历史表FPInformations.Dept） */
    @ApiModelProperty(value = "部门（历史表FPInformations.Dept）", position = 7)
    private String dept;

    /** 现从事专业（历史表FPInformations.MajorIn） */
    @ApiModelProperty(value = "现从事专业（历史表FPInformations.MajorIn）", position = 8)
    private String majorIn;

    /** 社保号（历史表FPInformations.SocialNumber） */
    @ApiModelProperty(value = "社保号（历史表FPInformations.SocialNumber）", position = 9)
    private String socialNumber;

    /** 身份证号（历史表FPInformations.IDNumber） */
    @ApiModelProperty(value = "身份证号（历史表FPInformations.IDNumber）", position = 10)
    private String idNumber;

    /** 入职日期（历史表FPInformations.EntryDate） */
    @ApiModelProperty(value = "入职日期（历史表FPInformations.EntryDate）", position = 11)
    private Date entryDate;

    /** 社保转入日期（历史表FPInformations.InDate） */
    @ApiModelProperty(value = "社保转入日期（历史表FPInformations.InDate）", position = 12)
    private Date inDate;

    /** 联系方式（历史表FPInformations.Phone） */
    @ApiModelProperty(value = "联系方式（历史表FPInformations.Phone）", position = 13)
    private String phone;

    /** QQ/邮箱（历史表FPInformations.Email） */
    @ApiModelProperty(value = "QQ/邮箱（历史表FPInformations.Email）", position = 14)
    private String email;

    /** 毕业证-毕业学校（历史表FPInformations.School） */
    @ApiModelProperty(value = "毕业证-毕业学校（历史表FPInformations.School）", position = 15)
    private String school;

    /** 毕业证-学历（历史表FPInformations.Education） */
    @ApiModelProperty(value = "毕业证-学历（历史表FPInformations.Education）", position = 16)
    private String education;

    /** 毕业证-学制（历史表FPInformations.System） */
    @ApiModelProperty(value = "毕业证-学制（历史表FPInformations.System）", position = 17)
    private String system;

    /** 毕业证-专业（历史表FPInformations.Major） */
    @ApiModelProperty(value = "毕业证-专业（历史表FPInformations.Major）", position = 18)
    private String major;

    /** 毕业证-毕业时间（历史表FPInformations.GraduateDate） */
    @ApiModelProperty(value = "毕业证-毕业时间（历史表FPInformations.GraduateDate）", position = 19)
    private Date graduateDate;

    /** 毕业证-证书编号（历史表FPInformations.CertificateNo） */
    @ApiModelProperty(value = "毕业证-证书编号（历史表FPInformations.CertificateNo）", position = 20)
    private String certificateNo;

    /** 职称-专业（历史表FPInformations.Majors） */
    @ApiModelProperty(value = "职称-专业（历史表FPInformations.Majors）", position = 21)
    private String majors;

    /** 职称-级别（历史表FPInformations.Rank） */
    @ApiModelProperty(value = "职称-级别（历史表FPInformations.Rank）", position = 22)
    private String rank;

    /** 职称-职称证号（历史表FPInformations.TitleNumber） */
    @ApiModelProperty(value = "职称-职称证号（历史表FPInformations.TitleNumber）", position = 23)
    private String titleNumber;

    /** 职称-授予时间（历史表FPInformations.TitleDate） */
    @ApiModelProperty(value = "职称-授予时间（历史表FPInformations.TitleDate）", position = 24)
    private Date titleDate;

    /** 职业资格证/注册证-名称（历史表FPInformations.CertificateName） */
    @ApiModelProperty(value = "职业资格证/注册证-名称（历史表FPInformations.CertificateName）", position = 25)
    private String certificateName;

    /** 职业资格证/注册证-级别（历史表FPInformations.CertificateRank） */
    @ApiModelProperty(value = "职业资格证/注册证-级别（历史表FPInformations.CertificateRank）", position = 26)
    private String certificateRank;

    /** 职业资格证/注册证-证书编号（历史表FPInformations.CertificateNumber） */
    @ApiModelProperty(value = "职业资格证/注册证-证书编号（历史表FPInformations.CertificateNumber）", position = 27)
    private String certificateNumber;

    /** 备注（历史表FPInformations.Note） */
    @ApiModelProperty(value = "备注（历史表FPInformations.Note）", position = 28)
    private String note;

    /** 社保情况（历史表FPInformations.Socail） */
    @ApiModelProperty(value = "社保情况（历史表FPInformations.Socail）", position = 29)
    private String social;

    /** 公积金情况（历史表FPInformations.Fund） */
    @ApiModelProperty(value = "公积金情况（历史表FPInformations.Fund）", position = 30)
    private String fund;

    /** 职称-附件（历史表FPInformations.Attachments1） */
    @ApiModelProperty(value = "职称-附件（历史表FPInformations.Attachments1）", position = 31)
    private String attachments1;

    /** 职业资格证/注册证-附件（历史表FPInformations.Attachments2） */
    @ApiModelProperty(value = "职业资格证/注册证-附件（历史表FPInformations.Attachments2）", position = 32)
    private String attachments2;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 33)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 34)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 35)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 36)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 37)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
