package vip.xiaonuo.sys.modular.hr.salary.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 工资统计数据
 */

@Getter
@Setter
public class SumSalaryTotal {

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 4)
    private String yearMonth;

    /** 工资结构-考核工资 */
    @ApiModelProperty(value = "工资结构-考核工资", position = 23)
    private BigDecimal meritAmount;

    /** 绩效工资-实发绩效 */
    @ApiModelProperty(value = "绩效工资-实发绩效", position = 25)
    private BigDecimal realMeritAmount;

    /** 附加工资-注册补贴 */
    @ApiModelProperty(value = "附加工资-注册补贴", position = 26)
    private BigDecimal registerAmount;

    /** 附加工资-工龄工资 */
    @ApiModelProperty(value = "附加工资-工龄工资", position = 27)
    private BigDecimal workAgeAmount;

    /** 附加工资-全勤奖 */
    @ApiModelProperty(value = "附加工资-全勤奖", position = 28)
    private BigDecimal fullAmount;

    /** 附加工资-讲师津贴 */
    @ApiModelProperty(value = "附加工资-讲师津贴", position = 29)
    private BigDecimal teacherAmount;

    /** 附加工资-值班补贴 */
    @ApiModelProperty(value = "附加工资-值班补贴", position = 30)
    private BigDecimal dutyAmount;

    /** 考勤扣款-病假 */
    @ApiModelProperty(value = "考勤扣款-病假", position = 31)
    private BigDecimal sickAmount;

    /** 考勤扣款-事假 */
    @ApiModelProperty(value = "考勤扣款-事假", position = 32)
    private BigDecimal workAmount;

    /** 考勤扣款-迟到 */
    @ApiModelProperty(value = "考勤扣款-迟到", position = 33)
    private BigDecimal lateAmount;

    /** 考勤扣款-矿工 */
    @ApiModelProperty(value = "考勤扣款-矿工", position = 34)
    private BigDecimal minerAmount;

    /** 考勤扣款-产假 */
    @ApiModelProperty(value = "考勤扣款-产假", position = 35)
    private BigDecimal materAmount;

    /** 当月入离职未满勤扣款 */
    @ApiModelProperty(value = "当月入离职未满勤扣款", position = 35)
    private BigDecimal inOutAmount;

    /** 税前补发补扣-其他税前补发 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补发", position = 36)
    private BigDecimal addAmount;

    /** 税前补发补扣-其他税前补扣 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补扣", position = 37)
    private BigDecimal minusAmount;

    /** 公积金差额 */
    @ApiModelProperty(value = "公积金差额", position = 38)
    private BigDecimal gjjDiffAmount;

    /** 应发工资 */
    @ApiModelProperty(value = "应发工资", position = 38)
    private BigDecimal grossPayAmount;

    /** 个人社保扣款-小计-个人 */
    @ApiModelProperty(value = "个人社保扣款-小计-个人", position = 39)
    private BigDecimal per;

    /** 个人社保扣款-养老-个人 */
    @ApiModelProperty(value = "个人社保扣款-养老-个人", position = 40)
    private BigDecimal ynPer;

    /** 个人社保扣款-失业-个人 */
    @ApiModelProperty(value = "个人社保扣款-失业-个人", position = 41)
    private BigDecimal syPer;

    /** 个人社保扣款-医疗-个人 */
    @ApiModelProperty(value = "个人社保扣款-医疗-个人", position = 42)
    private BigDecimal ylPer;

    /** 个人社保扣款-大额医疗-个人 */
    @ApiModelProperty(value = "个人社保扣款-大额医疗-个人", position = 43)
    private BigDecimal ylSale;

    /** 个人公积金扣款 */
    @ApiModelProperty(value = "个人公积金扣款", position = 44)
    private BigDecimal sysPer;

    /** 专项附加扣除项-赡养老人 */
    @ApiModelProperty(value = "专项附加扣除项-赡养老人", position = 45)
    private BigDecimal taxSy;

    /** 专项附加扣除项-子女教育 */
    @ApiModelProperty(value = "专项附加扣除项-子女教育", position = 46)
    private BigDecimal taxZn;

    /** 专项附加扣除项-房贷利息 */
    @ApiModelProperty(value = "专项附加扣除项-房贷利息", position = 47)
    private BigDecimal taxFd;

    /** 专项附加扣除项-住房租金 */
    @ApiModelProperty(value = "专项附加扣除项-住房租金", position = 48)
    private BigDecimal taxZf;

    /** 专项附加扣除项-继续教育 */
    @ApiModelProperty(value = "专项附加扣除项-继续教育", position = 49)
    private BigDecimal taxJy;

    /** 专项附加扣除项-婴幼儿照护 */
    @ApiModelProperty(value = "专项附加扣除项-婴幼儿照护", position = 50)
    private BigDecimal taxZh;

    /** 员工福利扣税额 */
    @ApiModelProperty(value = "员工福利扣税额", position = 51)
    private BigDecimal empWelfareAmount;

    /** 外业补贴扣税额 */
    @ApiModelProperty(value = "外业补贴扣税额", position = 52)
    private BigDecimal outerAmount;

    /** 其他税后补发 */
    @ApiModelProperty(value = "其他税后补发", position = 53)
    private BigDecimal lastAddAmount;

    /** 其他税后补扣 */
    @ApiModelProperty(value = "其他税后补扣", position = 54)
    private BigDecimal lastMinusAmount;

    /** 本月应纳税所得额 */
    @ApiModelProperty(value = "本月应纳税所得额", position = 55)
    private BigDecimal taxIncomeAmount;

    /** 累计应纳税所得额 */
    @ApiModelProperty(value = "累计应纳税所得额", position = 56)
    private BigDecimal ljTaxIncomeAmount;

    /** 累计税额 */
    @ApiModelProperty(value = "累计税额", position = 57)
    private BigDecimal ljTaxAmount;

    /** 已缴累计税额 */
    @ApiModelProperty(value = "已缴累计税额", position = 58)
    private BigDecimal haveLjTaxAmount;

    /** 本月应扣税额 */
    @ApiModelProperty(value = "本月应扣税额", position = 59)
    private BigDecimal monthTaxAmount;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资", position = 60)
    private BigDecimal realPayAmount;

    /** 合计-其他 */
    @ApiModelProperty(value = "合计-其他 ", position = 60)
    private BigDecimal totalAmount;
}
