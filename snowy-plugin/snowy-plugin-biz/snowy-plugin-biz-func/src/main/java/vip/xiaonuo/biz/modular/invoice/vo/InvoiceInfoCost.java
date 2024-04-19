package vip.xiaonuo.biz.modular.invoice.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 以 销售发票号 - 维度 - 统计 供应商+税率 统计
 */


@Getter
@Setter
public class InvoiceInfoCost {

    /** 开票申请编号(销售发票流程SN) */
    @ApiModelProperty(value = "开票申请编号(销售发票流程SN)", position = 1)
    private String kpSn;

    /** 供应商名称 */
    @ApiModelProperty(value = "供应商名称", position = 2)
    private String prvName;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 3)
    private Float taxRate;

    /** 同一销售发票号 + 同一供应商 + 同一税率 - 含税金额 - 小计 */
    @ApiModelProperty(value = "同一销售发票号 + 同一供应商 + 同一税率 - 含税金额 - 小计", position = 4)
    private BigDecimal costBillTotal;





}
