package vip.xiaonuo.oa.modular.attendance.apply.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 考勤申请管理表实体
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Getter
@Setter
@TableName(value = "OA_ATTN_APPLY")
public class Apply<T extends ApplyJson> {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 3)
    private String sn;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 4)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 5)
    private String applyUserName;

    /** 申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到 */
    @ApiModelProperty(value = "申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到", position = 6)
    private int applyType;

    /** 申请开始时间 */
    @ApiModelProperty(value = "申请开始时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyStartTime;

    /** 申请结束时间 */
    @ApiModelProperty(value = "申请结束时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyEndTime;

    @ApiModelProperty(value = "申请内容JSON", position = 9)
    @TableField(value = "APPLY_JSON")
    @JsonIgnore
    private String applyJsonStr;

    /** 申请内容JSON */
    @ApiModelProperty(value = "申请内容JSON", position = 9)
    @TableField(exist = false)
    private T applyJson;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 10)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 11)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 12)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 13)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
