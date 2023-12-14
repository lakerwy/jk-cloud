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
package vip.xiaonuo.biz.modular.cad.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAD-子项-专业-人员关系表添加参数
 *
 * @author yong.ye
 * @date  2023/11/29 10:39
 **/
@Getter
@Setter
public class CadSubItemUserAddParam {

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 3)
    private String subItemId;

    /** 外键：cad用户Id -> BIZ_CAD_USER.ID */
    @ApiModelProperty(value = "外键：cad用户Id -> BIZ_CAD_USER.ID", position = 4)
    private String cadUserId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 5)
    private String professionId;

    /** 是否专业负责人 */
    @ApiModelProperty(value = "是否专业负责人", position = 6)
    private String isMajor;

    /** 是否审定 */
    @ApiModelProperty(value = "是否审定", position = 7)
    private String isExamine;

    /** 是否审核 */
    @ApiModelProperty(value = "是否审核", position = 8)
    private String isAudit;

    /** 是否校对 */
    @ApiModelProperty(value = "是否校对", position = 9)
    private String isProofread;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

}
