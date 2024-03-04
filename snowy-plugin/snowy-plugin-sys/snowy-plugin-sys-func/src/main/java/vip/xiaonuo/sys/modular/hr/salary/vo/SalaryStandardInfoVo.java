package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 月度工资标准 实体对象 (简化信息)
 */

@Getter
@Setter
public class SalaryStandardInfoVo {
    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 类别：工资|奖金 */
    @ApiModelProperty(value = "类别：工资|奖金", position = 2)
    private String type;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 3)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 6)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 6)
    private String personnelId;

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 15)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 16)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 17)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 18)
    private BigDecimal standardAmount;

    /** 餐饮补助 */
    @ApiModelProperty(value = "餐饮补助", position = 19)
    private BigDecimal mealAmount;

}
