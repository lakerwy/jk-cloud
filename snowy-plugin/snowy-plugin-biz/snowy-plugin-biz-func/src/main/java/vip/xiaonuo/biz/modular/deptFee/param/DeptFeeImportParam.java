package vip.xiaonuo.biz.modular.deptFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DeptFeeImportParam {

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 2)
    private String deptName;

    /** 年份（例如：2025） */
    @ApiModelProperty(value = "年份（例如：2025）", position = 4)
    private String year;

    /** 季度（例如：1） */
    @ApiModelProperty(value = "季度（例如：1）", position = 5)
    private String season;

    /** 部门费用 */
    @ApiModelProperty(value = "部门费用", position = 6)
    private BigDecimal deptFee;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 错误信息  */
    @ApiModelProperty(value = "错误信息", position = 20)
    private String errorMsg;

}
