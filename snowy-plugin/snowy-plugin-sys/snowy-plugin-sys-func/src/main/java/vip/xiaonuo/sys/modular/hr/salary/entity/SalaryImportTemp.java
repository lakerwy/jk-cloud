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
 * @date  2024/02/26 09:31
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

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 4)
    private String no;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 5)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

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
    @ApiModelProperty(value = "花名册Id", position = 8)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 9)
    private String firstOrgName;

    /** 一级部门Id */
    @ApiModelProperty(value = "一级部门Id", position = 10)
    private String firstOrgId;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 11)
    private String orgName;

    /** 二级部门Id */
    @ApiModelProperty(value = "二级部门Id", position = 12)
    private String orgId;

    /** 职位|岗位 */
    @ApiModelProperty(value = "职位|岗位", position = 13)
    private String position;

    /** 职位Id */
    @ApiModelProperty(value = "职位Id", position = 14)
    private String positionId;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 15)
    private String hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 16)
    private String leaveDate;

    /** 基本工资 */
    @ApiModelProperty(value = "基本工资", position = 17)
    private BigDecimal basicAmount;

    /** 岗位工资 */
    @ApiModelProperty(value = "岗位工资", position = 18)
    private BigDecimal postAmount;

    /** 绩效工资 */
    @ApiModelProperty(value = "绩效工资", position = 19)
    private BigDecimal meritAmount;

    /** 月度标准工资 */
    @ApiModelProperty(value = "月度标准工资", position = 20)
    private BigDecimal standardAmount;

    /** 当月未打卡次数 */
    @ApiModelProperty(value = "当月未打卡次数", position = 22)
    private Integer missNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 23)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 24)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 25)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 26)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 27)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 28)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 29)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 30)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 31)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 32)
    private Float funDay;

    /** 所属单位 */
    @ApiModelProperty(value = "所属单位", position = 33)
    private String unitName;

    /** 缴费基数 */
    @ApiModelProperty(value = "缴费基数", position = 34)
    private BigDecimal base;

    /** 应缴金额|月缴存额 */
    @ApiModelProperty(value = "应缴金额|月缴存额", position = 35)
    private BigDecimal needPay;

    /** 合计-个人|个人实际缴纳 */
    @ApiModelProperty(value = "合计-个人|个人实际缴纳", position = 36)
    private BigDecimal per;

    /** 合计-单位|单位实际缴纳 */
    @ApiModelProperty(value = "合计-单位|单位实际缴纳", position = 37)
    private BigDecimal unit;

    /** 合计-应缴总金额|差额|合计|当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计 */
    @ApiModelProperty(value = "合计-应缴总金额|差额|合计|当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计", position = 38)
    private BigDecimal amount;

    /** 养老-个人 */
    @ApiModelProperty(value = "养老-个人", position = 39)
    private BigDecimal ynPer;

    /** 养老-单位 */
    @ApiModelProperty(value = "养老-单位", position = 40)
    private BigDecimal ynUnit;

    /** 失业-个人 */
    @ApiModelProperty(value = "失业-个人", position = 41)
    private BigDecimal syPer;

    /** 失业-单位 */
    @ApiModelProperty(value = "失业-单位", position = 42)
    private BigDecimal syUnit;

    /** 工伤-单位 */
    @ApiModelProperty(value = "工伤-单位", position = 43)
    private BigDecimal gsUnit;

    /** 医疗-个人 */
    @ApiModelProperty(value = "医疗-个人", position = 44)
    private BigDecimal ylPer;

    /** 医疗-单位 */
    @ApiModelProperty(value = "医疗-单位", position = 45)
    private BigDecimal ylUnit;

    /** 医疗-大额医疗 */
    @ApiModelProperty(value = "医疗-大额医疗", position = 46)
    private BigDecimal ylSale;

    /** 系统个人缴纳 */
    @ApiModelProperty(value = "系统个人缴纳", position = 47)
    private BigDecimal sysPer;

    /** 赡养老人 */
    @ApiModelProperty(value = "赡养老人", position = 48)
    private BigDecimal taxSy;

    /** 子女教育 */
    @ApiModelProperty(value = "子女教育", position = 49)
    private BigDecimal taxZn;

    /** 房贷利息 */
    @ApiModelProperty(value = "房贷利息", position = 50)
    private BigDecimal taxFd;

    /** 住房租金 */
    @ApiModelProperty(value = "住房租金", position = 51)
    private BigDecimal taxZf;

    /** 继续教育 */
    @ApiModelProperty(value = "继续教育", position = 52)
    private BigDecimal taxJy;

    /** 婴幼儿照护 */
    @ApiModelProperty(value = "婴幼儿照护", position = 53)
    private BigDecimal taxZh;

    /** 当月变动|备注 */
    @ApiModelProperty(value = "当月变动|备注", position = 54)
    private String memo;

    /** 错误信息 */
    @ApiModelProperty(value = "错误信息", position = 55)
    private String errorMsg;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 56)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 57)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 58)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 59)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 60)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 61)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 62)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 值班日期|课程日期|开工日期 */
    @ApiModelProperty(value = "值班日期|课程日期|开工日期", position = 63)
    private String btDate;

    /** 内部讲师级别|合同编号 */
    @ApiModelProperty(value = "内部讲师级别|合同编号", position = 64)
    private String btNo;

    /** 所讲课程名称|项目名称 */
    @ApiModelProperty(value = "所讲课程名称|项目名称", position = 65)
    private String btName;

    /** 参训人数（人）|项目所在地 */
    @ApiModelProperty(value = "参训人数（人）|项目所在地", position = 66)
    private String btContent;

    /** 评估分数（分） */
    @ApiModelProperty(value = "评估分数（分）", position = 67)
    private String btScore;

    /** 课时（精确到0.5小时）|外业天数 */
    @ApiModelProperty(value = "课时（精确到0.5小时）|外业天数", position = 68)
    private Float btNum;

    /** 竣工日期 */
    @ApiModelProperty(value = "竣工日期", position = 69)
    private String btFinishDate;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期", position = 70)
    private String btStartDate;

    /** 结束日期 */
    @ApiModelProperty(value = "结束日期", position = 71)
    private String btEndDate;

    /** 津贴标准（元/h）|伙食补贴标准 */
    @ApiModelProperty(value = "津贴标准（元/h）|伙食补贴标准", position = 72)
    private BigDecimal btStandard1;

    /** 伙食补贴小计 */
    @ApiModelProperty(value = "伙食补贴小计", position = 73)
    private BigDecimal btStandard1Total;

    /** 交通补贴标准 */
    @ApiModelProperty(value = "交通补贴标准", position = 74)
    private BigDecimal btStandard2;

    /** 交通补贴小计 */
    @ApiModelProperty(value = "交通补贴小计", position = 75)
    private BigDecimal btStandard2Total;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 76)
    private String company;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 77)
    private String positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 78)
    private String birthDay;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 79)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 80)
    private String haveBirthdayPay;

    /** （月工资标准外）其他导入-基于发放工资员工表-【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "月工资标准外）其他导入-基于发放工资员工表-【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 81)
    private String standardId;
}
