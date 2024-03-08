package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 员工福利 ： 五一|十一|元旦 福利 数据
 */

@Getter
@Setter
public class ImportPay {

    /** 导入 员工福利 ： 五一|十一|元旦 福利 数据 */
    @ApiModelProperty(value = "导入 员工福利 ： 五一|十一|元旦 福利 数据", position = 2)
    private List<SalaryImportPayParam> importList;
}
