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
package vip.xiaonuo.biz.modular.plan.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合约规划 - 申报（项目承包方）- 成本编辑参数
 *
 * @author yong.ye
 * @date  2024/04/22 11:44
 **/
@Getter
@Setter
public class ContractUnitCostEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 成本-序号 */
    @ApiModelProperty(value = "成本-序号", position = 2)
    private Integer itemNo;

    /** 成本-成本项名称 */
    @ApiModelProperty(value = "成本-成本项名称", position = 3)
    private String itemName;

    /** 成本-成本费用包含内容及来源说明（分包单位名称列全，询价有具体工程量明细的需要上传附件） */
    @ApiModelProperty(value = "成本-成本费用包含内容及来源说明（分包单位名称列全，询价有具体工程量明细的需要上传附件）", position = 4)
    private String itemContent;

    /** 成本-成本费用包含内容及来源说明（附件） */
    @ApiModelProperty(value = "成本-成本费用包含内容及来源说明（附件）", position = 5)
    private String itemAttach;

    /** 成本-费用金额（含税，元） */
    @ApiModelProperty(value = "成本-费用金额（含税，元）", position = 6)
    private BigDecimal expense;

    /** 成本-费用占比（%） */
    @ApiModelProperty(value = "成本-费用占比（%）", position = 7)
    private Float expenseRate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 外键：BIZ_CONTRACT_UNIT.ID */
    @ApiModelProperty(value = "外键：BIZ_CONTRACT_UNIT.ID", position = 16)
    private String bizContractUnitId;

    /** 外键：BIZ_CONTRACT_PLAN.ID */
    @ApiModelProperty(value = "外键：BIZ_CONTRACT_PLAN.ID", position = 17)
    private String bizContractPlanId;
}
