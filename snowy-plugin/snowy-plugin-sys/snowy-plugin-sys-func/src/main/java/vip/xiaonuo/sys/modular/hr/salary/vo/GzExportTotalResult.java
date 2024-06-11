package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 工资模板：GZTemplate.xlsx 合计行
 */
@Data
public class GzExportTotalResult {

    public GzExportTotalResult(String no,String typeName) {
        this.no = no;
        this.typeName = typeName;
        this.basicAmountTotal = BigDecimal.ZERO;
        this.postAmountTotal = BigDecimal.ZERO;
        this.meritAmountTotal = BigDecimal.ZERO;
        this.meritRateTotal = 0.00F;
        this.realMeritAmountTotal = BigDecimal.ZERO;
        this.registerAmountTotal = BigDecimal.ZERO;
        this.workAgeAmountTotal = BigDecimal.ZERO;
        this.fullAmountTotal = BigDecimal.ZERO;
        this.teacherAmountTotal = BigDecimal.ZERO;
        this.dutyAmountTotal = BigDecimal.ZERO;
        this.sickAmountTotal = BigDecimal.ZERO;
        this.workAmountTotal = BigDecimal.ZERO;
        this.lateAmountTotal = BigDecimal.ZERO;
        this.minerAmountTotal = BigDecimal.ZERO;
        this.materAmountTotal = BigDecimal.ZERO;
        this.inOutAmountTotal = BigDecimal.ZERO;
        this.addAmountTotal = BigDecimal.ZERO;
        this.minusAmountTotal = BigDecimal.ZERO;
        this.grossPayAmountTotal = BigDecimal.ZERO;
        this.perTotal = BigDecimal.ZERO;
        this.sysPerTotal = BigDecimal.ZERO;
        this.empWelfareAmountTotal = BigDecimal.ZERO;
        this.outerAmountTotal = BigDecimal.ZERO;
        this.lastAddAmountTotal = BigDecimal.ZERO;
        this.lastMinusAmountTotal = BigDecimal.ZERO;
        this.monthTaxAmountTotal = BigDecimal.ZERO;
        this.realPayAmountTotal = BigDecimal.ZERO;
    }

    /** 本页小计|合计 1 */
    @ApiModelProperty(value = "本页小计|合计 1", position = 1)
    private String no;

    /** 本页小计|合计 2 */
    @ApiModelProperty(value = "本页小计|合计 2", position = 1)
    private String typeName;

    /** 工资结构-基本工资 */
    @ApiModelProperty(value = "工资结构-基本工资", position = 21)
    private BigDecimal basicAmountTotal;

    /** 工资结构-岗位工资 */
    @ApiModelProperty(value = "工资结构-岗位工资", position = 22)
    private BigDecimal postAmountTotal;

    /** 工资结构-考核工资 */
    @ApiModelProperty(value = "工资结构-考核工资", position = 23)
    private BigDecimal meritAmountTotal;

    /** 绩效工资-绩效系数 */
    @ApiModelProperty(value = "绩效工资-绩效系数", position = 24)
    private Float meritRateTotal;

    /** 绩效工资-实发绩效 */
    @ApiModelProperty(value = "绩效工资-实发绩效", position = 25)
    private BigDecimal realMeritAmountTotal;

    /** 附加工资-注册补贴 */
    @ApiModelProperty(value = "附加工资-注册补贴", position = 26)
    private BigDecimal registerAmountTotal;

    /** 附加工资-工龄工资 */
    @ApiModelProperty(value = "附加工资-工龄工资", position = 27)
    private BigDecimal workAgeAmountTotal;

    /** 附加工资-全勤奖 */
    @ApiModelProperty(value = "附加工资-全勤奖", position = 28)
    private BigDecimal fullAmountTotal;

    /** 附加工资-讲师津贴 */
    @ApiModelProperty(value = "附加工资-讲师津贴", position = 29)
    private BigDecimal teacherAmountTotal;

    /** 附加工资-值班补贴 */
    @ApiModelProperty(value = "附加工资-值班补贴", position = 30)
    private BigDecimal dutyAmountTotal;

    /** 考勤扣款-病假 */
    @ApiModelProperty(value = "考勤扣款-病假", position = 31)
    private BigDecimal sickAmountTotal;

    /** 考勤扣款-事假 */
    @ApiModelProperty(value = "考勤扣款-事假", position = 32)
    private BigDecimal workAmountTotal;

    /** 考勤扣款-迟到 */
    @ApiModelProperty(value = "考勤扣款-迟到", position = 33)
    private BigDecimal lateAmountTotal;

    /** 考勤扣款-矿工 */
    @ApiModelProperty(value = "考勤扣款-矿工", position = 34)
    private BigDecimal minerAmountTotal;

    /** 考勤扣款-产假 */
    @ApiModelProperty(value = "考勤扣款-产假", position = 35)
    private BigDecimal materAmountTotal;

    /** 当月入离职未满勤扣款 */
    @ApiModelProperty(value = "当月入离职未满勤扣款", position = 35)
    private BigDecimal inOutAmountTotal;

    /** 税前补发补扣-其他税前补发 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补发", position = 36)
    private BigDecimal addAmountTotal;

    /** 税前补发补扣-其他税前补扣 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补扣", position = 37)
    private BigDecimal minusAmountTotal;

    /** 应发工资 */
    @ApiModelProperty(value = "应发工资", position = 38)
    private BigDecimal grossPayAmountTotal;

    /** 个人社保扣款-小计-个人 */
    @ApiModelProperty(value = "个人社保扣款-小计-个人", position = 39)
    private BigDecimal perTotal;

    /** 个人公积金扣款 */
    @ApiModelProperty(value = "个人公积金扣款", position = 44)
    private BigDecimal sysPerTotal;

    /** 员工福利扣税额 */
    @ApiModelProperty(value = "员工福利扣税额", position = 51)
    private BigDecimal empWelfareAmountTotal;

    /** 外业补贴扣税额 */
    @ApiModelProperty(value = "外业补贴扣税额", position = 52)
    private BigDecimal outerAmountTotal;

    /** 其他税后补发 */
    @ApiModelProperty(value = "其他税后补发", position = 53)
    private BigDecimal lastAddAmountTotal;

    /** 其他税后补扣 */
    @ApiModelProperty(value = "其他税后补扣", position = 54)
    private BigDecimal lastMinusAmountTotal;

    /** 本月应扣税额 */
    @ApiModelProperty(value = "本月应扣税额", position = 59)
    private BigDecimal monthTaxAmountTotal;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资", position = 60)
    private BigDecimal realPayAmountTotal;

    public GzExportTotalResult() {

    }



}
