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
package vip.xiaonuo.biz.modular.prj.plan.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目计划付款单-本次提供发票添加参数
 *
 * @author yong.ye
 * @date  2023/09/28 11:28
 **/
@Getter
@Setter
public class BizPrjPlanInvoiceAddParam {

    /** 发票内容 */
    @ApiModelProperty(value = "发票内容", position = 2)
    private String content;

    /** 普票/专票 */
    @ApiModelProperty(value = "普票/专票", position = 3)
    private String type;

    /** 发票份数 */
    @ApiModelProperty(value = "发票份数", position = 4)
    private String page;

    /** 税率(专用发票) */
    @ApiModelProperty(value = "税率(专用发票)", position = 5)
    private String rate;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 6)
    private Double seAmount;

    /** 发票金额 */
    @ApiModelProperty(value = "发票金额", position = 7)
    private Double fpAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 项目计划付款单主表主键BIZ_PRJ_PLAN.Id */
    @ApiModelProperty(value = "项目计划付款单主表主键BIZ_PRJ_PLAN.Id", position = 14)
    private String planId;

}
