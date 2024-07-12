package vip.xiaonuo.oa.modular.attendance.rule.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.handler.AdjustmentRuleHandler;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/11 15:59
 * 补卡规则
 */
@Getter
@Setter
@TableName(value = "OA_ATTN_RULE",autoResultMap = true)
public class Adjustment extends Rule<AdjustmentRule>{

    /** 规则JSON */
    @ApiModelProperty(value = "规则JSON", position = 5)
    @TableField(value = "RULE_JSON", typeHandler = AdjustmentRuleHandler.class)
    private List<AdjustmentRule> ruleJson;

}
