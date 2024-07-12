package vip.xiaonuo.oa.modular.attendance.rule.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.oa.modular.attendance.rule.entity.OverWork;
import vip.xiaonuo.oa.modular.attendance.rule.param.*;

import java.util.List;
import java.util.Map;

/**
 * 考勤规则配置表Service接口
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
public interface OverWorkService extends IService<OverWork> {

    /**
     * 获取考勤规则配置表分页
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    Page<OverWork> page(RulePageParam rulePageParam);

    /**
     * 添加考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void add(OverWorkAddParam ruleAddParam);

    /**
     * 编辑考勤规则配置表
     *
     * @author mayike
     * @date  2024/07/10 16:29
     */
    void edit(OverWorkEditParam ruleEditParam);

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
    OverWork detail(RuleIdParam ruleIdParam);

    /**
     * 获取考勤规则配置表详情
     *
     * @author mayike
     * @date  2024/07/10 16:29
     **/
    OverWork queryEntity(String id);

    List<Map<String, String>> configList();
}
