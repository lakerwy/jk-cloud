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
package vip.xiaonuo.biz.modular.card.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Ecard-电子名片编辑参数
 *
 * @author yong.ye
 * @date  2023/11/13 11:22
 **/
@Getter
@Setter
public class EcardsEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "cardId不能为空")
    private String cardId;

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 3)
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名", position = 4)
    private String userName;

    /** 电子名片小程序openId */
    @ApiModelProperty(value = "电子名片小程序openId", position = 5)
    private String openId;

    /** 职称 */
    @ApiModelProperty(value = "职称", position = 6)
    private String proHonors;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 7)
    private String phone;

    /** 电子邮箱 */
    @ApiModelProperty(value = "电子邮箱", position = 8)
    private String email;

    /** 网址 */
    @ApiModelProperty(value = "网址", position = 9)
    private String netUrl;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 10)
    private String position;

    /** 手机号2 */
    @ApiModelProperty(value = "手机号2", position = 11)
    private String postalCode;

    /** 电子名片照片 */
    @ApiModelProperty(value = "电子名片照片", position = 12)
    private String cardImgUrl;

    /** 微信url */
    @ApiModelProperty(value = "微信url", position = 13)
    private String perWeiXinUrl;

    /** 名片url */
    @ApiModelProperty(value = "名片url", position = 14)
    private String perCardUrl;

    /** 旧的-用户Id */
    @ApiModelProperty(value = "旧的-用户Id", position = 20)
    private String oldUserId;
}
