package vip.xiaonuo.oa.modular.attendance.rule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.rule.entity.Adjustment;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;

import java.util.List;

/**
 * 考勤规则配置表Service接口
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
public interface AdjustmentService extends IService<Adjustment> {

    /**
     * 获取考勤规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    Page<Adjustment> page(RulePageParam rulePageParam);

    /**
     * 添加考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void add(AdjustmentAddParam ruleAddParam);

    /**
     * 编辑考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void edit(AdjustmentEditParam ruleEditParam);

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
    Adjustment detail(RuleIdParam ruleIdParam);

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     **/
    Adjustment queryEntity(String id);
}
