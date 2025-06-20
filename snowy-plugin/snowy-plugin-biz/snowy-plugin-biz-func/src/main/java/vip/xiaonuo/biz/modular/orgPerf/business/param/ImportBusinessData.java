package vip.xiaonuo.biz.modular.orgPerf.business.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ImportBusinessData {

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 2)
    private String orgName;

    /** 组织ID */
    @ApiModelProperty(value = "组织ID", position = 3)
    private String orgId;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private Integer year;

    /** 月份（例如：1） */
    @ApiModelProperty(value = "月份（例如：1）", position = 5)
    private Integer month;

    /** 累计新签合同额 */
    @ApiModelProperty(value = "累计新签合同额", position = 6)
    private BigDecimal newContractAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 错误信息  */
    @ApiModelProperty(value = "错误信息", position = 20)
    private String errorMsg;

}
