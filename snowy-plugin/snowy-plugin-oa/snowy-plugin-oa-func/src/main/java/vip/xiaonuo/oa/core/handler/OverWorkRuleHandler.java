package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWorkRule;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class OverWorkRuleHandler extends CustomFastjsonTypeHandler<OverWorkRule>{
    public OverWorkRuleHandler(Class<OverWorkRule> type) {
        super(OverWorkRule.class);
    }
}
