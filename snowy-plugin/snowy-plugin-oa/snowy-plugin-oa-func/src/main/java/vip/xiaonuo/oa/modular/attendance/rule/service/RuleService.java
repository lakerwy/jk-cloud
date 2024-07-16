package vip.xiaonuo.oa.modular.attendance.rule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.rule.entity.BaseRule;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Rule;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleAddParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleEditParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RuleIdParam;
import vip.xiaonuo.oa.modular.attendance.rule.param.RulePageParam;

import java.util.List;
import java.util.Map;

/**
 * 考勤规则配置表Service接口
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
public interface RuleService<T extends BaseRule> extends IService<Rule<T>> {

    /**
     * 获取考勤规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    Page<Rule<T>> page(RulePageParam rulePageParam,Class<T> clazz);

    /**
     * 添加考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void add(RuleAddParam ruleAddParam);

    /**
     * 编辑考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void edit(RuleEditParam ruleEditParam);

    /**
     * 删除考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void delete(List<RuleIdParam> ruleIdParamList);

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    Rule detail(RuleIdParam ruleIdParam, Class<T> clazz);

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     **/
    Rule queryEntity(String id);

    List<Map<String, String>> overWorkConfigList();

    List<Rule<T>> getOwnerRules(List<String> objectIds, int type);
}
