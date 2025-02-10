package vip.xiaonuo.sys.modular.hr.reg.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 注册补贴（新版）- 结果反馈实体
 *
 * @author yong.ye
 * @date  2025/01/15 09:08
 **/
@Getter
@Setter
@TableName("REG_SUBSIDY_RESULT")
public class RegSubsidyResult {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键：REG_SUBSIDY.ID */
    @ApiModelProperty(value = "外键：REG_SUBSIDY.ID", position = 2)
    private String regSubsidyId;

    /** 注册等级 */
    @ApiModelProperty(value = "注册等级", position = 3)
    private String level;

    /** 注册类别 */
    @ApiModelProperty(value = "注册类别", position = 4)
    private String type;

    /** 注册专业 */
    @ApiModelProperty(value = "注册专业", position = 5)
    private String major;

    /** 补贴标准 */
    @ApiModelProperty(value = "补贴标准", position = 6)
    private String standard;

    /** 系数 */
    @ApiModelProperty(value = "系数", position = 7)
    private String rate;

    /** 补贴金额 */
    @ApiModelProperty(value = "补贴金额", position = 8)
    private BigDecimal amount;

    /** 费用类型 */
    @ApiModelProperty(value = "费用类型", position = 9)
    private String feeType;

    /** 费用归属部门 */
    @ApiModelProperty(value = "费用归属部门", position = 10)
    private String belongType;

    /** 是否通过审核 */
    @ApiModelProperty(value = "是否通过审核", position = 11)
    private String isPass;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 申请人：花名册主键id */
    @ApiModelProperty(value = "申请人：花名册主键id", position = 13)
    private String personnelId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 14)
    private String name;

    /** 身份证号 */
    @ApiModelProperty(value = "身份证号", position = 15)
    private String idCardNumber;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 16)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 17)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 21)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** 注册成功日期 */
    @ApiModelProperty(value = "注册成功日期", position = 30)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
}
