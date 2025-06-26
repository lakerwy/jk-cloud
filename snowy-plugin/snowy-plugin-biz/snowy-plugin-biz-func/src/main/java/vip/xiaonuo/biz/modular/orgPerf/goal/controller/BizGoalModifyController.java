package vip.xiaonuo.biz.modular.orgPerf.goal.controller;

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
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.orgPerf.goal.entity.BizGoalModify;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyAddParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyEditParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizGoalModifyIdParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.param.BizCompanyGoalModifyPageParam;
import vip.xiaonuo.biz.modular.orgPerf.goal.service.BizGoalModifyService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 组织绩效考核-目标调整控制器
 *
 * @author pzh
 * @date  2025/06/18 14:24
 */
@Api(tags = "组织绩效考核-目标调整控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class BizGoalModifyController {

    @Resource
    private BizGoalModifyService bizGoalModifyService;

    /**
     * 获取组织绩效考核-目标调整分页
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取组织绩效考核-目标调整分页")
    // @SaCheckPermission("/biz/orgPerf/goal/modify/page")
    @GetMapping("/biz/orgPerf/goal/modify/page")
    public CommonResult<Page<BizGoalModify>> page(BizCompanyGoalModifyPageParam bizCompanyGoalModifyPageParam) {
        return CommonResult.data(bizGoalModifyService.page(bizCompanyGoalModifyPageParam));
    }

    /**
     * 添加组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加组织绩效考核-目标调整")
    @CommonLog("添加组织绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/goal/modify/add")
    @PostMapping("/biz/orgPerf/goal/modify/add")
    public CommonResult<String> add(@RequestBody @Valid BizGoalModifyAddParam bizGoalModifyAddParam) {
        bizGoalModifyService.add(bizGoalModifyAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑组织绩效考核-目标调整")
    @CommonLog("编辑组织绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/goal/modify/edit")
    @PostMapping("/biz/orgPerf/goal/modify/edit")
    public CommonResult<String> edit(@RequestBody @Valid BizGoalModifyEditParam bizGoalModifyEditParam) {
        bizGoalModifyService.edit(bizGoalModifyEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除组织绩效考核-目标调整
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除组织绩效考核-目标调整")
    @CommonLog("删除组织绩效考核-目标调整")
    // @SaCheckPermission("/biz/orgPerf/goal/modify/delete")
    @PostMapping("/biz/orgPerf/goal/modify/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<BizGoalModifyIdParam> bizGoalModifyIdParamList) {
        bizGoalModifyService.delete(bizGoalModifyIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取组织绩效考核-目标调整详情
     *
     * @author pzh
     * @date  2025/06/18 14:24
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取组织绩效考核-目标调整详情")
    // @SaCheckPermission("/biz/orgPerf/goal/modify/detail")
    @GetMapping("/biz/orgPerf/goal/modify/detail")
    public CommonResult<BizGoalModify> detail(@Valid BizGoalModifyIdParam bizGoalModifyIdParam) {
        return CommonResult.data(bizGoalModifyService.detail(bizGoalModifyIdParam));
    }
}
