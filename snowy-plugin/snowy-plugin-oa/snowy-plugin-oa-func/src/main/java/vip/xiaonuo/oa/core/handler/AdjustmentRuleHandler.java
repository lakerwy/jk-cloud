package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.rule.entity.AdjustmentRule;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class AdjustmentRuleHandler extends CustomFastjsonTypeHandler<AdjustmentRule>{
    public AdjustmentRuleHandler(Class<AdjustmentRule> type) {
        super(AdjustmentRule.class);
    }
}
