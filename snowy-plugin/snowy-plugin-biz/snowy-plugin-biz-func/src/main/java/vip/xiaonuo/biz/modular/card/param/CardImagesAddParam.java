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
 * Ecard-电子名片-图片添加参数
 *
 * @author yong.ye
 * @date  2024/02/18 15:40
 **/
@Getter
@Setter
public class CardImagesAddParam {

    /** 图片类型 */
    @ApiModelProperty(value = "图片类型", position = 3)
    private String type;

    /** 图片 */
    @ApiModelProperty(value = "图片", position = 4)
    private String image;

    /** 外键-电子名片Id */
    @ApiModelProperty(value = "外键-电子名片Id", position = 5)
    private String cardId;

    /** 排序 */
    @ApiModelProperty(value = "排序", position = 6)
    private Integer sortIndex;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

}
