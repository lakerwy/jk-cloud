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
package vip.xiaonuo.biz.modular.crm.calocks.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-CRM-CA登记编辑参数
 *
 * @author yong.ye
 * @date  2023/06/30 16:35
 **/
@Getter
@Setter
public class CalocksEditParam {

    /** 主键ID（历史表CALocks.Id） */
    @ApiModelProperty(value = "主键ID（历史表CALocks.Id）", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 登记人（历史表CALocks.Opt） */
//    @ApiModelProperty(value = "登记人（历史表CALocks.Opt）", position = 2)
//    private String opt;

    /** 登记日期（历史表CALocks.OptDate） */
//    @ApiModelProperty(value = "登记日期（历史表CALocks.OptDate）", position = 3)
//    private Date optDate;

    /** 流水号SN（历史表CALocks.SN） */
//    @ApiModelProperty(value = "流水号SN（历史表CALocks.SN）", position = 4)
//    private String sn;

    /** 归属单位（历史表CALocks.UnitName） */
    @ApiModelProperty(value = "归属单位（历史表CALocks.UnitName）", position = 5)
    private String unitName;

    /** 发行机构（历史表CALocks.Issuer） */
    @ApiModelProperty(value = "发行机构（历史表CALocks.Issuer）", position = 6)
    private String issuer;

    /** CA锁保管人（历史表CALocks.Keeper） */
    @ApiModelProperty(value = "CA锁保管人（历史表CALocks.Keeper）", position = 7)
    private String keeper;

    /** 网址（历史表CALocks.URL） */
    @ApiModelProperty(value = "网址（历史表CALocks.URL）", position = 8)
    private String url;

    /** 申请人（历史表CALocks.Applicant） */
    @ApiModelProperty(value = "申请人（历史表CALocks.Applicant）", position = 9)
    private String applicant;

    /** 申请人手机（历史表CALocks.ApplicantPhone） */
    @ApiModelProperty(value = "申请人手机（历史表CALocks.ApplicantPhone）", position = 10)
    private String applicantPhone;

    /** 申请人邮箱（历史表CALocks.ApplicantEmail） */
    @ApiModelProperty(value = "申请人邮箱（历史表CALocks.ApplicantEmail）", position = 11)
    private String applicantEmail;

    /** 申请日期（历史表CALocks.ApplyDate） */
    @ApiModelProperty(value = "申请日期（历史表CALocks.ApplyDate）", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    /** 有效期（历史表CALocks.Indate） */
    @ApiModelProperty(value = "有效期（历史表CALocks.Indate）", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date indate;

    /** 状态（历史表CALocks.Status） */
    @ApiModelProperty(value = "状态（历史表CALocks.Status）", position = 14)
    private String status;

    /** 法人锁密码（历史表CALocks.Password） */
    @ApiModelProperty(value = "法人锁密码（历史表CALocks.Password）", position = 15)
    private String password;

    /** CA登录账号（历史表CALocks.Account1） */
    @ApiModelProperty(value = "CA登录账号（历史表CALocks.Account1）", position = 16)
    private String account1;

    /** CA密码（历史表CALocks.Password1） */
    @ApiModelProperty(value = "CA密码（历史表CALocks.Password1）", position = 17)
    private String password1;

    /** 注册登录账号（历史表CALocks.Account2） */
    @ApiModelProperty(value = "注册登录账号（历史表CALocks.Account2）", position = 18)
    private String account2;

    /** 注册登录密码（历史表CALocks.Password2） */
    @ApiModelProperty(value = "注册登录密码（历史表CALocks.Password2）", position = 19)
    private String password2;

    /** 备注（历史表CALocks.Note） */
    @ApiModelProperty(value = "备注（历史表CALocks.Note）", position = 20)
    private String note;

}
