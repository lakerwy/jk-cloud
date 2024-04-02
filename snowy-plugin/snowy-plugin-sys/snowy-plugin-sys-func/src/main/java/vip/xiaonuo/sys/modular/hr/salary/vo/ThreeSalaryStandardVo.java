package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryStandard;
import java.util.List;

/**
 * 月度工资标准 - 列表 分三个列表 无分页
 */

@Getter
@Setter
public class ThreeSalaryStandardVo {

    /** 总部 - 人员月工资标准列表 */
    @ApiModelProperty(value = "总部 - 人员月工资标准列表 ", position = 1)
    private List<SalaryStandard> zbList;

    /** 分公司 - 人员月工资标准列表 */
    @ApiModelProperty(value = "分公司 - 人员月工资标准列表 ", position = 2)
    private List<SalaryStandard> fgsList;

    /** 其他人员 - 人员月工资标准列表 */
    @ApiModelProperty(value = "其他人员 - 人员月工资标准列表 ", position = 3)
    private List<SalaryStandard> wpList;
}
