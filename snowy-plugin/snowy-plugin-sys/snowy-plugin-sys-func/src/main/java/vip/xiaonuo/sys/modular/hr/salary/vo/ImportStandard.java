package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryImportStandardParam;

import java.util.List;

/**
 * 导入 月工资标准
 */

@Getter
@Setter
public class ImportStandard {
    /** 月工资标准 */
    @ApiModelProperty(value = "月工资标准", position = 1)
    private List<SalaryImportStandardParam> importList;
}
