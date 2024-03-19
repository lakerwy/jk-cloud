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
 * 财务系统-成本发票申请【正式成本发票表】编辑参数
 *
 * @author yong.ye
 * @date  2024/03/19 10:23
 **/
@Getter
@Setter
public class InvoiceCostBillEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 2)
    private String no;

    /** 开票日期 */
    @ApiModelProperty(value = "开票日期", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date kpDate;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 4)
    private String fbContractNo;

    /** 分包合同Id */
    @ApiModelProperty(value = "分包合同Id", position = 5)
    private String fbHetongId;

    /** 供应商名称 */
    @ApiModelProperty(value = "供应商名称", position = 6)
    private String prvName;

    /** 发票类型 */
    @ApiModelProperty(value = "发票类型", position = 7)
    private String fpType;

    /** 发票号码 */
    @ApiModelProperty(value = "发票号码", position = 8)
    private String fpNo;

    /** 发票内容 */
    @ApiModelProperty(value = "发票内容", position = 9)
    private String content;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 10)
    private Float taxRate;

    /** 不含税金额 */
    @ApiModelProperty(value = "不含税金额", position = 11)
    private BigDecimal unTaxAmount;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 12)
    private BigDecimal taxAmount;

    /** 发票总额 */
    @ApiModelProperty(value = "发票总额", position = 13)
    private BigDecimal amount;

    /** 收款单位编码 */
    @ApiModelProperty(value = "收款单位编码", position = 14)
    private String unitCode;

    /** 收款单位名称 */
    @ApiModelProperty(value = "收款单位名称", position = 15)
    private String unitName;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 16)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 17)
    private String memo;

    /** 外键：BIZ_INVOICE_COST.ID */
    @ApiModelProperty(value = "外键：BIZ_INVOICE_COST.ID", position = 25)
    private String invoiceCostId;

}
