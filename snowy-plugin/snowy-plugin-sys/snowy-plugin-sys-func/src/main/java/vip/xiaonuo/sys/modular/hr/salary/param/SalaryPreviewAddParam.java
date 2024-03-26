/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.sys.modular.hr.salary.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资系统-工资预览表添加参数
 *
 * @author yong.ye
 * @date  2024/03/22 10:26
 **/
@Getter
@Setter
public class SalaryPreviewAddParam {
    /** 工资发放公司 */
    @ApiModelProperty(value = "工资发放公司", position = 3)
    private String company;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 4)
    private String yearMonth;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部", position = 5)
    private String userType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 6)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 7)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 8)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 9)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 10)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 11)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 12)
    private String position;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 16)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    /** 是否有生日福利 */
    @ApiModelProperty(value = "是否有生日福利", position = 17)
    private String haveBirthdayPay;

    /** 是否有工龄工资 */
    @ApiModelProperty(value = "是否有工龄工资", position = 18)
    private String haveSeniorityPay;

    /** 【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID", position = 19)
    private String standardId;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 20)
    private BigDecimal standardAmount;

    /** 工资结构-基本工资 */
    @ApiModelProperty(value = "工资结构-基本工资", position = 21)
    private BigDecimal basicAmount;

    /** 工资结构-岗位工资 */
    @ApiModelProperty(value = "工资结构-岗位工资", position = 22)
    private BigDecimal postAmount;

    /** 工资结构-考核工资 */
    @ApiModelProperty(value = "工资结构-考核工资", position = 23)
    private BigDecimal meritAmount;

    /** 绩效工资-绩效系数 */
    @ApiModelProperty(value = "绩效工资-绩效系数", position = 24)
    private Float meritRate;

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
    @ApiModelProperty(value = "当月入离职未满勤扣款", position = 36)
    private BigDecimal inOutAmount;

    /** 税前补发补扣-其他税前补发 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补发", position = 37)
    private BigDecimal addAmount;

    /** 税前补发补扣-其他税前补扣 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补扣", position = 38)
    private BigDecimal minusAmount;

    /** 公积金差额 */
    @ApiModelProperty(value = "公积金差额", position = 39)
    private BigDecimal gjjDiffAmount;

    /** 应发工资 */
    @ApiModelProperty(value = "应发工资", position = 40)
    private BigDecimal grossPayAmount;

    /** 个人社保扣款-小计-个人 */
    @ApiModelProperty(value = "个人社保扣款-小计-个人", position = 41)
    private BigDecimal per;

    /** 个人社保扣款-养老-个人 */
    @ApiModelProperty(value = "个人社保扣款-养老-个人", position = 42)
    private BigDecimal ynPer;

    /** 个人社保扣款-失业-个人 */
    @ApiModelProperty(value = "个人社保扣款-失业-个人", position = 43)
    private BigDecimal syPer;

    /** 个人社保扣款-医疗-个人 */
    @ApiModelProperty(value = "个人社保扣款-医疗-个人", position = 44)
    private BigDecimal ylPer;

    /** 个人社保扣款-大额医疗-个人 */
    @ApiModelProperty(value = "个人社保扣款-大额医疗-个人", position = 45)
    private BigDecimal ylSale;

    /** 个人公积金扣款 */
    @ApiModelProperty(value = "个人公积金扣款", position = 46)
    private BigDecimal sysPer;

    /** 专项附加扣除项-赡养老人 */
    @ApiModelProperty(value = "专项附加扣除项-赡养老人", position = 47)
    private BigDecimal taxSy;

    /** 专项附加扣除项-子女教育 */
    @ApiModelProperty(value = "专项附加扣除项-子女教育", position = 48)
    private BigDecimal taxZn;

    /** 专项附加扣除项-房贷利息 */
    @ApiModelProperty(value = "专项附加扣除项-房贷利息", position = 49)
    private BigDecimal taxFd;

    /** 专项附加扣除项-住房租金 */
    @ApiModelProperty(value = "专项附加扣除项-住房租金", position = 50)
    private BigDecimal taxZf;

    /** 专项附加扣除项-继续教育 */
    @ApiModelProperty(value = "专项附加扣除项-继续教育", position = 51)
    private BigDecimal taxJy;

    /** 专项附加扣除项-婴幼儿照护 */
    @ApiModelProperty(value = "专项附加扣除项-婴幼儿照护", position = 52)
    private BigDecimal taxZh;

    /** 员工福利扣税额 */
    @ApiModelProperty(value = "员工福利扣税额", position = 53)
    private BigDecimal empWelfareAmount;

    /** 外业补贴扣税额 */
    @ApiModelProperty(value = "外业补贴扣税额", position = 54)
    private BigDecimal outerAmount;

    /** 其他税后补发 */
    @ApiModelProperty(value = "其他税后补发", position = 55)
    private BigDecimal lastAddAmount;

    /** 其他税后补扣 */
    @ApiModelProperty(value = "其他税后补扣", position = 56)
    private BigDecimal lastMinusAmount;

    /** 本月应纳税所得额 */
    @ApiModelProperty(value = "本月应纳税所得额", position = 57)
    private BigDecimal taxIncomeAmount;

    /** 累计应纳税所得额 */
    @ApiModelProperty(value = "累计应纳税所得额", position = 58)
    private BigDecimal ljTaxIncomeAmount;

    /** 累计税额 */
    @ApiModelProperty(value = "累计税额", position = 59)
    private BigDecimal ljTaxAmount;

    /** 已缴累计税额 */
    @ApiModelProperty(value = "已缴累计税额", position = 60)
    private BigDecimal haveLjTaxAmount;

    /** 本月应扣税额 */
    @ApiModelProperty(value = "本月应扣税额", position = 61)
    private BigDecimal monthTaxAmount;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资", position = 62)
    private BigDecimal realPayAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 63)
    private String memo;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 71)
    private String note;

    /** 是否实习生 */
    @ApiModelProperty(value = "是否实习生", position = 72)
    private String isTry;

    /** 是否缓发 */
    @ApiModelProperty(value = "是否缓发", position = 73)
    private String isSlow;
}
