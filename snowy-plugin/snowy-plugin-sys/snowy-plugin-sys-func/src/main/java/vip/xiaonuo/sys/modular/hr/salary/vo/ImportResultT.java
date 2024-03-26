package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.salary.entity.SalaryImportTemp;

import java.util.List;

/**
 * 导入结果<T> 通用导入结果
 */

@Getter
@Setter
public class ImportResultT {

    /** 导入-总记录数 */
    @ApiModelProperty(value = "导入-总记录数", position = 3)
    private int total;

    /** 导入-校验无误记录数 */
    @ApiModelProperty(value = "导入-校验无误记录数", position = 4)
    private int sucCount;

    /** 导入-校验有误记录数 */
    @ApiModelProperty(value = "导入-校验有误记录数", position = 5)
    private int errCount;

    /** 导入-校验结果集 */
    @ApiModelProperty(value = "导入-校验结果集", position = 6)
    private List<?> resultList;

}
