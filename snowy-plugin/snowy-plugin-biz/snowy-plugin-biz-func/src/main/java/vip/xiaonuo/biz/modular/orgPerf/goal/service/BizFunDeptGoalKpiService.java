package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizFunDeptGoalKpiPageParam;

import java.util.List;

/**
 * 职能部门绩效考核-考核指标Service接口
 *
 * @author pzh
 * @date  2025/06/26 16:36
 **/
public interface BizFunDeptGoalKpiService extends IService<BizFunDeptGoalKpi> {

    /**
     * 获取职能部门绩效考核-考核指标分页
     *
     * @author pzh
     * @date  2025/06/26 16:36
     */
    Page<BizFunDeptGoalKpi> page(BizFunDeptGoalKpiPageParam bizFunDeptGoalKpiPageParam);

    /**
     * 添加职能部门绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/26 16:36
     */
    void add(BizFunDeptGoalKpiAddParam bizFunDeptGoalKpiAddParam);

    /**
     * 编辑职能部门绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/26 16:36
     */
    void edit(BizFunDeptGoalKpiEditParam bizFunDeptGoalKpiEditParam);

    /**
     * 删除职能部门绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/26 16:36
     */
    void delete(List<BizFunDeptGoalKpiIdParam> bizFunDeptGoalKpiIdParamList);

    /**
     * 获取职能部门绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/26 16:36
     */
    BizFunDeptGoalKpi detail(BizFunDeptGoalKpiIdParam bizFunDeptGoalKpiIdParam);

    /**
     * 获取职能部门绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/26 16:36
     **/
    BizFunDeptGoalKpi queryEntity(String id);
}
