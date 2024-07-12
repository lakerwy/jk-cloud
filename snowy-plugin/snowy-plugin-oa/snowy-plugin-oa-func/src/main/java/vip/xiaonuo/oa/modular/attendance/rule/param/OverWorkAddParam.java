package vip.xiaonuo.oa.modular.attendance.rule.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
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
public class OverWorkAddParam extends RuleAddParam<OverWorkRule> {

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    @TableField(value = "RULE_JSON", typeHandler = OverWorkRuleHandler.class)
    private List<OverWorkRule> ruleJson;

}
