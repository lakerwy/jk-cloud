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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资系统-月度工资标准实体
 *
 * @author yong.ye
 * @date  2024/02/26 09:36
 **/
@Getter
@Setter
@TableName("HR_SALARY_STANDARD")
public class SalaryStandard {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 类别：工资|奖金 */
    @ApiModelProperty(value = "类别：工资|奖金", position = 2)
    private String type;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 3)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 5)
    private String company;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 6)
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

    /** 职位Id */
    @ApiModelProperty(value = "职位Id", position = 12)
    private String positionId;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date leaveDate;

    /** 转正日期 */
    @ApiModelProperty(value = "转正日期", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date positiveTime;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 14)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date birthDay;

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

    /** 当月变动 */
    @ApiModelProperty(value = "当月变动", position = 20)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 21)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 22)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 24)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 25)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 26)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 27)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 员工类别：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类别：总部|分公司|外部", position = 28)
    private String userType;

    /** 是否有生日福利：是 否*/
    @ApiModelProperty(value = "是否有生日福利：是 否", position = 29)
    private String haveBirthdayPay;
}
