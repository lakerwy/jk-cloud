package vip.xiaonuo.oa.modular.attendance.shift.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.RuleHandler;

import java.util.Date;
import java.util.List;

/**
 * 班次表，用于存储班次的基本信息实体
 *
 * @author mayike
 * @date  2024/06/11 09:32
 **/
@Getter
@Setter
@TableName(value = "OA_ATTN_SHIFT", autoResultMap = true)
public class Shift {

    /** 主键，唯一标识一个班次 */
    @TableId
    @ApiModelProperty(value = "主键，唯一标识一个班次", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 班次名称 */
    @ApiModelProperty(value = "班次名称", position = 3)
    private String shiftName;

    /** 备注信息 */
    @ApiModelProperty(value = "备注信息", position = 4)
    private String remark;

    /** 打卡次数 */
    @ApiModelProperty(value = "打卡次数(和clockInRules 的数量必须一直)", position = 6)
    private Integer checkNum;

    /** 存储班次上下班打卡时间详情的JSON字段 */
    @ApiModelProperty(value = "存储班次上下班打卡时间详情的JSON字段", position = 5)
    @TableField(value = "RULES", typeHandler = RuleHandler.class)
    private List<Rule> rules;

    /** 工作时长，以分钟为单位 */
    @ApiModelProperty(value = "工作时长，以分钟为单位", position = 6)
    private Integer workTime;

    /** 扩展信息，JSON格式存储 */
    @ApiModelProperty(value = "扩展信息，JSON格式存储", position = 10)
    private String extJson;

    /** 删除标志，逻辑删除 */
    @ApiModelProperty(value = "删除标志，逻辑删除", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "是否为默认班次", position = 16)
    private Boolean defaultFlag;

//    public long shiftWorkTime() {
//        long totalMinutes = 0;
//
//        if (clockInRules != null) {
//            for (ClockInRule clockInRule : clockInRules) {
//                LocalTime normalStart = clockInRule.getNormal();
//                LocalTime normalEnd = clockInRule.getNormalEnd();
//
//                if (normalStart != null && normalEnd != null) {
//                    Duration duration;
//                    if (normalEnd.isBefore(normalStart)) {
//                        // 处理跨天的情况
//                        duration = Duration.between(normalStart, LocalTime.MAX)
//                                .plus(Duration.between(LocalTime.MIN, normalEnd));
//                    } else {
//                        duration = Duration.between(normalStart, normalEnd);
//                    }
//                    totalMinutes += duration.toMinutes();
//                }
//            }
//        }
//
//        return totalMinutes;
//    }
}
