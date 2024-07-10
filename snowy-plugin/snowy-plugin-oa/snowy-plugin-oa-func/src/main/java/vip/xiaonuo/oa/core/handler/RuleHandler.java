package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.shift.entity.Rule;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class RuleHandler extends CustomFastjsonTypeHandler<Rule>{
    public RuleHandler(Class<Rule> type) {
        super(Rule.class);
    }
}
