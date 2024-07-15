package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 成本报表 - 生成 参数
 */

@Getter
@Setter
public class CostReportParam {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月",required = true)
    @NotBlank(message = "所属年月不能为空")
    private String yearMonth;

    /** 数据来源：preview - 预览工资数据 salary - 封存工资数据 */
    @ApiModelProperty(value = "数据来源：preview - 预览工资数据 salary - 封存工资数据 ",required = true)
    @NotBlank(message = "报表数据来源不能为空")
    private String dataSource;
}
