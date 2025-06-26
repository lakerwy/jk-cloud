package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;

import java.util.List;

/**
 * 组织绩效考核-目标调整Service接口
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
public interface BizGoalModifyService extends IService<BizGoalModify> {

    /**
     * 获取组织绩效考核-目标调整分页
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    Page<BizGoalModify> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam);

    /**
     * 添加组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void add(BizGoalModifyAddParam bizGoalModifyAddParam);

    /**
     * 编辑组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void edit(BizGoalModifyEditParam bizGoalModifyEditParam);

    /**
     * 删除组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void delete(List<BizGoalModifyIdParam> bizGoalModifyIdParamList);

    /**
     * 获取组织绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    BizGoalModify detail(BizGoalModifyIdParam bizGoalModifyIdParam);

    /**
     * 获取组织绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     **/
    BizGoalModify queryEntity(String id);
}
