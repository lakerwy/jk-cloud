package vip.xiaonuo.sys.modular.hr.sub.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 年度汇总报表 - 汇总
 */


@Getter
@Setter
public class YearVo {
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

    /** 所属年 */
    @ApiModelProperty(value = "所属年", position = 4)
    private String yM;

    /** 应发工资人数（人）： 1月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：1月人数", position = 11)
    private Integer yfGzNum1 = 0;

    /** 应发工资人数（人）：2月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：2月人数", position = 12)
    private Integer yfGzNum2 = 0;

    /** 应发工资人数（人）：3月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：3月人数", position = 13)
    private Integer yfGzNum3 = 0;

    /** 应发工资人数（人）：4月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：4月人数", position = 14)
    private Integer yfGzNum4 = 0;

    /** 应发工资人数（人）：5月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：5月人数", position = 15)
    private Integer yfGzNum5 = 0;

    /** 应发工资人数（人）：6月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：6月人数", position = 16)
    private Integer yfGzNum6 = 0;

    /** 应发工资人数（人）：7月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：7月人数", position = 17)
    private Integer yfGzNum7 = 0;

    /** 应发工资人数（人）：8月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：8月人数", position = 18)
    private Integer yfGzNum8 = 0;

    /** 应发工资人数（人）：9月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：9月人数", position = 19)
    private Integer yfGzNum9 = 0;

    /** 应发工资人数（人）：10月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：10月人数", position = 20)
    private Integer yfGzNum10 = 0;

    /** 应发工资人数（人）：11月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：11月人数", position = 21)
    private Integer yfGzNum11 = 0;

    /** 应发工资人数（人）：12月人数 */
    @ApiModelProperty(value = "应发工资人数（人）：12月人数", position = 22)
    private Integer yfGzNum12 = 0;

    /** 应发工资人数（人）：平均人数 */
    @ApiModelProperty(value = "应发工资人数（人）：平均人数", position = 23)
    private Double yfGzAvgNum = 0d;




    /** 实发工资人数（人）： 1月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：1月人数", position = 111)
    private Integer sfGzNum1 = 0;

    /** 实发工资人数（人）：2月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：2月人数", position = 112)
    private Integer sfGzNum2 = 0;

    /** 实发工资人数（人）：3月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：3月人数", position = 113)
    private Integer sfGzNum3 = 0;

    /** 实发工资人数（人）：4月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：4月人数", position = 114)
    private Integer sfGzNum4 = 0;

    /** 实发工资人数（人）：5月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：5月人数", position = 115)
    private Integer sfGzNum5 = 0;

    /** 实发工资人数（人）：6月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：6月人数", position = 116)
    private Integer sfGzNum6 = 0;

    /** 实发工资人数（人）：7月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：7月人数", position = 117)
    private Integer sfGzNum7 = 0;

    /** 实发工资人数（人）：8月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：8月人数", position = 118)
    private Integer sfGzNum8 = 0;

    /** 实发工资人数（人）：9月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：9月人数", position = 119)
    private Integer sfGzNum9 = 0;

    /** 实发工资人数（人）：10月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：10月人数", position = 120)
    private Integer sfGzNum10 = 0;

    /** 实发工资人数（人）：11月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：11月人数", position = 121)
    private Integer sfGzNum11 = 0;

    /** 实发工资人数（人）：12月人数 */
    @ApiModelProperty(value = "实发工资人数（人）：12月人数", position = 122)
    private Integer sfGzNum12 = 0;

    /** 实发工资人数（人）：平均人数 */
    @ApiModelProperty(value = "实发工资人数（人）：平均人数", position = 123)
    private Double sfGzAvgNum = 0d;





    /** 应发工资总额：1月 */
    @ApiModelProperty(value = "应发工资总额：1月", position = 211)
    private BigDecimal yfGzAmount1 = BigDecimal.ZERO;

    /** 应发工资总额：2月 */
    @ApiModelProperty(value = "应发工资总额：2月", position = 212)
    private BigDecimal yfGzAmount2 = BigDecimal.ZERO;

    /** 应发工资总额：3月 */
    @ApiModelProperty(value = "应发工资总额：3月", position = 213)
    private BigDecimal yfGzAmount3 = BigDecimal.ZERO;

    /** 应发工资总额：4月 */
    @ApiModelProperty(value = "应发工资总额：4月", position = 214)
    private BigDecimal yfGzAmount4 = BigDecimal.ZERO;

    /** 应发工资总额：5月 */
    @ApiModelProperty(value = "应发工资总额：5月", position = 215)
    private BigDecimal yfGzAmount5 = BigDecimal.ZERO;

    /** 应发工资总额：6月 */
    @ApiModelProperty(value = "应发工资总额：6月", position = 216)
    private BigDecimal yfGzAmount6 = BigDecimal.ZERO;

    /** 应发工资总额：7月 */
    @ApiModelProperty(value = "应发工资总额：7月", position = 217)
    private BigDecimal yfGzAmount7 = BigDecimal.ZERO;

    /** 应发工资总额：8月 */
    @ApiModelProperty(value = "应发工资总额：8月", position = 218)
    private BigDecimal yfGzAmount8 = BigDecimal.ZERO;

    /** 应发工资总额：9月 */
    @ApiModelProperty(value = "应发工资总额：9月", position = 219)
    private BigDecimal yfGzAmount9 = BigDecimal.ZERO;

    /** 应发工资总额：10月 */
    @ApiModelProperty(value = "应发工资总额：10月", position = 220)
    private BigDecimal yfGzAmount10 = BigDecimal.ZERO;

    /** 应发工资总额：11月 */
    @ApiModelProperty(value = "应发工资总额：11月", position = 221)
    private BigDecimal yfGzAmount11 = BigDecimal.ZERO;

    /** 应发工资总额：12月 */
    @ApiModelProperty(value = "应发工资总额：12月", position = 222)
    private BigDecimal yfGzAmount12 = BigDecimal.ZERO;

    /** 应发工资总额：小计 */
    @ApiModelProperty(value = "应发工资总额：小计", position = 223)
    private BigDecimal yfGzTotal = BigDecimal.ZERO;





    /** 实发工资总额：1月 */
    @ApiModelProperty(value = "实发工资总额：1月", position = 311)
    private BigDecimal sfGzAmount1 = BigDecimal.ZERO;

    /** 实发工资总额：2月 */
    @ApiModelProperty(value = "实发工资总额：2月", position = 312)
    private BigDecimal sfGzAmount2 = BigDecimal.ZERO;

    /** 实发工资总额：3月 */
    @ApiModelProperty(value = "实发工资总额：3月", position = 313)
    private BigDecimal sfGzAmount3 = BigDecimal.ZERO;

    /** 实发工资总额：4月 */
    @ApiModelProperty(value = "实发工资总额：4月", position = 314)
    private BigDecimal sfGzAmount4 = BigDecimal.ZERO;

    /** 实发工资总额：5月 */
    @ApiModelProperty(value = "实发工资总额：5月", position = 315)
    private BigDecimal sfGzAmount5 = BigDecimal.ZERO;

    /** 实发工资总额：6月 */
    @ApiModelProperty(value = "实发工资总额：6月", position = 316)
    private BigDecimal sfGzAmount6 = BigDecimal.ZERO;

    /** 实发工资总额：7月 */
    @ApiModelProperty(value = "实发工资总额：7月", position = 317)
    private BigDecimal sfGzAmount7 = BigDecimal.ZERO;

    /** 实发工资总额：8月 */
    @ApiModelProperty(value = "实发工资总额：8月", position = 318)
    private BigDecimal sfGzAmount8 = BigDecimal.ZERO;

    /** 实发工资总额：9月 */
    @ApiModelProperty(value = "实发工资总额：9月", position = 319)
    private BigDecimal sfGzAmount9 = BigDecimal.ZERO;

    /** 实发工资总额：10月 */
    @ApiModelProperty(value = "实发工资总额：10月", position = 320)
    private BigDecimal sfGzAmount10 = BigDecimal.ZERO;

    /** 实发工资总额：11月 */
    @ApiModelProperty(value = "实发工资总额：11月", position = 321)
    private BigDecimal sfGzAmount11 = BigDecimal.ZERO;

    /** 实发工资总额：12月 */
    @ApiModelProperty(value = "实发工资总额：12月", position = 322)
    private BigDecimal sfGzAmount12 = BigDecimal.ZERO;

    /** 实发工资总额：小计 */
    @ApiModelProperty(value = "实发工资总额：小计", position = 323)
    private BigDecimal sfGzTotal = BigDecimal.ZERO;





    /** 实缴五险人数（人）： 1月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：1月人数", position = 411)
    private Integer sjWxNum1 = 0;

    /** 实缴五险人数（人）：2月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：2月人数", position = 412)
    private Integer sjWxNum2 = 0;

    /** 实缴五险人数（人）：3月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：3月人数", position = 413)
    private Integer sjWxNum3 = 0;

    /** 实缴五险人数（人）：4月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：4月人数", position = 414)
    private Integer sjWxNum4 = 0;

    /** 实缴五险人数（人）：5月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：5月人数", position = 415)
    private Integer sjWxNum5 = 0;

    /** 实缴五险人数（人）：6月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：6月人数", position = 416)
    private Integer sjWxNum6 = 0;

    /** 实缴五险人数（人）：7月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：7月人数", position = 417)
    private Integer sjWxNum7 = 0;

    /** 实缴五险人数（人）：8月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：8月人数", position = 418)
    private Integer sjWxNum8 = 0;

    /** 实缴五险人数（人）：9月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：9月人数", position = 419)
    private Integer sjWxNum9 = 0;

    /** 实缴五险人数（人）：10月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：10月人数", position = 420)
    private Integer sjWxNum10 = 0;

    /** 实缴五险人数（人）：11月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：11月人数", position = 421)
    private Integer sjWxNum11 = 0;

    /** 实缴五险人数（人）：12月人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：12月人数", position = 422)
    private Integer sjWxNum12 = 0;

    /** 实缴五险人数（人）：平均人数 */
    @ApiModelProperty(value = "实缴五险人数（人）：平均人数", position = 423)
    private Double sjWxAvgNum = 0d;







    /** 实缴五险一金总额：1月 */
    @ApiModelProperty(value = "实缴五险一金总额：1月", position = 511)
    private BigDecimal sjWxYjAmount1 = BigDecimal.ZERO;

    /** 实缴五险一金总额：2月 */
    @ApiModelProperty(value = "实缴五险一金总额：2月", position = 512)
    private BigDecimal sjWxYjAmount2 = BigDecimal.ZERO;

    /** 实缴五险一金总额：3月 */
    @ApiModelProperty(value = "实缴五险一金总额：3月", position = 513)
    private BigDecimal sjWxYjAmount3 = BigDecimal.ZERO;

    /** 实缴五险一金总额：4月 */
    @ApiModelProperty(value = "实缴五险一金总额：4月", position = 514)
    private BigDecimal sjWxYjAmount4 = BigDecimal.ZERO;

    /** 实缴五险一金总额：5月 */
    @ApiModelProperty(value = "实缴五险一金总额：5月", position = 515)
    private BigDecimal sjWxYjAmount5 = BigDecimal.ZERO;

    /** 实缴五险一金总额：6月 */
    @ApiModelProperty(value = "实缴五险一金总额：6月", position = 516)
    private BigDecimal sjWxYjAmount6 = BigDecimal.ZERO;

    /** 实缴五险一金总额：7月 */
    @ApiModelProperty(value = "实缴五险一金总额：7月", position = 517)
    private BigDecimal sjWxYjAmount7 = BigDecimal.ZERO;

    /** 实缴五险一金总额：8月 */
    @ApiModelProperty(value = "实缴五险一金总额：8月", position = 518)
    private BigDecimal sjWxYjAmount8 = BigDecimal.ZERO;

    /** 实缴五险一金总额：9月 */
    @ApiModelProperty(value = "实缴五险一金总额：9月", position = 519)
    private BigDecimal sjWxYjAmount9 = BigDecimal.ZERO;

    /** 实缴五险一金总额：10月 */
    @ApiModelProperty(value = "实缴五险一金总额：10月", position = 520)
    private BigDecimal sjWxYjAmount10 = BigDecimal.ZERO;

    /** 实缴五险一金总额：11月 */
    @ApiModelProperty(value = "实缴五险一金总额：11月", position = 521)
    private BigDecimal sjWxYjAmount11 = BigDecimal.ZERO;

    /** 实缴五险一金总额：12月 */
    @ApiModelProperty(value = "实缴五险一金总额：12月", position = 522)
    private BigDecimal sjWxYjAmount12 = BigDecimal.ZERO;

    /** 实缴五险一金总额：小计 */
    @ApiModelProperty(value = "实缴五险一金总额：小计", position = 523)
    private BigDecimal sjWxYjTotal = BigDecimal.ZERO;
}
