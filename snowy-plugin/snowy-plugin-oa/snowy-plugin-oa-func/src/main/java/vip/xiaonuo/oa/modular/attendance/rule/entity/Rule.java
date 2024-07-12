package vip.xiaonuo.oa.modular.attendance.rule.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.AttObjectHandler;
import vip.xiaonuo.oa.modular.attendance.group.entity.AttObject;

import java.util.Date;
import java.util.List;

/**
 * 考勤规则配置表实体
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
@Setter
@TableName(value = "OA_ATTN_RULE",autoResultMap = true)
public class Rule<T extends BaseRule> {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 规则类型( 1 补卡 2 请假 3 外出 4 出差 5 加班 6 签到) */
    @ApiModelProperty(value = "规则类型( 1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到)", position = 3)
    private int ruleType;

    /** 规则名称 */
    @ApiModelProperty(value = "规则名称", position = 4)
    private String ruleName;

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    @TableField(value = "RULE_JSON", typeHandler = FastjsonTypeHandler.class)
    private List<T> ruleJson;

    /** 应用范围，存储为 JSON 字符串 */
    @ApiModelProperty(value = "应用范围，存储为 JSON 字符串", position = 6)
    @TableField(value = "ATT_OBJECTS", typeHandler = AttObjectHandler.class)
    private List<AttObject> attObjects;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 7)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String remark;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人ID */
    @ApiModelProperty(value = "创建人ID", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

}
