package vip.xiaonuo.oa.modular.attendance.rule.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.poi.ss.formula.functions.T;
import vip.xiaonuo.oa.modular.attendance.rule.entity.BaseRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;

/**
 * 考勤规则配置表Mapper接口
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
public interface RuleMapper<T extends BaseRule> extends BaseMapper<Rule<T>> {
}
