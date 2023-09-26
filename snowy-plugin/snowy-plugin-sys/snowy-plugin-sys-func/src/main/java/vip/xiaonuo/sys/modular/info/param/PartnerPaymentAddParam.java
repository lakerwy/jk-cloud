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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-合作伙伴开户银行信息添加参数
 *
 * @author mayike
 * @date  2023/07/11 15:12
 **/
@Getter
@Setter
public class PartnerPaymentAddParam {

    /** 组织架构，伙伴ID */
    @ApiModelProperty(value = "伙伴ID", position = 3)
    private String infoId;

    /** 开户银行 */
    @ApiModelProperty(value = "开户银行", position = 4)
    private String bankName;

    /** 账号 */
    @ApiModelProperty(value = "账号", position = 5)
    private String bankNumber;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 6)
    private String receivingUnits;

    /** 邮递地址 */
    @ApiModelProperty(value = "邮递地址", position = 7)
    private String mailingAddress;

    /** 收款类型 */
    @ApiModelProperty(value = "收款类型", position = 8)
    private String type;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String note;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

}
