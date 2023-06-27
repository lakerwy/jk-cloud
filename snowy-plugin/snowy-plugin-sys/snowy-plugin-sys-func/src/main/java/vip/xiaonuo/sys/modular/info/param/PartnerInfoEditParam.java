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
package vip.xiaonuo.sys.modular.info.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.info.entity.PartnerFee;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 合作伙伴-基本信息编辑参数
 *
 * @author mayike
 * @date  2023/06/26 14:04
 **/
@Getter
@Setter
public class PartnerInfoEditParam {

    /** 合作伙伴ID */
    @ApiModelProperty(value = "合作伙伴ID(主键ID)", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String orgId;

    /** 实体 */
    @ApiModelProperty(value = "实体", position = 3)
    private String entity;

    /** 地址 */
    @ApiModelProperty(value = "地址", position = 4)
    private String address;

    /** 阶段 */
    @ApiModelProperty(value = "阶段", position = 5)
    private String stages;

    /** 协同客服 */
    @ApiModelProperty(value = "协同客服", position = 6)
    private String cservice;

    /** 主要联系人 */
    @ApiModelProperty(value = "主要联系人", position = 7)
    private String contact;
    /** 电话 */
    @ApiModelProperty(value = "电话", position = 8)
    private String telNumber;

    /** 伙伴状态 */
    @ApiModelProperty(value = "伙伴状态", position = 9)
    private String partnerStatus;

    /** 手机号码 */
    @ApiModelProperty(value = "手机号码", position = 10)
    private String phoneNumber;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 11)
    private String post;

    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱", position = 12)
    private String email;

    /** 伙伴分级 */
    @ApiModelProperty(value = "伙伴分级", position = 13)
    private String partnerLevel;

    /** 重要程度 */
    @ApiModelProperty(value = "重要程度", position = 14)
    private String vitalLevel;

    /** 开户银行 */
    @ApiModelProperty(value = "开户银行", position = 15)
    private String bankName;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 16)
    private String bankNumber;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 17)
    private String receivingUnits;

    /** 邮递地址 */
    @ApiModelProperty(value = "邮递地址", position = 18)
    private String mailingAddress;

    @ApiModelProperty(value = "管理费缴纳情况实体", position = 19)
    private List<PartnerFee> partnerFees;
}
