package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 员工福利（生日福利|其他福利）--发放公司
 */

@Getter
@Setter
public class SumEmpBonusData {

    /** 类别：值班补贴|讲师津贴|... */
    @ApiModelProperty(value = "类别：值班补贴|讲师津贴|...", position = 1)
    private String type;

    /** 发放公司 */
    @ApiModelProperty(value = "发放公司", position = 2)
    private String unitName;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 3)
    private String standardId;

    /** 当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计 */
    @ApiModelProperty(value = "当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计", position = 26)
    private BigDecimal sumAmount;

}
