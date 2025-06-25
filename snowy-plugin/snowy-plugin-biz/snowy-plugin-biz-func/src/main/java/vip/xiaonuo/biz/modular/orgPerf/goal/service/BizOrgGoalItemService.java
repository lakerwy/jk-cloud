package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizOrgGoalItemPageParam;

import java.util.List;

/**
 * 组织绩效考核-考核指标Service接口
 *
 * @author pzh
 * @date  2025/06/24 16:45
 **/
public interface BizOrgGoalItemService extends IService<BizOrgGoalItem> {

    /**
     * 获取组织绩效考核-考核指标分页
     *
     * @author pzh
     * @date  2025/06/24 16:45
     */
    Page<BizOrgGoalItem> page(BizOrgGoalItemPageParam bizOrgGoalItemPageParam);

    /**
     * 添加组织绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/24 16:45
     */
    void add(BizOrgGoalItemAddParam bizOrgGoalItemAddParam);

    /**
     * 编辑组织绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/24 16:45
     */
    void edit(BizOrgGoalItemEditParam bizOrgGoalItemEditParam);

    /**
     * 删除组织绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/24 16:45
     */
    void delete(List<BizOrgGoalItemIdParam> bizOrgGoalItemIdParamList);

    /**
     * 获取组织绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/24 16:45
     */
    BizOrgGoalItem detail(BizOrgGoalItemIdParam bizOrgGoalItemIdParam);

    /**
     * 获取组织绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/24 16:45
     **/
    BizOrgGoalItem queryEntity(String id);
}
