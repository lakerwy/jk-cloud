package vip.xiaonuo.oa.modular.attendance.rule.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.AttObjectHandler;
import vip.xiaonuo.oa.modular.attendance.group.entity.AttObject;
import vip.xiaonuo.oa.modular.attendance.rule.entity.BaseRule;

import java.util.List;

/**
 * 考勤规则配置表添加参数
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
@Setter
public class RuleAddParam<T extends BaseRule> {

    /** 规则类型( 1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到) */
//    @ApiModelProperty(value = "规则类型(1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到)", position = 3)
//    private int ruleType;

    /** 规则名称 */
    @ApiModelProperty(value = "规则名称", position = 4)
    private String ruleName;

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    private List<T> ruleJson;

    @ApiModelProperty(value = "应用范围，存储为 JSON 字符串", position = 6)
    @TableField(value = "ATT_OBJECTS", typeHandler = AttObjectHandler.class)
    private List<AttObject> attObjects;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String remark;

}
