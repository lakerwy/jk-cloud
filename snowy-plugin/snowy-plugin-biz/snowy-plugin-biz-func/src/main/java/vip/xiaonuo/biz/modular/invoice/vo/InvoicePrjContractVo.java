package vip.xiaonuo.biz.modular.invoice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.invoice.entity.InvoicePrjContract;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class InvoicePrjContractVo extends InvoicePrjContract {

    /** 项目回款 - 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "项目回款 - 申请人单位：总部|分公司|零星", position = 60)
    private String applyUnit;

    /** 项目回款 - 申请人姓名 */
    @ApiModelProperty(value = "项目回款 - 申请人姓名", position = 61)
    private String applyName;

    /** 项目回款 - 申请人部门 */
    @ApiModelProperty(value = "项目回款 - 申请人部门", position = 61)
    private String orgName;

    /** 项目回款 - 收款日期 */
    @ApiModelProperty(value = "项目回款 - 收款日期", position = 62)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date skDate;

    /** 项目回款 - 收款编号 */
    @ApiModelProperty(value = "项目回款 - 收款编号", position = 63)
    private String recordSn;

    /** 项目回款 - 收款单位 */
    @ApiModelProperty(value = "项目回款 - 收款单位", position = 64)
    private String unitName;

    /** 项目回款 - 收款金额 */
    @ApiModelProperty(value = "项目回款 - 收款金额", position = 65)
    private BigDecimal amount;

    /** 项目回款 - SN流水号 */
    @ApiModelProperty(value = "项目回款 - SN流水号", position = 66)
    private String sn;

    /** 项目回款 - 模型Id */
    @ApiModelProperty(value = "项目回款 - 模型Id", position = 67)
    private String modelId;

    /** 项目回款 - 流程实例Id */
    @ApiModelProperty(value = "项目回款 - 流程实例Id", position = 68)
    private String processInstanceId;

    /** 项目回款 - 流程状态 */
    @ApiModelProperty(value = "项目回款 - 流程状态", position = 69)
    private String status;
}
