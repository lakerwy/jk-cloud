package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 导入 社保|公积金|专项扣除 数据
 */

@Getter
@Setter
public class ImportInsGjjTax {

    /** 导入类别：社保|公积金|专项扣除|无工资-社保|无工资-公积金  */
    @ApiModelProperty(value = "导入类别：社保|公积金|专项扣除|无工资-社保|无工资-公积金 ", position = 1)
    private String type;

    /** 社保|公积金|专项扣除 数据 */
    @ApiModelProperty(value = "社保|公积金|专项扣除|无工资-社保|无工资-公积金 数据", position = 2)
    private List<SalaryImportInsGjjTaxParam> importList;
}
