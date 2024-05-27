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
package vip.xiaonuo.biz.modular.develop.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 需求单 - 确认流程添加参数
 *
 * @author yong.ye
 * @date  2024/05/27 16:09
 **/
@Getter
@Setter
public class DevConfirmAddParam {

    /** 外键(需求单Id)：BIZ_DEV_FORM.ID */
    @ApiModelProperty(value = "外键(需求单Id)：BIZ_DEV_FORM.ID", position = 2)
    private String bizDevFormId;

    /** 关联需求单 */
    @ApiModelProperty(value = "关联需求单", position = 3)
    private String relatedName;

    /** 需求单流水号 */
    @ApiModelProperty(value = "需求单流水号", position = 4)
    private String relatedSn;

    /** 预估成本（元） */
    @ApiModelProperty(value = "预估成本（元）", position = 5)
    private BigDecimal estimagedCost;

    /** 实际成本（元） */
    @ApiModelProperty(value = "实际成本（元）", position = 6)
    private BigDecimal actualCost;

    /** 需求结算类型 */
    @ApiModelProperty(value = "需求结算类型", position = 7)
    private String settlementType;

    /** 需求情况说明 */
    @ApiModelProperty(value = "需求情况说明", position = 8)
    private String situationDesc;

    /** 需求终止原因 */
    @ApiModelProperty(value = "需求终止原因", position = 9)
    private String readjustReason;

    /** 需求证明附件 */
    @ApiModelProperty(value = "需求证明附件", position = 10)
    private String proofAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String memo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 19)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 20)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 21)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 22)
    private String status;

}
