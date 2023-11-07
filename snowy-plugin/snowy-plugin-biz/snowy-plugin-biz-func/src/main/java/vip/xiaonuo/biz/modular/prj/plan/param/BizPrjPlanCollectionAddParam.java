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
 * 业务表-项目计划付款单-收款单位添加参数
 *
 * @author yong.ye
 * @date  2023/09/28 11:28
 **/
@Getter
@Setter
public class BizPrjPlanCollectionAddParam {

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 22)
    private String subContractNo;

    /** 分包合同名称 */
    @ApiModelProperty(value = "分包合同名称", position = 23)
    private String subContractName;

    /** 分包合同金额 */
    @ApiModelProperty(value = "分包合同金额", position = 24)
    private BigDecimal subContractAmount;

    /** 收款单位编码 */
    @ApiModelProperty(value = "收款单位编码", position = 2)
    private String unitCode;

    /** 收款单位名称 */
    @ApiModelProperty(value = "收款单位名称", position = 3)
    private String unitName;

    /** 银行账号 */
    @ApiModelProperty(value = "银行账号", position = 4)
    private String bankNo;

    /** 开户行 */
    @ApiModelProperty(value = "开户行", position = 5)
    private String bankName;

    /** 款项内容 */
    @ApiModelProperty(value = "款项内容", position = 6)
    private String content;

    /** 计划付款金额 */
    @ApiModelProperty(value = "计划付款金额", position = 7)
    private BigDecimal jhfkAmount;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 8)
    private String nature;

    /** 合同付款条款信息 */
    @ApiModelProperty(value = "合同付款条款信息", position = 9)
    private String payTerms;

    /** 合作方付款计划/后续安排 */
    @ApiModelProperty(value = "合作方付款计划/后续安排", position = 10)
    private String payPlan;

    /** 附件上传 */
    @ApiModelProperty(value = "附件上传", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

    /** 项目计划付款单主表主键BIZ_PRJ_PLAN.Id */
    @ApiModelProperty(value = "项目计划付款单主表主键BIZ_PRJ_PLAN.Id", position = 18)
    private String planId;

}
