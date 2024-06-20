package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 成本归属 - 社保|公积金|汇总 维度 统计
 */

@Getter
@Setter
public class CostReportVo {
    /** 成本归属-部门-ID */
    @ApiModelProperty(value = "成本归属-部门-ID", position = 1)
    private String deptId;

    /** 成本归属-部门 */
    @ApiModelProperty(value = "成本归属-部门", position = 2)
    private String dept;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 3)
    private String yearMonth;

    /** 缴纳人数|月度发薪人数 */
    @ApiModelProperty(value = "缴纳人数|月度发薪人数", position = 4)
    private Double payNum;

    /** 工资|薪酬 */
    @ApiModelProperty(value = "工资|薪酬", position = 5)
    private BigDecimal gz;

    /** 个人承担|社会保险 */
    @ApiModelProperty(value = "个人承担|社会保险", position = 6)
    private BigDecimal ins;

    /** 单位成本|单位实际成本|住房公积金 */
    @ApiModelProperty(value = "单位成本|单位实际成本|住房公积金", position = 7)
    private BigDecimal gjj;

    /** 注册补贴 */
    @ApiModelProperty(value = "注册补贴", position = 8)
    private BigDecimal register;

    /** 员工福利 */
    @ApiModelProperty(value = "员工福利", position = 9)
    private BigDecimal welfare;

    /** 外业补贴 */
    @ApiModelProperty(value = "外业补贴", position = 10)
    private BigDecimal field;

    /** 小计|合计 */
    @ApiModelProperty(value = "小计|合计", position = 11)
    private BigDecimal subTotal;
}
