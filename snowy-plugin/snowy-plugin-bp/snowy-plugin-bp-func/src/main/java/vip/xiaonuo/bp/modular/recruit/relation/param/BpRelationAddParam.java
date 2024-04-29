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
package vip.xiaonuo.bp.modular.recruit.relation.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商人员信息表添加参数
 *
 * @author hxw
 * @date  2024/04/17 15:49
 **/
@Getter
@Setter
public class BpRelationAddParam {

    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID", position = 2)
    private String supplierId;

    /** 管理员姓名 */
    @ApiModelProperty(value = "管理员姓名", position = 3)
    private String adminName;

    /** 管理员手机号 */
    @ApiModelProperty(value = "管理员手机号", position = 4)
    private String adminPhone;

    /** 管理员证件号码 */
    @ApiModelProperty(value = "管理员证件号码", position = 5)
    private String adminIdentityCard;

    /** 管理员照片 */
    @ApiModelProperty(value = "管理员照片", position = 6)
    private String adminImage;

    /** 投标专用手机号 */
    @ApiModelProperty(value = "投标专用手机号", position = 7)
    private String dedicatedPhone;

    /** 备用管理员姓名 */
    @ApiModelProperty(value = "备用管理员姓名", position = 8)
    private String standbyadminName;

    /** 备用管理员手机号 */
    @ApiModelProperty(value = "备用管理员手机号", position = 9)
    private String standbyadminPhone;

    /** 备用管理员证件号码 */
    @ApiModelProperty(value = "备用管理员证件号码", position = 10)
    private String standbyadminIdentityCard;

    /** 备用管理员照片 */
    @ApiModelProperty(value = "备用管理员照片", position = 11)
    private String standbyadminImage;

    /** 印章管理员姓名 */
    @ApiModelProperty(value = "印章管理员姓名", position = 12)
    private String signetadminName;

    /** 印章管理员手机号 */
    @ApiModelProperty(value = "印章管理员手机号", position = 13)
    private String signetadminPhone;

    /** 印章管理员证件号码 */
    @ApiModelProperty(value = "印章管理员证件号码", position = 14)
    private String signetadminIdentityCard;

    /** 印章管理员邮箱 */
    @ApiModelProperty(value = "印章管理员邮箱", position = 15)
    private String signetadminMailbox;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名", position = 16)
    private String businessadminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号", position = 17)
    private String businessadminPhone;

    /** 业务负责人身份证扫描件 */
    @ApiModelProperty(value = "业务负责人身份证扫描件", position = 18)
    private String businessadminImage;

    /** 业务负责人微信号 */
    @ApiModelProperty(value = "业务负责人微信号", position = 19)
    private String wechatNumber;

    /** 业务负责人邮箱 */
    @ApiModelProperty(value = "业务负责人邮箱", position = 20)
    private String businessadminMailbox;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 21)
    private String tenantId;
}
