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
 * 工资系统-各类补贴-明细记录实体
 *
 * @author yong.ye
 * @date  2024/02/26 09:37
 **/
@Getter
@Setter
@TableName("HR_SALARY_SUBSIDY_RECORD")
public class SalarySubsidyRecord {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 类别：值班补贴|讲师津贴|... */
    @ApiModelProperty(value = "类别：值班补贴|讲师津贴|...", position = 2)
    private String type;

    /** 【外键】发放工资员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资员工Id：HR_SALARY_STANDARD.ID", position = 3)
    private String standardId;

    /** 值班日期|课程日期|开工日期 */
    @ApiModelProperty(value = "值班日期|课程日期|开工日期", position = 13)
    private String btDate;

    /** 内部讲师级别|合同编号 */
    @ApiModelProperty(value = "内部讲师级别|合同编号", position = 14)
    private String btNo;

    /** 所讲课程名称|项目名称 */
    @ApiModelProperty(value = "所讲课程名称|项目名称", position = 15)
    private String btName;

    /** 参训人数（人）|项目所在地 */
    @ApiModelProperty(value = "参训人数（人）|项目所在地", position = 16)
    private String btContent;

    /** 评估分数（分） */
    @ApiModelProperty(value = "评估分数（分）", position = 17)
    private String btScore;

    /** 课时（精确到0.5小时）|外业天数 */
    @ApiModelProperty(value = "课时（精确到0.5小时）|外业天数", position = 18)
    private Float btNum;

    /** 竣工日期 */
    @ApiModelProperty(value = "竣工日期", position = 19)
    private String btFinishDate;

    /** 开始日期 */
    @ApiModelProperty(value = "开始日期", position = 20)
    private String btStartDate;

    /** 结束日期 */
    @ApiModelProperty(value = "结束日期", position = 21)
    private String btEndDate;

    /** 津贴标准（元/h）|伙食补贴标准 */
    @ApiModelProperty(value = "津贴标准（元/h）|伙食补贴标准", position = 22)
    private BigDecimal btStandard1;

    /** 伙食补贴小计 */
    @ApiModelProperty(value = "伙食补贴小计", position = 23)
    private BigDecimal btStandard1Total;

    /** 交通补贴标准 */
    @ApiModelProperty(value = "交通补贴标准", position = 24)
    private BigDecimal btStandard2;

    /** 交通补贴小计 */
    @ApiModelProperty(value = "交通补贴小计", position = 25)
    private BigDecimal btStandard2Total;

    /** 当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计 */
    @ApiModelProperty(value = "当月注册补贴标准|值班补助金额|讲师津贴总计|外业补贴合计", position = 26)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 27)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 28)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 29)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 30)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 32)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 34)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 36)
    private String extJson;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 100)
    private String note;
}
