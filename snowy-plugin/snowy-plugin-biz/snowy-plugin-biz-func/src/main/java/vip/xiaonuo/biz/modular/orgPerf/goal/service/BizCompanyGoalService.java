package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizCompanyGoalKpi;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizCompanyGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.CompanySummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryDetailVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.CompanySummaryListVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.DataUpdateTimeVo;

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
    BizCompanyGoalVo detail(BizCompanyGoalIdParam bizCompanyGoalIdParam);

    /**
     * 获取公司绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/18 10:02
     **/
    BizCompanyGoal queryEntity(String id);

    /**
     * 查询指定目标登记详情
     *
     * @author pzh
     * @date 2025/06/18 14:24
     */
    BizCompanyGoalVo getByCondition(BizCompanyGoalParam bizCompanyGoalParam);

    /**
     * 更新公司绩效考核-目标登记
     *
     * @author pzh
     * @date 2025/06/18 14:24
     */
    void updateCompanyGoal(String goalId, List<BizCompanyGoalKpi> kpiList);

    /**
     * 获取公司绩效汇总列表
     *
     * @author pzh
     * @date  2025/06/19 15:55
     */
    List<CompanySummaryListVo> companySummaryList(CompanySummaryListParam param);

    /**
     * 获取公司绩效汇总详情
     *
     * @author pzh
     * @date  2025/06/20 16:33
     */
    List<CompanySummaryDetailVo> companySummaryDetail(CompanySummaryDetailParam param);

    /**
     * 公司绩效-数据更新时间查询
     *
     * @author pzh
     * @date 2025/07/03 15:10
     */
    DataUpdateTimeVo companyDataUpdateTime(CompanySummaryDetailParam param);
}
