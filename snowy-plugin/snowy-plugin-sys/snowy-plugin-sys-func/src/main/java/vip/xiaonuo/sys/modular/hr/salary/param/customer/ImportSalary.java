package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 确认后的 正式的 工资数据
 */

@Getter
@Setter
public class ImportSalary {

    /** 导入 确认后的 正式的 工资数据 */
    @ApiModelProperty(value = "导入 确认后的 正式的 工资数据", position = 2)
    private List<SalaryImportSalaryParam> importList;
}
