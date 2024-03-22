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
 * 财务系统-销售发票开具申请-开票确认【正式销售发票表】添加参数
 *
 * @author yong.ye
 * @date  2024/03/19 10:19
 **/
@Getter
@Setter
public class InvoiceSaleBillAddParam {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 发票号码 */
    @ApiModelProperty(value = "发票号码", position = 3)
    private String fpNo;

    /** 开票日期 */
    @ApiModelProperty(value = "开票日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date kpDate;

    /** 发票内容 */
    @ApiModelProperty(value = "发票内容", position = 5)
    private String content;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 6)
    private Float taxRate;

    /** 价税合计 */
    @ApiModelProperty(value = "价税合计", position = 7)
    private BigDecimal amount;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 8)
    private BigDecimal taxAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 外键：BIZ_INVOICE_SALE.ID */
    @ApiModelProperty(value = "外键：BIZ_INVOICE_SALE.ID", position = 17)
    private String invoiceSaleId;

    /** 发票状态：正常|作废|红冲 */
    @ApiModelProperty(value = "发票状态：正常|作废|红冲", position = 18)
    private String fpStatus;

    /** 异动：作废流程实例Id */
    @ApiModelProperty(value = "异动：作废流程实例Id", position = 19)
    private String cancelProcessInstanceId;

    /** 异动：红冲流程实例Id */
    @ApiModelProperty(value = "异动：红冲流程实例Id", position = 20)
    private String redProcessInstanceId;

}
