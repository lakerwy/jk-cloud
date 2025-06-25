package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizOrgGoalVo;

import java.util.List;

/**
 * 组织绩效考核-目标登记Service接口
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
public interface BizOrgGoalService extends IService<BizOrgGoal> {

    /**
     * 获取组织绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    Page<BizOrgGoal> page(BizOrgGoalPageParam bizOrgGoalPageParam);

    /**
     * 添加组织绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void add(BizOrgGoalAddParam bizOrgGoalAddParam);

    /**
     * 编辑组织绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void edit(BizOrgGoalEditParam bizOrgGoalEditParam);

    /**
     * 删除组织绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void delete(List<BizOrgGoalIdParam> bizOrgGoalIdParamList);

    /**
     * 获取组织绩效考核-目标登记详情
     *
     * @author pzh
     * @date 2025/06/24 16:33
     */
    BizOrgGoalVo detail(BizOrgGoalIdParam bizOrgGoalIdParam);

    /**
     * 获取组织绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/24 16:33
     **/
    BizOrgGoal queryEntity(String id);

    /**
     * 条件查询组织绩效考核-目标登记详情
     *
     * @author pzh
     * @date 2025/06/24 16:33
     */
    BizOrgGoalVo getByCondition(BizOrgGoalParam bizOrgGoalParam);

    /**
     * 组织责任人变更
     *
     * @author pzh
     * @date  2025/06/25 10:01
     */
    void editOwner(BizOrgGoalEditOwnerParam bizOrgGoalEditOwnerParam);

    /**
     * 更新组织绩效考核-目标登记
     *
     * @author pzh
     * @date 2025/06/25 14:03
     */
    void updateOrgGoal(String goalId, List<BizOrgGoalItem> itemList);
}
