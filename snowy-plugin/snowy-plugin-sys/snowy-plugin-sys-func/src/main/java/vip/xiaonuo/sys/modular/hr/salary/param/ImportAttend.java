package vip.xiaonuo.sys.modular.hr.salary.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 考勤数据
 */

@Getter
@Setter
public class ImportAttend {
    /** 考勤数据 */
    @ApiModelProperty(value = "考勤数据", position = 1)
    private List<SalaryImportAttendParam> importList;
}
