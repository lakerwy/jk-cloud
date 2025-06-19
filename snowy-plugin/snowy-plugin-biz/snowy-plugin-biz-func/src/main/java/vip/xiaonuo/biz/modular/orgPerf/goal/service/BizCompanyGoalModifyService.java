package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;

import java.util.List;

/**
 * 公司绩效考核-目标调整Service接口
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
public interface BizCompanyGoalModifyService extends IService<BizCompanyGoalModify> {

    /**
     * 获取公司绩效考核-目标调整分页
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    Page<BizCompanyGoalModify> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam);

    /**
     * 添加公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void add(BizCompanyGoalModifyAddParam bizCompanyGoalModifyAddParam);

    /**
     * 编辑公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void edit(BizCompanyGoalModifyEditParam bizCompanyGoalModifyEditParam);

    /**
     * 删除公司绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    void delete(List<BizCompanyGoalModifyIdParam> bizCompanyGoalModifyIdParamList);

    /**
     * 获取公司绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    BizCompanyGoalModify detail(BizCompanyGoalModifyIdParam bizCompanyGoalModifyIdParam);

    /**
     * 获取公司绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     **/
    BizCompanyGoalModify queryEntity(String id);
}
