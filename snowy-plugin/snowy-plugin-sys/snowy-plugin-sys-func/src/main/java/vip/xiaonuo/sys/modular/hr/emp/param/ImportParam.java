package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImportParam {

    /** 员工月度考勤-集合 */
    @ApiModelProperty(value = "员工月度考勤-集合", position = 100)
    private List<EmpAttendImportParam> importData;
}
