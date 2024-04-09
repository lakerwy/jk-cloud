package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 其他费用明细 - 数据
 */

@Getter
@Setter
public class ImportAdd {

    /** 导入 其他费用明细 - 数据 */
    @ApiModelProperty(value = "导入 其他费用明细 - 数据", position = 2)
    private List<SalaryImportAddParam> importList;
}
