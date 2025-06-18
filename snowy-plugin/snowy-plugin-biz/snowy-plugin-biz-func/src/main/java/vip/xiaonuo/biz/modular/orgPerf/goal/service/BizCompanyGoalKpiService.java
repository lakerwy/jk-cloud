package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalKpiPageParam;

import java.util.List;

/**
 * 公司绩效考核-考核指标Service接口
 *
 * @author pzh
 * @date  2025/06/18 10:15
 **/
public interface BizCompanyGoalKpiService extends IService<BizCompanyGoalKpi> {

    /**
     * 获取公司绩效考核-考核指标分页
     *
     * @author pzh
     * @date  2025/06/18 10:15
     */
    Page<BizCompanyGoalKpi> page(BizCompanyGoalKpiPageParam bizCompanyGoalKpiPageParam);

    /**
     * 添加公司绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/18 10:15
     */
    void add(BizCompanyGoalKpiAddParam bizCompanyGoalKpiAddParam);

    /**
     * 编辑公司绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/18 10:15
     */
    void edit(BizCompanyGoalKpiEditParam bizCompanyGoalKpiEditParam);

    /**
     * 删除公司绩效考核-考核指标
     *
     * @author pzh
     * @date  2025/06/18 10:15
     */
    void delete(List<BizCompanyGoalKpiIdParam> bizCompanyGoalKpiIdParamList);

    /**
     * 获取公司绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/18 10:15
     */
    BizCompanyGoalKpi detail(BizCompanyGoalKpiIdParam bizCompanyGoalKpiIdParam);

    /**
     * 获取公司绩效考核-考核指标详情
     *
     * @author pzh
     * @date  2025/06/18 10:15
     **/
    BizCompanyGoalKpi queryEntity(String id);
}
