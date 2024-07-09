package vip.xiaonuo.biz.modular.prj.expense.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * excel报表参数
 */

@Getter
@Setter
public class ExcelParam {
    /** 主键id */
    @ApiModelProperty(value = "主键id",required = true)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 报表类型：1 费用报销  2 差旅费用报销 3 借支单 */
    @ApiModelProperty(value = "报表类型：1 费用报销  2 差旅费用报销 3 借支单",required = true)
    @NotBlank(message = "报表类型不能为空")
    private String reportType;
}
