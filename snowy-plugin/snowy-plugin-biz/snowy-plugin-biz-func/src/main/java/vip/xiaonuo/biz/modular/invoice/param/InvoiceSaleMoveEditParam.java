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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务系统-销售发票-作废|红冲异动申请编辑参数
 *
 * @author yong.ye
 * @date  2024/03/19 10:21
 **/
@Getter
@Setter
public class InvoiceSaleMoveEditParam {

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

    /** 异动类型：作废|红冲 */
    @ApiModelProperty(value = "异动类型：作废|红冲", position = 5)
    private String moveType;

    /** 外键-【发票号码】：BIZ_INVOICE_SALE_BILL.ID */
    @ApiModelProperty(value = "外键-【发票号码】：BIZ_INVOICE_SALE_BILL.ID", position = 6)
    private String invoiceSaleBillId;

    /** 理由 */
    @ApiModelProperty(value = "理由", position = 7)
    private String reason;

    /** 发票上传 */
    @ApiModelProperty(value = "发票上传", position = 8)
    private String fpAttach;

    /** 红字信息表上传 */
    @ApiModelProperty(value = "红字信息表上传", position = 9)
    private String redInfoAttach;

    /** 发票作废日期|发票红冲日期 */
    @ApiModelProperty(value = "发票作废日期|发票红冲日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date moveDate;

    /** 红冲发票号码 */
    @ApiModelProperty(value = "红冲发票号码", position = 11)
    private String redFpNo;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 12)
    private Float redTaxRate;

    /** 不含税金额 */
    @ApiModelProperty(value = "不含税金额", position = 13)
    private BigDecimal redUnTaxAmount;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 14)
    private BigDecimal redTaxAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 23)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 24)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 25)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 26)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 27)
    private String extJson;

}
