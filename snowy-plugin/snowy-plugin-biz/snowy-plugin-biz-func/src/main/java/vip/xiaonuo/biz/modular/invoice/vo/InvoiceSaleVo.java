package vip.xiaonuo.biz.modular.invoice.vo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSale;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleBill;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleCost;
import vip.xiaonuo.biz.modular.invoice.entity.InvoiceSaleKp;

import java.math.BigDecimal;
import java.util.List;

/**
 * 发票系统 - 销售发票Vo（携带信息）
 */

@Getter
@Setter
public class InvoiceSaleVo extends InvoiceSale {

    /** 合同名称（ProjectName） */
    @ApiModelProperty(value = "合同名称（ProjectName）", position = 99)
    private String htProjectName;

    /** 合同金额 - 建科所占金额 (ContractAmount)*/
    @ApiModelProperty(value = "合同金额 - 建科所占金额(ContractAmount) ", position = 99)
    private BigDecimal htContractAmount;

    /** 甲方（Counterparty） */
    @ApiModelProperty(value = "甲方（Counterparty）", position = 99)
    private String htCounterparty;

    /** 责任单位（CompanyOrDepartment） */
    @ApiModelProperty(value = "责任单位（CompanyOrDepartment）", position = 99)
    private String htCompanyOrDepartment;

    /** 责任人（Owner） */
    @ApiModelProperty(value = "责任人（Owner）", position = 99)
    private String htOwner;

    /** 立项-项目类型(BIZ_PROJECT.TYPE) */
    @ApiModelProperty(value = "立项-项目类型(BIZ_PROJECT.TYPE)", position = 99)
    private String htProjectType;


    /** 【销售发票】开票信息-集合 */
    @ApiModelProperty(value = "【销售发票】开票信息-集合", position = 100)
    private List<InvoiceSaleKp> kpList;

    /** 【销售发票】成本票应收-集合 */
    @ApiModelProperty(value = "【销售发票】成本票应收-集合", position = 100)
    private List<InvoiceSaleCost> costList;

    /** 【销售发票】开票确认-集合 */
    @ApiModelProperty(value = "【销售发票】开票确认-集合", position = 100)
    private List<InvoiceSaleBill> billList;

}
