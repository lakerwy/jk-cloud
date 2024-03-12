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
 * 工资系统-月度考勤表实体
 *
 * @author yong.ye
 * @date  2024/03/08 10:31
 **/
@Getter
@Setter
@TableName("HR_SALARY_ATTEND")
public class SalaryAttend {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID */
    @ApiModelProperty(value = "【外键】发放工资的员工Id：HR_SALARY_STANDARD.ID", position = 2)
    private String standardId;

    /** 未打卡（上班） */
    @ApiModelProperty(value = "未打卡（上班）", position = 3)
    private Integer upMissNum;

    /** 未打卡（下班） */
    @ApiModelProperty(value = "未打卡（下班）", position = 4)
    private Integer downMissNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 5)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 6)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 7)
    private Integer lateNumC;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 8)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 9)
    private Float workDay;

    /** 矿工天数 */
    @ApiModelProperty(value = "矿工天数", position = 10)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 11)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 12)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 13)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 14)
    private Float funDay;

    /** 是否全勤 */
    @ApiModelProperty(value = "是否全勤", position = 15)
    private String isFull;

    /** 应勤天数 */
    @ApiModelProperty(value = "应勤天数", position = 16)
    private Float requiredDay;

    /** 实勤天数 */
    @ApiModelProperty(value = "实勤天数", position = 17)
    private Float realDay;

    /** 调休（h） */
    @ApiModelProperty(value = "调休（h）", position = 18)
    private Float adjustHour;

    /** 实扣天数-当月病假（一个月以上病假） */
    @ApiModelProperty(value = "实扣天数-当月病假（一个月以上病假）", position = 21)
    private Float realMonthSickDay;

    /** 实扣天数-病假（一个月以内） */
    @ApiModelProperty(value = "实扣天数-病假（一个月以内）", position = 22)
    private Float realUnMonthSickDay;

    /** 实扣天数-产假 */
    @ApiModelProperty(value = "实扣天数-产假", position = 23)
    private Float realMaterDay;

    /** 加班时长（h） */
    @ApiModelProperty(value = "加班时长（h）", position = 24)
    private Float overtimeHour;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 25)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 26)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 27)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 28)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 29)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 30)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 31)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 32)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 100)
    private String note;
}
