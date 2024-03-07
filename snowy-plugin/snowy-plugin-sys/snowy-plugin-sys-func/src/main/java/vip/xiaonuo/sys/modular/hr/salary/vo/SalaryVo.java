package vip.xiaonuo.sys.modular.hr.salary.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class SalaryVo {

    /** 主键ID -- 【已确认】Salary.Id 或者 【未确认】SalaryStandard.Id */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 2)
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 3)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 4)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 5)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 6)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 7)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 8)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 9)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 10)
    private String orgId;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String position;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 16)
    private BigDecimal standardAmount;

    /** 工资组成-基本工资 */
    @ApiModelProperty(value = "工资组成-基本工资", position = 17)
    private BigDecimal basicAmount;

    /** 工资组成-岗位工资 */
    @ApiModelProperty(value = "工资组成-岗位工资", position = 18)
    private BigDecimal postAmount;

    /** 工资组成-绩效（考核）工资 */
    @ApiModelProperty(value = "工资组成-绩效（考核）工资", position = 19)
    private BigDecimal meritAmount;

    /** 附加工资-餐饮补助 */
    @ApiModelProperty(value = "附加工资-餐饮补助", position = 20)
    private BigDecimal mealAmount;

    /** 附加工资-注册补贴 */
    @ApiModelProperty(value = "附加工资-注册补贴", position = 21)
    private BigDecimal registerAmount;

    /** 附加工资-工龄工资 */
    @ApiModelProperty(value = "附加工资-工龄工资", position = 22)
    private BigDecimal workAgeAmount;

    /** 附加工资-全勤奖 */
    @ApiModelProperty(value = "附加工资-全勤奖", position = 23)
    private BigDecimal fullAmount;

    /** 附加工资-值班补贴 */
    @ApiModelProperty(value = "附加工资-值班补贴", position = 24)
    private BigDecimal dutyAmount;

    /** 附加工资-其他 */
    @ApiModelProperty(value = "附加工资-其他", position = 25)
    private BigDecimal fjOtherAmount;

    /** 扣款-病假工资 */
    @ApiModelProperty(value = "扣款-病假工资", position = 26)
    private BigDecimal sickAmount;

    /** 扣款-事假工资 */
    @ApiModelProperty(value = "扣款-事假工资", position = 27)
    private BigDecimal workAmount;

    /** 扣款-迟到工资 */
    @ApiModelProperty(value = "扣款-迟到工资", position = 28)
    private BigDecimal lateAmount;

    /** 扣款-矿工工资 */
    @ApiModelProperty(value = "扣款-矿工工资", position = 29)
    private BigDecimal minerAmount;

    /** 扣款-异动调整 */
    @ApiModelProperty(value = "扣款-异动调整", position = 30)
    private BigDecimal moveAmount;

    /** 扣款-其他 */
    @ApiModelProperty(value = "扣款-其他", position = 31)
    private BigDecimal kkOtherAmount;

    /** 应发工资 */
    @ApiModelProperty(value = "应发工资", position = 32)
    private BigDecimal grossPayAmount;

    /** 五险一金-社保 */
    @ApiModelProperty(value = "五险一金-社保", position = 33)
    private BigDecimal insAmount;

    /** 五险一金-公积金 */
    @ApiModelProperty(value = "五险一金-公积金", position = 34)
    private BigDecimal gjjAmount;

    /** 专项附加扣除项-赡养老人 */
    @ApiModelProperty(value = "专项附加扣除项-赡养老人", position = 35)
    private BigDecimal taxSy;

    /** 专项附加扣除项-子女教育 */
    @ApiModelProperty(value = "专项附加扣除项-子女教育", position = 36)
    private BigDecimal taxZn;

    /** 专项附加扣除项-房贷利息 */
    @ApiModelProperty(value = "专项附加扣除项-房贷利息", position = 37)
    private BigDecimal taxFd;

    /** 专项附加扣除项-住房租金 */
    @ApiModelProperty(value = "专项附加扣除项-住房租金", position = 38)
    private BigDecimal taxZf;

    /** 专项附加扣除项-继续教育 */
    @ApiModelProperty(value = "专项附加扣除项-继续教育", position = 39)
    private BigDecimal taxJy;

    /** 专项附加扣除项-婴幼儿照护 */
    @ApiModelProperty(value = "专项附加扣除项-婴幼儿照护", position = 40)
    private BigDecimal taxZh;

    /** 福利补助 */
    @ApiModelProperty(value = "福利补助", position = 41)
    private BigDecimal welfareAmount;

    /** 应纳税所得额 */
    @ApiModelProperty(value = "应纳税所得额", position = 42)
    private BigDecimal taxIncomeAmount;

    /** 累计应纳税所得额 */
    @ApiModelProperty(value = "累计应纳税所得额", position = 43)
    private BigDecimal ljTaxIncomeAmount;

    /** 累计税额 */
    @ApiModelProperty(value = "累计税额", position = 44)
    private BigDecimal ljTaxAmount;

    /** 已累计税额 */
    @ApiModelProperty(value = "已累计税额", position = 45)
    private BigDecimal haveLjTaxAmount;

    /** 本月应扣税额 */
    @ApiModelProperty(value = "本月应扣税额", position = 46)
    private BigDecimal monthTaxAmount;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资", position = 47)
    private BigDecimal realPayAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 48)
    private String memo;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 56)
    private String standardId;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 57)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 58)
    private String haveBirthdayPay;

    /** 是否有工龄工资：是 否*/
    @ApiModelProperty(value = "是否有工龄工资：是 否", position = 58)
    private String haveSeniorityPay;

    /** 确认状态：已确认|未确认*/
    @ApiModelProperty(value = "确认状态：已确认|未确认", position = 58)
    private String confirmStatus;

}
