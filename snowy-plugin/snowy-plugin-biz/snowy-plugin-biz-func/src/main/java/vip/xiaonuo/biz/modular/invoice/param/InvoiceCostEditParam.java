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
package vip.xiaonuo.biz.modular.invoice.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务系统-成本发票申请编辑参数
 *
 * @author yong.ye
 * @date  2024/03/19 10:22
 **/
@Getter
@Setter
public class InvoiceCostEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 申请人Id */
    @ApiModelProperty(value = "申请人Id", position = 3)
    private String applyId;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 4)
    private String applyName;

    /** 开票申请编号(销售发票流程SN) */
    @ApiModelProperty(value = "开票申请编号(销售发票流程SN)", position = 5)
    private String kpSn;

    /** 合同Id */
    @ApiModelProperty(value = "合同Id", position = 6)
    private String htId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 7)
    private String htProjectName;

    /** 销售合同编号 */
    @ApiModelProperty(value = "销售合同编号", position = 8)
    private String htContractNo;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 17)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 18)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 19)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 20)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 21)
    private String extJson;

}
