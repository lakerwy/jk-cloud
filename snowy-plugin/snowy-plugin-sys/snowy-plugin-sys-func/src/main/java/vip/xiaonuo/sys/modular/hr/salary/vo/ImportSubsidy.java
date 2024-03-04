package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.param.SalaryImportSubsidyParam;

import java.util.List;

/**
 * 导入 补贴数据
 */

@Getter
@Setter
public class ImportSubsidy {

    /** 导入类别：值班补贴|讲师津贴|  */
    @ApiModelProperty(value = "导入类别：值班补贴|讲师津贴|", position = 1)
    private String type;

    /** 月工资标准 */
    @ApiModelProperty(value = "月工资标准", position = 2)
    private List<SalaryImportSubsidyParam> importList;
}
