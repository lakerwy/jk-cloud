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
import vip.xiaonuo.biz.modular.orgPerf.goal.vo.BizFunDeptGoalVo;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizFunDeptGoal;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizFunDeptGoalService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 职能部门绩效考核-目标登记控制器
 *
 * @author pzh
 * @date  2025/06/26 16:25
 */
@Api(tags = "职能部门绩效考核-目标登记控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizFunDeptGoalController {

    @Resource
    private BizFunDeptGoalService bizFunDeptGoalService;

    /**
     * 获取职能部门绩效考核-目标登记分页
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取职能部门绩效考核-目标登记分页")
    // @SaCheckPermission("/biz/orgPerf/funDept/page")
    @GetMapping("/biz/orgPerf/funDept/page")
    public CommonResult<Page<BizFunDeptGoal>> page(BizFunDeptGoalPageParam bizFunDeptGoalPageParam) {
        return CommonResult.data(bizFunDeptGoalService.page(bizFunDeptGoalPageParam));
    }

    /**
     * 添加职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加职能部门绩效考核-目标登记")
    @CommonLog("添加职能部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/funDept/add")
    @PostMapping("/biz/orgPerf/funDept/add")
    public CommonResult<String> add(@RequestBody @Valid BizFunDeptGoalAddParam bizFunDeptGoalAddParam) {
        bizFunDeptGoalService.add(bizFunDeptGoalAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑职能部门绩效考核-目标登记")
    @CommonLog("编辑职能部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/funDept/edit")
    @PostMapping("/biz/orgPerf/funDept/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizFunDeptGoalEditParam bizFunDeptGoalEditParam) {
        bizFunDeptGoalService.edit(bizFunDeptGoalEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除职能部门绩效考核-目标登记
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除职能部门绩效考核-目标登记")
    @CommonLog("删除职能部门绩效考核-目标登记")
    // @SaCheckPermission("/biz/orgPerf/funDept/delete")
    @PostMapping("/biz/orgPerf/funDept/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizFunDeptGoalIdParam> bizFunDeptGoalIdParamList) {
        bizFunDeptGoalService.delete(bizFunDeptGoalIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取职能部门绩效考核-目标登记详情
     *
     * @author pzh
     * @date  2025/06/26 16:25
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取职能部门绩效考核-目标登记详情")
    // @SaCheckPermission("/biz/orgPerf/funDept/detail")
    @GetMapping("/biz/orgPerf/funDept/detail")
    public CommonResult<BizFunDeptGoalVo> detail(@Valid BizFunDeptGoalIdParam bizFunDeptGoalIdParam) {
        return CommonResult.data(bizFunDeptGoalService.detail(bizFunDeptGoalIdParam));
    }

    /**
     * 查询已有目标
     * 用于目标调整回显
     * @author pzh
     * @date  2025/06/30 16:02
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("查询已有目标")
    // @SaCheckPermission("/biz/orgPerf/funDept/getByCondition")
    @GetMapping("/biz/orgPerf/funDept/getByCondition")
    public CommonResult<BizFunDeptGoalVo> getByCondition(@Valid BizFunDeptGoalParam bizFunDeptGoalParam) {
        return CommonResult.data(bizFunDeptGoalService.getByCondition(bizFunDeptGoalParam));
    }

    /**
     * 组织责任人变更
     *
     * @author pzh
     * @date  2025/06/30 16:02
     */
    @ApiOperationSupport(order = 7)
    @ApiOperation("组织责任人变更")
    @CommonLog("组织责任人变更")
    // @SaCheckPermission("/biz/orgPerf/funDept/editOwner")
    @PostMapping("/biz/orgPerf/funDept/editOwner")
    public CommonResult<String> editOwner(@RequestBody @Valid BizFunDeptGoalEditOwnerParam bizOrgGoalEditOwnerParam) {
        bizFunDeptGoalService.editOwner(bizOrgGoalEditOwnerParam);
        return CommonResult.ok();
    }

    /**
     * 职能部门目标-字段编辑
     *
     * @author pzh
     * @date  2025/06/30 16:02
     */
    @ApiOperationSupport(order = 6)
    @ApiOperation("职能部门目标-字段编辑")
    @CommonLog("职能部门目标-字段编辑")
    // @SaCheckPermission("/biz/orgPerf/funDept/editPart")
    @PostMapping("/biz/orgPerf/funDept/editPart")
    public CommonResult<String> editPart(@RequestBody @Valid BizFunDeptGoalKpiEditPartParam bizFunDeptGoalEditParam) {
        bizFunDeptGoalService.editPart(bizFunDeptGoalEditParam);
        return CommonResult.ok();
    }
}
