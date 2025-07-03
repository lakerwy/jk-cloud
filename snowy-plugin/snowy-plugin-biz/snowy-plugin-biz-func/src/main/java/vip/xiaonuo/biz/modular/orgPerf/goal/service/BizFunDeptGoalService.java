package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizFunDeptGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.FunDeptSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryDetailVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.FunDeptSummaryListVo;

import java.util.List;

/**
 * 职能部门绩效考核-目标登记Service接口
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
public interface BizFunDeptGoalService extends IService<BizFunDeptGoal> {

    /**
     * 获取职能部门绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    Page<BizFunDeptGoal> page(BizFunDeptGoalPageParam bizFunDeptGoalPageParam);

    /**
     * 添加职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    void add(BizFunDeptGoalAddParam bizFunDeptGoalAddParam);

    /**
     * 编辑职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    void edit(BizFunDeptGoalEditParam bizFunDeptGoalEditParam);

    /**
     * 删除职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    void delete(List<BizFunDeptGoalIdParam> bizFunDeptGoalIdParamList);

    /**
     * 获取职能部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date 2025/06/26 16:25
     */
    BizFunDeptGoalVo detail(BizFunDeptGoalIdParam bizFunDeptGoalIdParam);

    /**
     * 获取职能部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/26 16:25
     **/
    BizFunDeptGoal queryEntity(String id);

    /**
     * 更新职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    void updateFunDeptGoal(String goalId, List<BizFunDeptGoalKpi> kpiList);

    /**
     * 职能部门绩效-汇总列表
     *
     * @author pzh
     * @date  2025/06/25 15:35
     */
    List<FunDeptSummaryListVo> funDeptList(FunDeptSummaryListParam param);

    /**
     * 职能部门绩效-汇总详情
     *
     * @author pzh
     * @date  2025/06/25 15:35
     */
    List<FunDeptSummaryDetailVo> funDeptDetail(FunDeptDetailParam param);

    /**
     * 职能部门目标-字段编辑
     *
     * @author pzh
     * @date  2025/06/30 16:02
     */
    void editPart(BizFunDeptGoalKpiEditPartParam bizFunDeptGoalEditParam);

    /**
     * 组织责任人变更
     *
     * @author pzh
     * @date  2025/06/30 16:02
     */
    void editOwner(BizFunDeptGoalEditOwnerParam bizOrgGoalEditOwnerParam);

    /**
     * 查询已有目标
     * 用于目标调整回显
     * @author pzh
     * @date  2025/06/30 16:02
     */
    BizFunDeptGoalVo getByCondition(BizFunDeptGoalParam bizFunDeptGoalParam);
}
