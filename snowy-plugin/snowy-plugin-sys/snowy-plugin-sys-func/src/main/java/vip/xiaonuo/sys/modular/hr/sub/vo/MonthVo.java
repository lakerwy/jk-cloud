package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 月度工资 统计
 */
@Getter
@Setter
public class MonthVo {

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 1)
    private Integer no;

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

    /** 工资总额 - 工资应发人数 */
    @ApiModelProperty(value = "工资总额 - 工资应发人数", position = 11)
    private Integer gzYfNum = 0;

    /** 工资总额 - 工资实发人数 */
    @ApiModelProperty(value = "工资总额 - 工资实发人数", position = 12)
    private Integer gzSfNum = 0;

    /** 工资总额 - 应发工资合计 */
    @ApiModelProperty(value = "工资总额 - 应发工资合计", position = 13)
    private BigDecimal yfGzTotal = BigDecimal.ZERO;

    /** 工资总额 - 五险（个人部分） */
    @ApiModelProperty(value = "工资总额 - 五险（个人部分）", position = 14)
    private BigDecimal insTotal = BigDecimal.ZERO;

    /** 工资总额 - 公积金（个人部分） */
    @ApiModelProperty(value = "工资总额 - 公积金（个人部分）", position = 15)
    private BigDecimal gjjTotal = BigDecimal.ZERO;

    /** 工资总额 - 工资应扣税额合计 */
    @ApiModelProperty(value = "工资总额 - 工资应扣税额合计", position = 16)
    private BigDecimal gzTaxTotal = BigDecimal.ZERO;

    /** 工资总额 - 实发工资合计 */
    @ApiModelProperty(value = "工资总额 - 实发工资合计", position = 17)
    private BigDecimal sfGzTotal = BigDecimal.ZERO;

    /** 奖金 - 奖金应发人数 */
    @ApiModelProperty(value = "奖金 - 奖金应发人数", position = 21)
    private Integer jjYfNum = 0;

    /** 奖金 - 奖金实发人数 */
    @ApiModelProperty(value = "奖金 - 奖金实发人数", position = 22)
    private Integer jjSfNum = 0;

    /** 奖金 - 应发奖金合计 */
    @ApiModelProperty(value = "奖金 - 应发奖金合计", position = 23)
    private BigDecimal yfJjTotal = BigDecimal.ZERO;

    /** 奖金 - 奖金应扣税额合计 */
    @ApiModelProperty(value = "奖金 - 奖金应扣税额合计", position = 24)
    private BigDecimal jjTaxTotal = BigDecimal.ZERO;

    /** 奖金 - 实发奖金合计 */
    @ApiModelProperty(value = "奖金 - 实发奖金合计", position = 25)
    private BigDecimal sfJjTotal = BigDecimal.ZERO;

    /** 五险一金 - 应缴五险人数 */
    @ApiModelProperty(value = "五险一金 - 应缴五险人数", position = 31)
    private Integer yjWxNum = 0;

    /** 五险一金 - 实缴五险人数 */
    @ApiModelProperty(value = "五险一金 - 实缴五险人数", position = 32)
    private Integer sjWxNum = 0;

    /** 五险一金 - 应缴五险总额 */
    @ApiModelProperty(value = "五险一金 - 应缴五险总额", position = 33)
    private BigDecimal yjWxTotal = BigDecimal.ZERO;

    /** 五险一金 - 实缴五险总额 */
    @ApiModelProperty(value = "五险一金 - 实缴五险总额", position = 34)
    private BigDecimal sjWxTotal = BigDecimal.ZERO;

    /** 五险一金 - 应缴公积金人数 */
    @ApiModelProperty(value = "五险一金 - 应缴公积金人数", position = 35)
    private Integer yjGjjNum = 0;

    /** 五险一金 - 实缴公积金人数 */
    @ApiModelProperty(value = "五险一金 - 实缴公积金人数", position = 36)
    private Integer sjGjjNum = 0;

    /** 五险一金 - 应缴公积金总额 */
    @ApiModelProperty(value = "五险一金 - 应缴公积金总额", position = 37)
    private BigDecimal yjGjjTotal = BigDecimal.ZERO;

    /** 五险一金 - 实缴公积金总额 */
    @ApiModelProperty(value = "五险一金 - 实缴公积金总额", position = 38)
    private BigDecimal sjGjjTotal = BigDecimal.ZERO;
}
