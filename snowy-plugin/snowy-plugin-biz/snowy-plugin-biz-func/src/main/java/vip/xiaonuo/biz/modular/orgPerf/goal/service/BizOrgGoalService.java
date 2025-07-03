package vip.xiaonuo.biz.modular.orgPerf.goal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoalItem;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizOrgGoalVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryDetailParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.OrgSummaryListParam;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.DataUpdateTimeVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryDetailVo;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.OrgSummaryListVo;

import java.util.List;

/**
 * 子公司及生产部门绩效考核-目标登记Service接口
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
public interface BizOrgGoalService extends IService<BizOrgGoal> {

    /**
     * 获取子公司及生产部门绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    Page<BizOrgGoal> page(BizOrgGoalPageParam bizOrgGoalPageParam);

    /**
     * 添加子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void add(BizOrgGoalAddParam bizOrgGoalAddParam);

    /**
     * 编辑子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void edit(BizOrgGoalEditParam bizOrgGoalEditParam);

    /**
     * 删除子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    void delete(List<BizGoalIdParam> bizGoalIdParamList);

    /**
     * 获取子公司及生产部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date 2025/06/24 16:33
     */
    BizOrgGoalVo detail(BizGoalIdParam bizGoalIdParam);

    /**
     * 获取子公司及生产部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/24 16:33
     **/
    BizOrgGoal queryEntity(String id);

    /**
     * 条件查询子公司及生产部门绩效考核-目标登记详情
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
     * 更新子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date 2025/06/25 14:03
     */
    void updateOrgGoal(String goalId, List<BizOrgGoalItem> itemList);

    /**
     * 获取子公司及生产部门绩效汇总列表
     *
     * @author pzh
     * @date 2025/06/25 15:39
     */
    List<OrgSummaryListVo> orgSummaryList(OrgSummaryListParam param);

    /**
     * 获取子公司及生产部门绩效-汇总详情
     *
     * @author pzh
     * @date  2025/06/26 09:00
     */
    List<OrgSummaryDetailVo> orgSummaryDetail(OrgSummaryDetailParam param);

    /**
     * 子公司及生产部门绩效-数据更新时间查询
     *
     * @author pzh
     * @date  2025/07/03 15:10
     */
    DataUpdateTimeVo orgDataUpdateTime(OrgSummaryDetailParam param);
}
