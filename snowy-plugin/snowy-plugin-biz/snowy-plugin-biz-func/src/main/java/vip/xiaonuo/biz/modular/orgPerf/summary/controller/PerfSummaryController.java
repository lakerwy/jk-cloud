package vip.xiaonuo.biz.modular.orgPerf.summary.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizCompanyGoalService;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalService;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalService;
import vip.xiaonuo.biz.modular.orgPerf.summary.param.*;
import vip.xiaonuo.biz.modular.orgPerf.summary.vo.*;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 组织绩效汇总控制器
 *
 * @author pzh
 * @date  2025/06/19 15:55
 */
@Api(tags = "组织绩效汇总控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class PerfSummaryController {

    @Resource
    private BizCompanyGoalService bizCompanyGoalService;

    @Resource
    private BizOrgGoalService bizOrgGoalService;

    @Resource
    private BizFunDeptGoalService bizFunDeptGoalService;

    /**
     * 绩效汇总列表
     *
     * @author pzh
     * @date  2025/06/19 15:55
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("公司绩效-汇总列表")
    // @SaCheckPermission("/biz/orgPerf/summary/company/list")
    @GetMapping("/biz/orgPerf/summary/company/list")
    public CommonResult<List<CompanySummaryListVo>> companySummaryList(CompanySummaryListParam param) {
        return CommonResult.data(bizCompanyGoalService.companySummaryList(param));
    }

    /**
     * 公司绩效汇总详情
     *
     * @author pzh
     * @date  2025/06/20 16:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("公司绩效-汇总详情")
    // @SaCheckPermission("/biz/orgPerf/summary/company/detail")
    @GetMapping("/biz/orgPerf/summary/company/detail")
    public CommonResult<List<CompanySummaryDetailVo>> companySummaryDetail(CompanySummaryDetailParam param) {
        return CommonResult.data(bizCompanyGoalService.companySummaryDetail(param));
    }


    /**
     * 子公司及生产部门绩效-汇总列表
     *
     * @author pzh
     * @date  2025/06/25 15:35
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("子公司及生产部门绩效-汇总列表")
    // @SaCheckPermission("/biz/orgPerf/summary/org/list")
    @GetMapping("/biz/orgPerf/summary/org/list")
    public CommonResult<List<OrgSummaryListVo>> orgSummaryList(@Valid OrgSummaryListParam param) {
        return CommonResult.data(bizOrgGoalService.orgSummaryList(param));
    }


    /**
     * 子公司及生产部门绩效-汇总详情
     *
     * @author pzh
     * @date  2025/06/26 09:00
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("子公司及生产部门绩效-汇总详情")
    // @SaCheckPermission("/biz/orgPerf/summary/org/detail")
    @GetMapping("/biz/orgPerf/summary/org/detail")
    public CommonResult<List<OrgSummaryDetailVo>> orgSummaryDetail(@Valid OrgSummaryDetailParam param) {
        return CommonResult.data(bizOrgGoalService.orgSummaryDetail(param));
    }


    /**
     * 职能部门绩效-汇总列表
     *
     * @author pzh
     * @date  2025/06/25 15:35
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("职能部门绩效-汇总列表")
    // @SaCheckPermission("/biz/orgPerf/summary/funDept/list")
    @GetMapping("/biz/orgPerf/summary/funDept/list")
    public CommonResult<List<FunDeptSummaryListVo>> funDeptList(@Valid FunDeptSummaryListParam param) {
        return CommonResult.data(bizFunDeptGoalService.funDeptList(param));
    }


    /**
     * 职能部门绩效-汇总详情
     *
     * @author pzh
     * @date  2025/06/25 15:35
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("子公司及生产部门绩效-汇总详情")
    // @SaCheckPermission("/biz/orgPerf/summary/funDept/detail")
    @GetMapping("/biz/orgPerf/summary/funDept/detail")
    public CommonResult<List<FunDeptSummaryDetailVo>> funDeptDetail(@Valid FunDeptDetailParam param) {
        return CommonResult.data(bizFunDeptGoalService.funDeptDetail(param));
    }

}
