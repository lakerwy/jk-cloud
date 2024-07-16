package vip.xiaonuo.oa.modular.attendance.rule.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.OverWorkRuleHandler;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;

import java.util.List;

/**
 * 考勤规则配置表添加参数
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
@Setter
public class OverWorkEditParam extends RuleEditParam<OverWorkRule> {

    /** 规则类型( 1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到) */
    @ApiModelProperty(value = "规则类型", position = 3)
    @Ignore
    private int ruleType = 3;

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    @TableField(value = "RULE_JSON", typeHandler = OverWorkRuleHandler.class)
    private List<OverWorkRule> ruleJson;

}
