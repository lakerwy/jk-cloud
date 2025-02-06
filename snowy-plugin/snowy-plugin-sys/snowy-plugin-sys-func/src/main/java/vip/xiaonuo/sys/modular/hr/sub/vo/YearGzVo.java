package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 年度 - 工资 统计
 */

@Getter
@Setter
public class YearGzVo {

    /** 所属大区 */
    @ApiModelProperty(value = "所属大区", position = 1)
    private String region;

    /** 部门Id - 分公司Id */
    @ApiModelProperty(value = "部门Id - 分公司Id", position = 2)
    private String orgId;

    /** 部门 - 分公司 */
    @ApiModelProperty(value = "部门 - 分公司", position = 3)
    private String orgName;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 4)
    private String yM;

    /** 1月 */
    @ApiModelProperty(value = "1月", position = 11)
    private BigDecimal num1;

    /** 2月 */
    @ApiModelProperty(value = "2月", position = 12)
    private BigDecimal num2;

    /** 3月 */
    @ApiModelProperty(value = "3月", position = 13)
    private BigDecimal num3;

    /** 4月 */
    @ApiModelProperty(value = "4月", position = 14)
    private BigDecimal num4;

    /** 5月 */
    @ApiModelProperty(value = "5月", position = 15)
    private BigDecimal num5;

    /** 6月 */
    @ApiModelProperty(value = "6月", position = 16)
    private BigDecimal num6;

    /** 7月 */
    @ApiModelProperty(value = "7月", position = 17)
    private BigDecimal num7;

    /** 8月 */
    @ApiModelProperty(value = "8月", position = 18)
    private BigDecimal num8;

    /** 9月 */
    @ApiModelProperty(value = "9月", position = 19)
    private BigDecimal num9;

    /** 10月 */
    @ApiModelProperty(value = "10月", position = 20)
    private BigDecimal num10;

    /** 11月 */
    @ApiModelProperty(value = "11月", position = 21)
    private BigDecimal num11;

    /** 12月 */
    @ApiModelProperty(value = "12月", position = 22)
    private BigDecimal num12;

    /** 小计 */
    @ApiModelProperty(value = "小计", position = 23)
    private BigDecimal total;
}
