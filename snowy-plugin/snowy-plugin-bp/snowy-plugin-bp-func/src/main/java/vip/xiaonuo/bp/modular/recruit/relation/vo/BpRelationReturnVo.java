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
package vip.xiaonuo.bp.modular.recruit.relation.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 供应商人员信息表返回参数
 *
 * @author hxw
 * @date  2024/04/17 15:49
 **/
@Getter
@Setter
public class BpRelationReturnVo {

    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    /** 管理员姓名 */
    @ApiModelProperty(value = "管理员姓名")
    private String adminName;

    /** 管理员手机号 */
    @ApiModelProperty(value = "管理员手机号")
    private String adminPhone;

    /** 管理员证件号码 */
    @ApiModelProperty(value = "管理员证件号码")
    private String adminIdentityCard;

    /** 管理员照片 */
    @ApiModelProperty(value = "管理员照片")
    private String adminImage;

    /** 投标专用手机号 */
    @ApiModelProperty(value = "投标专用手机号")
    private String dedicatedPhone;

    /** 备用管理员姓名 */
    @ApiModelProperty(value = "备用管理员姓名")
    private String standbyadminName;

    /** 备用管理员手机号 */
    @ApiModelProperty(value = "备用管理员手机号")
    private String standbyadminPhone;

    /** 备用管理员证件号码 */
    @ApiModelProperty(value = "备用管理员证件号码")
    private String standbyadminIdentityCard;

    /** 备用管理员照片 */
    @ApiModelProperty(value = "备用管理员照片")
    private String standbyadminImage;

    /** 印章管理员姓名 */
    @ApiModelProperty(value = "印章管理员姓名")
    private String signetadminName;

    /** 印章管理员手机号 */
    @ApiModelProperty(value = "印章管理员手机号")
    private String signetadminPhone;

    /** 印章管理员证件号码 */
    @ApiModelProperty(value = "印章管理员证件号码")
    private String signetadminIdentityCard;

    /** 印章管理员邮箱 */
    @ApiModelProperty(value = "印章管理员邮箱")
    private String signetadminMailbox;

    /** 业务负责人姓名 */
    @ApiModelProperty(value = "业务负责人姓名")
    private String businessadminName;

    /** 业务负责人手机号 */
    @ApiModelProperty(value = "业务负责人手机号")
    private String businessadminPhone;

    /** 业务负责人身份证扫描件 */
    @ApiModelProperty(value = "业务负责人身份证扫描件")
    private String businessadminImage;

    /** 业务负责人微信号 */
    @ApiModelProperty(value = "业务负责人微信号")
    private String wechatNumber;

    /** 业务负责人邮箱 */
    @ApiModelProperty(value = "业务负责人邮箱")
    private String businessadminMailbox;

}
