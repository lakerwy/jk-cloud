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
 * 工资系统 - 成本划分 - 月度个人统计实体
 *
 * @author yong.ye
 * @date  2024/06/19 15:06
 **/
@Getter
@Setter
@TableName("HR_SALARY_COST")
public class SalaryCost {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 数据来源：preview - 预览工资数据 salary - 封存工资数据 */
    @ApiModelProperty(value = "数据来源：preview - 预览工资数据 salary - 封存工资数据 ", position = 2)
    private String dataSource;

    /** 成本归属-统计维度 */
    @ApiModelProperty(value = "成本归属-统计维度", position = 2)
    private String type;

    /** 成本归属-部门-ID */
    @ApiModelProperty(value = "成本归属-部门-ID", position = 2)
    private String deptId;

    /** 成本归属-部门 */
    @ApiModelProperty(value = "成本归属-部门", position = 3)
    private String dept;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 4)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 5)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 6)
    private String userName;

    /** 职位|岗位 */
    @ApiModelProperty(value = "职位|岗位", position = 7)
    private String position;

    /** 入职日期 */
    @ApiModelProperty(value = "入职日期", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** 离职日期 */
    @ApiModelProperty(value = "离职日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    /** 【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID", position = 10)
    private String standardId;

    /** 【外键】工资封存表主键Id：HR_SALARY.ID */
    @ApiModelProperty(value = "【外键】工资封存表主键Id：HR_SALARY.ID", position = 11)
    private String salaryId;

    /** 月度发薪人数（当月在职天数/当月自然日天数） */
    @ApiModelProperty(value = "月度发薪人数（当月在职天数/当月自然日天数）", position = 12)
    private Double payNum;

    /** 工资|薪酬 */
    @ApiModelProperty(value = "工资|薪酬", position = 13)
    private BigDecimal gz;

    /** 社保 */
    @ApiModelProperty(value = "社保", position = 14)
    private BigDecimal ins;

    /** 公积金 */
    @ApiModelProperty(value = "公积金", position = 15)
    private BigDecimal gjj;

    /** 注册补贴 */
    @ApiModelProperty(value = "注册补贴", position = 16)
    private BigDecimal register;

    /** 员工福利 */
    @ApiModelProperty(value = "员工福利", position = 17)
    private BigDecimal welfare;

    /** 外业补贴 */
    @ApiModelProperty(value = "外业补贴", position = 18)
    private BigDecimal field;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 19)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 20)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 21)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 25)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 26)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 27)
    private String note;
}
