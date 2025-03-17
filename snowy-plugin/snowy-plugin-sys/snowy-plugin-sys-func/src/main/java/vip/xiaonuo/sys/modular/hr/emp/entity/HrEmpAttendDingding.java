package vip.xiaonuo.sys.modular.hr.emp.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工月度考勤(钉钉)实体
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
@Getter
@Setter
@TableName("HR_EMP_ATTEND_DINGDING")
public class HrEmpAttendDingding {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 所属年月 */
    @ApiModelProperty(value = "所属年月", position = 2)
    @TableField("`YEAR_MONTH`")
    private String yearMonth;

    /** 员工类型：总部|分公司|外部 */
    @ApiModelProperty(value = "员工类型：总部|分公司|外部", position = 3)
    private String userType;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 4)
    private String idCardNumber;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 5)
    private String userName;

    /** 所属公司 */
    @ApiModelProperty(value = "所属公司", position = 6)
    private String company;

    /** 建科云用户Id */
    @ApiModelProperty(value = "建科云用户Id", position = 7)
    private String userId;

    /** 总部+分公司-花名册Id */
    @ApiModelProperty(value = "总部+分公司-花名册Id", position = 8)
    private String personnelId;

    /** 一级部门 */
    @ApiModelProperty(value = "一级部门", position = 9)
    private String firstOrgName;

    /** 二级部门 */
    @ApiModelProperty(value = "二级部门", position = 10)
    private String orgName;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 11)
    private String position;

    /** 未打卡（上班） */
    @ApiModelProperty(value = "未打卡（上班）", position = 12)
    private Integer upMissNum;

    /** 未打卡（下班） */
    @ApiModelProperty(value = "未打卡（下班）", position = 13)
    private Integer downMissNum;

    /** 实扣未打卡（上班） */
    @ApiModelProperty(value = "实扣未打卡（上班）", position = 14)
    private Integer realUpMissNum;

    /** 实扣未打卡（下班） */
    @ApiModelProperty(value = "实扣未打卡（下班）", position = 15)
    private Integer realDownMissNum;

    /** 迟到次数 */
    @ApiModelProperty(value = "迟到次数", position = 16)
    @TableField(exist = false)
    private Integer lateNum;

    /** 迟到次数 */
    @ApiModelProperty(value = "严重迟到次数", position = 16)
    @TableField(exist = false)
    private Integer extremelyLateNum;

    /** 迟到次数-A类 */
    @ApiModelProperty(value = "迟到次数-A类", position = 16)
    private Integer lateNumA;

    /** 迟到次数-B类 */
    @ApiModelProperty(value = "迟到次数-B类", position = 17)
    private Integer lateNumB;

    /** 迟到次数-C类 */
    @ApiModelProperty(value = "迟到次数-C类", position = 18)
    private Integer lateNumC;

    /** 迟到超过45分钟的次数 */
    @ApiModelProperty(value = "迟到超过45分钟的次数", position = 18)
    @TableField(exist = false)
    private Integer lateNumD;

    /** 出勤需要扣除的天数 */
    @ApiModelProperty(value = "出勤需要扣除的天数", position = 18)
    @TableField(exist = false)
    private Integer invalidDays;

    /** 病假天数 */
    @ApiModelProperty(value = "病假天数", position = 19)
    private Float sickDay;

    /** 事假天数 */
    @ApiModelProperty(value = "事假天数", position = 20)
    private Float workDay;

    /** 旷工天数 */
    @ApiModelProperty(value = "旷工天数", position = 21)
    private Float minerDay;

    /** 年假天数 */
    @ApiModelProperty(value = "年假天数", position = 22)
    private Float yearDay;

    /** 产假天数 */
    @ApiModelProperty(value = "产假天数", position = 23)
    private Float materDay;

    /** 婚假天数 */
    @ApiModelProperty(value = "婚假天数", position = 24)
    private Float marryDay;

    /** 丧假天数 */
    @ApiModelProperty(value = "丧假天数", position = 25)
    private Float funDay;

    /** 是否全勤 */
    @ApiModelProperty(value = "是否全勤", position = 26)
    private String isFull;

    /** 应计薪天数 */
    @ApiModelProperty(value = "应计薪天数", position = 27)
    private Float requiredDay;

    /** 实勤天数 */
    @ApiModelProperty(value = "实勤天数", position = 28)
    private Float realDay;

    /** 调休（h） */
    @ApiModelProperty(value = "调休（h）", position = 29)
    private Float adjustHour;

    /** 加班时长（h） */
    @ApiModelProperty(value = "加班时长（h）", position = 30)
    private Float overtimeHour;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 31)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 32)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 33)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 34)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 35)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 36)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 37)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 38)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 留痕备注 */
    @ApiModelProperty(value = "留痕备注", position = 39)
    private String note;

    /** 类型（高管|返聘） */
    @ApiModelProperty(value = "类型（高管|返聘）", position = 50)
    @TableField(exist = false)
    private String type;

    @ApiModelProperty(value = "实扣未打卡（上班）计算次数", position = 61)
    @TableField(exist = false)
    private Integer realUpMissNumExclude;

    @ApiModelProperty(value = "实扣未打卡（下班）计算次数", position = 62)
    @TableField(exist = false)
    private Integer realDownMissNumExclude;

}
