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
package vip.xiaonuo.sys.modular.hr.salary.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资系统-相关数据导入临时表实体
 *
 * @author yong.ye
 * @date  2024/03/08 10:32
 **/
@Getter
@Setter
@TableName("HR_SALARY_IMPORT_TEMP")
public class SalaryImportTemp {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 导入批次号 */
    @ApiModelProperty(value = "导入批次号", position = 2)
    private String batchId;

    /** 导入类别：员工月工资标准|值班补贴|讲师津贴|... */
    @ApiModelProperty(value = "导入类别：员工月工资标准|值班补贴|讲师津贴|...", position = 3)
    private String type;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部", position = 4)
    private String userType;

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 5)
    private String no;

    /** 所属公司|工资发放公司 */
    @ApiModelProperty(value = "所属公司|工资发放公司", position = 6)
    private String company;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 7)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 8)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 9)
    private String userName;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 10)
    private String userId;

    /** 花名册Id */
    @ApiModelProperty(value = "花名册Id", position = 11)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 12)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 13)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 14)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 15)
    private String orgId;

    /** 职位|岗位 */
    @ApiModelProperty(value = "职位|岗位", position = 16)
    private String position;

    /** 职位Id */
    @ApiModelProperty(value = "职位Id", position = 17)
    private String positionId;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 18)
    private String hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 19)
    private String leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 20)
    private String positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 21)
    private String birthDay;

    /** 是否有生日福利 */
    @ApiModelProperty(value = "是否有生日福利", position = 22)
    private String haveBirthdayPay;

    /** 是否有工龄工资 */
    @ApiModelProperty(value = "是否有工龄工资", position = 23)
    private String haveSeniorityPay;

    /** 绩效系数 */
    @ApiModelProperty(value = "绩效系数", position = 24)
    private Float meritRate;

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 25)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 26)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 27)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 28)
    private BigDecimal standardAmount;

    /** 未打卡（上班） */
    @ApiModelProperty(value = "未打卡（上班）", position = 29)
    private Integer upMissNum;

    /** 未打卡（下班） */
    @ApiModelProperty(value = "未打卡（下班）", position = 30)
    private Integer downMissNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 31)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 32)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 33)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 34)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 35)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 36)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 37)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 38)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 39)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 40)
    private Float funDay;

    /** 是否全勤 */
    @ApiModelProperty(value = "是否全勤", position = 41)
    private String isFull;

    /** 应勤天数 */
    @ApiModelProperty(value = "应勤天数", position = 42)
    private Float requiredDay;

    /** 实勤天数 */
    @ApiModelProperty(value = "实勤天数", position = 43)
    private Float realDay;

    /** 调休（h） */
    @ApiModelProperty(value = "调休（h）", position = 44)
    private Float adjustHour;

    /** 实扣天数-当月病假（一个月以上病假） */
    @ApiModelProperty(value = "实扣天数-当月病假（一个月以上病假）", position = 47)
    private Float realMonthSickDay;

    /** 实扣天数-病假（一个月以内） */
    @ApiModelProperty(value = "实扣天数-病假（一个月以内）", position = 48)
    private Float realUnMonthSickDay;

    /** 实扣天数-产假 */
    @ApiModelProperty(value = "实扣天数-产假", position = 49)
    private Float realMaterDay;

    /** 加班时长（h） */
    @ApiModelProperty(value = "加班时长（h）", position = 50)
    private Float overtimeHour;

    /** 值班日期|课程日期|开工日期 */
    @ApiModelProperty(value = "值班日期|课程日期|开工日期", position = 51)
    private String btDate;

    /** 内部讲师级别|合同编号 */
    @ApiModelProperty(value = "内部讲师级别|合同编号", position = 52)
    private String btNo;

    /** 所讲课程名称|项目名称 */
    @ApiModelProperty(value = "所讲课程名称|项目名称", position = 53)
    private String btName;

    /** 参训人数（人）|项目所在地 */
    @ApiModelProperty(value = "参训人数（人）|项目所在地", position = 54)
    private String btContent;

    /** 评估分数（分） */
    @ApiModelProperty(value = "评估分数（分）", position = 55)
    private String btScore;

    /** 课时（精确到0.5小时）|外业天数 */
    @ApiModelProperty(value = "课时（精确到0.5小时）|外业天数", position = 56)
    private Float btNum;

    /** 竣工日期 */
    @ApiModelProperty(value = "竣工日期", position = 57)
    private String btFinishDate;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期", position = 58)
    private String btStartDate;

    /** 结束日期 */
    @ApiModelProperty(value = "结束日期", position = 59)
    private String btEndDate;

    /** 津贴标准（元/h）|伙食补贴标准 */
    @ApiModelProperty(value = "津贴标准（元/h）|伙食补贴标准", position = 60)
    private BigDecimal btStandard1;

    /** 伙食补贴小计 */
    @ApiModelProperty(value = "伙食补贴小计", position = 61)
    private BigDecimal btStandard1Total;

    /** 交通补贴标准 */
    @ApiModelProperty(value = "交通补贴标准", position = 62)
    private BigDecimal btStandard2;

    /** 交通补贴小计 */
    @ApiModelProperty(value = "交通补贴小计", position = 63)
    private BigDecimal btStandard2Total;

    /** 发放公司 */
    @ApiModelProperty(value = "发放公司", position = 64)
    private String unitName;

    /** 缴费基数 */
    @ApiModelProperty(value = "缴费基数", position = 65)
    private BigDecimal base;

    /** 应缴金额|月缴存额 */
    @ApiModelProperty(value = "应缴金额|月缴存额", position = 66)
    private BigDecimal needPay;

    /** 合计-个人|个人实际缴纳 */
    @ApiModelProperty(value = "合计-个人|个人实际缴纳", position = 67)
    private BigDecimal per;

    /** 合计-单位|单位实际缴纳 */
    @ApiModelProperty(value = "合计-单位|单位实际缴纳", position = 68)
    private BigDecimal unit;

    /** 合计-应缴总金额|差额|合计|当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计 */
    @ApiModelProperty(value = "合计-应缴总金额|差额|合计|当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计", position = 69)
    private BigDecimal amount;

    /** 养老-个人 */
    @ApiModelProperty(value = "养老-个人", position = 70)
    private BigDecimal ynPer;

    /** 养老-单位 */
    @ApiModelProperty(value = "养老-单位", position = 71)
    private BigDecimal ynUnit;

    /** 失业-个人 */
    @ApiModelProperty(value = "失业-个人", position = 72)
    private BigDecimal syPer;

    /** 失业-单位 */
    @ApiModelProperty(value = "失业-单位", position = 73)
    private BigDecimal syUnit;

    /** 工伤-单位 */
    @ApiModelProperty(value = "工伤-单位", position = 74)
    private BigDecimal gsUnit;

    /** 医疗-个人 */
    @ApiModelProperty(value = "医疗-个人", position = 75)
    private BigDecimal ylPer;

    /** 医疗-单位 */
    @ApiModelProperty(value = "医疗-单位", position = 76)
    private BigDecimal ylUnit;

    /** 医疗-大额医疗 */
    @ApiModelProperty(value = "医疗-大额医疗", position = 77)
    private BigDecimal ylSale;

    /** 系统个人缴纳 */
    @ApiModelProperty(value = "系统个人缴纳", position = 78)
    private BigDecimal sysPer;

    /** 赡养老人 */
    @ApiModelProperty(value = "赡养老人", position = 79)
    private BigDecimal taxSy;

    /** 子女教育 */
    @ApiModelProperty(value = "子女教育", position = 80)
    private BigDecimal taxZn;

    /** 房贷利息 */
    @ApiModelProperty(value = "房贷利息", position = 81)
    private BigDecimal taxFd;

    /** 住房租金 */
    @ApiModelProperty(value = "住房租金", position = 82)
    private BigDecimal taxZf;

    /** 继续教育 */
    @ApiModelProperty(value = "继续教育", position = 83)
    private BigDecimal taxJy;

    /** 婴幼儿照护 */
    @ApiModelProperty(value = "婴幼儿照护", position = 84)
    private BigDecimal taxZh;

    /** 福利小类：生日福利|结婚|嘉奖 */
    @ApiModelProperty(value = "福利小类：生日福利|结婚|嘉奖", position = 85)
    private String itemType;

    /** 绩效工资-实发绩效 */
    @ApiModelProperty(value = "绩效工资-实发绩效", position = 86)
    private BigDecimal realMeritAmount;

    /** 附加工资-注册补贴 */
    @ApiModelProperty(value = "附加工资-注册补贴", position = 87)
    private BigDecimal registerAmount;

    /** 附加工资-工龄工资 */
    @ApiModelProperty(value = "附加工资-工龄工资", position = 88)
    private BigDecimal workAgeAmount;

    /** 附加工资-全勤奖 */
    @ApiModelProperty(value = "附加工资-全勤奖", position = 89)
    private BigDecimal fullAmount;

    /** 附加工资-讲师津贴 */
    @ApiModelProperty(value = "附加工资-讲师津贴", position = 90)
    private BigDecimal teacherAmount;

    /** 附加工资-值班补贴 */
    @ApiModelProperty(value = "附加工资-值班补贴", position = 91)
    private BigDecimal dutyAmount;

    /** 考勤扣款-病假 */
    @ApiModelProperty(value = "考勤扣款-病假", position = 92)
    private BigDecimal sickAmount;

    /** 考勤扣款-事假 */
    @ApiModelProperty(value = "考勤扣款-事假", position = 93)
    private BigDecimal workAmount;

    /** 考勤扣款-迟到 */
    @ApiModelProperty(value = "考勤扣款-迟到", position = 94)
    private BigDecimal lateAmount;

    /** 考勤扣款-矿工 */
    @ApiModelProperty(value = "考勤扣款-矿工", position = 95)
    private BigDecimal minerAmount;

    /** 考勤扣款-产假 */
    @ApiModelProperty(value = "考勤扣款-产假", position = 96)
    private BigDecimal materAmount;

    /** 当月入离职未满勤扣款 */
    @ApiModelProperty(value = "当月入离职未满勤扣款", position = 96)
    private BigDecimal inOutAmount;

    /** 税前补发补扣-其他税前补发 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补发", position = 97)
    private BigDecimal addAmount;

    /** 税前补发补扣-其他税前补扣 */
    @ApiModelProperty(value = "税前补发补扣-其他税前补扣", position = 98)
    private BigDecimal minusAmount;

    /** 公积金差额 */
    @ApiModelProperty(value = "公积金差额", position = 99)
    private BigDecimal gjjDiffAmount;

    /** 应发工资 */
    @ApiModelProperty(value = "应发工资", position = 99)
    private BigDecimal grossPayAmount;

    /** 员工福利扣税额 */
    @ApiModelProperty(value = "员工福利扣税额", position = 100)
    private BigDecimal empWelfareAmount;

    /** 外业补贴扣税额 */
    @ApiModelProperty(value = "外业补贴扣税额", position = 101)
    private BigDecimal outerAmount;

    /** 其他税后补发 */
    @ApiModelProperty(value = "其他税后补发", position = 102)
    private BigDecimal lastAddAmount;

    /** 其他税后补扣 */
    @ApiModelProperty(value = "其他税后补扣", position = 103)
    private BigDecimal lastMinusAmount;

    /** 本月应纳税所得额 */
    @ApiModelProperty(value = "本月应纳税所得额", position = 104)
    private BigDecimal taxIncomeAmount;

    /** 累计应纳税所得额 */
    @ApiModelProperty(value = "累计应纳税所得额", position = 105)
    private BigDecimal ljTaxIncomeAmount;

    /** 累计税额 */
    @ApiModelProperty(value = "累计税额", position = 106)
    private BigDecimal ljTaxAmount;

    /** 已缴累计税额 */
    @ApiModelProperty(value = "已缴累计税额", position = 107)
    private BigDecimal haveLjTaxAmount;

    /** 本月应扣税额 */
    @ApiModelProperty(value = "本月应扣税额", position = 108)
    private BigDecimal monthTaxAmount;

    /** 实发工资 */
    @ApiModelProperty(value = "实发工资", position = 109)
    private BigDecimal realPayAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 110)
    private String memo;

    /** 当月变动 */
    @ApiModelProperty(value = "当月变动", position = 110)
    private String monthChange;

    /** 基于工资发放员工表-匹配待发工资员工【外键】：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "基于工资发放员工表-匹配待发工资员工【外键】：HR_SALARY_STANDARD.ID", position = 111)
    private String standardId;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息", position = 112)
    private String errorMsg;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 113)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 114)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 115)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 116)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 117)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 118)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 119)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 100)
    private String note;

    /** 是否重点关注 */
    @ApiModelProperty(value = "是否重点关注", position = 120)
    private String isSpec;

    /** 是否实习生 */
    @ApiModelProperty(value = "是否实习生", position = 121)
    private String isTry;

}
