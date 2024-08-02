package vip.xiaonuo.biz.modular.prj.expense.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Excel 打印版 参数实体
 */

@Getter
@Setter
public class ExcelParam {

    /** 报表类型：FEE 费用报销  TRAFFIC 差旅费用报销 DEBIT 借支单 */
    @ApiModelProperty(value = "报表类型：FEE 费用报销  TRAFFIC 差旅费用报销 DEBIT 借支单",required = true)
    @NotBlank(message = "报表类型不能为空")
    private String reportType;

    /** 请求参数json字符串 后端直接解析为对应实体 */
    @ApiModelProperty(value = "请求参数json字符串 后端直接解析为对应实体",required = true)
    @NotBlank(message = "参数不能为空")
    private String paramJson;

}
