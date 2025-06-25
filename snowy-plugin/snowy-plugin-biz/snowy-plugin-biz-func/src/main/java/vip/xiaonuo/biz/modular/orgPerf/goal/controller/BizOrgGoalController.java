package vip.xiaonuo.biz.modular.orgPerf.goal.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.*;
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizOrgGoalVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizOrgGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizOrgGoalService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 子公司及生产部门绩效考核-目标登记控制器
 *
 * @author pzh
 * @date  2025/06/24 16:33
 */
@Api(tags = "子公司及生产部门绩效考核-目标登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizOrgGoalController {

    @Resource
    private BizOrgGoalService bizOrgGoalService;

    /**
     * 获取子公司及生产部门绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取子公司及生产部门绩效考核-目标登记分页")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/page")
    @GetMapping("/biz/orgPerf/orgGoal/page")
    public CommonResult<Page<BizOrgGoal>> page(@Valid BizOrgGoalPageParam bizOrgGoalPageParam) {
        return CommonResult.data(bizOrgGoalService.page(bizOrgGoalPageParam));
    }

    /**
     * 添加子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加子公司及生产部门绩效考核-目标登记")
    @CommonLog("添加子公司及生产部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/add")
    @PostMapping("/biz/orgPerf/orgGoal/add")
    public CommonResult<String> add(@RequestBody @Valid BizOrgGoalAddParam bizOrgGoalAddParam) {
        bizOrgGoalService.add(bizOrgGoalAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑子公司及生产部门绩效考核-目标登记")
    @CommonLog("编辑子公司及生产部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/edit")
    @PostMapping("/biz/orgPerf/orgGoal/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizOrgGoalEditParam bizOrgGoalEditParam) {
        bizOrgGoalService.edit(bizOrgGoalEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除子公司及生产部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除子公司及生产部门绩效考核-目标登记")
    @CommonLog("删除子公司及生产部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/delete")
    @PostMapping("/biz/orgPerf/orgGoal/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizOrgGoalIdParam> bizOrgGoalIdParamList) {
        bizOrgGoalService.delete(bizOrgGoalIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取子公司及生产部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/24 16:33
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取子公司及生产部门绩效考核-目标登记详情")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/detail")
    @GetMapping("/biz/orgPerf/orgGoal/detail")
    public CommonResult<BizOrgGoalVo> detail(@Valid BizOrgGoalIdParam bizOrgGoalIdParam) {
        return CommonResult.data(bizOrgGoalService.detail(bizOrgGoalIdParam));
    }

    /**
     * 条件查询子公司及生产部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/25 10:01
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("条件查询子公司及生产部门绩效考核-目标登记详情")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/getByCondition")
    @GetMapping("/biz/orgPerf/orgGoal/getByCondition")
    public CommonResult<BizOrgGoalVo> getByCondition(@Valid BizOrgGoalParam bizOrgGoalParam) {
        return CommonResult.data(bizOrgGoalService.getByCondition(bizOrgGoalParam));
    }

    /**
     * 组织责任人变更
     *
     * @author pzh
     * @date  2025/06/25 10:01
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("组织责任人变更")
    @CommonLog("组织责任人变更")
    // @SaCheckPermission("/biz/orgPerf/orgGoal/editOwner")
    @PostMapping("/biz/orgPerf/orgGoal/editOwner")
    public CommonResult<String> editOwner(@RequestBody @Valid BizOrgGoalEditOwnerParam bizOrgGoalEditOwnerParam) {
        bizOrgGoalService.editOwner(bizOrgGoalEditOwnerParam);
        return CommonResult.ok();
    }

}
