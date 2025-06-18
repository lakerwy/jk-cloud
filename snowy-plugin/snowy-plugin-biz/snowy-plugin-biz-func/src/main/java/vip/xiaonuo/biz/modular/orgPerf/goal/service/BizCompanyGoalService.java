package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalPageParam;

import java.util.List;

/**
 * 公司绩效考核-目标登记Service接口
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
public interface BizCompanyGoalService extends IService<BizCompanyGoal> {

    /**
     * 获取公司绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    Page<BizCompanyGoal> page(BizCompanyGoalPageParam bizCompanyGoalPageParam);

    /**
     * 添加公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    void add(BizCompanyGoalAddParam bizCompanyGoalAddParam);

    /**
     * 编辑公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    void edit(BizCompanyGoalEditParam bizCompanyGoalEditParam);

    /**
     * 删除公司绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    void delete(List<BizCompanyGoalIdParam> bizCompanyGoalIdParamList);

    /**
     * 获取公司绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/18 10:02
     */
    BizCompanyGoal detail(BizCompanyGoalIdParam bizCompanyGoalIdParam);

    /**
     * 获取公司绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/18 10:02
     **/
    BizCompanyGoal queryEntity(String id);
}
