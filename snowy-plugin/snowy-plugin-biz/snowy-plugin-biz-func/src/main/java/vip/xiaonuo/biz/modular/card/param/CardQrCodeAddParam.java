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
 * Ecard-电子名片-二维码添加参数
 *
 * @author yong.ye
 * @date  2024/02/21 15:52
 **/
@Getter
@Setter
public class CardQrCodeAddParam {

    /** 用户Id */
    @ApiModelProperty(value = "用户Id", position = 3)
    private String userId;

    /** 用户名称 */
    @ApiModelProperty(value = "用户名称", position = 4)
    private String userName;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 5)
    private String deptName;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** 电子名片二维码base64 */
    @ApiModelProperty(value = "电子名片二维码base64", position = 12)
    private String qrCode;

}
