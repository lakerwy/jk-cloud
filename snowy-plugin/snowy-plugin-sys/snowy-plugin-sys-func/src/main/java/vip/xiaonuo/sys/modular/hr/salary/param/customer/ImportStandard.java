package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 月工资标准
 */

@Getter
@Setter
public class ImportStandard {
    /** 补贴数据 */
    @ApiModelProperty(value = "补贴数据", position = 1)
    private List<SalaryImportStandardParam> importList;
}
