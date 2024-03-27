package vip.xiaonuo.biz.modular.invoice.param.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 导入-项目回款-参数
 */

@Getter
@Setter
public class ImportRefundParam {

    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 1)
    private String applyUnit;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 2)
    private String applyName;

    /** 收款日期 */
    @ApiModelProperty(value = "收款日期", position = 3)
    private String skDate;

    /** 收款编号 */
    @ApiModelProperty(value = "收款编号", position = 6)
    private String recordSn;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 5)
    private String unitName;

    /** 收款金额 */
    @ApiModelProperty(value = "收款金额", position = 6)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 错误信息  */
    @ApiModelProperty(value = "错误信息", position = 100)
    private String errorMsg;
}
